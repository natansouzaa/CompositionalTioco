package gui.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFileChooser;

import br.edu.ufcg.symbolrt.base.TIOSTS;
import br.edu.ufcg.symbolrt.compiler.facade.Compiler;

public class ReaderSRT {
	
	private JFileChooser fileChooser;
	private HashMap<String, TIOSTS> mapTIOSTS = new HashMap<String, TIOSTS>();

	public ReaderSRT(JFileChooser fileChooser) {
		this.setFileChooser(fileChooser);
	}

	public JFileChooser getFileChooser() {
		return fileChooser;
	}

	public void setFileChooser(JFileChooser fileChooser) {
		this.fileChooser = fileChooser;
	}
	
	public HashMap<String, TIOSTS> main() throws IOException {
		String path = getFileChooser().getSelectedFile().getPath();
		if(!path.contains(".srt")) {
			path+=".srt";
		}
		System.out.println("COMPILOU");
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		String linha = "";
		while (linha != null) {
			int tamanho = linha.length();
			if(tamanho>7) {
				if(linha.substring(0, 6).equals("system")) {
					String name = linha.substring(7, tamanho-1);
					Compiler.compile(path, name);
				} else if (linha.substring(0, 7).equals("process")) {
					String name = linha.substring(8, tamanho-1);
					TIOSTS tiostsSpec = Compiler.getSpecification(name);
					mapTIOSTS.put(name, tiostsSpec);
				}
			} 
			linha = buffRead.readLine();
		}
		buffRead.close();
		return mapTIOSTS;
	}

}

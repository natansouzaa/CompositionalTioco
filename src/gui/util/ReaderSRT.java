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
		Compiler.compile(path, null);
		
//		TIOSTS tiostsSpec = Compiler.getSpecification(specName1);
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		String linha = "";
		while (true) {
			if (linha != null) {
				int tamanho = linha.length();
				if(tamanho>7) {
					if(linha.substring(0, 7).equals("process")) {
						String name = linha.substring(8, tamanho-1);
						TIOSTS tiostsSpec = Compiler.getSpecification(name);
						mapTIOSTS.put(name, tiostsSpec);
					}
				}
			} else
				break;
			linha = buffRead.readLine();
		}
		buffRead.close();
		return mapTIOSTS;
	}

}

package gui.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JFileChooser;

import com.mxgraph.model.mxCell;

import gui.types.MyGate;
import gui.types.MyTIOSTS;
import gui.types.MyTransition;
import gui.types.MyVariable;

public class GenerateSRT {
	private String systemName;
	private String systemDeclarations;
	private HashSet<MyGate> gate;
	private HashSet<String> clocks;
	private Collection<MyTIOSTS> process;
	private HashMap<MyTIOSTS, HashSet<String>> states;
	private HashMap<MyTIOSTS, HashSet<mxCell>> transitions;
	private HashMap<MyTIOSTS, HashSet<MyVariable>> variables;
	private JFileChooser fileChooser;

	public GenerateSRT(Collection<MyTIOSTS> collection, String processText, JFileChooser fileChooser, String systemName) {
		this.process = collection;
		this.systemDeclarations = processText;
		this.setFileChooser(fileChooser);
		this.systemName = systemName;	
	}
	
	public void getProperties(){	
		gate = new HashSet<MyGate>();
		clocks = new HashSet<String>();
		states = new HashMap<MyTIOSTS, HashSet<String>>();
		transitions = new HashMap<MyTIOSTS, HashSet<mxCell>>();
		variables = new HashMap<MyTIOSTS, HashSet<MyVariable>>();
		
		// states
		for(MyTIOSTS modelo : process) {
			states.put(modelo, new HashSet<String>());
			transitions.put(modelo, new HashSet<mxCell>());
			for(Object objeto : modelo.getAllCells()) {
				mxCell celula = (mxCell) objeto;
				if(celula.isVertex()) {
					states.get(modelo).add((String) celula.getValue());
				}else {
					transitions.get(modelo).add(celula);
				}
			}
		}
		
		// gates e clock
		for (String line : systemDeclarations.split("\n")) {				
			if(!line.equals("")) {
				int quantLinhas = line.length();
				if(quantLinhas>6) {
					if(line.substring(0, 6).equals("clock ")) {
						clocks.add(line.substring(6, quantLinhas-1));
					}
					if(line.substring(0, 5).equals("gate ")) {
						line = line.replace(" ", "");
						line = line.replace("	", "");
						line = line.substring(4, line.length()-1);	
						
						MyGate mygate;
						if(line.contains("(")) {
							line = line.replace("(", "/").replace(")", "");
							String[] separe = line.split("/");
							String name = separe[0];
							String tipo = separe[1];

							mygate = new MyGate(name, tipo);
						}else {
							mygate = new MyGate(line, "");
						}
						gate.add(mygate);
					}
				}
			}
		}
		
		// variables
		for(MyTIOSTS modelo : process) {
			String localDeclarations = modelo.getText().getText();
			variables.put(modelo, new HashSet<MyVariable>());
			
			for (String line : localDeclarations.split("\n")) {				
				if(!line.equals("")) {
					int tamanho = line.length();
					String[] variable = line.substring(0, tamanho-1).split(" ");
					if(variable.length == 2) {
						String b = variable[0];
						String c = variable[1];
						MyVariable novaVariavel = new MyVariable(c, b);
						variables.get(modelo).add(novaVariavel);
					}
				}
			}
		}
	}
	
	public void run(){
		getProperties();	
		FileWriter arq;
		try {
			if(getFileChooser().getSelectedFile().getPath().contains(".srt")) {
				arq = new FileWriter(getFileChooser().getSelectedFile());
			}else {
				arq = new FileWriter(getFileChooser().getSelectedFile() + ".srt");
			}
			PrintWriter gravarArq = new PrintWriter(arq);
			
			// system config
			gravarArq.printf("system " + getSystemName() +";%n");
			gravarArq.printf("%n");
			
			String clock = "	";			
			gravarArq.printf("clocks%n");
			if(clocks.size()>0) {
				for (String a : clocks) {
					clock+= a + ", ";	
				}
				clock = clock.substring(0, clock.length()-2) + ";";
			}else {
				clock+= "clock;";
			}
			gravarArq.printf(clock+"%n");
			gravarArq.printf("%n");
			
			//Gate
			gravarArq.printf("gate" + "%n");
			for (MyGate myGate : gate) {
				gravarArq.printf("	" + myGate + "%n");	
			}
			gravarArq.printf("%n");
			
			//process
			for(MyTIOSTS modelo : process) {
				gravarArq.printf("process " + modelo.getName() + ";%n");
				gravarArq.printf("%n");
				
				//input
				boolean control1 = false;
				boolean control2 = false;
				boolean control3 = false;
	
				HashSet<String> input = new HashSet<String>();
				HashSet<String> output = new HashSet<String>();
				HashSet<String> internal = new HashSet<String>();
				for (mxCell x : transitions.get(modelo)) {
					MyTransition s = (MyTransition) x.getValue();
					String dataTransition = s.getDataTransicao();
					for (MyGate g : gate) {
						if(dataTransition.contains(" " + g.getName())) {
							if(dataTransition.contains("?")) {
								control1 = true;
								input.add(g.getName());	
							}else if(dataTransition.contains("!")) {
								control2 = true;
								output.add(g.getName());	
							}else {
								control3 = true;
								internal.add(g.getName());
							}
						}
					}
				}
				if(control1) {
					String finalString= "";
					gravarArq.printf("input" + "%n");	
					for (String inputGate : input) {
						finalString += inputGate+ ", ";
					}
					gravarArq.printf("	" + finalString.substring(0, finalString.length()-2) + ";%n");	
					gravarArq.printf("%n");
				}
				
				//output
				if(control2) {
					String finalString= "";
					gravarArq.printf("output" + "%n");	
					for (String outputGate : output) {
						finalString += outputGate+ ", ";
					}
					gravarArq.printf("	" + finalString.substring(0, finalString.length()-2) + ";%n");	
					gravarArq.printf("%n");
				}
				
				//internal
				if(control3) {
					String finalString= "";
					gravarArq.printf("internal" + "%n");	
					for (String internalGate : internal) {
						finalString += internalGate+ ", ";
					}
					gravarArq.printf("	" + finalString.substring(0, finalString.length()-2) + ";%n");	
					gravarArq.printf("%n");
				}
				
				//variables
				if(variables.get(modelo).size()>0) {
					gravarArq.printf("variables%n");					//Receber 
					for (MyVariable s : variables.get(modelo)) {
						gravarArq.printf("	" + s.toString() + "%n");	
					}
					gravarArq.printf("%n");
				}
		
				// state
				gravarArq.printf("state%n");					//Receber 
				for (String s : states.get(modelo)) {
					if(modelo.getInit().getValue().equals(s)) {
						gravarArq.printf("	init: " + s + ";%n");						
					}else {
						gravarArq.printf("	" + s + ";%n");						
					}
				}
				gravarArq.printf("%n");
	
				//transitions								//Receber
				gravarArq.printf("transition%n");
				for (mxCell x : transitions.get(modelo)) {
					MyTransition s = (MyTransition) x.getValue();

					gravarArq.printf("	from " + x.getSource().getValue() + "%n");	//From
					if(!s.getDataGuarda().trim().equals("")) {
						gravarArq.printf("		" + s.getDataGuarda() + "%n");	//From
					}
					if(!s.getDataTransicao().trim().equals("")) {
						gravarArq.printf("		" + s.getDataTransicao() + "%n");	//From
					}
					if(!s.getDataAtribuicoes().trim().equals("")) {
						gravarArq.printf("		" + s.getDataAtribuicoes() + "%n");	//From
					}
					if(!s.getDeadLine().trim().equals("")) {
						gravarArq.printf("		deadline " + s.getDeadLine() + "%n");	//From
					}
					gravarArq.printf("	to " + x.getTarget().getValue() + ";%n");	//From
				}
				gravarArq.printf("%n");
			}

			arq.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private String getSystemName() {
		return this.systemName;
	}

	public String getSystemDeclarations() {
		return systemDeclarations;
	}

	public void setSystemDeclarations(String systemDeclarations) {
		this.systemDeclarations = systemDeclarations;
	}

	public JFileChooser getFileChooser() {
		return fileChooser;
	}

	public void setFileChooser(JFileChooser fileChooser) {
		this.fileChooser = fileChooser;
	}

	public HashSet<String> getClocks() {
		return clocks;
	}

	public void setClocks(HashSet<String> clocks) {
		this.clocks = clocks;
	}
}
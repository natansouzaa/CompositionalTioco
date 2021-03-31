package gui.types;

import java.io.Serializable;

public class MyTransition implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String dataGuarda;
	private String dataTransicao; 
	private String dataAtribuicoes;
	private String deadLine;

	public MyTransition() {

	}
		
	public MyTransition(String dataGuarda, String dataTransicao, String dataAtribuicoes, String deadLine) {
		this.dataGuarda = dataGuarda;
		this.dataTransicao = dataTransicao;
		this.dataAtribuicoes = dataAtribuicoes;
		this.deadLine = deadLine;
	}
		
	public String getDataGuarda() {
		return dataGuarda;
	}

	public void setDataGuarda(String dataGuarda) {
		this.dataGuarda = dataGuarda;
	}

	public String getDataTransicao() {
		return dataTransicao;
	}

	public void setDataTransicao(String dataTransicao) {
		this.dataTransicao = dataTransicao;
	}

	public String getDataAtribuicoes() {
		return dataAtribuicoes;
	}

	public void setDataAtribuicoes(String dataAtribuicoes) {
		this.dataAtribuicoes = dataAtribuicoes;
	}

	public String getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(String deadLine) {
		this.deadLine = deadLine;
	}	
	
	@Override
	public String toString() {
		return dataGuarda + "\n" + dataTransicao + "\n"	+ dataAtribuicoes + "\n" + deadLine;
	}
}
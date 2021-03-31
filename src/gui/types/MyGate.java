package gui.types;

public class MyGate {

	private String name;
	private String tipo;
		
	public MyGate() {

	}
		
	public MyGate(String name, String tipo) {
		this.name = name;
		this.tipo = tipo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return this.tipo;
	}

	public void setStatus(String tipo) {
		this.tipo = tipo;
	}
		
	@Override
	public String toString() {
		if(tipo.equals("")) {
			return this.name + ";";
		}
		return this.name + "(" + this.tipo +")" + ";";
	}
}
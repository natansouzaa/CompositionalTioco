package gui.types;

public class MyVariable {
	private String name;
	private String type;
	
	public MyVariable() {

	}
	
	public MyVariable(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public Object gettype() {
		return type;
	}

	public void settype(String type) {
		this.type = type;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name + " : " + this.type + ";";
	}
}
package gui.util;

public class SimpleNode {
	
    private String name;
    
    public SimpleNode() {

    }
    
    public SimpleNode(String name) {
        this.setName(name);
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
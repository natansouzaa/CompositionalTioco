package gui.util;

public class IconNode {
	
    private String name;
    private String role;
    
    public IconNode() {

    }
    
    public IconNode(String name, String role) {
        this.setName(name);
        this.setRole(role);
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return name;
	}

}
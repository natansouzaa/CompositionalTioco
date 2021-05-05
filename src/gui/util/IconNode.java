package gui.util;

import java.awt.Color;

public class IconNode {
	
    private String name;
    private String role;
    private Color color;
    
    public IconNode() {

    }
    
    public IconNode(String name, String role) {
        this.setName(name);
        this.setRole(role);
        this.setColor(null);
    }
    
    public IconNode(String name, String role, Color color) {
        this.setName(name);
        this.setRole(role);
        this.setColor(color);
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
package gui.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class TreeRenderer extends DefaultTreeCellRenderer {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private final ImageIcon defaultIcon;

    public TreeRenderer() {
    	defaultIcon = new ImageIcon(TreeRenderer.class.getResource("/gui/src/icons/max_sheet.png"));
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object userObject = node.getUserObject();
        if (userObject instanceof IconNode) {
            IconNode pp = (IconNode) userObject;
            if(pp.getColor()!=null) {
               this.setForeground(Color.RED);
            }
            this.setText(pp.getName());
            this.setIcon(new ImageIcon(TreeRenderer.class.getResource(pp.getRole())));
        } else if (userObject instanceof SimpleNode) {
            SimpleNode project = (SimpleNode) userObject;
            this.setText(project.getName());
            this.setIcon(defaultIcon);
        } else {
            this.setText((String) userObject);
            this.setIcon(defaultIcon);
        }
        return this;
    }
}
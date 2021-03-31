package gui.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.mxgraph.model.mxCell;

import gui.types.MyTIOSTS;

public class VertexInsert extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VertexInsert(MyTIOSTS tiosts, int x, int y) {
		setTitle("Add Vertex");
		setSize(450, 200);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Name:");
		label.setBounds(20, 20, 116, 15);
		contentPane.add(label);
		
		JTextField textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(20, 40, 410, 19);
		contentPane.add(textField);
		
		Object parent = tiosts.getGraph().getDefaultParent();

		JButton button1 = new JButton("Add");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Invalid name", "Warning" , JOptionPane.WARNING_MESSAGE);
				}else if(tiosts.getVertexByName(textField.getText())!=null) {
					JOptionPane.showMessageDialog(null, "The name must be unique", "Warning" , JOptionPane.WARNING_MESSAGE);
				}else {
					tiosts.getGraph().getModel().beginUpdate();
					try{
						Object v1 = tiosts.getGraph().insertVertex(parent, null, textField.getText(), x, y, 50, 50, "shape=ellipse;perimeter=ellipsePerimeter");	
						tiosts.getAllCells().add((mxCell) v1);
						dispose();
					}
					finally{
						tiosts.getGraph().getModel().endUpdate();
					}
				}
			}
		});
		button1.setBounds(325, 110, 100, 25);
		contentPane.add(button1);
		
		JButton button2 = new JButton("Cancel");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button2.setBounds(207, 110, 100, 25);
		contentPane.add(button2);
	}
}

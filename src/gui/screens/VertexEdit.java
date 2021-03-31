package gui.screens;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mxgraph.model.mxCell;

import gui.types.MyTIOSTS;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VertexEdit extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 * @param cell 
	 */
	public VertexEdit(MyTIOSTS tiosts, mxCell cell) {
		setTitle("Edit Vertex");
		setSize(450, 200);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String originalName = (String) cell.getValue();
		
		JLabel label = new JLabel("Name:");
		label.setBounds(20, 20, 116, 15);
		contentPane.add(label);
		
		JTextField textField = new JTextField();
		textField.setBounds(80, 20, 345, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText(originalName);

		JCheckBox checkBox = new JCheckBox("Initial");
		checkBox.setBounds(14, 52, 129, 23);
		if(tiosts.getInit() != null && tiosts.getInit().equals(cell)) {
			checkBox.setSelected(true);
		}
		contentPane.add(checkBox);

		JButton button = new JButton("Save");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!originalName.equals(textField.getText().trim()) && tiosts.getVertexByName(textField.getText())!=null) {
					JOptionPane.showMessageDialog(null, "Name unavailable", "Warning" , JOptionPane.WARNING_MESSAGE);
				}else if(!textField.getText().trim().equals("")) {
					tiosts.getGraph().getModel().setValue(cell, textField.getText().trim());
					if(checkBox.isSelected()) {
						if(tiosts.getInit() != null) {
							tiosts.getGraph().getModel().setStyle(tiosts.getInit(), "shape=ellipse;perimeter=ellipsePerimeter");
						}
						tiosts.getGraph().getModel().setStyle(cell, "shape=doubleEllipse;perimeter=ellipsePerimeter;fillColor=gray;");
						tiosts.setInit(cell);							
					}else {
						if(tiosts.getInit() != null && tiosts.getInit().equals(cell)) {
							tiosts.setInit(null);							
						}
						tiosts.getGraph().getModel().setStyle(cell, "shape=ellipse;perimeter=ellipsePerimeter");	
					}
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "Invalid name", "Warning" , JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		button.setBounds(325, 110, 100, 25);
		contentPane.add(button);
	}
}

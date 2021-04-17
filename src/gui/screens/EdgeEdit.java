package gui.screens;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mxgraph.model.mxCell;

import gui.types.MyTIOSTS;
import gui.types.MyTransition;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class EdgeEdit extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public EdgeEdit(MyTIOSTS tiosts, mxCell cell) {
		setTitle("Edit Edge");
		setBounds(100, 100, 450, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Object[] source = new Object[1];
		Object[] target = new Object[1];
		
		source[0]  = cell.getSource().getValue();
		target[0]  = cell.getTarget().getValue();

		JLabel lblNewLabel = new JLabel("Source:");
		lblNewLabel.setBounds(12, 12, 151, 24);
		contentPane.add(lblNewLabel);
		
		JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.setBounds(140, 12, 150, 24);
		contentPane.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel<Object>(source));
		
		JLabel lblNewLabel_1 = new JLabel("Target:");
		lblNewLabel_1.setBounds(12, 48, 151, 24);
		contentPane.add(lblNewLabel_1);
		
		JComboBox<Object> comboBox_1 = new JComboBox<Object>();
		comboBox_1.setBounds(140, 48, 150, 24);
		contentPane.add(comboBox_1);
		comboBox_1.setModel(new DefaultComboBoxModel<Object>(target));

		JLabel lblGuarda = new JLabel("Guard:");
		lblGuarda.setBounds(12, 84, 70, 15);
		contentPane.add(lblGuarda);
		
		JTextArea textArea = new JTextArea();
		contentPane.add(textArea);
		textArea.setTabSize(2);
		textArea.setLineWrap(true);
		textArea.setText(((MyTransition) cell.getValue()).getDataGuarda());
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(140, 80, 285, 40);
		contentPane.add(scrollPane);
		
		JLabel lblTransio = new JLabel("Sync:");
		lblTransio.setBounds(12, 128, 70, 15);
		contentPane.add(lblTransio);
		
		JTextField textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(140, 126, 285, 24);
		contentPane.add(textField_1);
		textField_1.setText(((MyTransition) cell.getValue()).getDataTransicao());

		JLabel lblAtribuio = new JLabel("do:");
		lblAtribuio.setBounds(12, 164, 100, 15);
		contentPane.add(lblAtribuio);
		
		JTextField textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(140, 162, 285, 24);
		contentPane.add(textField_2);
		textField_2.setText(((MyTransition) cell.getValue()).getDataAtribuicoes());
		
		JLabel lblDeadline = new JLabel("Deadline:");
		lblDeadline.setBounds(12, 200, 100, 15);
		contentPane.add(lblDeadline);
		
		JTextField textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(140, 198, 285, 24);
		contentPane.add(textField_3);
		textField_3.setText(((MyTransition) cell.getValue()).getDeadLine());
		
		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				tiosts.getGraph().getModel().beginUpdate();
				MyTransition novaTransicao = new MyTransition(textArea.getText(), textField_1.getText(), textField_2.getText(), textField_3.getText());
				try
				{
					tiosts.getGraph().getModel().setValue(cell, novaTransicao);
				}
				finally
				{
					tiosts.getGraph().getModel().endUpdate();
				}
				dispose();
			}
		});
		btnNewButton.setBounds(300, 250, 117, 25);
		contentPane.add(btnNewButton);
	}
}

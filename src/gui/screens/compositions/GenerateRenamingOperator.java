package gui.screens.compositions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.edu.ufcg.symbolrt.base.TIOSTS;
import br.edu.ufcg.symbolrt.compositions.RenamingOperator;
import br.edu.ufcg.symbolrt.compositions.exceptions.IncompatibleCompositionalOperationException;
import gui.screens.CompositionScreen;
import gui.util.DateTime;

public class GenerateRenamingOperator extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public GenerateRenamingOperator(HashMap<String, TIOSTS> collection, CompositionScreen compositionScreen) {
		setTitle("Renaming Operator");
		setBounds(100, 100, 450, 300);
		setVisible(true);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		int quant = collection.size();
		String[] items = new String[quant];

		int count = 0;
		for (String s : collection.keySet()) {
			items[count] = s;
			count++;
		}
		contentPane.setLayout(null);
		
		JLabel lblNewLabel1 = new JLabel("Select TIOSTS");
		lblNewLabel1.setBounds(30, 12, 125, 15);
		contentPane.add(lblNewLabel1);
		
		JComboBox<String> comboBox1 = new JComboBox<String>();
		comboBox1.setBounds(185, 12, 227, 24);
		contentPane.add(comboBox1);
		comboBox1.setModel(new DefaultComboBoxModel<String>(items));

		JLabel lblNewLabel2 = new JLabel("Current name");
		lblNewLabel2.setBounds(30, 62, 147, 15);
		contentPane.add(lblNewLabel2);
		
		JTextField textField1 = new JTextField();
		textField1.setColumns(10);
		textField1.setBounds(185, 58, 227, 24);
		contentPane.add(textField1);
		
		JLabel lblNewLabel3 = new JLabel("New name");
		lblNewLabel3.setBounds(30, 107, 147, 15);
		contentPane.add(lblNewLabel3);
		
		JTextField textField2 = new JTextField();
		textField2.setBounds(185, 105, 227, 24);
		contentPane.add(textField2);
		textField2.setColumns(10);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String itemSelected = (String) comboBox1.getSelectedItem();
				String currentName = textField1.getText();
				String newName  = textField2.getText();
				String tiostsName = "Rename " + itemSelected;

				TIOSTS model = collection.get(itemSelected);
				
				RenamingOperator renOperator = RenamingOperator.getInstance();
				
				try {
					model = renOperator.renamingOperator(model, currentName, newName);
				} catch (IncompatibleCompositionalOperationException e) {
					e.printStackTrace();
				} catch (Exception e) {
					compositionScreen.getTextEditor().setText(e.toString());
					dispose();
					return;
				}
				compositionScreen.addTIOSTS(tiostsName, model);
				compositionScreen.getTextEditor().setText("["+ DateTime.getCurrentTime() +"] " + "Renaming operator performed successfully");
				dispose();
			}
		});
		btnGenerate.setBounds(295, 206, 117, 25);
		contentPane.add(btnGenerate);
	}

}

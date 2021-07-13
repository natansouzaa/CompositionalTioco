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
import javax.swing.border.EmptyBorder;

import br.edu.ufcg.symbolrt.base.TIOSTS;
import br.edu.ufcg.symbolrt.compositions.InterruptionComposition;
import br.edu.ufcg.symbolrt.compositions.exceptions.IncompatibleCompositionalOperationException;
import gui.screens.CompositionScreen;
import gui.util.DateTime;

import javax.swing.JTextField;

public class GenerateInterruptionComposition extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public GenerateInterruptionComposition(HashMap<String, TIOSTS> collection, CompositionScreen compositionScreen) {
		setTitle("Generate Interruption Composition");
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

		JLabel lblNewLabel2 = new JLabel("Select TIOSTS");
		lblNewLabel2.setBounds(30, 62, 147, 15);
		contentPane.add(lblNewLabel2);
		
		JComboBox<String> comboBox2 = new JComboBox<String>();
		comboBox2.setBounds(185, 62, 227, 24);
		contentPane.add(comboBox2);
		comboBox2.setModel(new DefaultComboBoxModel<String>(items));
		
		JLabel lblNewLabel3 = new JLabel("Composition Name");
		lblNewLabel3.setBounds(30, 107, 147, 15);
		contentPane.add(lblNewLabel3);
		
		JTextField textField = new JTextField();
		textField.setBounds(185, 105, 227, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String itemSelected1 = (String) comboBox1.getSelectedItem();
				String itemSelected2 = (String) comboBox2.getSelectedItem();
				String name  = textField.getText();
				
				TIOSTS spec1 = collection.get(itemSelected1);
				TIOSTS spec2 = collection.get(itemSelected2);
				
				TIOSTS modelResult = null;	
				InterruptionComposition intComposition = InterruptionComposition.getInstance();
				
				try {
					modelResult = intComposition.interruptionComposition(spec1, spec2);
				} catch (IncompatibleCompositionalOperationException e) {
					e.printStackTrace();
				} catch (Exception e) {
					compositionScreen.getTextEditor().setText(e.toString());
					dispose();
					return;
				}
				compositionScreen.addTIOSTS(name, modelResult);
				compositionScreen.getTextEditor().setText("["+ DateTime.getCurrentTime() +"] " + "Interruption composition performed successfully");
				dispose();
			}
		});
		btnGenerate.setBounds(295, 206, 117, 25);
		contentPane.add(btnGenerate);
	}
	
}

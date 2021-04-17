package gui.screens.compositions;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.ufcg.symbolrt.base.TIOSTS;
import br.edu.ufcg.symbolrt.facade.SYMBOLRT;
import gui.screens.CompositionScreen;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.awt.Desktop;
import java.awt.event.ActionEvent;


public class GenerateTestCases extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public GenerateTestCases(HashMap<String, TIOSTS> collection, CompositionScreen compositionScreen) {
		setTitle("Generate TestCases");
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

		JLabel lblNewLabel = new JLabel("Select TIOSTS");
		lblNewLabel.setBounds(30, 12, 125, 15);
		contentPane.add(lblNewLabel);
		
		JComboBox<String> comboBox1 = new JComboBox<String>();
		comboBox1.setBounds(185, 12, 227, 24);
		contentPane.add(comboBox1);
		comboBox1.setModel(new DefaultComboBoxModel<String>(items));

		JLabel lblSelectTestpurpose = new JLabel("Select testPurpose");
		lblSelectTestpurpose.setBounds(30, 62, 147, 15);
		contentPane.add(lblSelectTestpurpose);
		
		JComboBox<String> comboBox2 = new JComboBox<String>();
		comboBox2.setBounds(185, 62, 227, 24);
		contentPane.add(comboBox2);
		comboBox2.setModel(new DefaultComboBoxModel<String>(items));

		JButton btnNewButton = new JButton("Generate");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String itemSelected1 = (String) comboBox1.getSelectedItem();
				String itemSelected2 = (String) comboBox2.getSelectedItem();
				TIOSTS spec1 = collection.get(itemSelected1);
				TIOSTS spec2 = collection.get(itemSelected2);
				
				long start = System.currentTimeMillis();

				SYMBOLRT symbolrt = SYMBOLRT.getInstance();
				List<TIOSTS> testCases = symbolrt.generateTestCases(spec1, spec2, true);
				symbolrt.show(testCases);

				long finish = System.currentTimeMillis();
				long result = finish - start;
				
				String output = testCases.size() + " test case(s) generated in " + result + " milliseconds.";
				compositionScreen.getTextEditor().setText(output);
				openDirectory();
				dispose();
			}
		});
		btnNewButton.setBounds(295, 209, 117, 25);
		contentPane.add(btnNewButton);
	}
	
	private void openDirectory() {
		Desktop desktop = Desktop.getDesktop();
        File dirToOpen = null;
        try {
        	dirToOpen = new File("./testcases/");
        	desktop.open(dirToOpen);
        } catch (IOException e) {
        	System.out.println("File Not Found");
        }
	}
}

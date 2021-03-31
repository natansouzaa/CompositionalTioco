package gui.screens;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.mxgraph.model.mxCell;

import gui.types.MyTIOSTS;
import gui.types.MyTransition;

public class EdgeInsert extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -934272115736416043L;

	/**
	 * Create the frame.
	 */
	public EdgeInsert(MyTIOSTS tiosts) {
		setTitle("Add Edge");
		setBounds(100, 100, 450, 330);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		int amountVertices = 0;
		for (mxCell cell : tiosts.getAllCells()) {
			if(cell.isVertex()) {
				amountVertices ++;
			}
		}
		
		String[] items = new String[amountVertices];
		
		int count = 0;
		for (mxCell s : tiosts.getAllCells()) {
			if(s.isVertex()) {
				items[count] = (String) s.getValue();
			    count ++;
			}
		}
		
		JLabel lblNewLabel = new JLabel("Source:");
		lblNewLabel.setBounds(12, 12, 151, 24);
		contentPane.add(lblNewLabel);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(140, 12, 150, 24);
		contentPane.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel<String>(items));
		
		JLabel lblNewLabel_1 = new JLabel("Target:");
		lblNewLabel_1.setBounds(12, 48, 151, 24);
		contentPane.add(lblNewLabel_1);
		
		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(140, 48, 150, 24);
		contentPane.add(comboBox_1);
		comboBox_1.setModel(new DefaultComboBoxModel<String>(items));

		JLabel lblGuarda = new JLabel("Guard:");
		lblGuarda.setBounds(12, 84, 70, 15);
		contentPane.add(lblGuarda);
		
		JTextArea textArea = new JTextArea();
		contentPane.add(textArea);
		textArea.setTabSize(2);
		textArea.setLineWrap(true);
		
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

		JLabel lblAtribuio = new JLabel("do:");
		lblAtribuio.setBounds(12, 164, 100, 15);
		contentPane.add(lblAtribuio);
		
		JTextField textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(140, 162, 285, 24);
		contentPane.add(textField_2);
		
		JLabel lblDeadline = new JLabel("Deadline:");
		lblDeadline.setBounds(12, 200, 100, 15);
		contentPane.add(lblDeadline);
		
		JTextField textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(140, 198, 285, 24);
		contentPane.add(textField_3);
		
		JButton btnNewButton = new JButton("Salvar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				mxCell v1 = tiosts.getVertexByName(comboBox.getSelectedItem().toString());
				mxCell v2 = tiosts.getVertexByName(comboBox_1.getSelectedItem().toString());
									
				tiosts.getGraph().getModel().beginUpdate();
				Object parent = tiosts.getGraph().getDefaultParent();
				MyTransition transicao = new MyTransition(textArea.getText(), textField_1.getText(), textField_2.getText(), textField_3.getText());
				try
				{
					Object v3 = tiosts.getGraph().insertEdge(parent, null, transicao, v1, v2);
					tiosts.getAllCells().add((mxCell) v3);
				}
				finally
				{
					tiosts.getGraph().getModel().endUpdate();
				}			
				dispose();
			}
		});
		btnNewButton.setBounds(300, 245, 117, 25);
		contentPane.add(btnNewButton);
	}
}

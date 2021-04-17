package gui.screens;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.App;

import javax.swing.JFileChooser;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Upload extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path;
	private boolean control;
	private JPanel contentPane;
	private JFileChooser fileChooser;
	
	/**
	 * Create the frame.
	 */
	public Upload() {
		setTitle("Upload SRT");
		setSize(450, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);		
		setVisible(true);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.control = false;
	
		JButton btnNoAperte = new JButton("Upload File");
		btnNoAperte.setBounds(24, 41, 399, 31);
		contentPane.add(btnNoAperte);
		btnNoAperte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null)) {
					setPath(chooser.getSelectedFile().getAbsolutePath());
					setControl(true);
					setFileChooser(chooser);
				}
			}
		});
		btnNoAperte.requestFocus();
		
		JButton btnEnviar = new JButton("Send");
		btnEnviar.setBounds(306, 126, 117, 25);
		contentPane.add(btnEnviar);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				App.main(null);
				dispose();
			}
		});
		btnCancel.setBounds(172, 126, 117, 25);
		contentPane.add(btnCancel);
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(control) {
					new CompositionScreen(getFileChooser());	
					dispose();
				}
			}
		});
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isControl() {
		return control;
	}

	public void setControl(boolean control) {
		this.control = control;
	}

	public JFileChooser getFileChooser() {
		return fileChooser;
	}

	public void setFileChooser(JFileChooser fileChooser) {
		this.fileChooser = fileChooser;
	}
}

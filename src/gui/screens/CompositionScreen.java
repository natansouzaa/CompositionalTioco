package gui.screens;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import br.edu.ufcg.symbolrt.base.TIOSTS;
import gui.screens.compositions.GenerateHidingOperator;
import gui.screens.compositions.GenerateInterruptionComposition;
import gui.screens.compositions.GenerateParallelComposition;
import gui.screens.compositions.GenerateRenamingOperator;
import gui.screens.compositions.GenerateSequentialComposition;
import gui.screens.compositions.GenerateTestCases;
import gui.util.IconNode;
import gui.util.ReaderSRT;
import gui.util.TreeRenderer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JTree;
import java.awt.FlowLayout;

public class CompositionScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTree tree;
	private JPanel contentPane;
	private JFileChooser fileChooser;
	private HashMap<String, TIOSTS> setTIOSTS;
	private HashMap<DefaultMutableTreeNode, TIOSTS> treeTIOSTS = new HashMap<DefaultMutableTreeNode, TIOSTS>();
	private JTextPane textEditor = new JTextPane();

	/**
	 * Create the frame.
	 */
	public CompositionScreen(JFileChooser chooser) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		
		// Carrega os TIOSTS
		setFileChooser(chooser);
		loadTIOSTS();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		//splitPane
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		//textEditor
		textEditor.setEditable(false);
		splitPane.setRightComponent(textEditor);

		//scrollPane
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);

		//tree
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new IconNode(fileChooser.getSelectedFile().getName(), "/gui/icons/tab.png"));
		tree = new JTree(root);
		tree.setCellRenderer(new TreeRenderer());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		for (String nome : setTIOSTS.keySet()) {
			inserNode(nome, setTIOSTS.get(nome));
		}
		scrollPane.setViewportView(tree);
		
		//tabbedPane
//		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
//		splitPane.setRightComponent(tabbedPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
				
		//toolBars
		JToolBar toolBar1 = new JToolBar();
		panel.add(toolBar1);
				
		JToolBar toolBar2 = new JToolBar();
		panel.add(toolBar2);
		
		JToolBar toolBar3 = new JToolBar();
		panel.add(toolBar3);
		
		//buttons
		JButton button1 = new JButton("");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateTestCases(setTIOSTS,  CompositionScreen.this);
			}
		});
		toolBar3.add(button1);
		button1.setIcon(new ImageIcon(CompositionScreen.class.getResource("/gui/icons/a-24.png")));
		button1.setToolTipText("Generate Tests");
		
		JButton button2 = new JButton("");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateSequentialComposition(setTIOSTS,  CompositionScreen.this);
			}
		});
		toolBar1.add(button2);
		button2.setIcon(new ImageIcon(CompositionScreen.class.getResource("/gui/icons/b-24.png")));
		button2.setToolTipText("Sequential Composition");

		JButton button3 = new JButton("");
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateParallelComposition(setTIOSTS,  CompositionScreen.this);
			}
		});
		toolBar1.add(button3);
		button3.setIcon(new ImageIcon(CompositionScreen.class.getResource("/gui/icons/c-24.png")));
		button3.setToolTipText("Parallel Composition");

		JButton button4 = new JButton("");
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateInterruptionComposition(setTIOSTS,  CompositionScreen.this);
			}
		});
		toolBar1.add(button4);
		button4.setIcon(new ImageIcon(CompositionScreen.class.getResource("/gui/icons/d-24.png")));
		button4.setToolTipText("Interruption Composition");

		JButton button5 = new JButton("");
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateHidingOperator(setTIOSTS,  CompositionScreen.this);
			}
		});
		toolBar2.add(button5);
		button5.setIcon(new ImageIcon(CompositionScreen.class.getResource("/gui/icons/e-24.png")));
		button5.setToolTipText("Hiding Operator");
		
		JButton button6 = new JButton("");
		button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateRenamingOperator(setTIOSTS,  CompositionScreen.this);
			}
		});
		toolBar2.add(button6);
		button6.setIcon(new ImageIcon(CompositionScreen.class.getResource("/gui/icons/f-24.png")));
		button6.setToolTipText("Renaming Operator");
	}
	
	public void inserNode(String name, TIOSTS tiosts) {
		IconNode node1 = new IconNode(name, "/gui/icons/graph.png");
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

		DefaultMutableTreeNode schema = new DefaultMutableTreeNode(node1);
		root.add(schema);
		
		model.reload(root);
		treeTIOSTS.put(schema, tiosts);
	}
	
	public void loadTIOSTS() {
		HashMap<String, TIOSTS> novo = new HashMap<String, TIOSTS>();
		try {
			novo = new ReaderSRT(getFileChooser()).main();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(novo.size()>0) {
			setSetTIOSTS(novo);			
		}
	}
	
	public void addTIOSTS(String name, TIOSTS novoTIOSTS) {
		HashMap<String, TIOSTS> novo = getSetTIOSTS();
		novo.put(name, novoTIOSTS);
		setSetTIOSTS(novo);
		inserNode(name, novoTIOSTS);
	}
	
	public HashMap<DefaultMutableTreeNode, TIOSTS> getTreeTIOSTS() {
		return treeTIOSTS;
	}

	public void setTreeTIOSTS(HashMap<DefaultMutableTreeNode, TIOSTS> treeTIOSTS) {
		this.treeTIOSTS = treeTIOSTS;
	}

	public HashMap<String, TIOSTS> getSetTIOSTS() {
		return setTIOSTS;
	}

	public void setSetTIOSTS(HashMap<String, TIOSTS> setTIOSTS) {
		this.setTIOSTS = setTIOSTS;
	}

	public JFileChooser getFileChooser() {
		return fileChooser;
	}

	public void setFileChooser(JFileChooser chooser) {
		this.fileChooser = chooser;
	}

	public JTextPane getTextEditor() {
		return textEditor;
	}

	public void setTextEditor(JTextPane textEditor) {
		this.textEditor = textEditor;
	}
}

package gui.screens;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
import java.awt.Dimension;

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
		scrollPane.setMinimumSize(new Dimension(250, 22));
		splitPane.setLeftComponent(scrollPane);

		//tree
		String fileName = fileChooser.getSelectedFile().getName();
		if (fileName.contains(".srt")) {
			fileName = fileName.substring(0, fileName.length()-4);
		}
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new IconNode(fileName, "/gui/src/icons/max_folder.png"));
		tree = new JTree(root);
		tree.setCellRenderer(new TreeRenderer());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		for (String nome : setTIOSTS.keySet()) {
			inserNode(nome, setTIOSTS.get(nome), null);
		}
		scrollPane.setViewportView(tree);
		
		//menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
				
		JMenu menu1 = new JMenu("File");
		menuBar.add(menu1);
		
		JMenu menu2 = new JMenu("Edit");
		menuBar.add(menu2);
		
		JMenu menu3 = new JMenu("Help");
		menuBar.add(menu3);
		
		JMenuItem item1Menu1 = new JMenuItem("Open new editor");
		item1Menu1.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/max_newProject.png")));
		item1Menu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new GraphEditor();
			}
		});
		menu1.add(item1Menu1);
		
		JMenuItem item2Menu1 = new JMenuItem("Import new SRT");
		item2Menu1.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/max_newProject.png")));
		item2Menu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new Upload();
			}
		});
		menu1.add(item2Menu1);
		
		JMenuItem item4Menu1 = new JMenuItem("Exit");
		item4Menu1.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/max_save.png")));
		item4Menu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		menu1.add(item4Menu1);
		
		JMenu item1Menu2 = new JMenu("Compositions");
//		item1Menu2.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/icons/handCursor.png")));
		item1Menu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//
			}
		});
		menu2.add(item1Menu2);
		
		JMenu item2Menu2 = new JMenu("Operators");
//		item2Menu2.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/icons/handCursor.png")));
		item2Menu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//
			}
		});
		menu2.add(item2Menu2);
		
		JMenuItem item1item1Menu2 = new JMenuItem("Sequential Composition");
		item1item1Menu2.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/SequentialComposition.png")));
		item1item1Menu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateSequentialComposition(setTIOSTS,  CompositionScreen.this);
			}
		});
		item1Menu2.add(item1item1Menu2);
		
		JMenuItem item2item1Menu2 = new JMenuItem("Parallel Composition");
		item2item1Menu2.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/ParallelComposition.png")));
		item2item1Menu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateParallelComposition(setTIOSTS,  CompositionScreen.this);
			}
		});
		item1Menu2.add(item2item1Menu2);
		
		JMenuItem item3item1Menu2 = new JMenuItem("Interruption Composition");
		item3item1Menu2.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/InterruptionComposition.png")));
		item3item1Menu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateInterruptionComposition(setTIOSTS,  CompositionScreen.this);
			}
		});
		item1Menu2.add(item3item1Menu2);
		
		JMenuItem item1item2Menu2 = new JMenuItem("Renaming Operator");
		item1item2Menu2.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/RenamingOperator.png")));
		item1item2Menu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateRenamingOperator(setTIOSTS,  CompositionScreen.this);
			}
		});
		item2Menu2.add(item1item2Menu2);
		
		JMenuItem item2item2Menu2 = new JMenuItem("Hiding Operator");
		item2item2Menu2.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/HidingOperator.png")));
		item2item2Menu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateHidingOperator(setTIOSTS,  CompositionScreen.this);
			}
		});
		item2Menu2.add(item2item2Menu2);
		
		JMenuItem item3Menu1 = new JMenuItem("Generate Tests");
		item3Menu1.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/GenerateTestCases.png")));
		item3Menu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateTestCases(setTIOSTS,  CompositionScreen.this);
			}
		});
		menu2.add(item3Menu1);
		
		JMenuItem item9Menu2 = new JMenuItem("Remove TIOSTS");
		item9Menu2.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/remove.png")));
		item9Menu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int cont = tree.getSelectionModel().getLeadSelectionRow();
				DefaultTreeModel model1 = (DefaultTreeModel) tree.getModel();
				DefaultMutableTreeNode root1 = (DefaultMutableTreeNode) model1.getRoot();
				if(cont<0) {
					JOptionPane.showMessageDialog(null, "Select a TIOSTS to remove.", "Warning" , JOptionPane.WARNING_MESSAGE);
				}else if (cont==0){
					JOptionPane.showMessageDialog(null, "Only removal of child nodes is allowed.", "Warning" , JOptionPane.WARNING_MESSAGE);
				} else {
					DefaultMutableTreeNode ab = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					if (((IconNode) ab.getUserObject()).getColor()==null) {
						JOptionPane.showMessageDialog(null, "Dont remove original tiosts.", "Warning" , JOptionPane.WARNING_MESSAGE);
					}else {
						if(treeTIOSTS.containsKey(ab)) {
							TIOSTS y = treeTIOSTS.get(ab);
							System.out.print(y.getName());
							String x = ab.toString();
							setTIOSTS.remove(x);
							model1.removeNodeFromParent(ab);
						}
						model1.reload(root1);		
					}
				}
			}
		});
		menu2.add(item9Menu2);

		JMenuItem item1Menu3 = new JMenuItem("Help");
		item1Menu3.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/Help.png")));
		item1Menu3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Help();
			}
		});
		menu3.add(item1Menu3);
		//
		
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
		button1.setIcon(new ImageIcon(CompositionScreen.class.getResource("/gui/src/icons/GenerateTestCases.png")));
		button1.setToolTipText("Generate Tests");
		
		JButton button2 = new JButton("");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateSequentialComposition(setTIOSTS,  CompositionScreen.this);
			}
		});
		toolBar1.add(button2);
		button2.setIcon(new ImageIcon(CompositionScreen.class.getResource("/gui/src/icons/SequentialComposition.png")));
		button2.setToolTipText("Sequential Composition");

		JButton button3 = new JButton("");
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateParallelComposition(setTIOSTS,  CompositionScreen.this);
			}
		});
		toolBar1.add(button3);
		button3.setIcon(new ImageIcon(CompositionScreen.class.getResource("/gui/src/icons/ParallelComposition.png")));
		button3.setToolTipText("Parallel Composition");

		JButton button4 = new JButton("");
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateInterruptionComposition(setTIOSTS,  CompositionScreen.this);
			}
		});
		toolBar1.add(button4);
		button4.setIcon(new ImageIcon(CompositionScreen.class.getResource("/gui/src/icons/InterruptionComposition.png")));
		button4.setToolTipText("Interruption Composition");

		JButton button5 = new JButton("");
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateHidingOperator(setTIOSTS,  CompositionScreen.this);
			}
		});
		toolBar2.add(button5);
		button5.setIcon(new ImageIcon(CompositionScreen.class.getResource("/gui/src/icons/HidingOperator.png")));
		button5.setToolTipText("Hiding Operator");
		
		JButton button6 = new JButton("");
		button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GenerateRenamingOperator(setTIOSTS,  CompositionScreen.this);
			}
		});
		toolBar2.add(button6);
		button6.setIcon(new ImageIcon(CompositionScreen.class.getResource("/gui/src/icons/RenamingOperator.png")));
		button6.setToolTipText("Renaming Operator");
	}
	
	public void inserNode(String name, TIOSTS tiosts, Color color) {
		IconNode node1;
		if(color!=null) {
			node1 = new IconNode(name, "/gui/src/icons/TIOSTS.png", color);			
		}else {
			node1 = new IconNode(name, "/gui/src/icons/TIOSTS.png");			
		}
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
		inserNode(name, novoTIOSTS, new Color(13, 57 ,115));
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

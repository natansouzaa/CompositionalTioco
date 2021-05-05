package gui.screens;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

import javax.swing.tree.TreeSelectionModel;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;

import gui.examples.CellPhoneApp;
import gui.types.MyTIOSTS;
import gui.util.IconNode;
import gui.util.GenerateSRT;
import gui.util.SimpleNode;
import gui.util.TreeRenderer;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GraphEditor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTree tree;
	private JPanel contentPane;
	private JButton[] buttonArray;
	private JTextPane textEditor = new JTextPane();
	
	private JPanel panel_1;

	private String systemName = "";
	private MyTIOSTS selected;
	private HashMap<DefaultMutableTreeNode, MyTIOSTS> treeTIOSTS = new HashMap<DefaultMutableTreeNode, MyTIOSTS>();
	
	
	public JPanel getPanel_1() {
		return panel_1;
	}

	public void setPanel_1(JPanel panel_1) {
		this.panel_1 = panel_1;
	}

	public void repaintTree() {
		tree.repaint();
		tree.revalidate();
	}
	
	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	public HashMap<DefaultMutableTreeNode, MyTIOSTS> getTreeTIOSTS() {
		return treeTIOSTS;
	}

	public void setTreeTIOSTS(HashMap<DefaultMutableTreeNode, MyTIOSTS> treeTIOSTS) {
		this.treeTIOSTS = treeTIOSTS;
	}

	public MyTIOSTS getSelected() {
		return selected;
	}

	public void setSelected(MyTIOSTS selected) {
		this.selected = selected;
	}
	
	public JEditorPane getProcessText() {
		return textEditor;
	}

	public void setProcessText(JTextPane textEditor) {
		this.textEditor = textEditor;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String name) {
		this.systemName = name;
	}
	/**
	 * Create the frame.
	 */
	public GraphEditor() {
		setVisible(true);
		setSize(800, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		// splitPane
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		scrollPane_1.setViewportView(panel_1);
		
		// tree
		setSystemName("TIOSTS");
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new IconNode(getSystemName(), "/gui/src/icons/max_folder.png"));
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(new SimpleNode("System Declarations"));

		root.add(node);
		
        tree = new JTree(root);
        tree.setCellRenderer(new TreeRenderer());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		MyTIOSTS firstTIOSTS = new MyTIOSTS("TIOSTS");
		setSelected(firstTIOSTS);
		
		insertNode(null, firstTIOSTS);
		scrollPane.setViewportView(tree);

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				int cont = tree.getSelectionModel().getLeadSelectionRow();
				panel_1.removeAll();
				panel_1.repaint();
				panel_1.revalidate();
				statusButton(false);
				if(cont == 0) {
					renderGraph(panel_1, null);	
				}else if(cont == 1) {
					panel_1.add(textEditor, BorderLayout.CENTER);
					panel_1.repaint();
					panel_1.revalidate();
				}else if(cont>1){
					DefaultMutableTreeNode ab = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					DefaultMutableTreeNode parentAab = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).getParent();
					
					if(treeTIOSTS.containsKey(ab)) {
						selected = treeTIOSTS.get(ab);
						renderGraph(panel_1, selected);
					}
					else if(treeTIOSTS.containsKey(parentAab)){
						panel_1.add(treeTIOSTS.get(parentAab).getText(), BorderLayout.CENTER);
						panel_1.repaint();
						panel_1.revalidate();
					}
				}
				addMouseEvent(selected);
			}
		});
		
		// menuBar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
				
		JMenu menu1 = new JMenu("File");
		menuBar.add(menu1);
			
		JMenu menu3 = new JMenu("Edit");
		menuBar.add(menu3);
		
		JMenu menu2 = new JMenu("Examples");
		menuBar.add(menu2);
		
		JMenu menu4 = new JMenu("Help");
		menuBar.add(menu4);
		
		JMenuItem item3Menu1 = new JMenuItem("New Project");
		item3Menu1.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/max_newProject.png")));
		item3Menu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GraphEditor();
				dispose();
			}
		});
		menu1.add(item3Menu1);
		
		JMenuItem item1Menu1 = new JMenuItem("Add TIOSTS");
		item1Menu1.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/max_add.png")));
		item1Menu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertNode(null, null);
			}
		});
		menu3.add(item1Menu1);
		
		JMenuItem item2Menu1 = new JMenuItem("Remove TIOSTS");
		item2Menu1.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/remove.png")));
		item2Menu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int cont = tree.getSelectionModel().getLeadSelectionRow();
				DefaultTreeModel model1 = (DefaultTreeModel) getTree().getModel();
				DefaultMutableTreeNode root1 = (DefaultMutableTreeNode) model1.getRoot();

				if(cont<0) {
					JOptionPane.showMessageDialog(null, "Select a TIOSTS to remove.", "Warning" , JOptionPane.WARNING_MESSAGE);
				}else if (cont<=1){
					JOptionPane.showMessageDialog(null, "Only removal of child nodes is allowed.", "Warning" , JOptionPane.WARNING_MESSAGE);
				} else {
					DefaultMutableTreeNode ab = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					DefaultMutableTreeNode parentAab = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).getParent();
					if(treeTIOSTS.containsKey(ab)) {
						treeTIOSTS.remove(ab);
						model1.removeNodeFromParent(ab);
					}
					else if(treeTIOSTS.containsKey(parentAab)){
						JOptionPane.showMessageDialog(null, "Cannot remove local declarations.", "Warning" , JOptionPane.WARNING_MESSAGE);
					}
					model1.reload(root1);		
				}
			}
		});
		menu3.add(item2Menu1);
		
		JMenuItem item5Menu1 = new JMenuItem("Export TIOSTS");
		item5Menu1.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/max_save.png")));
		item5Menu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showSaveFileDialog();
			}
		});
		menu1.add(item5Menu1);
		
		JMenuItem item1Menu2 = new JMenuItem("CellPhoneApp");
		item1Menu2.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/Help.png")));
		item1Menu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new CellPhoneApp(GraphEditor.this).main();
			}
		});
		menu2.add(item1Menu2);
		
		JMenuItem item1Menu4 = new JMenuItem("Help");
		item1Menu4.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/Help.png")));
		item1Menu4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//
			}
		});
		menu4.add(item1Menu4);
		
		JMenuItem item4Menu1 = new JMenuItem("Exit");
		item4Menu1.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/max_exit.png")));
		item4Menu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		menu1.add(item4Menu1);
		
		// buttons
		buttonArray = new JButton[4];
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
				
		JToolBar toolBar = new JToolBar();
		panel.add(toolBar, BorderLayout.WEST);

		buttonArray[0] = new JButton("");
		buttonArray[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertVertex();
			}
		});
		toolBar.add(buttonArray[0]);
		buttonArray[0].setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/vertex.png")));
			
		buttonArray[1] = new JButton("");
		buttonArray[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EdgeInsert a = new EdgeInsert(selected);
				a.setVisible(true);
			}
		});
		toolBar.add(buttonArray[1]);
		buttonArray[1].setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/Edge.png")));
		
		buttonArray[2] = new JButton("");
		buttonArray[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showSaveFileDialog();
			}
		});
		toolBar.add(buttonArray[2]);
		buttonArray[2].setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/max_save.png")));
		
		buttonArray[3] = new JButton("");
		buttonArray[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mxHierarchicalLayout layout = new mxHierarchicalLayout(selected.getGraph());
			    layout.execute(selected.getGraph().getDefaultParent());
			}
		});
		toolBar.add(buttonArray[3]);
		buttonArray[3].setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/Organize.png")));
		statusButton(false);
		addKeysEvent();
		
		JToolBar toolBar2 = new JToolBar();
		panel.add(toolBar2, BorderLayout.CENTER);
		
		JButton runComposition = new JButton("");
		runComposition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[] options = { "Confirmar", "Cancelar" };
				int x = JOptionPane.showOptionDialog(null, "Clique Confirmar para continuar", "Executar", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				if(x==0) {
					JFileChooser fileChooser = showSaveFileDialog();
					if(fileChooser!=null) {
						new CompositionScreen(fileChooser);		
						dispose();
					}
				}
			}
		});
		toolBar2.add(runComposition);
		runComposition.setIcon(new ImageIcon(GraphEditor.class.getResource("/gui/src/icons/GenerateTestCases.png")));
		runComposition.setToolTipText("Play");
	}	

	private void addMouseEvent(MyTIOSTS tiosts) {
		tiosts.getGraphComponent().getGraphControl().addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				if(e.getClickCount()==2 && !e.isConsumed()){
					e.consume();
					Object cell = tiosts.getGraphComponent().getCellAt(e.getX(), e.getY());
					if (cell != null){
						if(((mxCell) cell).isVertex()) {
							VertexEdit newFrame = new VertexEdit(tiosts, (mxCell) cell);
							newFrame.setVisible(true);							
						}else {
							EdgeEdit newFrame = new EdgeEdit(tiosts, (mxCell) cell);
							newFrame.setVisible(true);							
						}
					}
		        }
			}
		});
	}
		
	private void addKeysEvent() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		        if(e.getKeyCode()==127 && !e.isConsumed()) {
		        	e.consume();
		        	Object[] selectionCells = selected.getGraph().getSelectionCells();
		        	selected.getGraph().removeCells();
		        	for(Object selectionCell : selectionCells) {
		        		selected.getAllCells().remove((mxCell) selectionCell);
		        	}
		        }
		        return false;
		      }
		});
	}
	
	public void insertNode(String name, MyTIOSTS newTIOSTS) {
		if(name == null) {
			name = "new TIOSTS";
		}
		IconNode node1 = new IconNode(name, "/gui/src/icons/TIOSTS.png");
		SimpleNode node2 = new SimpleNode("Local Declarations");
		
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		
		DefaultMutableTreeNode schema = new DefaultMutableTreeNode(node1);
		root.add(schema);
        
		DefaultMutableTreeNode sheet = new DefaultMutableTreeNode(node2);
		schema.add(sheet);
		
		model.reload(root);		
		if(newTIOSTS==null) {
			MyTIOSTS novo = new MyTIOSTS(name);
			treeTIOSTS.put(schema, novo);
		}else {
			treeTIOSTS.put(schema, newTIOSTS);
		}
	}
	
	public void statusButton(Boolean value) {
		for(JButton button : buttonArray) {
			button.setEnabled(value);
		}
	}

	public void insertVertex() {
		selected.getGraph().getModel().beginUpdate();
		try{
			selected.getGraphComponent().getGraphControl().addMouseListener((MouseListener) new MouseAdapter() {
		    	public void mouseReleased(MouseEvent e){
		    		VertexInsert inserir = new VertexInsert(selected, e.getX(), e.getY());
		    		inserir.setVisible(true);
		    		selected.getGraphComponent().getGraphControl().removeMouseListener((MouseListener) this);
				}
		    });
		}finally{
			selected.getGraph().getModel().endUpdate();
		}
	}
	
	public void renderGraph(JPanel panel, MyTIOSTS novoTIOSTS) {
		JPanel panel_123 = new JPanel();
		panel_123.setLayout(new BorderLayout(0, 0));
		
		JTextField textField = new JTextField();
		panel_123.add(textField, BorderLayout.CENTER);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Name: ");
		panel_123.add(lblNewLabel, BorderLayout.WEST);
		
		JButton btnNewButton = new JButton("Save");
		panel_123.add(btnNewButton, BorderLayout.EAST);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField.getText().trim().equals("")) {
					String newName = textField.getText().replace(" ", "").replace("	", "");
					if(novoTIOSTS!=null) {
						novoTIOSTS.setName(newName);
					}
					DefaultMutableTreeNode a =  (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
					IconNode b = (IconNode) a.getUserObject();
					b.setName(newName);
					if(a.isRoot()) {
						setSystemName(newName);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Invalid name", "Warning" , JOptionPane.WARNING_MESSAGE);
				}
				tree.repaint();
				tree.revalidate();
				textField.setText("");
			}
		});
		
		panel.add(panel_123, BorderLayout.NORTH);
		if(novoTIOSTS==null) {
			panel.add(new JPanel(), BorderLayout.CENTER);			
		}else {
			panel.add(novoTIOSTS.getGraphComponent(), BorderLayout.CENTER);
			statusButton(true);
		}
		panel.repaint();
		panel.revalidate();
	}
	
	private JFileChooser showSaveFileDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");
		
		//filter
		FileFilter filter = new FileNameExtensionFilter("SRT files (*.srt)", "srt");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(filter);
		
		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			GenerateSRT createSRT = new GenerateSRT(treeTIOSTS.values(), textEditor.getText(), fileChooser, getSystemName());
			createSRT.run();
			return fileChooser;
		}
		return null;
	}
}
package gui.screens;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import gui.util.IconNode;
import gui.util.SimpleNode;
import gui.util.TreeRenderer;

import javax.swing.JTree;
import java.awt.Dimension;

public class Help extends JFrame {
	
	private JTree tree;
	private JPanel contentPane;
	private JTextPane textEditor;	
	private HashMap<DefaultMutableTreeNode, String> map = new HashMap<DefaultMutableTreeNode, String>();

	/**
	 * Create the frame.
	 */
	public Help() {
		setSize(800, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		//splitPane
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		//scrollPane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setMinimumSize(new Dimension(250, 22));
		splitPane.setLeftComponent(scrollPane);

		//tree
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new IconNode("Compositional tioco", "/gui/src/icons/max_folder.png"));
		tree = new JTree(root);
		tree.setCellRenderer(new TreeRenderer());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		//textEditor
		textEditor = new JTextPane();
		textEditor.setContentType("text/html");
		textEditor.setEditable(false);
		splitPane.setRightComponent(textEditor);
		
		//
		String text = "<html>"
				+ "<style>p{text-align: justify}</style>"
				+ "<h1>TIOSTS</h1>"
					+ "<p>To express specifications and to represent implementations under test, we use Timed Input Output Symbolic Transitions Systems "
					+ "(TIOSTS). These models store symbolic data and clock variables, avoiding the state space and region explosion problems.</p>"
					+ "<p>Formally, a TIOSTS is a tuple 〈 V, P, Θ, L, l0, Σ, C, T 〉, where:</p>"
					+ "<ul>"
						+ "<li>V is a finite set of typed variables;</li>"
						+ "<li>P is a finite set of action parameters;</li>"
						+ "<li>Θ is the initial condition, a predicate with variables in V;</li>"
						+ "<li>L is a finite, non-empty set of locations;</li>"
						+ "<li>l0 ∈ L is the initial location;</li>"
						+ "<li>Σ = Σ? ∪ Σ! is a finite, non-empty alphabet, where Σ? is a finite set of input actions and Σ! is a finite set of output actions;</li>"
						+ "<li>C is a finite set of clocks;</li>"
						+ "<li>T is a finite set of transitions.</li>"
					+ "</ul>"
				+ "</html>";
		insertNode("TIOSTS", text, "/gui/src/icons/TIOSTS.png", null);
		//
		DefaultMutableTreeNode nodeCompositions = insertNode("Compositions", "", "/gui/src/icons/max_folder.png", null);
		
		String sequentialComposition = ""
				+ "<html>"
					+ "<style>p{text-align: justify}</style>"
					+ "<h1>Sequential Composition</h1>"
					+ "<p>Sequential composition is applied when the interaction between two subsystems must be ordered and the first subsystem"
					+ "finishes before the second starts. We used the message passing paradigm to define this operator in a way that information is communicated"
					+ "from one subsystem to another through a single action which is present in both subsystems.</p>"
				+ "</html>";

		insertNode("Sequential Composition", sequentialComposition, "/gui/src/icons/SequentialComposition.png", nodeCompositions);
		//
		String interruptionComposition = ""
				+ "<html>"
					+ "<style>p{text-align: justify}</style>"
					+ "<h1>Interruption Composition</h1>"
					+ "<p>When composing subsystems by interruptions, one subsystem may interrupt the execution of another when shared resources"
					+ "are instantly required. For example, consider a phone call that arrives when the user is editing a document on a smartphone. This is an"
					+ "application level interruption and the effect is that the call subsystem is brought forward, sending the edition subsystem to the background.</p>"
				+ "</html>";
		insertNode("Interruption Composition", interruptionComposition, "/gui/src/icons/InterruptionComposition.png", nodeCompositions);
		//
		String parallelComposition = ""
				+ "<html>"
					+ "<style>p{text-align: justify}</style>"
					+ "<h1>Parallel Composition</h1>"
					+ "<p>This operator can be applied to define a system that is composed by two communicating subsystems, without shared memory, that may"
					+ "either execute independently or may communicate to each other by using messages, defined as parameterized shared actions with the same label, but"
					+ "opposing input or output types. Also, during the synchronization process, an input action can be communicated to a single output action and vice-versa.</p>"
				+ "</html>";
		insertNode("Parallel Composition", parallelComposition, "/gui/src/icons/ParallelComposition.png", nodeCompositions);
		//
		DefaultMutableTreeNode nodeOperators = insertNode("Operators", "", "/gui/src/icons/max_folder.png", null);

		String hidingOperator = ""
				+ "<html>"
					+ "<style>p{text-align: justify}</style>"
					+ "<h1>Hiding Operator</h1>"
					+ "<p>The hiding operator replaces each action from a predefined set by the τ action. A consequence from this composition is that hidden actions"
					+ "cannot be seen by subsystems which are outside the composition.</p>"
				+ "</html>";
		insertNode("Hiding Operator", hidingOperator, "/gui/src/icons/HidingOperator.png", nodeOperators);
		//
		String renamingOperator = ""
				+ "<html>"
				+ "<style>p{text-align: justify}</style>"
				+ "<h1>Renaming Operator</h1>"
				+ "<p>This operator is responsible for replacing a set of actions by others.</p>"
				+ "</html>";
		insertNode("Renaming Operator", renamingOperator, "/gui/src/icons/RenamingOperator.png", nodeOperators);
		//
		scrollPane.setViewportView(tree);

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				int cont = tree.getSelectionModel().getLeadSelectionRow();
				if(cont <= 0) {
					textEditor.setText("<html><style>p{text-align: left}</style><p>For more information access: <a href=\"\">https://sites.google.com/site/compositionaltioco/home/</a></p></html>");
				}else{
					textEditor.setText(map.get(tree.getLastSelectedPathComponent()));
				}
			}
		});
	}
	
	private DefaultMutableTreeNode insertNode(String name, String text, String iconPath, DefaultMutableTreeNode node) {
		DefaultMutableTreeNode sheet;
		if(iconPath==null) {
			SimpleNode newNode = new SimpleNode(name);		
			sheet = new DefaultMutableTreeNode(newNode);
		}else {
			IconNode newNode = new IconNode(name, iconPath);
			sheet = new DefaultMutableTreeNode(newNode);
		}
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		 
		if(node==null) {
			root.add(sheet);
		}else {
			node.add(sheet);
		}
		
		map.put(sheet, text);
		model.reload(root);		
		return sheet;
	}
}
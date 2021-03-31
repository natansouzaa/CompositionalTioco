package gui.types;

import java.awt.BorderLayout;
import java.util.HashSet;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class MyTIOSTS extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private mxGraphComponent graphComponent;
	private mxGraph graph = new mxGraph();
	
	private String name;
	private mxCell init;
	private JEditorPane text = new JEditorPane();
	private HashSet<mxCell> allCells = new HashSet<mxCell>();
	
	public MyTIOSTS(String name) {
		this.setName(name);
		setBounds(100, 100, 450, 300);
		graph.setCellsEditable(false);
		graph.setAllowDanglingEdges(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		graphComponent = new mxGraphComponent(graph);
		contentPane.add(graphComponent);
	}
	
	public mxCell getVertexByName(String name) {
		for (mxCell cell : allCells) {
			if(cell.isVertex()) {
				if(cell.getValue().equals(name)) {
					return cell;
				}
			}
		}
		return null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public mxCell getInit() {
		return init;
	}

	public void setInit(mxCell init) {
		this.init = init;
	}

	public mxGraphComponent getGraphComponent() {
		return graphComponent;
	}

	public void setGraphComponent(mxGraphComponent graphComponent) {
		this.graphComponent = graphComponent;
	}

	public mxGraph getGraph() {
		return graph;
	}

	public void setGraph(mxGraph graph) {
		this.graph = graph;
	}

	public JEditorPane getText() {
		return text;
	}

	public void setText(JEditorPane text) {
		this.text = text;
	}

	public HashSet<mxCell> getAllCells() {
		return allCells;
	}

	public void setAllCells(HashSet<mxCell> allCells) {
		this.allCells = allCells;
	}
}

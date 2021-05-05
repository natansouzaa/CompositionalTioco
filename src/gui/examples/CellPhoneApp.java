package gui.examples;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.mxgraph.model.mxCell;

import gui.screens.GraphEditor;
import gui.types.MyTIOSTS;
import gui.types.MyTransition;

public class CellPhoneApp {
	
	private GraphEditor graphEditor;
	
	public CellPhoneApp(GraphEditor graphEditor) {
		this.setGraphEditor(graphEditor);
	}

	public GraphEditor getGraphEditor() {
		return graphEditor;
	}

	public void setGraphEditor(GraphEditor graphEditor) {
		this.graphEditor = graphEditor;
	}
	
	public void main() {
		DefaultTreeModel model = (DefaultTreeModel) getGraphEditor().getTree().getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		
		for(DefaultMutableTreeNode a : getGraphEditor().getTreeTIOSTS().keySet()) {
			model.removeNodeFromParent(a);
		}
		getGraphEditor().getTreeTIOSTS().clear();
		model.reload(root);		
		
		getGraphEditor().insertNode("ContactsApp", null);
		getGraphEditor().insertNode("MessageApp", null);
		getGraphEditor().insertNode("ReceiveCallApp", null);
		getGraphEditor().insertNode("CellPhoneAppTP6", null);

		getGraphEditor().setSystemName("CellPhoneApp");
		t0();
		
		for(DefaultMutableTreeNode a : getGraphEditor().getTreeTIOSTS().keySet()) {
			switch (a.toString()) {
				case "ContactsApp":
					t1(getGraphEditor().getTreeTIOSTS().get(a));
					break;
				case "MessageApp":
					t2(getGraphEditor().getTreeTIOSTS().get(a));
					break;
				case "ReceiveCallApp":
					t3(getGraphEditor().getTreeTIOSTS().get(a));
					break;
				case "CellPhoneAppTP6":
					t4(getGraphEditor().getTreeTIOSTS().get(a));
					break;
			}		
		}
	}
	
	public void t0() {
		String clocks = "clock clockC;\n"
				+ "clock clockM;\n"
				+ "clock clockR;\n";
		
		String gate = "gate InitC;\n"
				+ "gate Select(int);\n"
				+ "gate AddContactSelected;\n"
				+ "gate Insert(int, int);\n"
				+ "gate Display(int);\n"
				+ "gate DoneContacts(int);\n"
				+ "gate SearchContactSelected;\n"
				+ "gate View(int);\n"
				+ "gate EditContactSelected;\n"
				+ "gate Delete(int);\n"
				+ "gate MessageAppSelected(int, int);\n"
				+ "gate DeleteContactSelected;\n"
				+ "gate Type(int);\n"
				+ "gate Transmit(int, int);\n"
				+ "gate DoneMessage(int);\n"
				+ "gate ShowMessage(int);\n"
				+ "gate InitM;\n"
				+ "gate InitR;\n"
				+ "gate CallSelected(int, int);\n"
				+ "gate CallReceived;\n"
				+ "gate Receive(int);\n"
				+ "gate Reject(int);\n"
				+ "gate DoneCall(int);\n"
				+ "gate Talk(int);\n"
				+ "gate ReceiveInterruption;\n"
				+ "gate SendInterruption;\n"
				+ "gate Confirm(int);\n";
		
		String text = clocks + gate;
		getGraphEditor().getProcessText().setText(text);
	}
	
	//process ContactsApp;
	public void t1(MyTIOSTS tiosts) {
		String variables = "int optionContacts;\n"
				+ "int numberContacts;\n"
				+ "int contactName;\n";
				
		tiosts.getText().setText(variables);
		
		String[] states = new String[] {
				"STARTC",
				"S1",
				"S2",
				"S3",
				"S4",
				"S6",
				"S7",
				"S8",
				"S9",
				"S10",
				"S11",
				"S12",
				"S13",
				"S14",
				"S15",
				"S16",
				"S33"
		};

		MyTransition[] transitions = new MyTransition[] {
				new MyTransition("if (TRUE)", "sync InitC()", "", "lazy"),
				new MyTransition("if (TRUE)", "sync Select?(op)", "do optionContacts := op", "lazy"),
				new MyTransition("if (optionContacts = 6)", "sync AddContactSelected!()", "", "lazy"),
				new MyTransition("if (TRUE)", "sync Insert?(name, number)", "do { numberContacts := number | contactName := name }", "lazy"),
				new MyTransition("if (optionContacts = 6 AND optionContacts = op)", "sync DoneContacts!(op)", "", "lazy"),
				new MyTransition("if (optionContacts = 7)", "sync SearchContactSelected!()", "", "lazy"),
				new MyTransition("if (TRUE)", "sync View?(name)", "do { numberContacts := number | contactName := name | clockC := 0 }", "lazy"),
				new MyTransition("when clockC <= 1\n"
						+ "		if (contactName = info)", "sync Display!(info)", "", "lazy"),
				new MyTransition("if (TRUE)", "sync SendInterruption!()", "", "lazy"),
				new MyTransition("if (TRUE)", "sync ReceiveInterruption?()", "", "lazy"),
				new MyTransition("if (TRUE)", "sync Select?(op)", "do optionContacts := op", "lazy"),
				new MyTransition("if (optionContacts = 9)", "sync DeleteContactSelected!()", "", "lazy"),
				new MyTransition("if (TRUE)", "sync Confirm?(info)", "do answer := info", "lazy"),
				new MyTransition("if (contactName = name AND answer = 9)", "sync Delete!(name)", "", "lazy"),
				new MyTransition("if (optionContacts = 9 AND optionContacts = op)", "sync DoneContacts!(op)", "", "lazy"),
				new MyTransition("if (optionContacts = 1 AND numberContacts = number)", "sync MessageAppSelected!(op, number)", "", "lazy"),
				new MyTransition("if (optionContacts = 8)", "sync EditContactSelected!()", "", "lazy"),
				new MyTransition("if (TRUE)", "sync Insert?(name, number)", "do { optionContacts := number | contactName := name | clockC := 0 }", "lazy"),
				new MyTransition("when clockC <= 1\n"
						+ "		if (numberContacts = info)", "sync Display!(info)", "", "lazy"),
				new MyTransition("if (optionContacts = 8 AND optionContacts = op)", "sync DoneContacts!(op)", "", "lazy"),
		};
		
		String[] from = new String[] {
				"STARTC",
				"S1",
				"S2",
				"S3",
				"S4",
				"S2",
				"S6",
				"S7",
				"S8",
				"S33",
				"S8",
				"S9",
				"S13",
				"S14",
				"S15",
				"S9",
				"S9",
				"S10",
				"S11",
				"S12"				
		};
		
		String[] to = new String[] {
				"S1",
				"S2",
				"S3",
				"S4",
				"S1",
				"S6",
				"S7",
				"S8",
				"S33",
				"S8",
				"S9",
				"S13",
				"S14",
				"S15",
				"S1",
				"S16",
				"S10",
				"S11",
				"S12",
				"S1"	
		};

		Object parent = tiosts.getGraph().getDefaultParent();
		int x = 0;
		int y = 0;
		
		for(int i = 0; i< states.length; i++) {
			Object v2 = tiosts.getGraph().insertVertex(parent, null, states[i], x+=50, y+=50, 50, 50, "shape=ellipse;perimeter=ellipsePerimeter");	
			tiosts.getAllCells().add((mxCell) v2);
		}
		tiosts.setInit(tiosts.getVertexByName("STARTC"));
		
		for(int i = 0; i< to.length; i++) {
			Object v3 = tiosts.getGraph().insertEdge(parent, null, transitions[i], tiosts.getVertexByName(from[i]), tiosts.getVertexByName(to[i]));
			tiosts.getAllCells().add((mxCell) v3);
		}
		
	}

	//process MessageApp;
	public void t2(MyTIOSTS tiosts) {
		String variables = "int choice;\n"
				+ "int phoneNumber;\n"
				+ "int message;\n";
				
		tiosts.getText().setText(variables);
		
		String[] states = new String[] {
				"STARTM",
				"S17",
				"S18",
				"S19",
				"S20",
				"S21",
				"S22",
				"S23"
		};

		MyTransition[] transitions = new MyTransition[] {
				new MyTransition("if (TRUE)", "sync InitM()", "", "lazy"),
				new MyTransition("if (TRUE)", "sync MessageAppSelected?(op, number)", "do { choice := op | phoneNumber := number | clockM := 0}", "lazy"),
				new MyTransition("if (choice = 1)", "sync Type?(data)", "do message := data", "lazy"),
				new MyTransition("if (message = data AND phoneNumber = number)", "sync Transmit!(data, number)", "do clockM := 0", "lazy"),
				new MyTransition("when clockM <= 2		\n"
						+ "		if (choice = 1 AND choice = op)", "sync DoneMessage!(op)", "", "lazy"),
				new MyTransition("if (choice = 2 AND phoneNumber = number)", "sync ShowMessage!(number)", "do clockM := 0", "lazy"),
				new MyTransition("when clockM <= 2", "if (choice = 2 AND choice = op)", "sync DoneMessage!(op)", "lazy")
		};
		
		String[] from = new String[] {
				"STARTM",
				"S17",
				"S18",
				"S19",
				"S20",
				"S18",
				"S22",
		};
		
		String[] to = new String[] {
				"S17",
				"S18",
				"S19",
				"S20",
				"S21",
				"S22",
				"S23"
		};

		Object parent = tiosts.getGraph().getDefaultParent();
		int x = 0;
		int y = 0;
		
		for(int i = 0; i< states.length; i++) {
			Object v2 = tiosts.getGraph().insertVertex(parent, null, states[i], x+=50, y+=50, 50, 50, "shape=ellipse;perimeter=ellipsePerimeter");	
			tiosts.getAllCells().add((mxCell) v2);
		}
		tiosts.setInit(tiosts.getVertexByName("STARTM"));
		
		for(int i = 0; i< to.length; i++) {
			Object v3 = tiosts.getGraph().insertEdge(parent, null, transitions[i], tiosts.getVertexByName(from[i]), tiosts.getVertexByName(to[i]));
			tiosts.getAllCells().add((mxCell) v3);
		}
	}

	//process ReceiveCallApp
	public void t3(MyTIOSTS tiosts) {
		String variables = "int optionCall;\n"
				+ "int numberCall;\n";
				
		tiosts.getText().setText(variables);
		
		String[] states = new String[] {
				"STARTR",
				"S24",
				"S25",
				"S26",
				"S27",
				"S28",
				"S29",
				"S30",
				"S32",
				"S35"
		};

		MyTransition[] transitions = new MyTransition[] {
				new MyTransition("if (TRUE)", "sync InitR()", "", "lazy"),
				new MyTransition("if (TRUE)", "sync SendInterruption?()", "", "lazy"),
				new MyTransition("if (TRUE)", "sync CallSelected?(op, number)", "do { optionCall := op | numberCall := number }", "lazy"),
				new MyTransition("if (optionCall = 3)", "sync CallReceived!()", "", "lazy"),
				new MyTransition("when clockR <= 1\n"
						+ "		if (TRUE)", "sync Receive?(op)", "do optionCall := op", "lazy"),
				new MyTransition("if (optionCall = 4 AND numberCall = number)", "sync Reject!(number)", "", "lazy"),
				new MyTransition("if (optionCall = 4 AND optionCall = op)", "sync DoneCall!(op)", "", "lazy"),
				new MyTransition("if (TRUE)", "sync ReceiveInterruption!()", "", "lazy"),
				new MyTransition("if (optionCall = 5 AND numberCall = number)", "sync Talk!(number)", "", "lazy"),
				new MyTransition("if (optionCall = 5 AND optionCall = op)", "sync DoneCall!(op)", "", "lazy"),
				new MyTransition("if (optionCall = 1 AND optionCall = op AND numberCall = number)", "sync MessageAppSelected!(op, number)", "", "lazy")
		};
		
		String[] from = new String[] {
				"STARTR",
				"S35",
				"S24",
				"S25",
				"S26",
				"S27",
				"S28",
				"S29",
				"S27",
				"S30",
				"S27"
		};
		
		String[] to = new String[] {
				"S35",
				"S24",
				"S25",
				"S26",
				"S27",
				"S28",
				"S29",
				"S35",
				"S30",
				"S29",
				"S32"
		};

		Object parent = tiosts.getGraph().getDefaultParent();
		int x = 0;
		int y = 0;
		
		for(int i = 0; i< states.length; i++) {
			Object v2 = tiosts.getGraph().insertVertex(parent, null, states[i], x+=50, y+=50, 50, 50, "shape=ellipse;perimeter=ellipsePerimeter");	
			tiosts.getAllCells().add((mxCell) v2);
		}
		tiosts.setInit(tiosts.getVertexByName("STARTR"));
		
		for(int i = 0; i< to.length; i++) {
			Object v3 = tiosts.getGraph().insertEdge(parent, null, transitions[i], tiosts.getVertexByName(from[i]), tiosts.getVertexByName(to[i]));
			tiosts.getAllCells().add((mxCell) v3);
		}
	}
	
	//process CellPhoneAppTP6
	public void t4(MyTIOSTS tiosts) {	
		String[] states = new String[] {
				"STARTTP1",
				"S60",
				"S61",
				"S62",
				"S63",
				"Accept",
				"Reject"
		};

		MyTransition[] transitions = new MyTransition[] {
				new MyTransition("if (TRUE)", "sync InitC()", "", "lazy"),
				new MyTransition("if (TRUE)", "sync SendInterruption!()", "", "lazy"),
				new MyTransition("if (TRUE)", "sync CallSelected?(op, number)", "", "lazy"),
				new MyTransition("if (TRUE)", "sync MessageAppSelected!(op, number)", "", "lazy"),
				new MyTransition("if (TRUE)", "sync DoneMessage!(op)", "", "lazy"),
				new MyTransition("if (TRUE)", "sync ReceiveInterruption!()", "", "lazy")
		};
		
		String[] from = new String[] {
				"STARTTP1",
				"S60",
				"S61",
				"S62",
				"S63",
				"S62"
		};
		
		String[] to = new String[] {
				"S60",
				"S61",
				"S62",
				"S63",
				"Accept",
				"Reject"
		};

		Object parent = tiosts.getGraph().getDefaultParent();
		int x = 0;
		int y = 0;
		
		for(int i = 0; i< states.length; i++) {
			Object v2 = tiosts.getGraph().insertVertex(parent, null, states[i], x+=50, y+=50, 50, 50, "shape=ellipse;perimeter=ellipsePerimeter");	
			tiosts.getAllCells().add((mxCell) v2);
		}
		tiosts.setInit(tiosts.getVertexByName("STARTTP1"));
		
		for(int i = 0; i< to.length; i++) {
			Object v3 = tiosts.getGraph().insertEdge(parent, null, transitions[i], tiosts.getVertexByName(from[i]), tiosts.getVertexByName(to[i]));
			tiosts.getAllCells().add((mxCell) v3);
		}
	}
}
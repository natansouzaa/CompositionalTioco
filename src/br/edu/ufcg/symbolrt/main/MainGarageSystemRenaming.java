package br.edu.ufcg.symbolrt.main;

import java.util.List;

import br.edu.ufcg.symbolrt.base.TIOSTS;
import br.edu.ufcg.symbolrt.compiler.facade.Compiler;
import br.edu.ufcg.symbolrt.compositions.ParallelComposition;
import br.edu.ufcg.symbolrt.compositions.RenamingOperator;
import br.edu.ufcg.symbolrt.compositions.exceptions.IncompatibleCompositionalOperationException;
import br.edu.ufcg.symbolrt.facade.SYMBOLRT;

public class MainGarageSystemRenaming {

	public static void main(String[] args) {
		//Estudo de caso do Sistema de garagem (renaming)
		long start = System.currentTimeMillis();

		Compiler.compile("./examples/GarageSystem.srt", "GarageSystem");
		TIOSTS tiostsRemoteControl = Compiler.getSpecification("RemoteControl");
		TIOSTS tiostsGarageDoorController = Compiler.getSpecification("GarageDoorController");
		TIOSTS testPurpose = Compiler.getSpecification("GarageSystemTP1");
		
		// Using the renaming operator
		TIOSTS tiostsSmartphoneControl = null;		
		
		RenamingOperator renOperator = RenamingOperator.getInstance();
		try {
			tiostsSmartphoneControl = renOperator.renamingOperator(tiostsRemoteControl, "remoteControlSelected", "smartphoneSelected");
		} catch (IncompatibleCompositionalOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tiostsSmartphoneControl.setName("SmartphoneControl");
		
		// Using the parallel composition with two TIOSTS: SmartphoneControl and GarageDoorController
		TIOSTS tiostsParSmartphoneControlGarageDoor = null;
		ParallelComposition parComposition = ParallelComposition.getInstance();
		try {
			tiostsParSmartphoneControlGarageDoor = parComposition.parallelComposition(tiostsSmartphoneControl, tiostsGarageDoorController);
		} catch (IncompatibleCompositionalOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tiostsParSmartphoneControlGarageDoor.setName("SmartphoneControl X GarageDoorController");
		
		SYMBOLRT symbolrt = SYMBOLRT.getInstance();
		
		List<TIOSTS> testCases = symbolrt.generateTestCases(tiostsParSmartphoneControlGarageDoor, testPurpose, true);

		int size = testCases.size();
		
		System.out.print(size + " test case(s) generated");
		
		symbolrt.show(testCases);

		long finish = System.currentTimeMillis();
		long result = finish - start;
		
		System.out.println(" in " + result + " milliseconds.");
	}
	
}

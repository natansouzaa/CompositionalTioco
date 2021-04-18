package br.edu.ufcg.symbolrt.main;

import java.util.List;

import br.edu.ufcg.symbolrt.base.TIOSTS;
import br.edu.ufcg.symbolrt.compiler.facade.Compiler;
import br.edu.ufcg.symbolrt.compositions.HidingOperator;
import br.edu.ufcg.symbolrt.compositions.exceptions.IncompatibleCompositionalOperationException;
import br.edu.ufcg.symbolrt.facade.SYMBOLRT;

public class MainGarageSystemHiding {
	
	public static void main(String[] args) {
		//Estudo de caso do Sistema de garagem (hiding)
		long start = System.currentTimeMillis();

		Compiler.compile("./examples/GarageSystem.srt", "GarageSystem");
		TIOSTS tiostsGarageDoorController = Compiler.getSpecification("GarageDoorController");
		TIOSTS testPurpose = Compiler.getSpecification("GarageSystemTP1");

		// Using the hiding operator	
		HidingOperator hidOperator = HidingOperator.getInstance();
		try {
			tiostsGarageDoorController = hidOperator.hidingOperator(tiostsGarageDoorController, "press");
		} catch (IncompatibleCompositionalOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tiostsGarageDoorController.setName("GarageDoorController");
		
		SYMBOLRT symbolrt = SYMBOLRT.getInstance();
		
		List<TIOSTS> testCases = symbolrt.generateTestCases(tiostsGarageDoorController, testPurpose, true);

		int size = testCases.size();
		
		System.out.print(size + " test case(s) generated");
		
		symbolrt.show(testCases);

		long finish = System.currentTimeMillis();
		long result = finish - start;
		
		System.out.println(" in " + result + " milliseconds.");
	}

}

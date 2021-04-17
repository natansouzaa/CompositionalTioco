package gui.util;

import java.util.List;

import br.edu.ufcg.symbolrt.base.TIOSTS;
import br.edu.ufcg.symbolrt.compositions.HidingOperator;
import br.edu.ufcg.symbolrt.compositions.InterruptionComposition;
import br.edu.ufcg.symbolrt.compositions.ParallelComposition;
import br.edu.ufcg.symbolrt.compositions.RenamingOperator;
import br.edu.ufcg.symbolrt.compositions.SequentialComposition;
import br.edu.ufcg.symbolrt.compositions.exceptions.IncompatibleCompositionalOperationException;
import br.edu.ufcg.symbolrt.facade.SYMBOLRT;

public class Compositions {

	public Compositions() {
		
	}
	
	public TIOSTS operatorRenamingGui(TIOSTS model, String currentName, String newName) {
		RenamingOperator renOperator = RenamingOperator.getInstance();
		
		try {
			model = renOperator.renamingOperator(model, currentName, newName);
		} catch (IncompatibleCompositionalOperationException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	public TIOSTS operatorHidingGui(TIOSTS model, String name) {
		HidingOperator hidOperator = HidingOperator.getInstance();
		
		try {
			model = hidOperator.hidingOperator(model, name);
		} catch (IncompatibleCompositionalOperationException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	public TIOSTS parallelCompositionGui(TIOSTS model1, TIOSTS model2) {
		TIOSTS modelResult = null;
		ParallelComposition parComposition = ParallelComposition.getInstance();
		
		try {
			modelResult = parComposition.parallelComposition(model1, model2);
		} catch (IncompatibleCompositionalOperationException e) {
			e.printStackTrace();
		}
		return modelResult;
	}
	
	public TIOSTS sequentialCompositionGui(TIOSTS model1, TIOSTS model2) {
		TIOSTS modelResult = null;
		SequentialComposition seqComposition = SequentialComposition.getInstance();
		
		try {
			modelResult = seqComposition.sequentialComposition(model1, model2);
		} catch (IncompatibleCompositionalOperationException e) {
			e.printStackTrace();
		}
		return modelResult;
	}
	
	public TIOSTS interruptionCompositionGui(TIOSTS model1, TIOSTS model2) {
		TIOSTS modelResult = null;	
		InterruptionComposition intComposition = InterruptionComposition.getInstance();
		
		try {
			modelResult = intComposition.interruptionComposition(model1, model2);
		} catch (IncompatibleCompositionalOperationException e) {
			e.printStackTrace();
		}
		return modelResult;
	}
	
	public List<TIOSTS> testCaseGeneratorGui(TIOSTS model, TIOSTS testPurpuse) {
		SYMBOLRT symbolrt = SYMBOLRT.getInstance();
		List<TIOSTS> testCases = symbolrt.generateTestCases(model, testPurpuse, true);
		return testCases;
	}
	
}
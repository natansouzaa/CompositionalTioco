package br.edu.ufcg.symbolrt.main;

import java.util.LinkedList;
import java.util.List;

import br.edu.ufcg.symbolrt.base.TIOSTS;
import br.edu.ufcg.symbolrt.compiler.facade.Compiler;
import br.edu.ufcg.symbolrt.compositions.HidingOperator;
import br.edu.ufcg.symbolrt.compositions.exceptions.IncompatibleCompositionalOperationException;
import br.edu.ufcg.symbolrt.facade.SYMBOLRT;

//Classe usada apenas para geração dos modelos, sem fazer nenhuma alteração nos mesmos.

public class RunModels {

	public static void main(String[] args) {
		
		Compiler.compile("./examples/SetTopBox.srt", "SetTopBox");
		TIOSTS tiostsSeparator = Compiler.getSpecification("Separator");
		TIOSTS tiostsTuner = Compiler.getSpecification("Tuner");
		TIOSTS tiostsVideoDecoder = Compiler.getSpecification("VideoDecoder");
		TIOSTS tiostsAudioDecoder = Compiler.getSpecification("AudioDecoder");
		TIOSTS tiostsInterruptionSetTopBox = Compiler.getSpecification("InterruptionSetTopBox");
		TIOSTS tiostsSetTopBoxTP1 = Compiler.getSpecification("SetTopBoxTP1");		
		
		SYMBOLRT symbolrt = SYMBOLRT.getInstance();
		
		List<TIOSTS> testCases = new LinkedList<TIOSTS>();
		
		testCases.add(tiostsSeparator);
		testCases.add(tiostsTuner);
		testCases.add(tiostsVideoDecoder);
		testCases.add(tiostsAudioDecoder);
		testCases.add(tiostsInterruptionSetTopBox);
		testCases.add(tiostsSetTopBoxTP1);
		
		symbolrt.show(testCases);
		
	}
	
}

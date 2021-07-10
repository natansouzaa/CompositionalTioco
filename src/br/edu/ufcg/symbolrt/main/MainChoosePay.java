package br.edu.ufcg.symbolrt.main;

import java.util.List;

import br.edu.ufcg.symbolrt.base.TIOSTS;
import br.edu.ufcg.symbolrt.compiler.facade.Compiler;
import br.edu.ufcg.symbolrt.compositions.SequentialComposition;
import br.edu.ufcg.symbolrt.compositions.exceptions.IncompatibleCompositionalOperationException;
import br.edu.ufcg.symbolrt.facade.SYMBOLRT;

//Classe usada apenas para geração dos modelos, sem fazer nenhuma alteração nos mesmos.

public class MainChoosePay {

	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
		Compiler.compile("./examples/ChoosePay.srt", "Choose");
		TIOSTS tpChoose = Compiler.getSpecification("Choose");
		TIOSTS tpPay = Compiler.getSpecification("Pay");
		TIOSTS tpName = Compiler.getSpecification("ChoosePayTP1");		
		
		SequentialComposition seqComposition = SequentialComposition.getInstance();	
		TIOSTS tpSeqChoosePay = null;
		try {
			tpSeqChoosePay = seqComposition.sequentialComposition(tpChoose, tpPay);
		} catch (IncompatibleCompositionalOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SYMBOLRT symbolrt = SYMBOLRT.getInstance();
		List<TIOSTS> testCases = symbolrt.generateTestCases(tpSeqChoosePay, tpName, true);
		
		long finish = System.currentTimeMillis();
		long result = finish - start;
		System.out.println(testCases.size() + " test case(s) generated in " + result + " milliseconds.");
		
		symbolrt.show(testCases);
//		symbolrt.store(testCases);
	}
}
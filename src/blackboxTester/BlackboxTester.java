package blackboxTester;
import java.util.ArrayList;
import java.util.List;

import blackboxTester.ast.AST;
import blackboxTester.ast.Evaluate;
import blackboxTester.ast.generator.ASTGenerator;
import blackboxTester.parser.Parser;
import blackboxTester.parser.ast.Equation;
import blackboxTester.parser.ast.Input;
import blackboxTester.outputGeneration.OutputGenerator;

/**
 * The BlackboxTester class is the main class that initiates
 * the parser with the file to be parse, which is being input
 * by the user
 *
 */
public class BlackboxTester {
	/**
	 * main will pass the input file to parser and create a new text
	 * file for generated output
	 * 
	 * @param args The files used for parser and to be written
	 * @throws Exception when parser cannot parse
	 */
	public static void main(String[] args) throws Exception {
		Input input = Parser.parse(args[0]);
		
		int count = 0;
		List<AST> generatedASTs = (List<AST>)ASTGenerator.generateASTs(input.getSignatures());
		ArrayList<Equation> equations = input.getEquations();
		
		Evaluate evaluator = new Evaluate();
		List<AST> reducedASTs = (List<AST>)evaluator.replace((ArrayList<AST>)generatedASTs, equations);
		
		
		for (int i = 0; i < generatedASTs.size(); i++) {
			
			if (reducedASTs.get(i).isFullyReduced()) {
				count++;
				if (count >= 1000) {
					reducedASTs =  reducedASTs.subList(0, i);
					generatedASTs =  generatedASTs.subList(0, i);
					break;
				}
			} else {
				reducedASTs.remove(i);
				generatedASTs.remove(i);
				i--;
			}
		}
		ArrayList<String> typeNames = new ArrayList<String>();
		for (int i = 0; i < input.getSignatures().size(); i++) {
			typeNames.add(input.getSignatures().get(i).getTypename());
		}
		OutputGenerator outputGen = new OutputGenerator(generatedASTs, reducedASTs, typeNames, args[1]);
		outputGen.generateOutput();
	}
}

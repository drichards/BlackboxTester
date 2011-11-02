package blackboxTester;
import java.io.PrintWriter;
import java.util.ArrayList;

import blackboxTester.ast.AST;
import blackboxTester.ast.Evaluate;
import blackboxTester.ast.generator.ASTGenerator;
import blackboxTester.parser.Parser;
import blackboxTester.parser.ast.Equation;
import blackboxTester.parser.ast.Input;

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
		PrintWriter out = new PrintWriter(args[1], "UTF8");
		
		int count = 0;
		ArrayList<AST> generatedASTs = ASTGenerator.generateASTs(input.getSignatures());
		ArrayList<AST> reducedASTs = deepCopy(generatedASTs);
		ArrayList<Equation> equations = input.getEquations();
		
		Evaluate evaluator = new Evaluate();
		evaluator.replace(reducedASTs, equations);
		
		for (int i = 0; i < generatedASTs.size(); i++) {
			
			if (reducedASTs.get(i).isPrimitive()) {
				out.println(generatedASTs.get(i).toString());
				out.println(reducedASTs.get(i).toString());
			
				count++;
				if (count >= 1000) {
					break;
				}
			}
		}
		
		out.close();
	}
	
	private static ArrayList<AST> deepCopy(ArrayList<AST> asts) {
		ArrayList<AST> copiedAsts = new ArrayList<AST>();
		
		for (AST ast : asts) {
			copiedAsts.add(ast.deepCopy());
		}
		
		return copiedAsts;
	}
}

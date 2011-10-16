package blackboxTester;
import java.io.PrintWriter;

import blackboxTester.ast.AST;
import blackboxTester.ast.generator.ASTGenerator;
import blackboxTester.parser.Parser;
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
		for (AST ast : ASTGenerator.generateASTs(input.getSignatures())) {

			out.println(ast.toString());
			
			count++;
			if (count >= 1000) {
				break;
			}
		}
		
		out.close();
	}
}

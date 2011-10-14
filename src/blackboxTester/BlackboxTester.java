package blackboxTester;
import java.io.FileWriter;
import java.io.PrintWriter;

import blackboxTester.ast.AST;
import blackboxTester.ast.generator.ASTGenerator;
import blackboxTester.parser.Parser;
import blackboxTester.parser.ast.Input;


public class BlackboxTester {
	public static void main(String[] args) throws Exception {
		Input input = Parser.parse(args[0]);
		PrintWriter out = new PrintWriter(new FileWriter(args[1]));
		
		for (AST ast : ASTGenerator.generateASTs(input.getSignatures())) {
			out.println(ast.toString());
		}
		
		out.close();
	}
}

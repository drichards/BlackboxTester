import ast.AST;
import ast.generator.ASTGenerator;
import parser.Parser;
import parser.ast.Input;


public class BlackboxTester {
	public static void main(String[] args) throws Exception {
		Input input = Parser.parse();
		
		for (AST ast : ASTGenerator.generateASTs(input.getSignatures())) {
			System.out.println(ast.toString());
		}
	}
}

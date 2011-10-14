package blackboxTester;
import blackboxTester.ast.AST;
import blackboxTester.ast.generator.ASTGenerator;
import blackboxTester.parser.Parser;
import blackboxTester.parser.ast.Input;


public class BlackboxTester {
	public static void main(String[] args) throws Exception {
		Input input = Parser.parse();
		
		for (AST ast : ASTGenerator.generateASTs(input.getSignatures())) {
			System.out.println(ast.toString());
		}
	}
}

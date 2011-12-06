package blackboxTester.tests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import junit.framework.TestCase;
import blackboxTester.ast.AST;
import blackboxTester.ast.FunctionCall;
import blackboxTester.ast.PrimitiveAST;
import blackboxTester.outputGeneration.OutputGenerator;

public class OutputTests extends TestCase {
	public void testOutput() throws IOException {
		ArrayList<AST> asts = new ArrayList<AST>();
		ArrayList<AST> reducedAsts = new ArrayList<AST>();
		ArrayList<String> imports = new ArrayList<String>();
		
		ArrayList<AST> args = new ArrayList<AST>();
		
		args.add(new FunctionCall("baz", new ArrayList<AST>()));
		
		asts.add(new FunctionCall("bar", args));
		
		args = new ArrayList<AST>();
		
		args.add(new PrimitiveAST.IntegerAST(1));
		args.add(new PrimitiveAST.IntegerAST(2));
		
		reducedAsts.add(new PrimitiveAST.PrimitiveFunctionCall("<", args));
		
		imports.add("foobar");
		
		OutputGenerator generator = new OutputGenerator(
			asts,
			reducedAsts,
			imports,
			"assets/tests/testOutput.txt"
		);
		
		generator.generateOutput();
		
		BufferedReader templateReader = new BufferedReader(
			new InputStreamReader(
                new FileInputStream("assets/outputTemplate.txt")
            )
		);
		
		BufferedReader outputReader = new BufferedReader(
			new InputStreamReader(
                new FileInputStream("assets/tests/testOutput.txt")
	        )
		);
		
		String line;
		String outputLine;
		while((line = templateReader.readLine()) != null) {
			outputLine = outputReader.readLine();
			
			if (line.trim().startsWith("//imports")) {
				assertEquals("(testing foobar)", outputLine.trim());
				
				outputLine = outputReader.readLine();
				
				assertEquals(")", outputLine.trim());
			} else if (line.startsWith("//tests")) {
				assertEquals("(test \"bar1\" (equal? (< 1 2) (bar (baz))))", outputLine.trim());
			} else {
				assertEquals(line, outputLine);
			}
		}

		templateReader.close();
		outputReader.close();
	}
}

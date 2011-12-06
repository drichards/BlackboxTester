package blackboxTester.outputGeneration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import blackboxTester.ast.AST;
import blackboxTester.ast.FunctionCall;

/**
 * Helper class used to produce the output test program from the
 * processed ASTs
 */
public class OutputGenerator {
	/**
	 * The original form ASTs
	 */
	private List<AST> generatedASTs;
	
	/**
	 * The reduced form ASTs
	 */
	private List<AST> reducedASTs;
	
	/**
	 * The ADTs to import
	 */
	private ArrayList<String> imports;
	
	/**
	 * The file to write to
	 */
	private String outFilePath;
	
	/**
	 * Create a new OutputGenerator.
	 * 
	 * @param generatedASTs The original form ASTs
	 * @param reducedASTs The reduced form ASTs
	 * @param imports The ADTs to import
	 * @param outFilePath The file to write to
	 */
	public OutputGenerator(List<AST> generatedASTs, List<AST> reducedASTs,
			ArrayList<String> imports, String outFilePath){
		this.generatedASTs = generatedASTs;
		this.reducedASTs = reducedASTs;
		this.imports = imports;
		this.outFilePath = outFilePath;
	}
	
	/**
	 * Generate the output and write it to file.
	 * 
	 * @throws IOException thrown if the chosen output file isn't writable,
	 * or the template file isn't readable.
	 */
	public void generateOutput() throws IOException {
		// create a reader for the template file
		BufferedReader reader = new BufferedReader(
			new InputStreamReader(
                new FileInputStream("assets/outputTemplate.txt")
            )
		);
		
		// create a writer for the output file
		PrintWriter out = new PrintWriter(this.outFilePath, "UTF8");

		String line;
		HashMap<String, Integer> methodCounts = new HashMap<String, Integer>();
		// read through the template file
		while((line = reader.readLine()) != null) {
			if (line.trim().startsWith("//imports")) {
				// print out the imports
				for(int i = 0; i < this.imports.size(); i++) {
					out.println("\t\t(testing " + this.imports.get(i) + ")");
				}
				out.println("\t\t)");
			} else if (line.startsWith("//tests")) {
				// print out the test cases
				for(int i = 0; i < this.generatedASTs.size(); i++) {
					String currentMethod = 
						((FunctionCall)this.generatedASTs.get(i)).getMethodName();
					
					// lookup the test count we're on for this method
					Integer methodCount = methodCounts.get(currentMethod);
					int updatedCount;
					
					if (methodCount == null) {
						updatedCount = 1;
					} else {
						updatedCount = methodCount + 1;
					}
					
					// print out the test case
					out.println(
						genTest(
							this.reducedASTs.get(i), 
							this.generatedASTs.get(i), 
							currentMethod + updatedCount
						)
					);
					
					// update the count for this method
					methodCounts.put(currentMethod, updatedCount);
				}
			} else {
				// print out the template file's line otherwise
				out.println(line);
			}
		}
		out.close();
		reader.close();
	}
	
	/**
	 * Create the string output for a single test.
	 * 
	 * @param reduced The reduced AST
	 * @param generated The original AST
	 * @param testName The name of the test
	 * @return The string output for the test
	 */
	private String genTest(AST reduced, AST generated, String testName) {
		return "(test \"" + 
			testName + "\" (" + 
			reduced.getComparator() + " " + 
			reduced.toString() + " " + 
			generated.toString() + "))";
	}
	
}

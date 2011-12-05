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

public class OutputGenerator {
	List<AST> generatedASTs;
	List<AST> reducedASTs;
	ArrayList<String> imports;
	String outFilePath;
	
	public OutputGenerator(List<AST> generatedASTs, List<AST> reducedASTs,
			ArrayList<String> imports, String outFilePath){
		this.generatedASTs = generatedASTs;
		this.reducedASTs = reducedASTs;
		this.imports = imports;
		this.outFilePath = outFilePath;
	}
	
	public void generateOutput() throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(
		                new FileInputStream("assets/outputTemplate.txt")));
		
		PrintWriter out = new PrintWriter(this.outFilePath, "UTF8");

		String line;
		HashMap<String, Integer> methodCounts = new HashMap<String, Integer>();
		while((line = reader.readLine()) != null) {
			if (line.trim().startsWith("//imports")) {
				for(int i = 0; i < this.imports.size(); i++) {
					out.println("\t\t(testing " + this.imports.get(i) + ")");
				}
				out.println("\t\t)");
			} else if (line.startsWith("//tests")) {
				for(int i = 0; i < this.generatedASTs.size(); i++) {
					String currentMethod = 
						((FunctionCall)this.generatedASTs.get(i)).getMethodName();
					
					Integer methodCount = methodCounts.get(currentMethod);
					int updatedCount;
					
					if (methodCount == null) {
						updatedCount = 1;
					} else {
						updatedCount = methodCount + 1;
					}
					
					out.println(this.genTest(this.reducedASTs.get(i), this.generatedASTs.get(i), currentMethod + updatedCount));
					methodCounts.put(currentMethod, updatedCount);
				}
			} else {
				out.println(line);
			}
		}
		out.close();
		
	}
	
	private String genTest(AST reduced, AST generated, String testName) {
		return "(test \"" + testName + "\" (" + reduced.getComparator() + " " + reduced.toString() + " " + generated.toString() + "))";
	}
	
}

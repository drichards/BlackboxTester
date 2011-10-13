package ast.generator;

import java.util.ArrayList;
import java.util.HashMap;

import parser.ast.Signature;

import ast.AST;
import ast.FunctionCall;

public class ASTGenerator {
	private static int DEPTH = 10;
	
	private HashMap<AST, ArrayList<FunctionCall>> trees = 
		new HashMap<AST, ArrayList<FunctionCall>>();
	
	public static void generateASTs(ArrayList<Signature> signatures) {
		for (int i = 0; i < DEPTH; i++) {
			for (Signature sig : signatures) {
				
			}
		}
	}
}

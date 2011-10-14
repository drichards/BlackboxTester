package blackboxTester.ast.generator;

import java.util.ArrayList;
import java.util.HashMap;

import blackboxTester.ast.AST;
import blackboxTester.ast.FunctionCall;
import blackboxTester.ast.PrimitiveAST;
import blackboxTester.parser.ast.OpSpec;
import blackboxTester.parser.ast.PrimitiveType;
import blackboxTester.parser.ast.Signature;
import blackboxTester.parser.ast.Type;



public class ASTGenerator {
	private static int DEPTH = 10;
	
	private static HashMap<Type, ArrayList<AST>> trees = 
		new HashMap<Type, ArrayList<AST>>();
	private static HashMap<Type, ArrayList<AST>> lastLayer = 
		new HashMap<Type, ArrayList<AST>>();
	private static HashMap<Type, ArrayList<AST>> currentLayer = 
		new HashMap<Type, ArrayList<AST>>();
	
	public static ArrayList<AST> generateASTs(ArrayList<Signature> signatures) {
		for (int i = 0; i < DEPTH; i++) {
			for (Signature sig : signatures) {
				for (OpSpec opSpec : sig.getOpSpecs()) {
					if (opSpec.hasOnlyPrimitiveArgs() && i != 0) { continue; }
					
					Permutations p = null;
					for (Type argType : opSpec.getArgTypes()) {
						ArrayList<AST> data;
						if (argType.isPrimitive()) {
							data = new ArrayList<AST>();
							if (argType instanceof PrimitiveType.BooleanType) {
								data.add(new PrimitiveAST.BooleanAST());
							} else if (argType instanceof PrimitiveType.CharacterType) {
								data.add(new PrimitiveAST.CharacterAST());
							} else if (argType instanceof PrimitiveType.StringType) {
								data.add(new PrimitiveAST.StringAST());
							} else if (argType instanceof PrimitiveType.IntegerType) {
								data.add(new PrimitiveAST.IntegerAST());
							}
						} else {
							data = lastLayer.get(argType);
						}
						
						if (data == null) {
							data = new ArrayList<AST>();
						}
						
						if (p == null) {
							p = new Permutations(data);
						} else {
							p.addData(data);
						}
					}
					
					if (p != null) {
						for (ArrayList<AST> args : p.permutations) {
							getASTList(opSpec.getType()).add(
								new FunctionCall(opSpec.getOperation(), args)
							);
						}
					} else if (opSpec.getArgTypes().size() == 0) {
						getASTList(opSpec.getType()).add(
							new FunctionCall(
								opSpec.getOperation(), 
								new ArrayList<AST>()
							)
						);
					}
				}
			}
			
			for (Type returnType : currentLayer.keySet()) {
				ArrayList<AST> totalSet = trees.get(returnType);
				if (totalSet == null) {
					totalSet = new ArrayList<AST>();
					trees.put(returnType, totalSet);
				}
				
				for (AST ast : currentLayer.get(returnType)) {
					totalSet.add(ast);
				}
			}
			
			lastLayer = currentLayer;
			currentLayer = new HashMap<Type, ArrayList<AST>>();
		}
		
		ArrayList<AST> returnList = new ArrayList<AST>();
		
		for (Type type : trees.keySet()) {
			if (type.isPrimitive()) {
				for (AST ast : trees.get(type)) {
					returnList.add(ast);
				}
			}
		}
		
		return returnList;
	}
	
	private static ArrayList<AST> getASTList(Type type) {
		ArrayList<AST> astList = currentLayer.get(type);
		
		if (astList == null) {
			astList = new ArrayList<AST>();
			currentLayer.put(type, astList);
		}
		
		return astList;
	}
	
	private static class Permutations {
		ArrayList<ArrayList<AST>> permutations = 
			new ArrayList<ArrayList<AST>>();
		
		public Permutations(ArrayList<AST> initialList) {
			for (AST ast : initialList) {
				ArrayList<AST> astList = new ArrayList<AST>();
				astList.add(ast);
				permutations.add(astList);
			}
		}
		
		public void addData(ArrayList<AST> newList) {
			ArrayList<ArrayList<AST>> newPermutations = 
				new ArrayList<ArrayList<AST>>();
			
			for (AST ast : newList) {
				for (ArrayList<AST> permutation : permutations) {
					ArrayList<AST> astList = new ArrayList<AST>(permutation);
					astList.add(ast);
					newPermutations.add(astList);
				}
			}
			
			permutations = newPermutations;
		}
	}
}
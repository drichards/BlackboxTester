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

/**
 * The <code>ASTGenerator</code> provides a single static method to generate
 * a list of all possible abstract syntax trees of a predetermined depth
 * from the provided list of <code>Signatures</code>
 * 
 * @see ASTGenerator#generateASTs(ArrayList)
 * @see Signature
 *
 */
public class ASTGenerator {
	/**
	 * Depth to which we will generate all possible abstract syntax trees.
	 */
	private static final int DEPTH = 10;
	
	/**
	 * Maximum number of expressions we want to generate
	 */
	private static final int MAX_EXPRESSIONS = 10000;
	
	/**
	 * The aggregated set of abstract syntax trees that have been generated.
	 * This is keyed by the return type of the top level function call.
	 */
	private static HashMap<Type, ArrayList<AST>> trees = 
		new HashMap<Type, ArrayList<AST>>();
	/**
	 * The last layer of abstract syntax trees that were generated.  This
	 * is keyed by the return type of the top level function call.  We store
	 * this set separately because we only want to apply the provided function
	 * calls to the last layer of generated syntax trees in order to generate
	 * the next layer.
	 */ 
	private static HashMap<Type, ArrayList<AST>> lastLayer = 
		new HashMap<Type, ArrayList<AST>>();
	/**
	 * The current layer of abstract syntax trees that are being generated. 
	 * This is keyed by the return type of the top level function call.
	 */
	private static HashMap<Type, ArrayList<AST>> currentLayer = 
		new HashMap<Type, ArrayList<AST>>();
	
	/**
	 * Generate all the abstract syntax trees of a certain preset depth from
	 * the provided list of <code>Signature</code>s.
	 * 
	 * @param signatures The list of signatures to generate ASTs from
	 * @return An array list of ASTs.
	 * 
	 * @see ASTGenerator#DEPTH
	 * @see Signature
	 * @see AST
	 */
	public static ArrayList<AST> generateASTs(ArrayList<Signature> signatures) {
		int count = 0;
		// for each depth
		for (int i = 0; i < DEPTH; i++) {
			// for each signature
			for (Signature sig : signatures) {
				//for each op spec
				for (OpSpec opSpec : sig.getOpSpecs()) {
					// only generate the primitive/no-argument function calls
					// on the first path (this eliminates some dups)
					if (opSpec.hasOnlyPrimitiveArgs() && i != 0) { continue; }
					
					Permutations p = null;
					// for each arg type in the current op spec
					for (Type argType : opSpec.getArgTypes()) {
						ArrayList<AST> data;
						
						// if the arg type is primitive, generate a new primitive
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
							// otherwise get the trees of this type from the
							// previous layer
							data = lastLayer.get(argType);
						}
						
						// if there's was no data in the previous layer, create
						// an empty list
						if (data == null) {
							data = new ArrayList<AST>();
						}
						
						// either start a new permutation of arg types
						// or add to the existing one
						if (p == null) {
							p = new Permutations(data);
						} else {
							p.addData(data);
						}
					}
					
					// if there are permutations
					if (p != null) {
						// for each permutation add a new function call AST
						// to the current layer with the provided args
						for (ArrayList<AST> args : p.permutations) {
							getASTList(opSpec.getType()).add(
								new FunctionCall(opSpec.getOperation(), args)
							);
							if (opSpec.getType().isPrimitive()) {
								count++;
								
								if (count >= MAX_EXPRESSIONS) {
									break;
								}
							}
						}
					} else if (opSpec.getArgTypes().size() == 0) {
						// otherwise if the op spec didn't have any arguments
						// add a new function call with no arguments
						getASTList(opSpec.getType()).add(
							new FunctionCall(
								opSpec.getOperation(), 
								new ArrayList<AST>()
							)
						);
						if (opSpec.getType().isPrimitive()) {
							count++;
						}
					}
					
					if (count >= MAX_EXPRESSIONS) break;
				}
				
				if (count >= MAX_EXPRESSIONS) break;
			}
			
			// add the current layer to the total aggregation of trees
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
			
			// set the last layer equal to the current layer
			lastLayer = currentLayer;
			// create a new empty current layer
			currentLayer = new HashMap<Type, ArrayList<AST>>();
			
			if (count >= MAX_EXPRESSIONS) break;
		}
		
		ArrayList<AST> returnList = new ArrayList<AST>();
	
		// for each type of AST
		for (Type type : trees.keySet()) {
			// if the return type is a primitive
			if (type.isPrimitive()) {
				// add all the ASTs to the return list
				for (AST ast : trees.get(type)) {
					returnList.add(ast);
					// add a pair instead of just an ast (AST, primitiveType)
				}
			}
		}
		
		return returnList;
	}
	
	/**
	 * Returns the AST list for the given return type in the current layer.
	 * Will generate an empty list if no list exists.
	 * 
	 * @param type The return type to lookup the list of ASTs for.
	 * @return The list of ASTs from the current layer that have the given
	 * return type.
	 * 
	 * @see Type
	 */
	private static ArrayList<AST> getASTList(Type type) {
		ArrayList<AST> astList = currentLayer.get(type);
		
		if (astList == null) {
			astList = new ArrayList<AST>();
			currentLayer.put(type, astList);
		}
		
		return astList;
	}
	
	/**
	 * Encapsulation of a list of permutations for arg types of a function.
	 */
	private static class Permutations {
		ArrayList<ArrayList<AST>> permutations = 
			new ArrayList<ArrayList<AST>>();
		
		/**
		 * Create a new Permutations object with an initial list of ASTs.
		 * 
		 * @param initialList
		 */
		public Permutations(ArrayList<AST> initialList) {
			for (AST ast : initialList) {
				ArrayList<AST> astList = new ArrayList<AST>();
				astList.add(ast);
				permutations.add(astList);
				
				if (permutations.size() >= MAX_EXPRESSIONS) {
					break;
				}
			}
		}
		
		/**
		 * Add a new list of ASTs to the permutations.  This will cross the new
		 * list with any current permutations.
		 *  
		 * @param newList
		 */
		public void addData(ArrayList<AST> newList) {
			ArrayList<ArrayList<AST>> newPermutations = 
				new ArrayList<ArrayList<AST>>();
			
			for (AST ast : newList) {
				for (ArrayList<AST> permutation : permutations) {
					if (newPermutations.size() >= MAX_EXPRESSIONS) {
						break;
					}
					
					ArrayList<AST> astList = new ArrayList<AST>(permutation);
					astList.add(ast);
					newPermutations.add(astList);
				}
			}
			
			permutations = newPermutations;
		}
	}
}

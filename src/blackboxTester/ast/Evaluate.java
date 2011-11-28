package blackboxTester.ast;

import java.util.ArrayList;
import java.util.HashMap;

import blackboxTester.parser.ast.Equation;
import blackboxTester.parser.ast.Operation;
import blackboxTester.parser.ast.RHS;
import blackboxTester.parser.ast.RHSFalse;
import blackboxTester.parser.ast.RHSInteger;
import blackboxTester.parser.ast.RHSOperation;
import blackboxTester.parser.ast.RHSPrimitiveOperation;
import blackboxTester.parser.ast.RHSTrue;
import blackboxTester.parser.ast.Term;
import blackboxTester.parser.ast.Variable;

/**
 * The Class Evaluate provides one public method that will try to replace and 
 * reduce a List of ASTs. There are several methods that will be used to assist 
 * the replace method
 * 
 * @see Evaluate#replace(ArrayList, ArrayList)
 *
 */
public class Evaluate  {
	
	private static final int MAX_REWRITE_ATTEMPTS = 100;

	/**
	 * Reduces all the ASTs using the provided equations. Replace will ignore any AST 
	 * that could not be reduced.
	 * 
	 * @param asts List of ASTs to be reduced
	 * @param eqList List of equations used for the reduction
	 */
	public ArrayList<AST> replace(ArrayList<AST> asts, ArrayList<Equation> eqList) {
		ArrayList<AST> reducedASTs = new ArrayList<AST>();
		for(AST ast : asts) {
			AST reducedAST = ast.deepCopy();
			
			try {
				AST newAST = rewrite(reducedAST, eqList, 0);
				
				if (newAST != null) {
					// replace the current ast in asts with the new ast
					reducedAST = newAST;
				}
			} catch (InfiniteRewriteException e) {
				// no-op, we just want to break out of our infinite loop
			}
			
			reducedASTs.add(reducedAST);
		}
		
		return reducedASTs;
	}

	/**
	 * Rewrite will attempt to match the given AST with the given equation.
	 * If both match, a reduced AST will be returned. If it doesn't match at first,
	 * it will attempt to go through each FunctionCall in the AST and attempt
	 * to match the given equation
	 * 
	 * @param ast the AST to be rewritten
	 * @param equation Equation used to rewrite the AST
	 * @return AST, whether the AST matches the equation or not
	 * @throws InfiniteRewriteException 
	 */
	private AST rewrite(AST ast, ArrayList<Equation> equations, int counter) throws InfiniteRewriteException {
		if (counter > MAX_REWRITE_ATTEMPTS) {
			throw new InfiniteRewriteException();
		}
				
		if (ast.isFullyReduced()) {
			return null;
		}
		
		IFunctionCall functionCall = (IFunctionCall) ast;
		ArrayList<AST> functionArgs = functionCall.getArgs();
		
		boolean rewritten = false;
		
		for(int index = 0; index < functionArgs.size(); index++) {
			AST arg = functionArgs.get(index);
			if (!arg.isFullyReduced()) {
				AST newArg = rewrite(arg, equations, 0);
				if (newArg != null){
					functionArgs.set(index, newArg);
					rewritten = true;
				}
			}
		}
			
		if (!functionCall.isPrimitive()) {
			for (Equation equation : equations) {
				AST newAST = match(ast, equation);
				if (newAST != null) {
					ast = newAST;
					
					newAST = rewrite(newAST, equations, ++counter);
					
					if (newAST != null) {
						ast = newAST;
					}
					
					rewritten = true;
				}
			}
		}
		
		return rewritten ? ast : null;
	}

	/**
	 * Match takes a single AST and an equation and attempts to generate
	 * a new AST using the equation.  It will return null if it is unable
	 * to do this.
	 * 
	 * This function does not recursively try to match sub-asts.
	 * 
	 * @param ast The AST to match against.
	 * @param equations The equation to do the matching.
	 * @return The rewritten AST or null if it didn't match.
	 */
	private AST match(AST ast, Equation equations) {
		HashMap <String, AST> env = generateENV(
			ast, equations.getLeftHandSide(), new HashMap<String, AST>()
		);
		
		if (env == null) {
			return null;
		}
		return rewriteWithEnv(equations.getRightHandSide(), env);
	}

	/**
	 * Given an AST and the left hand side term of an equation, generate
	 * an environment that maps variables in the equation to ASTs.
	 * 
	 * @param ast The AST to generate the environment from.
	 * @param leftHandside The left hand side of a re-write equation.
	 * @param env The current environment.
	 * @return The update environment or null if the left hand side doesn't
	 * match the ast.
	 */
	private HashMap<String, AST> generateENV(
		AST ast, 
		Term leftHandside, 
		HashMap<String, AST> env
	) {
		if(leftHandside instanceof Variable) {
			env.put(((Variable) leftHandside).getName(), ast);
			return env;
		}
		if (!(ast instanceof FunctionCall)) return null;
		if(!((Operation)leftHandside).getName().equals(((FunctionCall)ast).getMethodName())) {
			return null;
		}
		
		if (((FunctionCall)ast).getArgs().size() != ((Operation)leftHandside).getArgs().size()) {
			return null;
		}
		
		int i;
		for (i = 0; i < ((FunctionCall)ast).getArgs().size(); i++) {
			env = generateENV(
					((FunctionCall)ast).getArgs().get(i), 
					((Operation)leftHandside).getArgs().get(i), 
					env);
			if (env == null) {
				return env;
			}		
		}
		return env;
	}

	/**
	 * Given an environment that maps variables to ASTs, and the right
	 * hand side of a re-write equation, create a new AST.
	 * 
	 * @param rightHandside The right hand side of a re-write equation.
	 * @param env An environment mapping variables to ASTs.
	 * @return A new AST rewritten from the right hand side and the environment.
	 */
	private AST rewriteWithEnv(RHS rightHandside, HashMap<String, AST> env) {
		if (rightHandside instanceof Variable) {
			return env.get(((Variable)rightHandside).getName());
		} else if (rightHandside instanceof RHSTrue) {
			return new PrimitiveAST.BooleanAST(true);
		} else if (rightHandside instanceof RHSFalse) {
			return new PrimitiveAST.BooleanAST(false);
		} else if (rightHandside instanceof RHSInteger) {
			return new PrimitiveAST.IntegerAST(((RHSInteger)rightHandside).getValue());
		} else if (rightHandside instanceof RHSOperation) {
			RHSOperation operation = (RHSOperation)rightHandside;
			
			ArrayList<AST> args = new ArrayList<AST>();
			for(RHS arg : operation.getArgs()) {
				args.add(rewriteWithEnv(arg, env));
			}
			return new FunctionCall(operation.getName(), args);
		} else {
			RHSPrimitiveOperation operation = (RHSPrimitiveOperation)rightHandside;
			
			ArrayList<AST> args = new ArrayList<AST>();
			for(RHS arg : operation.getArgs()) {
				args.add(rewriteWithEnv(arg, env));
			}
			return new PrimitiveAST.PrimitiveFunctionCall(operation.getOperation(), args);
		}
	}
	
	@SuppressWarnings("serial")
	private static class InfiniteRewriteException extends Exception {}
}

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
	 * Reduces all the ASTs using the provided equations. Replace will ignore 
	 * any AST that could not be reduced, created an infinite loop of 
	 * reductions, or was non-deterministic.
	 * 
	 * @param asts List of ASTs to be reduced
	 * @param eqList List of equations used for the reduction
	 */
	public ArrayList<AST> replace(ArrayList<AST> asts, ArrayList<Equation> eqList) {
		ArrayList<AST> reducedASTs = new ArrayList<AST>();
		
		// for each of the given asts
		for(AST ast : asts) {
			// create a copy because the reduction will mutate
			AST reducedAST = ast.deepCopy();
			
			try {
				// attempt the rewrite
				AST newAST = rewrite(reducedAST, eqList, 0);
				
				// if we were successful update the reducedAST 
				if (newAST != null) {
					reducedAST = newAST;
				}
			} catch (InfiniteRewriteException e) {
				// no-op, we just want to break out of our infinite loop
			} catch (NonDeterministicException e) {
				// no-op, we just want to ignore non-deterministic expressions
			}
			
			// add our newly reduced ast to the return list
			reducedASTs.add(reducedAST);
		}
		
		return reducedASTs;
	}

	/**
	 * Rewrite will attempt to recursively rewrite the given AST with the list
	 * of equations.  If no rewrite occurs it will return null.
	 * 
	 * This rewriting is done from the inside out.
	 * 
	 * @param ast the AST to be rewritten
	 * @param equations Equations used to rewrite the AST
	 * @param counter The number of times this has recursively been called
	 * to rewrite the current AST.
	 * @return AST the rewritten AST, or null if no rewrite occurred.
	 * @throws InfiniteRewriteException if this rewrites infinitely.
	 * @throws NonDeterministicException if this rewrites non-deterministically.
	 */
	private AST rewrite(
		AST ast,
		ArrayList<Equation> equations, 
		int counter
	) throws InfiniteRewriteException, NonDeterministicException {
		// if we've rewritten too many times, throw an exception
		if (counter > MAX_REWRITE_ATTEMPTS) {
			throw new InfiniteRewriteException();
		}
				
		// if this is already fully reduced (a primitive type that contains
		// only primitive types) it can't be rewritten.
		if (ast.isFullyReduced()) {
			return null;
		}
		
		// If it's not fully reduced it must be a function call (either
		// primitive or non-primitive at this point).
		IFunctionCall functionCall = (IFunctionCall) ast;
		ArrayList<AST> functionArgs = functionCall.getArgs();
		
		boolean rewritten = false;
		
		// attempt to rewrite all the arguments of the function
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
			
		// if this isn't a primitive function, attempt to rewrite it
		if (!functionCall.isPrimitive()) {
			// for each equation
			for (Equation equation : equations) {
				// check to see if we have a match
				AST newAST = match(ast, equation, equations);
				if (newAST != null) {
					// if we do, update the equation
					ast = newAST;
					
					// recursively rewrite the equation, incrementing the
					// recursion counter
					newAST = rewrite(newAST, equations, ++counter);
					
					// update it again if we successfully reduced again
					if (newAST != null) {
						ast = newAST;
					}
					
					rewritten = true;
					break;
				}
			}
		}
		
		// if we rewrote, return that value, otherwise return null
		return rewritten ? ast : null;
	}

	/**
	 * Match takes a single AST and an equation and attempts to generate
	 * a new AST using the equation.  It will return null if it is unable
	 * to do this.  It also takes a list of equations for use in checking
	 * for non-deterministic rewrites.
	 * 
	 * This function does not recursively try to match sub-asts.
	 * 
	 * @param ast The AST to match against.
	 * @param equations The equation to do the matching.
	 * @param equations The full list of rewrite equations.
	 * @return The rewritten AST or null if it didn't match.
	 * @throws NonDeterministicException when the expression doesn't
	 * deterministically rewrite.
	 */
	private AST match(
		AST ast, 
		Equation equation, 
		ArrayList<Equation> equations
	) throws NonDeterministicException {
		// attempt to generate an environment mapping from the left hand
		// side of the equation
		HashMap <String, AST> env = generateEnv(
			ast, equation.getLeftHandSide(), new HashMap<String, AST>()
		);
		
		// if we couldn't generate a mapping, there was no match so return null
		if (env == null) {
			return null;
		} else if (matchCount(ast, equations) > 1) {
			// if we did match, check to see that we're only matching one
			// rewrite equations and not multiple.  If we do match multiple
			// this must be non-deterministic, so throw an exception.
			throw new NonDeterministicException();
		}
		
		// if we successfully matched, rewrite the equation using the 
		// right hand side and the generated environment.
		return rewriteWithEnv(equation.getRightHandSide(), env);
	}
	
	/**
	 * Calculate how many of the given rewrite equations the given ast matches.
	 * 
	 * @param ast The AST to find out how many equations it matches.
	 * @param equations The list of rewrite equations.
	 * @return The number of matches.
	 */
	private int matchCount(AST ast, ArrayList<Equation> equations) {
		int count = 0;
		
		for(Equation equation : equations) {
			if (
				generateEnv(
					ast, equation.getLeftHandSide(), new HashMap<String,AST>()
				) != null
			) {
				count++;
			}
		}
		
		return count;
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
	private HashMap<String, AST> generateEnv(
		AST ast, 
		Term leftHandside, 
		HashMap<String, AST> env
	) {
		// if this is a variable, update the environment and return it
		if(leftHandside.isVariable()) {
			env.put(((Variable) leftHandside).getName(), ast);
			return env;
		}
		
		// if this is a primitive, it can't be rewritten, so return null
		if (ast.isPrimitive()) {
			return null;
		}
		
		Operation lhsOp = (Operation)leftHandside;
		FunctionCall functCall = (FunctionCall)ast;
		
		// otherwise, if the left hand side's name doesn't equal 
		// the ast's name, there was no match, so return null
		if(!lhsOp.getName().equals(functCall.getMethodName())) {
			return null;
		}
		
		// otherwise, if the left hand side function has a different number
		// of arguments from the ast, we don't match.
		if (functCall.getArgs().size() != lhsOp.getArgs().size()) {
			return null;
		}
		
		// recursively generate the environment using the arguments, and
		// return null if one of them doesn't match.
		int i;
		for (i = 0; i < ((FunctionCall)ast).getArgs().size(); i++) {
			env = generateEnv(
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
	
	@SuppressWarnings("serial")
	private static class NonDeterministicException extends Exception {}
}

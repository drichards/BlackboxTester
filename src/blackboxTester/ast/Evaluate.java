package blackboxTester.ast;

import java.util.ArrayList;
import java.util.HashMap;
import blackboxTester.ast.AST;
import blackboxTester.ast.FunctionCall;
import blackboxTester.parser.ast.Term;
import blackboxTester.parser.ast.Variable;
import blackboxTester.parser.ast.Operation;
import blackboxTester.parser.ast.Equation;

/**
 * The Class Evaluate provides one public method that will try to replace and 
 * reduce a List of ASTs. There are several methods that will be used to assist 
 * the replace method
 * 
 * @see Evaluate#replace(ArrayList, ArrayList)
 *
 */
public class Evaluate  {

	/**
	 * Reduces all the ASTs using the provided equations. Replace will ignore any AST 
	 * that could not be reduced.
	 * 
	 * @param asts List of ASTs to be reduced
	 * @param eqList List of equations used for the reduction
	 */
	public void replace(ArrayList<AST> asts, ArrayList<Equation> eqList) {
		AST newAST = null;
		for(int index = 0; index < asts.size(); index++) {
			AST arg = asts.get(index);
			Boolean replaced;
			do {
				replaced = false;
				for(Equation eq : eqList) {
					newAST = this.rewrite(arg, eq);
					if (newAST != null) {
						// replace the current ast in asts with the new ast
						asts.set(index, newAST);
						arg = newAST;
						replaced = true;
					}
				}
			} while (replaced);
		}
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
	 */
	private AST rewrite(AST ast, Equation equation ) {
		AST newAST = match(ast, equation);
		if (newAST != null) {
			return newAST;
		}
		if (!(ast instanceof FunctionCall)) return null;
		ArrayList<AST> functionArgs = ((FunctionCall) ast).getArgs();
		for(int index = 0; index < functionArgs.size(); index++) {
			AST arg = functionArgs.get(index);
			if (!arg.isPrimitive()) {
				AST newArg = rewrite(arg, equation);
				if (newArg != null){
					((FunctionCall)(ast)).getArgs().set(index, newArg);
					return ast;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param ast
	 * @param equations
	 * @return
	 */
	private AST match(AST ast, Equation equations) {
		HashMap <String, AST> env = generateENV(ast, equations.getLeftHandSide(), new HashMap<String, AST>());
		if (env == null) {
			return null;
		}
		return rewriteWithEnv(equations.getRightHandSide(), env);
	}

	private HashMap<String, AST> generateENV(AST ast, Term leftHandside, HashMap<String, AST> env) {
		if(leftHandside instanceof Variable) {
			env.put(((Variable) leftHandside).getName(), ast);
			return env;
		}
		if (!(ast instanceof FunctionCall)) return null;
		if(!((Operation)leftHandside).getName().equals(((FunctionCall)ast).getMethodName())) {
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

	private AST rewriteWithEnv(Term rightHandside, HashMap<String, AST> env) {
		if (rightHandside instanceof Variable) {
			return env.get(((Variable)rightHandside).getName());
		}
		ArrayList<AST> args = new ArrayList<AST>();
		for(Term arg : ((Operation)rightHandside).getArgs()) {
			args.add(rewriteWithEnv(arg, env));
		}
		return new FunctionCall(((Operation)rightHandside).getName(), args);
	}
}

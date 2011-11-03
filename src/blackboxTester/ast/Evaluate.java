package blackboxTester.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import blackboxTester.ast.AST;
import blackboxTester.ast.FunctionCall;
import blackboxTester.parser.ast.Term;
import blackboxTester.parser.ast.Variable;
import blackboxTester.parser.ast.Operation;
import blackboxTester.parser.ast.Equation;


public class Evaluate  {


	public void replace(ArrayList<AST> asts, ArrayList<Equation> eqList) {
		AST newAST = null;
		for(int index = 0; index < asts.size(); index++) {
			AST arg = asts.get(index);
			HashSet<String> seenASTs = new HashSet<String>();
			seenASTs.add(arg.getHash());
			
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
						
						String hash = newAST.getHash();
						
						if (seenASTs.contains(hash)) {
							replaced = false;
							break;
						} else {
							seenASTs.add(hash);
						}
					}
				}
			} while (replaced);
		}
	}


	//	AST rewrite
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
					//this.rewrite(newArg, equation);
					((FunctionCall)(ast)).getArgs().set(index, newArg);
					return ast;
				}
			}
		}
		return null;
	}

	//	AST match

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
				env
			);
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

package blackboxTester.ast;

import java.util.ArrayList;
import java.util.HashMap;
import blackboxTester.ast.AST;
import blackboxTester.ast.FunctionCall;
import blackboxTester.ast.PrimitiveAST;
import blackboxTester.parser.ast.OpSpec;
import blackboxTester.parser.ast.PrimitiveType;
import blackboxTester.parser.ast.Signature;
import blackboxTester.parser.ast.Type;
import blackboxTester.parser.ast.Term;
import blackboxTester.parser.ast.Variable;
import blackboxTester.parser.ast.Operation;
import blackboxTester.parser.ast.Equation;


public class Evaluate  {


	public AST replace(AST ast, ArrayList<Equation> eqList) {
		AST newAST = null;
		for(AST arg : ((FunctionCall) ast).getArgs()) {
			Boolean replaced = false;
			for(Equation eq : eqList) {
				newAST = this.rewrite(arg, eq);
				if (newAST != null) {
					this.replace(newAST, eqList);
					replaced = true;
				}
			}
			while (replaced);
		}
		return newAST;
	}


	//	AST rewrite
	private AST rewrite(AST ast, Equation equation ) {
		AST newAST = match(ast, equation);
		if (newAST != null) {
			return newAST;
		}
		for(AST arg: ((FunctionCall) ast).getArgs()) {
			if (!arg.isPrimitive()) {
				AST newArg = rewrite(arg, equation);
				if (newArg != null){
					this.rewrite(newArg, equation);
					return ast;
				}
			}
		}
		return null;
	}

	//	AST match

	private AST match(AST ast, Equation equations) {
		HashMap <String, AST> env = generateENV(ast, equations.getRightHandSide(), null);
		if (env == null) {
			return null;
		}
		return rewriteWithEnv(equations.getRightHandSide(), env);
	}


	private HashMap<String, AST> generateENV(AST ast, Term leftHandside, HashMap<String, AST> env) {
		if(leftHandside instanceof Variable) {
			env.put(leftHandside, ast);
			return env;
		}
		if(leftHandside.operation != ast.operation) {
			return null;
		}
		int i;
		for (i = 0; i < ast.args.length(); i++) {
			env = generateENV(ast.args[i], leftHandside.args[i], env);
			if (env == null) {
				return env;
			}
			return env;
		}

	}

	private AST rewriteWithEnv(Term rightHandside, HashMap<String, AST> env) {
		if (rightHandside instanceof String) {
			return env.get(rightHandside);
		}
		ArrayList<AST> args = new ArrayList<AST>();
		for(AST args : rightHandside.arg) {
			args.add(rewriteWithEnv(args, env));
		}
		return new FunctionCall(rightHandside.operation, args);
	}
}

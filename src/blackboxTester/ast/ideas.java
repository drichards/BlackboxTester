package blackboxTester.ast;

import java.util.ArrayList;
import java.util.HashMap;

import blackboxTester.parser.ast.Equation;

public class ideas {
	
	void replace(AST ast, Equation eq){
		for(AST ast : ast) {
			Boolean replaced = false;
			for(Equation eq) {
				AST newAST= new AST(rewrite(ast, eq))
				if (newAST != null) {
					ast.replace(ast, newAST);
					replaced = true;
				}
			}
			while (replaced);
		}
	}

//	AST rewrite
	AST rewrite(AST ast, Equation equation ) {
		AST newAST = match(ast, equation);
		if (newAST != null) {
			return newAST;
		}
		for(AST arg: args) {
			if (!arg.isPrimitive()) {
				AST newArg = rewrite(arg, equation);
				if (newArg != null){
					args.replace(newArg, arg);
					return ast;
				}
			}
		}
		return null;
	}
	
//	AST match
	
	AST match(AST ast, Equation equations) {
		HashMap <String, AST> env = generateENV(ast, equations.rightHandSide(), HashMap<String, AST>);
		if (env == null) {
			return null;
		}
		return rewriteWithEnv(equations.rightHandSide(), env);
	}
	
	
	HashMap<String, AST> generateENV(AST ast, Term leftHandside, HashMap<String, AST> env) {
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
	
	AST rewriteWithEnv(Term rightHandside, HashMap<String, AST> env) {
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

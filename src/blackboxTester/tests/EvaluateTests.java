package blackboxTester.tests;

import java.util.ArrayList;

import junit.framework.TestCase;
import blackboxTester.ast.AST;
import blackboxTester.ast.Evaluate;
import blackboxTester.ast.FunctionCall;
import blackboxTester.ast.PrimitiveAST;
import blackboxTester.parser.ast.Equation;
import blackboxTester.parser.ast.Operation;
import blackboxTester.parser.ast.RHS;
import blackboxTester.parser.ast.RHSFalse;
import blackboxTester.parser.ast.RHSOperation;
import blackboxTester.parser.ast.RHSPrimitiveOperation;
import blackboxTester.parser.ast.Term;
import blackboxTester.parser.ast.Variable;

public class EvaluateTests extends TestCase {
	public void testReplace() {
		Evaluate e = new Evaluate();
		
		ArrayList<AST> asts = new ArrayList<AST>();
		ArrayList<AST> args = new ArrayList<AST>();
		
		args.add(new PrimitiveAST.StringAST("foo"));
		args.add(new PrimitiveAST.BooleanAST(false));
		
		asts.add(new FunctionCall("foo", args));
		asts.add(new FunctionCall("bar", args));
		
		ArrayList<Equation> eqs = new ArrayList<Equation>();
		ArrayList<Term> termArgs = new ArrayList<Term>();
		
		termArgs.add(new Variable("m"));
		termArgs.add(new Variable("n"));
		
		eqs.add(new Equation(new Operation("foo", termArgs), new Variable("m")));

		ArrayList<AST> replacements = e.replace(asts, eqs);
		
		assertTrue(replacements.get(0).isFullyReduced());
		assertTrue(replacements.get(0).toString().equals("\"foo\""));
		assertFalse(replacements.get(1).isFullyReduced());
	}
	
	public void testComplexReplace() {
		Evaluate e = new Evaluate();
		
		ArrayList<AST> asts = new ArrayList<AST>();
		ArrayList<AST> args = new ArrayList<AST>();
		
		args.add(new FunctionCall("baz", new ArrayList<AST>()));
		
		asts.add(new FunctionCall("foo", args));
		asts.add(new FunctionCall("bar", args));
		
		ArrayList<Equation> eqs = new ArrayList<Equation>();
		ArrayList<Term> termArgs = new ArrayList<Term>();
		ArrayList<RHS> rhsArgs = new ArrayList<RHS>();
		
		termArgs.add(new Variable("m"));
		rhsArgs.add(new Variable("m"));
		
		eqs.add(new Equation(new Operation("baz", new ArrayList<Term>()), new RHSFalse()));
		eqs.add(new Equation(new Operation("foo", termArgs), new RHSPrimitiveOperation("not", rhsArgs)));

		ArrayList<AST> replacements = e.replace(asts, eqs);
		
		assertTrue(replacements.get(0).isFullyReduced());
		assertFalse(replacements.get(1).isFullyReduced());
	}
	
	public void testReplaceInfiniteLoop() {
		Evaluate e = new Evaluate();
		
		ArrayList<AST> asts = new ArrayList<AST>();
		ArrayList<AST> args = new ArrayList<AST>();
		
		args.add(new PrimitiveAST.StringAST("foo"));
		args.add(new FunctionCall("baz", new ArrayList<AST>()));
		
		asts.add(new FunctionCall("foo", args));
		asts.add(new FunctionCall("bar", args));
		
		ArrayList<Equation> eqs = new ArrayList<Equation>();
		ArrayList<Term> termArgs = new ArrayList<Term>();
		
		termArgs.add(new Variable("m"));
		termArgs.add(new Variable("n"));
		
		eqs.add(new Equation(new Operation("foo", termArgs), new Variable("m")));
		eqs.add(new Equation(new Operation("baz", new ArrayList<Term>()), new RHSOperation("baz", new ArrayList<RHS>())));

		ArrayList<AST> replacements = e.replace(asts, eqs);
		
		assertFalse(replacements.get(0).isFullyReduced());
		assertFalse(replacements.get(1).isFullyReduced());
	}
	
	public void testReplaceNonDeterminism() {
		Evaluate e = new Evaluate();
		
		ArrayList<AST> asts = new ArrayList<AST>();
		ArrayList<AST> args = new ArrayList<AST>();
		
		args.add(new PrimitiveAST.StringAST("foo"));
		args.add(new FunctionCall("baz", new ArrayList<AST>()));
		
		asts.add(new FunctionCall("foo", args));
		asts.add(new FunctionCall("bar", args));
		
		ArrayList<Equation> eqs = new ArrayList<Equation>();
		ArrayList<Term> termArgs = new ArrayList<Term>();
		
		termArgs.add(new Variable("m"));
		termArgs.add(new Variable("n"));
		
		eqs.add(new Equation(new Operation("foo", termArgs), new Variable("m")));
		eqs.add(new Equation(new Operation("foo", termArgs), new Variable("n")));

		ArrayList<AST> replacements = e.replace(asts, eqs);
		
		assertFalse(replacements.get(0).isFullyReduced());
		assertFalse(replacements.get(1).isFullyReduced());
	}
}

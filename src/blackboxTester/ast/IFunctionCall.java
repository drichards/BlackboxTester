package blackboxTester.ast;

import java.util.ArrayList;

public interface IFunctionCall extends AST {
	/**
	 * 
	 * @return ArrayList<AST> of this FunctionCall's Arguments
	 */
	public ArrayList<AST> getArgs();
}

package blackboxTester.ast;

import java.util.ArrayList;

/**
 * An interface encapsulating function calls, both primitive and non-primitive.
 */
public interface IFunctionCall extends AST {
	/**
	 * @return ArrayList<AST> of this FunctionCall's Arguments
	 */
	public ArrayList<AST> getArgs();
}

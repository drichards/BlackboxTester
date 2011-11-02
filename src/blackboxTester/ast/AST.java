package blackboxTester.ast;

/**
 * An Abstract Syntax Tree that describes the output blackbox test code.
 *
 */
public interface AST{
	@Override
	public String toString();
	
	/**
	 * @return true if this AST represents a primitive type, false otherwise.
	 */
	public boolean isPrimitive();
	
	/**
	 * @return A deep copy of this AST.
	 */
	public AST deepCopy();
}

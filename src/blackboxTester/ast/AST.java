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
	
	/**
	 * @return An MD5 hash of this ast
	 */
	public String getHash();
	
	/**
	 * @return True if this AST is in it's fully reduced primitive form.
	 */
	public boolean isFullyReduced();
}

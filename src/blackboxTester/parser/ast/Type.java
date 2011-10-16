package blackboxTester.parser.ast;

/**
 * An interface representing types of the parsed input.
 */
public interface Type {
	/**
	 * @return True if this type is a primitive type.
	 */
	public boolean isPrimitive();
}

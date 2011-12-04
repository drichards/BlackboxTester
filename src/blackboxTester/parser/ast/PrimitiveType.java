package blackboxTester.parser.ast;

/**
 * An abstract syntax tree representing a primitive type.
 */
public abstract class PrimitiveType implements Type {
	/**
	 * The name of this type
	 */
	protected String type;
	
	/**
	 * Create a new type with the given name.
	 * 
	 * @param type
	 */
	protected PrimitiveType(String type) {
		this.type = type;
	}
	
	@Override
	public boolean isPrimitive() {
		return true;
	}
	
	//TODO: public String equalityComparator() {}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof PrimitiveType) && 
			((PrimitiveType)other).type.equals(this.type);
	}
	
	@Override
	public int hashCode() {
		return type.hashCode();
	}
	
	/**
	 * Abstract syntax tree representing an integer type in the parsed input.
	 */
	public static class IntegerType extends PrimitiveType {

		public IntegerType() {
			super("int");
		}
	}

	/**
	 * Abstract syntax tree representing a string type in the parsed input.
	 */
	public static class StringType extends PrimitiveType {

		public StringType() {
			super("string");
		}
	}

	/**
	 * Abstract syntax tree representing a boolean type in the parsed input.
	 */
	public static class BooleanType extends PrimitiveType {

		public BooleanType() {
			super("boolean");
		}
	}
	
	/**
	 * Abstract syntax tree representing a character type in the parsed input.
	 */
	public static class CharacterType extends PrimitiveType {

		public CharacterType() {
			super("character");
		}
	}
}

package parser.ast;

public abstract class PrimitiveType implements Type {
	protected String type;
	
	protected PrimitiveType(String type) {
		this.type = type;
	}
	
	public boolean isPrimitive() {
		return true;
	}
	
	public boolean equals(Object other) {
		return (other instanceof PrimitiveType) && 
			((PrimitiveType)other).type.equals(this.type);
	}
	
	public int hashCode() {
		return type.hashCode();
	}
	
	public static class IntegerType extends PrimitiveType {

		public IntegerType() {
			super("int");
		}
	}

	public static class StringType extends PrimitiveType {

		public StringType() {
			super("string");
		}
	}

	public static class BooleanType extends PrimitiveType {

		public BooleanType() {
			super("boolean");
		}
	}
	
	public static class CharacterType extends PrimitiveType {

		public CharacterType() {
			super("character");
		}
	}
}

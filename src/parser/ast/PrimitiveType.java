package parser.ast;

public abstract class PrimitiveType implements Type {
	public boolean isPrimitive() {
		return true;
	}
	
	public static class IntegerType extends PrimitiveType {}

	public static class StringType extends PrimitiveType {}

	public static class BooleanType extends PrimitiveType {}
	
	public static class CharacterType extends PrimitiveType {}


}

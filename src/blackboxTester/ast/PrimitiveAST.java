package blackboxTester.ast;

public abstract class PrimitiveAST implements AST {
	private String type;
	
	protected PrimitiveAST(String type) {
		this.type = type;
	}
	
	public String toString() {
		return type;
	}
	
	public boolean isPrimitive() {
		return true;
	}
	
	public static class BooleanAST extends PrimitiveAST { 
		public BooleanAST() {
			super("boolean");
		}
	}
	
	public static class CharacterAST extends PrimitiveAST { 
		public CharacterAST() {
			super("character");
		}
	}
	
	public static class IntegerAST extends PrimitiveAST { 
		public IntegerAST() {
			super("int");
		}
	}
	
	public static class StringAST extends PrimitiveAST { 
		public StringAST() {
			super("string");
		}
	}
}

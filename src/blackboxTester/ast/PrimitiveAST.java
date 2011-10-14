package blackboxTester.ast;

import blackboxTester.ast.generator.RandomPrimitiveGenerator;

public abstract class PrimitiveAST implements AST {
	private String type;
	
	protected PrimitiveAST(String type) {
		this.type = type;
	}
	
	public boolean isPrimitive() {
		return true;
	}
	
	public static class BooleanAST extends PrimitiveAST { 
		public BooleanAST() {
			super("boolean");
		}
		
		public String toString() {
			return String.valueOf(RandomPrimitiveGenerator.genBool());
		}
	}
	
	public static class CharacterAST extends PrimitiveAST { 
		public CharacterAST() {
			super("character");
		}
		
		public String toString() {
			return String.valueOf(RandomPrimitiveGenerator.genChar());
		}
	}
	
	public static class IntegerAST extends PrimitiveAST { 
		public IntegerAST() {
			super("int");
		}
		
		public String toString() {
			return String.valueOf(RandomPrimitiveGenerator.genInt());
		}
	}
	
	public static class StringAST extends PrimitiveAST { 
		public StringAST() {
			super("string");
		}
		
		public String toString() {
			return "\"" + RandomPrimitiveGenerator.genString() + "\"";
		}
	}
}

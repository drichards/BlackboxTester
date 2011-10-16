package blackboxTester.ast;

import blackboxTester.ast.generator.RandomPrimitiveGenerator;

/**
 * The superclass of all primitive abstract syntax trees (ASTs).
 *
 */
public abstract class PrimitiveAST implements AST {
	protected PrimitiveAST() {}
	
	@Override
	public boolean isPrimitive() {
		return true;
	}
	
	/**
	 * A primitive abstract syntax tree representing booleans.
	 */
	public static class BooleanAST extends PrimitiveAST { 
		@Override
		public String toString() {
			return String.valueOf(RandomPrimitiveGenerator.genBool());
		}
	}
	
	/**
	 * A primitive abstract syntax tree representing characters.
	 */
	public static class CharacterAST extends PrimitiveAST { 
		@Override
		public String toString() {
			return String.valueOf(RandomPrimitiveGenerator.genChar());
		}
	}
	
	/**
	 * A primitive abstract syntax tree representing integers.
	 */
	public static class IntegerAST extends PrimitiveAST { 
		@Override
		public String toString() {
			return String.valueOf(RandomPrimitiveGenerator.genInt());
		}
	}
	
	/**
	 * A primitive abstract syntax tree representing strings.
	 */
	public static class StringAST extends PrimitiveAST { 
		@Override
		public String toString() {
			return "\"" + RandomPrimitiveGenerator.genString() + "\"";
		}
	}
}

package blackboxTester.ast;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import blackboxTester.ast.generator.RandomPrimitiveGenerator;

/**
 * The superclass of all primitive abstract syntax trees (ASTs).
 *
 */
public abstract class PrimitiveAST implements AST, Serializable {
	protected PrimitiveAST() {}
	
	@Override
	public boolean isPrimitive() {
		return true;
	}
	
	@Override
	public AST deepCopy() {
		return this;
	}
	
	@Override
	public String getHash() {
		MessageDigest digest = null;
		
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		return new String(digest.digest(toString().getBytes()));
	}
	
	/**
	 * A primitive abstract syntax tree representing booleans.
	 */
	public static class BooleanAST extends PrimitiveAST { 
		Boolean value;
		
		public BooleanAST() {
			value = RandomPrimitiveGenerator.genBool();
		}
		
		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}
	
	/**
	 * A primitive abstract syntax tree representing characters.
	 */
	public static class CharacterAST extends PrimitiveAST { 
		char value;
		
		public CharacterAST() {
			value = RandomPrimitiveGenerator.genChar();
		}
		
		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}
	
	/**
	 * A primitive abstract syntax tree representing integers.
	 */
	public static class IntegerAST extends PrimitiveAST { 
		int value;
		
		public IntegerAST() {
			value = RandomPrimitiveGenerator.genInt();
		}
		
		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}
	
	/**
	 * A primitive abstract syntax tree representing strings.
	 */
	public static class StringAST extends PrimitiveAST { 
		String value;
		
		public StringAST() {
			value = "\"" + RandomPrimitiveGenerator.genString() + "\"";
		}
		@Override
		public String toString() {
			return value;
		}
	}
}

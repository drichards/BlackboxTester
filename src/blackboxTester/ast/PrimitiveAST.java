package blackboxTester.ast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import blackboxTester.ast.generator.RandomPrimitiveGenerator;

/**
 * The superclass of all primitive abstract syntax trees (ASTs).
 *
 */
public abstract class PrimitiveAST implements AST {
	protected PrimitiveAST() {}
	
	public String getComparator() {
		return null;
	}

	@Override
	public boolean isPrimitive() {
		return true;
	}
	
	@Override
	public AST deepCopy() {
		return this;
	}
	
	@Override
	public boolean isFullyReduced() {
		return true;
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
			this(RandomPrimitiveGenerator.genBool());
		}
		
		public BooleanAST(Boolean value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value ? "#t" : "#f";
		}
		
		@Override
		public String getComparator() {
			return "equal?";
		}
	}
	
	/**
	 * A primitive abstract syntax tree representing characters.
	 */
	public static class CharacterAST extends PrimitiveAST { 
		char value;
		
		public CharacterAST() {
			this(RandomPrimitiveGenerator.genChar());
		}
		
		public CharacterAST(char value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return String.valueOf(value);
		}
		
		@Override
		public String getComparator() {
			return "equal?";
		}
	}
	
	/**
	 * A primitive abstract syntax tree representing integers.
	 */
	public static class IntegerAST extends PrimitiveAST { 
		int value;
		
		public IntegerAST() {
			this(RandomPrimitiveGenerator.genInt());
		}
		
		public IntegerAST(int value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return String.valueOf(value);
		}
		
		@Override
		public String getComparator() {
			return "=";
		}
	}
	
	/**
	 * A primitive abstract syntax tree representing strings.
	 */
	public static class StringAST extends PrimitiveAST { 
		String value;
		
		public StringAST() {
			this(RandomPrimitiveGenerator.genString());
		}
		
		public StringAST(String value) {
			this.value ="\"" + value + "\"";
		}
		
		@Override
		public String toString() {
			return value;
		}

		@Override
		public String getComparator() {
			return "equal?";
		}
	}
	
	public static class PrimitiveFunctionCall extends PrimitiveAST implements IFunctionCall{
		private String operation;
		private ArrayList<AST> args;
		
		public PrimitiveFunctionCall(String operation, ArrayList<AST> args) {
			this.operation = operation;
			this.args = args;
		}
		
		@Override
		public ArrayList<AST> getArgs() {
			return args;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			
			builder.append("(");
			builder.append(operation);
			
			for (AST arg : args) {
				builder.append(" ");
				builder.append(arg);
			}
			
			builder.append(")");
			
			return builder.toString();
		}
		
		@Override
		public AST deepCopy() {
			ArrayList<AST> copiedArgs = new ArrayList<AST>();
			
			for (AST arg: args) {
				copiedArgs.add(arg.deepCopy());
			}
			
			return new PrimitiveFunctionCall(this.operation, copiedArgs);
		}
		
		@Override
		public boolean isFullyReduced() {
			for (AST arg : args) {
				if (!arg.isFullyReduced()) {
					return false;
				} 
			}
			
			return true;
		}
		
		@Override
		public String getComparator() {
			//TODO Is this always "="?
			return this.args.get(0).getComparator();
		}
	}
}

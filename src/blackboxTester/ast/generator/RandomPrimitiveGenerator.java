package blackboxTester.ast.generator;

import java.util.Random;

/**
 * The RandomPrimitiveGenerator class is used to generated random inputs
 * for the generated ASTs. The class contains methods <code>genInt</code> 
 * which returns numbers, <code>genString</code> which returns a random
 * pre-defined string, <code>genBool</code> which returns a random Boolean,
 * <code>genChar</code> which returns a random char
 * 
 * @see RandomPrimitiveGenerator#genInt()
 * @see RandomPrimitiveGenerator#genString()
 * @see RandomPrimitiveGenerator#genBool()
 * @see RandomPrimitiveGenerator#genChar()
 * 
 */
public class RandomPrimitiveGenerator {
	
	/**
	 * Generates a random number from 0-9 but only accepts the random numbers
	 * 30% of the time, only when the number is 0, 1 or 2. When that occurs
	 * pre-defined numbers are returned
	 * 
	 * @return A pre-defined number when the numbers 0, 1, or 2 is generated
	 */
	public static int genInt() {
		Random generator = new Random();
		int randInt = generator.nextInt(10);
		if (randInt == 0) {
			return -1;
		} else if (randInt == 1) {
			return 0;
		} else if (randInt == 2) {
			return 1;
		} else {
			return generator.nextInt();
		}
	}
	
	/**
	 * Used to return a random pre-defined string. The sets of strings are
	 * picked by random. 
	 * 
	 * @return A string from a pre-defined set of strings
	 */
	public static String genString() {
		Random generator = new Random();
		int randomInt = generator.nextInt(10);
		if (randomInt == 0) {
			return "";
		} else if (randomInt == 1) {
			return " ";
		} else if (randomInt == 2) {
			return " (empty()) ";
		} else if (randomInt == 3) {
			return "a";
		} else if (randomInt == 4) {
			return "!@#$%^&*()-=+_/'[]{}L:><.,`~";
		} else if (randomInt == 5) {
			return " fjdhfjhadslfkahjs lhajfkhad jsfh adjshfjhfl asdf hj ";
		} else if (randomInt == 6) {
			return "QEUIPD CB<XNCB SBDKJSAHUIOWH UWHOE QE";
		} else if (randomInt == 7) {
			return "\\";
		} else if (randomInt == 8) {
			return "\"+3";
		} else if (randomInt == 9) {
			return "@$(*C(XCLZKJKDFLN fnjknzcx89h \\";
		} else {
			return "blah";
		}
	}
	/**
	 * Creates a boolean by generating a random Double from 0 to 1
	 * and round it. Then comparing to 0
	 * 
	 * @return A random Boolean
	 */
	public static boolean genBool() {
		return (0 == Math.round(Math.random()));
	}
	
	/**
	 * Creates a random number and 0 to 255 and casts it to a char
	 * 
	 * @return A number casted in a char
	 */
	public static char genChar() {
		Random generator = new Random();
		int i = generator.nextInt(256);
		
		return (char) i;
	}
}
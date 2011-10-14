package blackboxTester.ast.generator;

import java.util.Random;

public class RandomPrimitiveGenerator {
	
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
	
	public static boolean genBool() {
		return (0 == Math.round(Math.random()));
	}
	
	public static char genChar() {
		Random generator = new Random();
		int i = generator.nextInt(256);
		
		return (char) i;
	}
}
package blackboxTester.tests;

import java.io.IOException;

import blackboxTester.parser.Parser;
import blackboxTester.parser.generated.ParseException;
import junit.framework.TestCase;

public class ParserTests extends TestCase {
	public void testGood() throws IOException {
		boolean caught = false;
		
		try {
			Parser.parse("assets/tests/goodSpec.txt");
		} catch (ParseException e) {
			caught = true;
		}
		
		assertFalse(caught);
	}
	
	public void testMismatchedRewriteRule() throws IOException {
		boolean caught = false;
		
		try {
			Parser.parse("assets/tests/badEquations1.txt");
		} catch (ParseException e) {
			caught = true;
		}
		
		assertTrue(caught);
	}
	
	public void testMissingVariable() throws IOException {
		boolean caught = false;
		
		try {
			Parser.parse("assets/tests/badEquations2.txt");
		} catch (ParseException e) {
			caught = true;
		}
		
		assertTrue(caught);
	}
	
	public void testDuplicateVariable() throws IOException {
		boolean caught = false;
		
		try {
			Parser.parse("assets/tests/badEquations3.txt");
		} catch (ParseException e) {
			caught = true;
		}
		
		assertTrue(caught);
	}
	
	public void testUnknownADT() throws IOException {
		boolean caught = false;
		
		try {
			Parser.parse("assets/tests/badSignatures1.txt");
		} catch (ParseException e) {
			caught = true;
		}
		
		assertTrue(caught);
	}
	
	public void testReservedOperationName() throws IOException {
		boolean caught = false;
		
		try {
			Parser.parse("assets/tests/badSignatures2.txt");
		} catch (ParseException e) {
			caught = true;
		}
		
		assertTrue(caught);
	}
	
	public void testBadUnicodeInitial() throws IOException {
		boolean caught = false;
		
		try {
			Parser.parse("assets/tests/badSignatures3.txt");
		} catch (ParseException e) {
			caught = true;
		}
		
		assertTrue(caught);
	}
}

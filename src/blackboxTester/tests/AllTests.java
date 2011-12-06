package blackboxTester.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for Blackbox Tester");

		suite.addTestSuite(EvaluateTests.class);
		suite.addTestSuite(OutputTests.class);
		suite.addTestSuite(ParserTests.class);

		return suite;
	}

}

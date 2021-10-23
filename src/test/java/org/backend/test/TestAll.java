package org.backend.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestAll {

	public static Test suite() {
		System.out.println("Coucou");
	    TestSuite suite= new TestSuite();
	    suite.addTestSuite(TestSimple.class);
	    suite.addTestSuite(TestLive.class);
	    return suite;
	}
}

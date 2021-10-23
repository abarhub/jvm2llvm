package org.backend.test;

import org.backend.gen.Outils;
import junit.framework.TestCase;

public class TestSimple extends TestCase {

	public void test1()
	{
		assertTrue(true);
	}
	
	public void test2()
	{
		float f=1.0f;
		String res;
		res=Outils.conv(f);
		System.out.println("res="+res);
		assertEquals("111111100000000000000000000000", res);
		System.out.println("res="+Outils.conv(-2.0f));
		res=Outils.conv(2.0f);
		assertEquals("1000000000000000000000000000000", res);
		res=Outils.conv(-2.0f);
		assertEquals("11000000000000000000000000000000", res);
		res=Outils.conv(-211.f/8.f);
		assertEquals("11000001110100110000000000000000", res);
	}
}

package org.backend.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.backend.gen.Environement;
//import org.backend.gen.asm8086.InstrAsm86;
//import org.backend.gen.asm8086.LiveVar;
import org.backend.ssa.intermediaire.Instr;
import org.backend.ssa.jvm.runtime.LiveVar;

public class TestLive extends TestCase {

	public void test1()
	{
		EnvTest env;
		LiveVar live;
		List<Set<String>> in,out;
		env=new EnvTest();
		env.add_instr(instr(set("a"),set("b")));
		live=new LiveVar(env);
		live.calcul();
		in=live.getIn();
		out=live.getOut();
		assertEquals(env.nb_instr(),in.size());
		assertEquals(in.size(), out.size());
		assertEquals(set("a"),in.get(0));
		assertEquals(set(),out.get(0));
	}

	public void test2()
	{
		EnvTest env;
		LiveVar live;
		List<Set<String>> in,out;
		env=new EnvTest();
		env.add_instr(instr(set("a"),set("b")));
		env.add_instr(instr("test_label"));
		env.add_instr(instr(set("b"),set("c")));
		live=new LiveVar(env);
		live.calcul();
		in=live.getIn();
		out=live.getOut();
		assertEquals(env.nb_instr(),in.size());
		assertEquals(in.size(), out.size());
		assertEquals(set("a"),in.get(0));
		assertEquals(set("b"),out.get(0));
		assertEquals(set("b"),in.get(1));
		assertEquals(set("b"),out.get(1));
		assertEquals(set("b"),in.get(2));
		assertEquals(set(),out.get(2));
	}

	public void test3()
	{
		EnvTest env;
		LiveVar live;
		List<Set<String>> in,out;
		env=new EnvTest();
		env.add_instr(instr(set("a"),set("b")));
		env.add_instr(instr(set(),set(),set("test_label"),false));
		env.add_instr(instr(set("d"),set("e")));
		env.add_instr(instr("test_label"));
		env.add_instr(instr(set("b"),set("c")));
		live=new LiveVar(env);
		live.calcul();
		in=live.getIn();
		out=live.getOut();
		assertEquals(env.nb_instr(),in.size());
		assertEquals(in.size(), out.size());
		assertEquals(set("a"),in.get(0));
		assertEquals(set("b"),out.get(0));
		assertEquals(set("b"),in.get(1));
		assertEquals(set("b"),out.get(1));
		assertEquals(set("b","d"),in.get(2));// est-ce que c'est normal ?
		assertEquals(set("b"),out.get(2));
		assertEquals(set("b"),in.get(3));
		assertEquals(set("b"),out.get(3));
		assertEquals(set("b"),in.get(4));
		assertEquals(set(),out.get(4));
	}

	public void test4()
	{
		EnvTest env;
		LiveVar live;
		List<Set<String>> in,out;
		env=new EnvTest();
		env.add_instr(instr(set("a"),set("b")));
		env.add_instr(instr(set("d"),set("b")));
		env.add_instr(instr(set("b"),set("c")));
		live=new LiveVar(env);
		live.calcul();
		in=live.getIn();
		out=live.getOut();
		assertEquals(env.nb_instr(),in.size());
		assertEquals(in.size(), out.size());
		assertEquals(set("a","d"),in.get(0));
		assertEquals(set("d"),out.get(0));
		assertEquals(set("d"),in.get(1));
		assertEquals(set("b"),out.get(1));
		assertEquals(set("b"),in.get(2));
		assertEquals(set(),out.get(2));
	}
	
	public void test5()
	{
		EnvTest env;
		LiveVar live;
		List<Set<String>> in,out;
		env=new EnvTest();
		env.add_instr(instr(set(),set("a")));
		env.add_instr(instr("L1"));
		env.add_instr(instr(set("a"),set("b")));
		env.add_instr(instr(set("c","b"),set("c")));
		env.add_instr(instr(set("b"),set("a")));
		env.add_instr(instr(set("a"),set(),set("L1"),true));
		env.add_instr(instr(set("c"),set()));
		live=new LiveVar(env);
		live.calcul();
		in=live.getIn();
		out=live.getOut();
		assertEquals(env.nb_instr(),in.size());
		assertEquals(in.size(), out.size());
		assertEquals(set("c"),in.get(0));
		assertEquals(set("a","c"),out.get(0));
		assertEquals(set("a","c"),in.get(1));
		assertEquals(set("a","c"),out.get(1));
		assertEquals(set("a","c"),in.get(2));
		assertEquals(set("b","c"),out.get(2));
		assertEquals(set("b","c"),in.get(3));
		assertEquals(set("b","c"),out.get(3));
		assertEquals(set("b","c"),in.get(4));
		assertEquals(set("a","c"),out.get(4));
		assertEquals(set("a","c"),in.get(5));
		assertEquals(set("a","c"),out.get(5));
		assertEquals(set("c"),in.get(6));
		assertEquals(set(),out.get(6));
	}
	
	public Instr instr(String label)
	{
		return new InstrTest(label+":",null,null,null,true);
	}
	
	public Instr instr(Set<String> src,Set<String> dest)
	{
		return instr(src,dest,null,true);
	}

	public Instr instr(Set<String> src,Set<String> dest,Set<String> label)
	{
		return instr(src,dest,label,true);
	}
	
	public Instr instr(Set<String> src,Set<String> dest,Set<String> label,boolean suite)
	{
		Instr instr;
		/*String tab1[],tab2[],tab3[];
		tab1=conv(src);
		tab2=conv(dest);
		tab3=conv(label);
		instr=new InstrAsm86("",tab1,tab2,tab3,suite);*/
		instr=new InstrTest("",src,dest,label,suite);
		return instr;
	}
	
	private String[] conv(Set<String> set) {
		String tab[];
		int i=0;
		if(set==null||set.isEmpty())
			return null;
		tab=new String[set.size()];
		for(String s:set)
		{
			tab[i]=s;
			i++;
		}
		return tab;
	}

	public Set<String> set(String... s)
	{
		Set<String> res;
		res=new HashSet<String>();
		if(s!=null)
		{
			for(String s2:s)
			{
				res.add(s2);
			}
		}
		return res;
	}

	public static Test suite() {
	    TestSuite suite= new TestSuite();
	    suite.addTestSuite(TestLive.class);
	    return suite;
	}
}

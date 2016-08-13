package org.backend.ssa.test;

import java.util.ArrayList;
import java.util.List;

//import junit.framework.Assert;

import org.junit.Test;
import org.junit.*;

import org.backend.ssa.intermediaire.Affect;
import org.backend.ssa.intermediaire.Appel;
import org.backend.ssa.intermediaire.Cst;
import org.backend.ssa.intermediaire.ExprOpe;
import org.backend.ssa.intermediaire.Goto;
import org.backend.ssa.intermediaire.If;
import org.backend.ssa.intermediaire.Instr;
import org.backend.ssa.intermediaire.Methode;
import org.backend.ssa.intermediaire.Operation;
import org.backend.ssa.intermediaire.Module;
import org.backend.ssa.intermediaire.Type;
import org.backend.ssa.intermediaire.Var;



public class ProgramTest {

	@Test
	public void test1()
	{
		Module p;
		Methode m;
		Var v1,v2,v3;
		Type t;
		String s;
		List<String> liste_erreurs;
		liste_erreurs=new ArrayList<String>();
		p=new Module();
		m=new Methode();
		p.getListe_methodes().add(m);
		
		t=new Type("int");
		v1=new Var("a",t);
		v2=new Var("b",t);
		v3=new Var("c",t);
		
		m.getListe_variables().add(v1);
		m.getListe_variables().add(v2);
		m.getListe_variables().add(v3);
		
		m.getListe_instructions().add(new Affect(v1,new Cst(1)));
		m.getListe_instructions().add(new Affect(v2,new Cst(50)));
		m.getListe_instructions().add(new Affect(v3,new Cst(86)));
		
		Assert.assertTrue("erreurs:"+liste_erreurs,m.est_valide(liste_erreurs));
		
		s=m.toString();
		Assert.assertEquals("a:int\nb:int\nc:int\na:=1\nb:=50\nc:=86\n", s);
		System.out.println("res="+p.toString());
	}

	@Test
	public void test2()
	{
		Module p;
		Methode m;
		Var v1,v2,v3,v4,v5;
		Type t;
		String s;
		Instr instr;
		List<String> liste_erreurs;
		liste_erreurs=new ArrayList<String>();
		p=new Module();
		m=new Methode();
		p.getListe_methodes().add(m);
		
		t=new Type("int");
		v1=new Var("a",t);
		v2=new Var("b",t);
		v3=new Var("c",t);
		v4=new Var("max",t);
		v5=new Var("res",t);
		
		m.getListe_variables().add(v1);
		m.getListe_variables().add(v2);
		m.getListe_variables().add(v3);
		m.getListe_variables().add(v4);
		m.getListe_variables().add(v5);
		
		m.getListe_instructions().add(new Affect(v1,new Cst(1)));
		m.getListe_instructions().add(new Affect(v2,new Cst(50)));
		m.getListe_instructions().add(new Affect(v3,new Cst(86)));
		
		m.getListe_instructions().add(new If(new ExprOpe(Operation.INFS,v1,v2),"suite2"));
		m.getListe_instructions().add(new If(new ExprOpe(Operation.INFS,v1,v3),"suite"));
		m.getListe_instructions().add(new Affect(v4,v1));
		m.getListe_instructions().add(new Goto("fin"));
		instr=new Affect(v4,v3);
		instr.getListe_labels().add("suite");
		m.getListe_instructions().add(instr);
		m.getListe_instructions().add(new Goto("fin"));
		
		instr=new If(new ExprOpe(Operation.INFS,v2,v3),"suite3");
		instr.getListe_labels().add("suite2");
		m.getListe_instructions().add(instr);
		m.getListe_instructions().add(new Affect(v4,v3));
		m.getListe_instructions().add(new Goto("fin"));
		instr=new Affect(v4,v3);
		instr.getListe_labels().add("suite3");
		m.getListe_instructions().add(instr);
		
		instr=new Affect(v5,v4);
		instr.getListe_labels().add("fin");
		m.getListe_instructions().add(instr);
		
		Assert.assertTrue("erreurs:"+liste_erreurs,m.est_valide(liste_erreurs));
		
		s=m.toString();
		Assert.assertEquals("a:int\nb:int\nc:int\nmax:int\nres:int\n" +
				"a:=1\nb:=50\nc:=86\n" +
				"if a < b goto suite2\n" +
				"if a < c goto suite\n" +
				"max:=a\n" +
				"goto fin\n" +
				"suite: max:=c\n" +
				"goto fin\n" +
				"suite2: if b < c goto suite3\n" +
				"max:=c\n" +
				"goto fin\n" +
				"suite3: max:=c\n" +
				"fin: res:=max\n", s);
		System.out.println("res="+p.toString());
	}

	@Test
	public void test3()
	{
		Module p;
		Methode m;
		Var a,lsr,rsr,t,res,sr;
		Type type;
		String s;
		Instr instr;
		List<String> liste_erreurs;
		liste_erreurs=new ArrayList<String>();
		p=new Module();
		m=new Methode();
		p.getListe_methodes().add(m);
		
		type=new Type("int");
		a=new Var("a",type);
		lsr=new Var("lsr",type);
		rsr=new Var("rsr",type);
		t=new Var("t",type);
		res=new Var("res",type);
		sr=new Var("sr",type);
		
		m.getListe_variables().add(a);
		m.getListe_variables().add(lsr);
		m.getListe_variables().add(rsr);
		m.getListe_variables().add(t);
		m.getListe_variables().add(res);
		m.getListe_variables().add(sr);
		
		m.getListe_instructions().add(new Affect(a,new Cst(50)));
		m.getListe_instructions().add(new Affect(lsr,new Cst(1)));
		m.getListe_instructions().add(new Affect(rsr,lsr));
		m.getListe_instructions().add(new Affect(lsr,new ExprOpe(Operation.PLUSB,lsr,rsr)));
		
		instr=new Affect(t,new ExprOpe(Operation.FOIS,sr,sr));
		instr.getListe_labels().add("repeat");
		m.getListe_instructions().add(instr);
		
		m.getListe_instructions().add(new If(new ExprOpe(Operation.INFE,t,a),"suite"));
		m.getListe_instructions().add(new Affect(rsr,sr));
		m.getListe_instructions().add(new Goto("suite3"));
		
		instr=new If(new ExprOpe(Operation.SUPE,t,a),"suite2");
		instr.getListe_labels().add("suite");
		m.getListe_instructions().add(instr);
		
		m.getListe_instructions().add(new Affect(lsr,sr));
		m.getListe_instructions().add(new Goto("suite3"));
		
		instr=new Affect(lsr,sr);
		instr.getListe_labels().add("suite2");
		m.getListe_instructions().add(instr);
		m.getListe_instructions().add(new Affect(rsr,sr));
		
		instr=new Affect(sr,new ExprOpe(Operation.PLUSB,lsr,rsr));
		instr.getListe_labels().add("suite3");
		m.getListe_instructions().add(instr);
		
		m.getListe_instructions().add(new If(new ExprOpe(Operation.EGAL,lsr,rsr),"repeat"));
				
		instr=new Affect(res,t);
		instr.getListe_labels().add("fin");
		m.getListe_instructions().add(instr);
		
		if(!m.est_valide(liste_erreurs))
		{
			Assert.assertTrue("erreurs:"+liste_erreurs+"\n"+p.toString(),false);
		}
		
		s=m.toString();
		Assert.assertEquals("a:int\nlsr:int\nrsr:int\nt:int\nres:int\nsr:int\n"+
							"a:=50\n"+
							"lsr:=1\n"+
							"rsr:=lsr\n"+
							"lsr:=lsr + rsr\n"+
							"repeat: t:=sr * sr\n"+
							"if t <= a goto suite\n"+
							"rsr:=sr\n"+
							"goto suite3\n"+
							"suite: if t >= a goto suite2\n"+
							"lsr:=sr\n"+
							"goto suite3\n"+
							"suite2: lsr:=sr\n"+
							"rsr:=sr\n"+
							"suite3: sr:=lsr + rsr\n"+
							"if lsr = rsr goto repeat\n"+
							"fin: res:=t\n", s);
		System.out.println("res="+p.toString());
		
		m.calcul_basic_bloc();
		System.out.println("blocs="+m.BloctoString());
	}

	@Test
	public void test4()
	{
		Module p;
		Methode m;
		Var i,n;
		Type type;
		String s;
		Instr instr;
		List<String> liste_erreurs;
		liste_erreurs=new ArrayList<String>();
		p=new Module();
		m=new Methode();
		p.getListe_methodes().add(m);
		
		type=new Type("int");
		i=new Var("i",type);
		n=new Var("n",type);
		
		m.getListe_variables().add(i);
		m.getListe_variables().add(n);
		
		m.getListe_instructions().add(new Appel("start",null));
		
		m.getListe_instructions().add(new Affect(i,new Cst(0)));
		//p.getListe_instructions().add(new Affect(n,new Cst(0)));
		m.getListe_instructions().add(new Appel("read",null));
				
		instr=new If(new ExprOpe(Operation.DIFF,n,new Cst(1)),"suite");
		instr.getListe_labels().add("suite2");
		m.getListe_instructions().add(instr);
		
		m.getListe_instructions().add(new If(new ExprOpe(Operation.EGAL,n,new Cst(1)),"suite3"));
		
		m.getListe_instructions().add(new Affect(n,new ExprOpe(Operation.DIVE,n,new Cst(2))));
		m.getListe_instructions().add(new Goto("suite4"));
		
		instr=new Affect(n,new ExprOpe(Operation.FOIS,n,new Cst(3)));
		instr.getListe_labels().add("suite3");
		m.getListe_instructions().add(instr);
		
		instr=new Affect(i,new ExprOpe(Operation.PLUSB,i,new Cst(1)));
		instr.getListe_labels().add("suite4");
		m.getListe_instructions().add(instr);
		m.getListe_instructions().add(new Goto("suite2"));
		
		instr=new Appel("print",null);
		instr.getListe_labels().add("suite");
		m.getListe_instructions().add(instr);

		m.getListe_instructions().add(new Appel("stop",null));
		
		if(!m.est_valide(liste_erreurs))
		{
			Assert.assertTrue("erreurs:"+liste_erreurs+"\n"+p.toString(),false);
		}
		
		s=m.toString();
		Assert.assertEquals("i:int\nn:int\n" +
				"start\n"+
				"i:=0\n"+
				"read\n"+
				"suite2: if n <> 1 goto suite\n"+
				"if n = 1 goto suite3\n"+
				"n:=n / 2\n"+
				"goto suite4\n"+
				"suite3: n:=n * 3\n"+
				"suite4: i:=i + 1\n"+
				"goto suite2\n"+
				"suite: print\n"+
				"stop\n", s);
		System.out.println("res="+p.toString());
		
		m.calcul_basic_bloc();
		System.out.println("blocs="+m.BloctoString());
	}
}

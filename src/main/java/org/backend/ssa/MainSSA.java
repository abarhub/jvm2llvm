package org.backend.ssa;

import org.backend.ssa.intermediaire.Affect;
import org.backend.ssa.intermediaire.Cst;
import org.backend.ssa.intermediaire.Methode;
import org.backend.ssa.intermediaire.Module;
import org.backend.ssa.intermediaire.Type;
import org.backend.ssa.intermediaire.Var;

public class MainSSA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Module p;
		Methode m;
		Var v1,v2,v3;
		Type t;
		//AssembleurJvm ass;
		p=new Module("Exemple");
		
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
		System.out.println("res="+p.toString());
		/*ass=new AssembleurJvm();
		try {
			ass.assemble(p);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}

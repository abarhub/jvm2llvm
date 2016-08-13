package org.backend.ssa.intermediaire;

//import org.backend.burg.ArbreBurg;

public class Cst extends Expr {

	private String c;
	
	public Cst(String c)
	{
		this.c=c;
	}

	public Cst(int i)
	{
		this.c=""+i;
	}
	
	@Override
	public String toString() {
		return c;
	}

	@Override
	public void remplace(Var x, Var y) {
		
	}

	/*public ArbreBurg get(int i) {
		assert(false);
		return null;
	}*/

	public String getNomArbreBurg() {
		return "CST";
	}

	public int nb_fils() {
		return 0;
	}

}

package org.backend.ssa.intermediaire;

import java.util.List;

//import org.backend.burg.ArbreBurg;

public class Affect extends Instr {

	private Var var;
	private Expr expr;
	
	public Affect(Var var,Expr expr)
	{
		super();
		this.var=var;
		this.expr=expr;
	}

	public Var getVar() {
		return var;
	}

	public void setVar(Var var) {
		this.var = var;
	}

	public Expr getExpr() {
		return expr;
	}

	public void setExpr(Expr expr) {
		this.expr = expr;
	}
	
	public String toString()
	{
		String s="";
		s+=liste_labels_toString();
		s+=var.toString()+":="+expr.toString();
		return s;
	}

	@Override
	public boolean instr_suivante() {
		return true;
	}

	@Override
	public List<String> saut() {
		return null;
	}

	@Override
	public boolean fin_block() {
		return false;
	}

	/*public ArbreBurg get(int i) {
		assert(i>=0);
		assert(i<2);
		if(i==0)
			return var;
		else
			return expr;
	}*/

	public String getNomArbreBurg() {
		return "AFFECT";
	}

	public int nb_fils() {
		return 2;
	}
}

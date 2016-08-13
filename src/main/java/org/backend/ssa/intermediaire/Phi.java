package org.backend.ssa.intermediaire;

import java.util.List;

//import org.backend.burg.ArbreBurg;

public class Phi extends Instr {

	private List<Expr> liste_variables;
	private Var var;
	
	public Phi(Var v,List<Expr> liste_variables)
	{
		var=v;
		this.liste_variables=liste_variables;
	}
	
	@Override
	public boolean fin_block() {
		return false;
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
	public String toString() {
		String s="";
		s+=liste_labels_toString();
		s+=var.toString()+" := phi ";
		if(liste_variables!=null&&!liste_variables.isEmpty())
		{
			boolean debut=true;
			for(Expr v:liste_variables)
			{
				if(!debut)
					s+=",";
				s+=v.toString();
				debut=false;
			}
		}
		return s;
	}

	public List<Expr> getListe_variables() {
		return liste_variables;
	}

	public void setListe_variables(List<Expr> liste_variables) {
		this.liste_variables = liste_variables;
	}

	public Var getVar() {
		return var;
	}

	public void setVar(Var var) {
		this.var = var;
	}

	/*public ArbreBurg get(int i) {
		assert(false);
		return null;
	}*/

	public String getNomArbreBurg() {
		assert(false);
		return null;
	}

	public int nb_fils() {
		assert(false);
		return 0;
	}

}

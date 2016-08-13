package org.backend.ssa.intermediaire;

//import org.backend.burg.ArbreBurg;


public class Var extends Expr /*implements ArbreBurg*/ {

	private String nom_var;
	private Type type;
	
	public Var(String nom,Type type)
	{
		this.nom_var=nom;
		this.type=type;
	}

	public String getNomVar() {
		return nom_var;
	}

	public void setNomVar(String nom) {
		this.nom_var = nom;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public String toString()
	{
		return nom_var;
	}
	
	public boolean equals(Var v)
	{
		if(v==null)
			return false;
		if(v.getNomVar()==null)
			return nom_var==null;
		if(nom_var==null)
			return false;
		return v.getNomVar().equalsIgnoreCase(nom_var);
		
	}

	@Override
	public void remplace(Var x, Var y)  {
		throw new RuntimeException("Operation non permise");
	}

	/*public ArbreBurg get(int i) {
		assert(false);
		return null;
	}*/

	/*public int nb_fils() {
		return 0;
	}

	/*public String getNomArbreBurg() {
		return "VAR";
	}*/
}

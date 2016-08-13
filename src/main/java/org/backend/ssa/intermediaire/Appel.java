package org.backend.ssa.intermediaire;

import java.util.List;

//import org.backend.burg.ArbreBurg;

public class Appel extends Instr {

	private String nom_methode;
	private List<Expr> param;
	
	public Appel(String nom_methode,List<Expr> param) {
		this.nom_methode=nom_methode;
		this.param=param;
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
		s+=nom_methode;
		if(param!=null&&!param.isEmpty())
		{
			boolean debut=true;
			for(Expr e:param)
			{
				if(debut)
					s+=",";
				s+=e.toString();
				debut=false;
			}
		}
		return s;
	}

	@Override
	public boolean fin_block() {
		return true;
	}

	public String getNom_methode() {
		return nom_methode;
	}

	public void setNom_methode(String nom_methode) {
		this.nom_methode = nom_methode;
	}

	public List<Expr> getParam() {
		return param;
	}

	public void setParam(List<Expr> param) {
		this.param = param;
	}

	/*public ArbreBurg get(int i) {
		assert(i>=0);
		assert(i<nb_fils());
		return param.get(i);
	}*/

	public int nb_fils() {
		if(param==null||param.isEmpty())
			return 0;
		else
			return param.size();
	}

	public String getNomArbreBurg() {
		return "CALL";
	}

}

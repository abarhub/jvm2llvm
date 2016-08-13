package org.backend.ssa.intermediaire;

public enum Operation {

	PLUSB("+",2),MOINSB("-",2),INFS("<",2),INFE("<=",2),EGAL("=",2),
	DIFF("<>",2),SUPE(">=",2),SUPS(">",2),FOIS("*",2),DIVE("/",2);
	
	private String nom;
	private int nb_param;
	
	private Operation(String s,int nb_param)
	{
		this.nom=s;
		this.nb_param=nb_param;
	}

	public String getNom() {
		return nom;
	}

	public int getNb_param() {
		return nb_param;
	}
	
	
}

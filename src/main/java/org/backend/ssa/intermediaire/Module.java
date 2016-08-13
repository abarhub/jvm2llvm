package org.backend.ssa.intermediaire;

import java.util.ArrayList;
import java.util.List;

public class Module {

	private String nom;
	private List<Methode> liste_methodes;
	
	public Module(String nom)
	{
		this.nom=nom;
		liste_methodes=new ArrayList<Methode>();
	}
	
	public Module()
	{
		liste_methodes=new ArrayList<Methode>();
	}

	public List<Methode> getListe_methodes() {
		return liste_methodes;
	}

	public void setListe_methodes(List<Methode> liste_methodes) {
		this.liste_methodes = liste_methodes;
	}
	
	public String toString()
	{
		String res="";
		if(liste_methodes!=null&&!liste_methodes.isEmpty())
		{
			for(Methode m:liste_methodes)
			{
				if(!res.isEmpty())
					res+="***********\n";
				res+=m.toString();
			}
		}
		return res;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}

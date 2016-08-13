package org.backend.ssa.intermediaire;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import org.backend.ssa.SSA;

public class Methode {

	private String nom;
	private List<Var> liste_variables;
	private List<Instr> liste_instructions;
	private List<BasicBloc> liste_basic_bloc;
	private int no_temp;
	
	public Methode(String nom)
	{
		this.nom=nom;
		liste_variables=new ArrayList<Var>();
		liste_instructions=new ArrayList<Instr>();
		no_temp=0;
	}
	
	/*
	 * methode anonyme
	 */
	public Methode()
	{
		this.nom=null;
		liste_variables=new ArrayList<Var>();
		liste_instructions=new ArrayList<Instr>();
		no_temp=0;
	}

	public List<Var> getListe_variables() {
		return liste_variables;
	}

	public void setListe_variables(List<Var> liste_variables) {
		this.liste_variables = liste_variables;
	}

	public List<Instr> getListe_instructions() {
		return liste_instructions;
	}

	public void setListe_instructions(List<Instr> liste_instructions) {
		this.liste_instructions = liste_instructions;
	}
	
	public String toString()
	{
		String res;
		res="";
		if(liste_variables!=null)
		{
			for(Var v:liste_variables)
			{
				res+=v.toString()+":"+v.getType().toString()+"\n";
			}
		}
		if(liste_instructions!=null)
		{
			for(Instr ins:liste_instructions)
			{
				res+=ins.toString()+"\n";
			}
		}
		return res;
	}

	public boolean est_valide(List<String> liste_erreurs) {
		String nom_var;
		int no;
		for(int i=0;i<liste_variables.size();i++)
		{
			assert(liste_variables.get(i)!=null);
			nom_var=liste_variables.get(i).getNomVar();
			if(nom_var==null||nom_var.isEmpty()||
				nom_var.trim().isEmpty()||!nom_var.matches("^[a-zA-Z_][a-zA-Z_0-9]*$"))
			{
				liste_erreurs.add("La variable n°"+i+" ('"+nom_var+"') n'est pas un nom valide");
				return false;
			}
			if(liste_variables.get(i).getType()==null)
			{
				liste_erreurs.add("Le type de la variable n°"+i+" ('"+nom_var+"') n'est pas un nom valide");
				return false;
			}
			for(int j=0;j<i;j++)
			{
				if(liste_variables.get(j).getNomVar().equalsIgnoreCase(nom_var))
				{
					liste_erreurs.add("La variable "+nom_var+" est déclarée plusieurs fois");
					return false;
				}
			}
		}
		no=0;
		for(Instr ins:liste_instructions)
		{
			if(ins instanceof If)
			{
				If tmp=(If) ins;
				assert(tmp.getExpr()!=null);
				assert(tmp.getLabel()!=null);
				if(!verif_expr(liste_erreurs,tmp.getExpr(),no))
					return false;
				if(get_label(tmp.getLabel())==-1)
				{
					liste_erreurs.add("Le label "+tmp.getLabel()+" n'existe pas (instr "+no+")");
					return false;
				}
			}
			else if(ins instanceof Goto)
			{
				Goto tmp=(Goto) ins;
				assert(tmp.getLabel()!=null);
				if(get_label(tmp.getLabel())==-1)
				{
					liste_erreurs.add("Le label "+tmp.getLabel()+" n'existe pas (instr "+no+")");
					return false;
				}
			}
			else if(ins instanceof Affect)
			{
				Affect tmp=(Affect) ins;
				assert(tmp.getVar()!=null);
				if(donne_var(tmp.getVar().getNomVar())==-1)
				{
					liste_erreurs.add("La variable "+tmp.getVar().getNomVar()+" n'existe pas (instr "+no+")");
					return false;
				}
				assert(tmp.getExpr()!=null);
				if(!verif_expr(liste_erreurs,tmp.getExpr(),no))
					return false;
			}
			else if(ins instanceof Appel)
			{
				Appel tmp=(Appel) ins;
				assert(tmp.getNomArbreBurg()!=null);
				if(tmp.getParam()!=null&&!tmp.getParam().isEmpty())
				{
					for(Expr e:tmp.getParam())
					{
						if(!verif_expr(liste_erreurs,e,no))
							return false;
					}
				}
			}
			else
			{
				assert(false);
			}
			no++;
		}
		return true;
	}

	private int get_label(String label) {
		int no=0;
		assert(label!=null);
		assert(!label.isEmpty());
		for(Instr ins:liste_instructions)
		{
			if(ins.contient_labels(label))
			{
				return no;
			}
			no++;
		}
		return -1;
	}

	private boolean verif_expr(List<String> liste_erreurs, Expr expr,int no_instr) {
		if(expr==null)
		{
			liste_erreurs.add("L'expression est vide (instr "+no_instr+")");
			return false;
		}
		assert(expr!=null);
		if(expr instanceof ExprOpe)
		{
			ExprOpe tmp=(ExprOpe) expr;
			if(tmp.getOp()==null)
			{
				liste_erreurs.add("L'operateur de l'expression est invalide (instr "+no_instr+")");
				return false;
			}
			if(tmp.getListe_expr()!=null&&!tmp.getListe_expr().isEmpty())
			{
				for(Expr e:tmp.getListe_expr())
				{
					if(!verif_expr(liste_erreurs,e,no_instr))
						return false;
				}
			}
		}
		else if(expr instanceof Cst)
		{// aucun probl�me
			
		}
		else if(expr instanceof Var)
		{
			Var tmp=(Var) expr;
			if(tmp.getNomVar()==null||tmp.getNomVar().isEmpty())
			{
				liste_erreurs.add("Le nom de la variable est invalide (instr "+no_instr+")");
				return false;
			}
			if(donne_var(tmp.getNomVar())==-1)
			{
				liste_erreurs.add("La variable "+tmp.getNomVar()+" n'existe pas (instr "+no_instr+")");
				return false;
			}
		}
		else if(expr instanceof ExprOpe)
		{
			ExprOpe tmp=(ExprOpe) expr;
			if(tmp.getOp()==null)
			{
				liste_erreurs.add("L'operateur de l'expression est invalide (instr "+no_instr+")");
				return false;
			}
			if(tmp.getListe_expr()!=null&&!tmp.getListe_expr().isEmpty())
			{
				for(Expr e:tmp.getListe_expr())
				{
					if(!verif_expr(liste_erreurs,e,no_instr))
						return false;
				}
			}
		}
		else
		{
			assert(false);
		}
		return true;
	}

	private int donne_var(String nom) {
		assert(nom!=null);
		for(int i=0;i<liste_variables.size();i++)
		{
			if(liste_variables.get(i).getNomVar().equalsIgnoreCase(nom))
				return i;
		}
		return -1;
	}
	
	public void calcul_basic_bloc()
	{
		BasicBloc tmp=null,tmp2;
		Instr ins,ins2;
		int no=0;
		List<Instr> liste,liste2;
		List<String> labels;
		liste_basic_bloc= Lists.newArrayList();
		// on découpe les bloques
		for(int i=0;i<liste_instructions.size();i++)
		{
			if(tmp==null)
			{
				tmp=new BasicBloc(no);
				liste_basic_bloc.add(tmp);
				no++;
			}
			ins=liste_instructions.get(i);
			tmp.getListe_instr().add(ins);
			if(!ins.instr_suivante()||(ins.saut()!=null&&!ins.saut().isEmpty()))
			{// c'est la fin du bloc
				tmp=null;
			}
			else if(i+1<liste_instructions.size())
			{
				labels=liste_instructions.get(i+1).getListe_labels();
				if(labels!=null&&!labels.isEmpty())
				{// la prochaine instruction est un label => fin du bloc
					tmp=null;
				}
				else if(ins.fin_block())
				{
					tmp=null;
				}
			}
		}
		// on determine les liens entre les bloques
		for(BasicBloc b:liste_basic_bloc)
		{
			liste=b.getListe_instr();
			if(liste!=null&&!liste.isEmpty())
			{
				ins=liste.get(liste.size()-1);
				if(ins.instr_suivante()&&b.getNo()+1<liste_basic_bloc.size())
				{
					assert(b.getNo()+1<liste_basic_bloc.size());
					b.defini_bloc_fils(liste_basic_bloc.get(b.getNo()+1));
				}
				labels=ins.saut();
				if(labels!=null&&!labels.isEmpty())
				{
					for(String s:labels)
					{
						tmp2=null;
						for(BasicBloc b2:liste_basic_bloc)
						{
							liste2=b2.getListe_instr();
							if(liste2!=null&&!liste2.isEmpty())
							{
								ins2=liste2.get(0);
								if(ins2.contient_labels(s))
								{
									tmp2=b2;
									break;
								}
							}
						}
						assert(tmp2!=null):"Impossible de trouver le label "+s;
						b.defini_bloc_fils(tmp2);
					}
				}
			}
		}
		calcul_arbre_domination();
	}

	

	private void calcul_arbre_domination() {
		SSA ssa;
		ssa=new SSA(this);
		ssa.convertie_ssa();
		
	}

	public List<BasicBloc> getListe_basic_bloc() {
		return liste_basic_bloc;
	}

	public void setListe_basic_bloc(List<BasicBloc> liste_basic_bloc) {
		this.liste_basic_bloc = liste_basic_bloc;
	}

	public String BloctoString() {
		String s="";
		for(BasicBloc b:liste_basic_bloc)
		{
			if(!s.isEmpty())
				s+="\n";
			s+=b.toString()+"\n";
		}
		return s;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Var newVarTemp(Type type)
	{
		Var var;
		var=new Var("temp"+no_temp,type);
		getListe_variables().add(var);
		no_temp++;
		return var;
	}
}

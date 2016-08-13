package org.backend.ssa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import com.google.common.collect.Lists;
import org.backend.ssa.intermediaire.Affect;
import org.backend.ssa.intermediaire.Appel;
import org.backend.ssa.intermediaire.BasicBloc;
import org.backend.ssa.intermediaire.Expr;
import org.backend.ssa.intermediaire.Goto;
import org.backend.ssa.intermediaire.If;
import org.backend.ssa.intermediaire.Instr;
import org.backend.ssa.intermediaire.Methode;
import org.backend.ssa.intermediaire.Phi;
import org.backend.ssa.intermediaire.Var;


public class SSA {

	private int N;
	private int dfnum[],samedom[];
	private BasicBloc semi[],ancetre[],idom[],vertex[],parent[],best[];
	private List<BasicBloc> liste_basic_bloc;
	private Methode methode;
	
	public SSA(Methode methode)
	{
		this.methode=methode;
		this.liste_basic_bloc=methode.getListe_basic_bloc();
	}
	
	public void calcul_arbre_domination() {
		BasicBloc racine,tmp;
		int i;
		List<Set<Integer>> bucket;
		BasicBloc n,p,s,s2;
		assert(liste_basic_bloc!=null);
		if(!liste_basic_bloc.isEmpty())
		{
			for(int j=0;j<liste_basic_bloc.size();j++)
				assert(j==liste_basic_bloc.get(j).getNo());
			racine=liste_basic_bloc.get(0);
			N=0;
			dfnum=init(0);
			semi=init();
			ancetre=init();
			idom=init();
			samedom=init(-1);
			vertex=init();
			parent=init();
			bucket=init2();
			if(methode2)
			{
				best=init();
			}
			DFS(null,racine);
			for(i=N-1;i>=1;i--)
			{
				n=vertex[i];
				p=parent[n.getNo()];
				s=p;
				for(BasicBloc b:n.getListe_bloc_precedant())
				{
					if(dfnum[b.getNo()]<=dfnum[n.getNo()])
					{
						s2=b;
					}
					else
					{
						tmp=AncestorWithLowestSemi(b);
						s2=semi[tmp.getNo()];
					}
					if(dfnum[s2.getNo()]<dfnum[s.getNo()])
					{
						s=s2;
					}
				}
				semi[n.getNo()]=s;
				bucket.get(s.getNo()).add(n.getNo());
				link(p,n);
				for(int no:bucket.get(p.getNo()))
				{
					BasicBloc b=liste_basic_bloc.get(no);
					tmp=AncestorWithLowestSemi(b);
					int y=tmp.getNo();
					if(semi[y]==semi[b.getNo()])
					{
						idom[b.getNo()]=p;
					}
					else
					{
						samedom[b.getNo()]=y;
					}
				}
				bucket.get(p.getNo()).clear();
			}
			for(i=1;i<N;i++)
			{
				n=vertex[i];
				if(samedom[n.getNo()]!=-1)
				{
					idom[n.getNo()]=idom[samedom[n.getNo()]];
				}
			}
			for(i=0;i<idom.length;i++)
			{
				if(idom[i]!=null)
				{
					BasicBloc b1,b2;
					b1=liste_basic_bloc.get(i);
					b2=idom[i];
					b2.defini_bloc_fils_dom(b1);
				}
			}
		}
	}

	private static final boolean methode2=true;
	
	private void link(BasicBloc p, BasicBloc n) {
		if(methode2)
		{
			ancetre[n.getNo()]=p;
			best[n.getNo()]=n;
		}
		else
		{
			ancetre[n.getNo()]=p;
		}
	}

	private BasicBloc AncestorWithLowestSemi(BasicBloc v) {
		if(methode2)
		{
			BasicBloc a,b;
			a=ancetre[v.getNo()];
			if(ancetre[a.getNo()]!=null)
			{
				b=AncestorWithLowestSemi(a);
				ancetre[v.getNo()]=ancetre[a.getNo()];
				if(dfnum[semi[b.getNo()].getNo()]<dfnum[semi[best[v.getNo()].getNo()].getNo()])
				{
					best[v.getNo()]=b;
				}
			}
			return best[v.getNo()];
		}
		else
		{
			BasicBloc u;
			u=v;
			while(ancetre[v.getNo()]!=null)
			{
				if(dfnum[semi[v.getNo()].getNo()]<dfnum[semi[u.getNo()].getNo()])
				{
					u=v;
				}
				v=ancetre[v.getNo()];
			}
			return u;
		}
	}

	private List<Set<Integer>> init2() {
		List<Set<Integer>> res;
		res=Lists.newArrayList();
		for(int i=0;i<liste_basic_bloc.size();i++)
		{
			res.add(new HashSet<Integer>());
		}
		return res;
	}

	private void DFS(BasicBloc pere, BasicBloc fils) {
		assert(fils!=null);
		int n;
		n=fils.getNo();
		if(dfnum[n]==0)
		{
			dfnum[fils.getNo()]=N;
			vertex[N]=fils;
			if(pere!=null)
				parent[n]=pere;
			else
				parent[n]=null;
			N++;
			for(BasicBloc b:fils.getListe_bloc_suivant())
			{
				DFS(fils,b);
			}
		}
	}

	private int[] init(int val_init) {
		int[] tab;
		tab=new int[liste_basic_bloc.size()];
		for(int i=0;i<tab.length;i++)
			tab[i]=val_init;
		return tab;
	}

	private BasicBloc[] init() {
		BasicBloc[] tab;
		tab=new BasicBloc[liste_basic_bloc.size()];
		return tab;
	}
	
	public void calcul_dominance()
	{
		if(liste_basic_bloc!=null&&!liste_basic_bloc.isEmpty())
		{
			dominance_frontiere(liste_basic_bloc.get(0));
		}
	}
	
	private void dominance_frontiere(BasicBloc n)
	{
		for(BasicBloc c:n.getFils_dom())
		{
			dominance_frontiere(c);
		}
		for(BasicBloc s:n.getListe_bloc_suivant())
		{
			if(s.getPere_dom()!=null&&s.getPere_dom().getNo()!=n.getNo())
			{
				n.getDominance_frontiere().add(s);
			}
		}
		for(BasicBloc c:n.getFils_dom())
		{
			for(BasicBloc p:c.getDominance_frontiere())
			{
				if(p.getPere_dom()!=null&&p.getPere_dom().getNo()!=n.getNo())
				{
					n.getDominance_frontiere().add(p);
				}
			}
		}
	}
	
	public void convertie_ssa()
	{
		calcul_arbre_domination();
		calcul_dominance();
		for(Var v:methode.getListe_variables())
		{
			place_phi_fonction(v);
		}
		renome_variables();
	}

	private void renome_variables() {
		List<Integer> pile;
		int v;
		BasicBloc debut;
		if(!liste_basic_bloc.isEmpty())
		{
			System.out.println("****************************");
			System.out.println("prog="+methode.toString());
			System.out.println("bloques de base="+methode.BloctoString());
			pile=Lists.newArrayList();
			debut=liste_basic_bloc.get(0);
			for(Var x:methode.getListe_variables())
			{
				System.out.println("var="+x);
				pile.clear();
				v=1;
				pile.add(0);
				renome_variables(x,debut,pile,v);
			}
			System.out.println("Résultat="+methode.BloctoString());
			
		}
	}

	private int renome_variables(final Var x, BasicBloc b, List<Integer> pile, int v) {
		int ve;
		Var sommet;
		Preconditions.checkNotNull(x);
		Preconditions.checkNotNull(pile);
		Preconditions.checkArgument(!pile.isEmpty());
		System.out.println("renomage de "+x+" dans le bloque "+b.getNo());
		ve=pile.get(pile.size()-1);
		//sommet=new Var(x.getNomArbreBurg()+pile.get(pile.size()-1),x.getType());
		sommet=new Var(getNomTemp(x, pile),x.getType());
		for(Instr ins:b.getListe_instr())
		{
			// on remplace les utilisations de la variable
			if(ins instanceof Phi)
			{// on ne fait rien
				
			}
			else if(ins instanceof Affect)
			{
				Affect tmp=(Affect) ins;
				Expr e;
				e=tmp.getExpr();
				if(e instanceof Var)
				{
					if(e.equals(x))
						tmp.setExpr(sommet);
				}
				else
				{
					e.remplace(x,sommet);
				}
			}
			else if(ins instanceof Appel)
			{
				Appel tmp=(Appel) ins;
				int no=0;
				if(tmp.getParam()!=null&&!tmp.getParam().isEmpty())
				{
					sommet=new Var(getNomTemp(x, pile),x.getType());
					for(Expr e:tmp.getParam())
					{
						if(e instanceof Var)
						{
							if(e.equals(x))
								tmp.getParam().set(no, sommet);
						}
						else
						{
							e.remplace(x,sommet);
						}
					}
					no++;
				}
			}
			else if(ins instanceof Goto)
			{// rien à faire
				
			}
			else if(ins instanceof If)
			{
				If tmp=(If) ins;
				Expr e;
				e=tmp.getExpr();
				if(e instanceof Var)
				{
					if(e.equals(x))
						tmp.setExpr(sommet);
				}
				else
				{
					e.remplace(x,sommet);
				}
			}
			else
			{
				assert(false);
				Verify.verify(false);
			}
			// on remplace les définitions de la variable
			if(ins instanceof Affect)
			{
				Affect tmp=(Affect) ins;
				if(tmp.getVar().equals(x))
				{
					pile.add(v);
					sommet=new Var(getNomTemp(x, pile),x.getType());
					tmp.setVar(sommet);
					v++;
				}
			}
			else if(ins instanceof Phi)
			{
				Phi tmp=(Phi) ins;
				if(tmp.getVar().equals(x))
				{
					sommet=new Var(getNomTemp(x, pile),x.getType());
					pile.add(v);
					tmp.setVar(sommet);
					v++;
				}
			}
		}
		for(BasicBloc s:b.getListe_bloc_suivant())
		{
			int j=0;
			for(BasicBloc s2:s.getListe_bloc_precedant())
			{
				if(s2==b)
				{
					for(Instr ins:s.getListe_instr())
					{
						if(ins instanceof Phi)
						{
							Phi tmp=(Phi) ins;
							if(tmp.getVar().equals(x))
							{
								assert(j<tmp.getListe_variables().size());
								Verify.verify(j<tmp.getListe_variables().size());
								tmp.getListe_variables().set(j, sommet);
							}
						}
					}
				}
				j++;
			}
		}
		for(BasicBloc c:b.getFils_dom())
		{
			v=renome_variables(x,c,pile,v);
		}
		/*do{
			assert(pile.size()>1):"pile="+pile+",ve="+ve;
			pile.remove(pile.size()-1);
		}while(pile.get(pile.size()-1)!=ve);*/
		while(pile.get(pile.size()-1)!=ve)
		{
			assert(pile.size()>1):"pile="+pile+",ve="+ve;
			Verify.verify(pile.size()>1);
			pile.remove(pile.size() - 1);
		}
		return v;
	}

	private String getNomTemp(Var x, List<Integer> pile) {
		//return x.getNomArbreBurg()+pile.get(pile.size()-1);
		return x.getNomVar()+"_"+pile.get(pile.size()-1);
	}

	private void place_phi_fonction(Var v) {
		boolean has_phi[],processed[];
		List<BasicBloc> w;
		BasicBloc t;
		has_phi=new boolean[liste_basic_bloc.size()];
		processed=new boolean[liste_basic_bloc.size()];
		w= Lists.newArrayList();
		for(BasicBloc b:liste_basic_bloc)
		{
			for(Instr ins:b.getListe_instr())
			{
				if(ins instanceof Affect)
				{
					Affect tmp=(Affect) ins;
					Var n;
					n=tmp.getVar();
					if(n.getNomVar().equalsIgnoreCase(v.getNomVar()))
					{
						processed[b.getNo()]=true;
						w.add(b);
						break;
					}
				}
			}
		}
		while(!w.isEmpty())
		{
			t=w.get(0);
			w.remove(0);
			for(BasicBloc c:t.getDominance_frontiere())
			{
				if(!has_phi[c.getNo()])
				{
					Instr ins;
					List<Expr> liste;
					liste=new ArrayList<Expr>();
					for(int i=0;i<c.getListe_bloc_precedant().size();i++)
					{
						liste.add(new Var(v.getNomVar(),v.getType()));
					}
					ins=new Phi(new Var(v.getNomVar(),v.getType()),liste);
					c.getListe_instr().add(0, ins);
					has_phi[c.getNo()]=true;
					if(!processed[c.getNo()])
					{
						processed[c.getNo()]=true;
						w.add(c);
					}
				}
			}
		}
	}
	
}

package org.backend.ssa.intermediaire;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

//import org.backend.burg.ArbreBurg;

public class ExprOpe extends Expr {

	private Operation op;
	private List<Expr> liste_expr;
	
	public ExprOpe(Operation op,Expr expr1)
	{
		assert(op!=null);
		assert(expr1!=null);
		this.op=op;
		this.liste_expr=new ArrayList<Expr>();
		liste_expr.add(expr1);
	}

	public ExprOpe(Operation op,Expr expr1,Expr expr2)
	{
		assert(op!=null);
		assert(expr1!=null);
		assert(expr2!=null);
		this.op=op;
		this.liste_expr=new ArrayList<Expr>();
		liste_expr.add(expr1);
		liste_expr.add(expr2);
	}
	
	public ExprOpe(Operation op,List<Expr> liste_expr)
	{
		assert(op!=null);
		if(liste_expr!=null)
		{
			for(Expr e:liste_expr)
			{
				assert(e!=null);
			}
		}
		this.op=op;
		this.liste_expr=liste_expr;
	}
	
	@Override
	public String toString() {
		if(liste_expr==null||liste_expr.isEmpty())
			return op.getNom();
		else
		{
			switch(liste_expr.size())
			{
			case 1:
				return op.getNom()+" "+liste_expr.get(0).toString();
			case 2:
				return liste_expr.get(0).toString()+" "+op.getNom()+" "+liste_expr.get(1).toString();
			default:
				assert(false);
				return null;
			}
		}
	}

	public Operation getOp() {
		return op;
	}

	public void setOp(Operation op) {
		this.op = op;
	}

	@Override
	public void remplace(Var x, Var y) {
		if(liste_expr!=null&&!liste_expr.isEmpty())
		{
			ListIterator iter;
			iter=liste_expr.listIterator();
			//for(Expr expr:liste_expr)
			while(iter.hasNext())
			{
				Expr expr;
				expr= (Expr) iter.next();
				if(expr instanceof Var)
				{
					if(expr.equals(x))
					{
						expr=y;
						//liste_expr.set()
						iter.set(y);
					}
				}
				else
				{
					expr.remplace(x, y);
				}
			}
		}
	}

	public List<Expr> getListe_expr() {
		return liste_expr;
	}

	public void setListe_expr(List<Expr> liste_expr) {
		this.liste_expr = liste_expr;
	}

	/*public ArbreBurg get(int i) {
		assert(i>=0);
		assert(i<nb_fils());
		return liste_expr.get(i);
	}*/

	public String getNomArbreBurg() {
		return "PLUS";
	}

	public int nb_fils() {
		if(liste_expr==null||liste_expr.isEmpty())
			return 0;
		else
			return liste_expr.size();
	}

}

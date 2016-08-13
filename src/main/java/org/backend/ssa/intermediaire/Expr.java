package org.backend.ssa.intermediaire;

//import org.backend.burg.ArbreBurg;

public abstract class Expr /*implements ArbreBurg*/ {

	public abstract String toString();

	public abstract void remplace(Var x, Var y);
}

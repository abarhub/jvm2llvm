package org.backend.gen;

import java.util.ArrayList;
import java.util.List;

/*import backend.burg.EnvBurg;
import backend.gen.asm8086.InstrAsm86;
import backend.lang.Instruction;
import backend.lang.Type;
import backend.lang.VariableTemp;*/
import com.google.common.collect.Lists;
import org.backend.ssa.intermediaire.Instr;
import org.backend.ssa.intermediaire.Type;
import org.backend.ssa.intermediaire.Var;

public abstract class Environement /*implements EnvBurg*/ {

	protected List<Instr> asm;
	protected List<Var> variables;

	public Environement()
	{
		asm= Lists.newArrayList();
		variables=Lists.newArrayList();
	}
	
	public abstract Object getInstr();

	public int nb_instr() {
		return asm.size();
	}

	public Instr get_instr(int no) {
		assert(no>=0);
		assert(no<asm.size());
		return asm.get(no);
	}

	public void add_var(Var v) {
		assert(v!=null);
		variables.add(v);
	}

	public int getNoVar(Var var) {
		assert(var!=null);
		for(int i=0;i<variables.size();i++)
		{
			if(variables.get(i).getNomVar().equals(var.getNomVar()))
				return i;
		}
		return -1;
	}

	public String getVar(Var var) {
		assert(var!=null);
		for(int i=0;i<variables.size();i++)
		{
			if(variables.get(i).getNomVar().equals(var.getNomVar()))
			{
				switch(i)
				{
				case 0:
					return "eax";
				case 1:
					return "ebx";
				case 2:
					return "ecx";
				case 3:
					return "edx";
				case 4:
					return "esi";
				case 5:
					return "edi";
				default:
					assert(false):"no de var "+i+" invalide";
					return null;
				}
			}
		}
		return null;
	}

	public void affiche_instr() {
		System.out.println("Liste instr:");
		for(Instr ins:asm)
		{
			System.out.println(ins);
		}
		System.out.println("Fin liste instr");
	}

	public int nbVar() {
		return variables.size();
	}

	public Var newVarTemp(Type type) {
		String tmp;
		int m=0;
		boolean trouve;
		Var res;
		assert(type!=null);
		do{
			tmp="temp"+m;
			System.out.println("test var:"+tmp);
			trouve=var_existe(variables, tmp);
			m++;
		}while(trouve);
		res=new Var(tmp,type);
		variables.add(res);
		return res;
	}

	public Var getVar(int j) {
		assert(j>=0&&j<variables.size()):"j="+j;
		return variables.get(j);
	}

	public Var getVar(String nom) {
		assert(nom!=null);
		for(int i=0;i<variables.size();i++)
		{
			if(variables.get(i).getNomVar().equals(nom))
				return variables.get(i);
		}
		return null;
	}

	/*public int nb_bits(Type type) {
		assert(type!=null);
		if(type.equals(Type.TYPE_I32))
		{
			return 32;
		}
		else if(type.equals(Type.TYPE_I16))
		{
			return 16;
		}
		else if(type.equals(Type.TYPE_I8))
		{
			return 8;
		}
		else if(type.equals(Type.TYPE_F32))
		{
			return 32;
		}
		else if(type.equals(Type.TYPE_F64))
		{
			return 64;
		}
		else if(type.equals(Type.TYPE_F80))
		{
			return 80;
		}
		assert(false):"type="+type;
		return 0;
	}*/

	public void add_instr(Instr s) {
		asm.add(s);
	}

	public void add_instr(int pos, Instr s) {
		assert(pos>=0):"pos="+pos;
		assert(pos<=asm.size()):"pos="+pos;
		if(pos<asm.size())
		{
			asm.add(pos,s);
		}
		else
		{
			asm.add(s);
		}
	}

	/*public void add_instr(Instr instr) {
		for(int i=0;i<instr.nb_asm();i++)
		{
			asm.add(instr.getAsm(i));
		}
	}*/

	public boolean var_existe(List<Var> liste_var, String tmp) {
		for(Var v:liste_var)
		{
			if(v.getNomVar().equals(tmp))
			{
				return true;
			}
		}
		return false;
	}

	public int getNoLabel(String s) {
		assert(s!=null);
		assert(s.length()>0);
		assert(s.trim().length()>0);
		Instr ins;
		String tmp;
		for(int i=0;i<asm.size();i++)
		{
			ins=asm.get(i);
			//tmp=ins.getInstr();
			tmp=ins.toString();
			if(tmp!=null&&tmp.length()>0&&tmp.matches("^[a-zA-Z0-9_]+:$"))
			{// c'est un label
				if(tmp.equals(s)||tmp.equals(s+":"))
					return i;
			}
		}
		return -1;
	}

}

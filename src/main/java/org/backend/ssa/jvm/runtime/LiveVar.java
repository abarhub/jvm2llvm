package org.backend.ssa.jvm.runtime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import org.backend.gen.Environement;
import org.backend.gen.OutilsAnalyse;
import org.backend.ssa.intermediaire.Instr;

public class LiveVar {

	private List<Set<String>> in;
	private List<Set<String>> out;
	private Environement env;
	
	public LiveVar(Environement env)
	{
		this.env=env;
	}
	
	public void calcul()
	{
		List<Set<String>> def;
		List<Set<String>> use;
		List<Set<String>> in2;
		List<Set<String>> out2;
		int i;
		int j;
		int nb_iter;
		Instr ins;
		Set<String> set1;
		Set<String> set2;
		def=new ArrayList<Set<String>>();
		use=new ArrayList<Set<String>>();
		// calcul des def et des use
		for(i=0;i<env.nb_instr();i++)
		{
			ins=env.get_instr(i);
			set1=new HashSet<String>();
			set2=new HashSet<String>();
			def.add(set2);
			use.add(set1);
			if(ins.getSrc()!=null&&ins.getSrc().size()>0)
			{
				for(j=0;j<ins.getSrc().size();j++)
				{
					if(ins.getSrc().get(j).matches(OutilsAnalyse.VAR_REG))
					{
						set1.add(ins.getSrc().get(j));
					}
				}
			}
			if(ins.getDest()!=null&&ins.getDest().size()>0)
			{
				for(j=0;j<ins.getDest().size();j++)
				{
					//if(!ins.getDest()[j].contains("["))
					if(ins.getDest().get(j).matches(OutilsAnalyse.VAR_REG))
					{
						set2.add(ins.getDest().get(j));
					}
				}
			}
		}
		// calcul de in et de out
		in= Lists.newArrayList();
		in2=Lists.newArrayList();
		out=Lists.newArrayList();
		out2=Lists.newArrayList();
		for(i=0;i<env.nb_instr();i++)
		{
			in.add(new HashSet<String>());
			in2.add(new HashSet<String>());
			out.add(new HashSet<String>());
			out2.add(new HashSet<String>());
		}
		nb_iter=0;
		do{
			for(i=0;i<env.nb_instr();i++)
			{
				in2.get(i).clear();
				in2.get(i).addAll(in.get(i));
				out2.get(i).clear();
				out2.get(i).addAll(out.get(i));
			}
			for(i=0;i<env.nb_instr();i++)
			{
				set1=OutilsAnalyse.union(use.get(i),OutilsAnalyse.moins(out.get(i),def.get(i)));
				in.get(i).addAll(set1);
				ins=env.get_instr(i);
				if(ins.instr_suivante())
				{// le succésseur suit
					if(i+1<env.nb_instr())
					{
						out.get(i).addAll(in.get(i+1));
					}
				}
				if(ins.getListe_labels()!=null&&ins.getListe_labels().size()>0)
				{// il y a plusieurs suites
					int pos;
					for(String s:ins.getListe_labels())
					{
						pos=env.getNoLabel(s);
						if(pos>=0&&pos<env.nb_instr())
						{
							out.get(i).addAll(in.get(pos));
						}
					}
				}
			}
			nb_iter++;
			assert(nb_iter<=env.nb_instr()*env.nb_instr()+1):"nb_iter="+nb_iter;
		}while(!OutilsAnalyse.egaux(in,in2)||!OutilsAnalyse.egaux(out,out2));
	}
	

	public List<Set<String>> getIn() {
		return in;
	}

	public List<Set<String>> getOut() {
		return out;
	}
}

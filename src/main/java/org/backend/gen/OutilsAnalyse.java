package org.backend.gen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class OutilsAnalyse {

	public static final String VAR_REG = "^[a-zA-Z_\\$][a-zA-Z_0-9\\$]*$";
	
	public static SortedSet<String> moins(Set<String> set1, Set<String> set2) {
		SortedSet<String> set;
		if(set1 instanceof SortedSet)
		{
			SortedSet<String> s;
			s=(SortedSet<String>) set1;
			set=new TreeSet<String>(s.comparator());
		}
		else
		{
			set=new TreeSet<String>();
		}
		set.addAll(set1);
		set.removeAll(set2);
		return set;
	}

	public static Set<String> union(Set<String> set1, Set<String> set2) {
		HashSet<String> set;
		set=new HashSet<String>();
		set.addAll(set1);
		set.addAll(set2);
		return set;
	}

	public static SortedSet<String> intersection(SortedSet<String> set1, Set<String> set2) {
		TreeSet<String> set;
		set=new TreeSet<String>(set1.comparator());
		/*for(String s:set1)
		{
			if(set2.contains(s))
			{
				set.add(s);
			}
		}*/
		set.addAll(set1);
		set.retainAll(set2);
		return set;
	}
	
	public static String output(String instr, List<String> src, List<String> dest, List<String> label)
	{
		String res;
		StringBuilder buf;
		char c,c2;
		assert(instr!=null);
		buf=new StringBuilder();
		for(int i=0;i<instr.length();i++)
		{
			c=instr.charAt(i);
			if(c=='{'&&i+1<instr.length())
			{
				c2=instr.charAt(i+1);
				if(c2=='d')
				{
					i = ajoute_valeur(instr, dest, buf, i);
				}
				else if(c2=='s')
				{
					i = ajoute_valeur(instr, src, buf, i);
				}
				else if(c2=='l')
				{
					i = ajoute_valeur(instr, label, buf, i);
				}
				else
				{
					buf.append(c);
				}
			}
			else
			{
				buf.append(c);
			}
		}
		res=buf.toString();
		return res;
	}

	private static int ajoute_valeur(String instr, List<String> param, StringBuilder buf, int i) {
		int nb=0;
		i+=2;
		assert(param!=null);
		while(i<instr.length()&&
			instr.charAt(i)>='0'&&instr.charAt(i)<='9')
		{
			nb=nb*10+(instr.charAt(i)-'0');
			i++;
		}
		assert(i<instr.length());
		assert(instr.charAt(i)=='}');
		//i++;
		assert(nb>=0);
		assert(nb<param.size()):"nb="+nb+",param="+param.size()+"("+instr+")";
		buf.append(param.get(nb));
		return i;
	}

	public static boolean egaux(List<Set<String>> liste, List<Set<String>> liste2) {
		assert(liste!=null);
		assert(liste2!=null);
		if(liste.size()!=liste2.size())
			return false;
		for(int i=0;i<liste.size();i++)
		{
			if(liste.get(i).size()!=liste2.get(i).size())
				return false;
			if(!liste.get(i).equals(liste2.get(i)))
				return false;
		}
		return true;
	}
}

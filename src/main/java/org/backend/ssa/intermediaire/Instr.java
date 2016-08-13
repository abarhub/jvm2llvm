package org.backend.ssa.intermediaire;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

//import org.backend.burg.ArbreBurg;

public abstract class Instr /*implements ArbreBurg*/ {

	protected List<String> liste_labels;
	protected List<String> src;
	protected List<String> dest;

	public Instr()
	{
		liste_labels=new ArrayList<String>();
	}
	
	public List<String> getListe_labels() {
		return liste_labels;
	}

	public void setListe_labels(List<String> liste_labels) {
		this.liste_labels = liste_labels;
	}
	
	public abstract String toString();

	protected String liste_labels_toString() {
		String s="";
		if(liste_labels!=null&&!liste_labels.isEmpty())
		{
			for(String s2:liste_labels)
			{
				if(s2!=null&&!s2.trim().isEmpty())
				{
					if(!s.isEmpty())
						s+=", ";
					s+=s2.trim();
				}
			}
			if(!s.isEmpty())
				s+=": ";
		}
		return s;
	}

	public boolean contient_labels(String label) {
		assert(label!=null);
		assert(!label.isEmpty());
		if(liste_labels!=null&&!liste_labels.isEmpty())
		{
			for(String s2:liste_labels)
			{
				if(s2!=null&&!s2.trim().isEmpty())
				{
					String s3;
					s3=s2.trim();
					if(s3.equalsIgnoreCase(label))
						return true;
				}
			}
		}
		return false;
	}
	
	public abstract boolean instr_suivante();
	
	public abstract List<String> saut();
	
	public abstract boolean fin_block();

	public List<String> getSrc(){
		if(src==null)
			src= Lists.newArrayList();
		return src;
	}

	public List<String> getDest(){
		if(dest==null)
			dest= Lists.newArrayList();
		return dest;
	}
}

package org.backend.test;

import com.google.common.collect.Lists;
import org.backend.gen.OutilsAnalyse;
import org.backend.ssa.intermediaire.Instr;

import java.util.List;
import java.util.Set;

/**
 * Created by Alain on 25/05/2015.
 */
public class InstrTest extends Instr {

	private final String instr;
	private final Set<String> src;
	private final Set<String> dest;
	private final Set<String> label;
	private final boolean suite;

	public InstrTest(String instr,Set<String> src,Set<String> dest,Set<String> label,boolean suite) {
		this.instr=instr;
		this.src=src;
		this.dest=dest;
		this.label=label;
		this.suite=suite;
	}

	@Override
	public String toString() {
		String res;
		//return instr;
		res= OutilsAnalyse.output(instr, getSrc(), getDest(), saut());
		return res;
	}

	@Override
	public boolean instr_suivante() {
		return suite;
	}

	@Override
	public List<String> saut() {
		return convList(label);
	}

	@Override
	public boolean fin_block() {
		return !suite;
	}

	public List<String> getSrc(){
		return convList(src);
	}

	public List<String> getDest(){
		return convList(dest);
	}

	private List<String> convList(Set<String> set){
		if(set==null||set.isEmpty())
			return Lists.newArrayList();
		else
			return Lists.newArrayList(set);
	}


}

package org.jvm2llvm.tcclass.tcconst;

import org.jvm2llvm.tcclass.TCpInfo;

/**
 * Created by Alain on 17/05/2015.
 */
public class TCLong extends TCpInfo {

	private final long num;

	public TCLong(long num) {
		this.num = num;
	}

	public long getNum() {
		return num;
	}

	@Override
	public String toString() {
		return "TCLong{" +
				"num=" + num +
				'}';
	}
}

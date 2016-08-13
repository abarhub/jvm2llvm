package org.jvm2llvm.tcclass.tcconst;

import org.jvm2llvm.tcclass.TCpInfo;

/**
 * Created by Alain on 17/05/2015.
 */
public class TCInt extends TCpInfo {

	private final int num;

	public TCInt(int num) {
		this.num = num;
	}

	public int getNum() {
		return num;
	}

	@Override
	public String toString() {
		return "TCInt{" +
				"num=" + num +
				'}';
	}
}

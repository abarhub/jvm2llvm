package org.jvm2llvm.tcclass.tcconst;

import org.jvm2llvm.tcclass.TCpInfo;

/**
 * Created by Alain on 17/05/2015.
 */
public class TCDouble extends TCpInfo {

	private final double num;

	public TCDouble(double num) {
		this.num = num;
	}

	public double getNum() {
		return num;
	}

	@Override
	public String toString() {
		return "TCDouble{" +
				"num=" + num +
				'}';
	}
}

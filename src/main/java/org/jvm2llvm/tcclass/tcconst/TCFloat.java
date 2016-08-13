package org.jvm2llvm.tcclass.tcconst;

import org.jvm2llvm.tcclass.TCpInfo;

/**
 * Created by Alain on 17/05/2015.
 */
public class TCFloat extends TCpInfo {

	private final float num;

	public TCFloat(float num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "TCFloat{" +
				"num=" + num +
				'}';
	}

	public float getNum() {
		return num;
	}
}

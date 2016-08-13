package org.jvm2llvm.tcclass.tcconst;

import org.jvm2llvm.tcclass.TCpInfo;

/**
 * Created by Alain on 17/05/2015.
 */
public class TCMethodType extends TCpInfo {

	private final int type_descriptor;

	public TCMethodType(int type_descriptor) {
		this.type_descriptor = type_descriptor;
	}

	public int getType_descriptor() {
		return type_descriptor;
	}

	@Override
	public String toString() {
		return "TCMethodType{" +
				"type_descriptor=" + type_descriptor +
				'}';
	}
}

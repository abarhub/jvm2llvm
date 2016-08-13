package org.jvm2llvm.tcclass.tcconst;

import org.jvm2llvm.tcclass.TCpInfo;

/**
 * Created by Alain on 17/05/2015.
 */
public class TCMethodHandle extends TCpInfo {

	private final int type_descriptor;
	private final int index;

	public TCMethodHandle(int type_descriptor, int index) {
		this.type_descriptor = type_descriptor;
		this.index = index;
	}

	public int getType_descriptor() {
		return type_descriptor;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return "TCMethodHandle{" +
				"type_descriptor=" + type_descriptor +
				", index=" + index +
				'}';
	}
}

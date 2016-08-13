package org.jvm2llvm.tcclass.tcconst;

import org.jvm2llvm.tcclass.TCpInfo;

/**
 * Created by Alain on 17/05/2015.
 */
public class TCClassRef extends TCpInfo {

	private final int index;

	public TCClassRef(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return "TCClassRef{" +
				"index=" + index +
				'}';
	}
}

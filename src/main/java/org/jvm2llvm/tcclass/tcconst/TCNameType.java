package org.jvm2llvm.tcclass.tcconst;

import org.jvm2llvm.tcclass.TCpInfo;

/**
 * Created by Alain on 17/05/2015.
 */
public class TCNameType extends TCpInfo {

	private final int index_name;
	private final int index_type;

	public TCNameType(int index_name, int index_type) {
		this.index_name = index_name;
		this.index_type = index_type;
	}

	public int getIndex_name() {
		return index_name;
	}
	public int getIndex_type() {
		return index_type;
	}

	@Override
	public String toString() {
		return "TCNameType{" +
				"index_name=" + index_name +
				", index_type=" + index_type +
				'}';
	}
}

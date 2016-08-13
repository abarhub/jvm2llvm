package org.jvm2llvm.tcclass.tcconst;

import org.jvm2llvm.tcclass.TCpInfo;

/**
 * Created by Alain on 17/05/2015.
 */
public class TCInvokeDynamic extends TCpInfo {

	//private final int index_class;
	//private final int index_name;

	public TCInvokeDynamic(/*int index_class, int index_field*/) {
		//this.index_class = index_class;
		//this.index_name = index_field;
		throw new IllegalArgumentException();

	}

	/*public int getIndex_class() {
		return index_class;
	}

	public int getIndex_name() {
		return index_name;
	}

	@Override
	public String toString() {
		return "TCInvokeDynamic{" +
				"index_class=" + index_class +
				", index_name=" + index_name +
				'}';
	}*/
}

package org.jvm2llvm.conv;

import org.jvm2llvm.EOpCode;

import java.util.Arrays;

/**
 * Created by Alain on 25/05/2015.
 */
public class InstrJvm {

	private final EOpCode op;
	private final byte[] param;

	public InstrJvm(EOpCode op, byte[] param) {
		this.op = op;
		this.param = param;
	}

	public EOpCode getOp() {
		return op;
	}

	public byte[] getParam() {
		return param;
	}

	@Override
	public String toString() {
		return "InstrJvm{" +
				"op=" + op +
				((param!=null)?", param=" + Arrays.toString(param):"") +
				'}';
	}
}

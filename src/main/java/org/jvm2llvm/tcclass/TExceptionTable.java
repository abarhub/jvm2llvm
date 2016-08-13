package org.jvm2llvm.tcclass;

/**
 * Created by Alain on 17/05/2015.
 */
public class TExceptionTable {

	private final char start_pc;
	private final char end_pc;
	private final char handler_pc;
	private final char catch_type;

	public TExceptionTable(char start_pc, char end_pc, char handler_pc, char catch_type) {
		this.start_pc = start_pc;
		this.end_pc = end_pc;
		this.handler_pc = handler_pc;
		this.catch_type = catch_type;
	}

	public char getStart_pc() {
		return start_pc;
	}

	public char getEnd_pc() {
		return end_pc;
	}

	public char getHandler_pc() {
		return handler_pc;
	}

	public char getCatch_type() {
		return catch_type;
	}

	@Override
	public String toString() {
		return "TExceptionTable{" +
				"start_pc=" + start_pc +
				", end_pc=" + end_pc +
				", handler_pc=" + handler_pc +
				", catch_type=" + catch_type +
				'}';
	}
}

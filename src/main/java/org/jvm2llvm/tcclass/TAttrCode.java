package org.jvm2llvm.tcclass;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alain on 17/05/2015.
 */
public class TAttrCode extends TAttrDetails {

	private final char name;
	private final long len;
	private final char max_stack;
	private final char max_local;
	private final long code_len;
	private final byte[] code;
	private final List<TExceptionTable> table_exception;
	private final char attr_len;
	private final List<TAttributInfo> attr;

	public TAttrCode(char name, long len, char max_stack, char max_local, long code_len,
	                 byte[] code, List<TExceptionTable> table_exception, char attr_len, List<TAttributInfo> attr) {
		this.name = name;
		this.len = len;

		this.max_stack = max_stack;
		this.max_local = max_local;
		this.code_len = code_len;
		this.code = code;
		this.table_exception = table_exception;
		this.attr_len = attr_len;
		this.attr = attr;
	}

	public char getName() {
		return name;
	}

	public long getLen() {
		return len;
	}

	public char getMax_stack() {
		return max_stack;
	}

	public char getMax_local() {
		return max_local;
	}

	public long getCode_len() {
		return code_len;
	}

	public byte[] getCode() {
		return code;
	}

	public List<TExceptionTable> getTable_exception() {
		return table_exception;
	}

	public char getAttr_len() {
		return attr_len;
	}

	public List<TAttributInfo> getAttr() {
		return attr;
	}

	@Override
	public String toString() {
		return "TAttrCode{" +
				"name=" + name +
				", len=" + len +
				", max_stack=" + max_stack +
				", max_local=" + max_local +
				", code_len=" + code_len +
				", code=" + Arrays.toString(code) +
				", table_exception=" + table_exception +
				", attr_len=" + attr_len +
				", attr=" + attr +
				'}';
	}
}

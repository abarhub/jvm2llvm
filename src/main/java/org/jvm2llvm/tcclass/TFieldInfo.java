package org.jvm2llvm.tcclass;

import java.util.List;

/**
 * Created by Alain on 17/05/2015.
 */
public class TFieldInfo {

	private char access;
	private char name;
	private char descriptor;
	private char attrLen;
	private List<TAttributInfo> attrList;

	public TFieldInfo() {
	}

	public char getAccess() {
		return access;
	}

	public void setAccess(char access) {
		this.access = access;
	}

	public char getName() {
		return name;
	}

	public void setName(char name) {
		this.name = name;
	}

	public char getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(char descriptor) {
		this.descriptor = descriptor;
	}

	public char getAttrLen() {
		return attrLen;
	}

	public void setAttrLen(char attrLen) {
		this.attrLen = attrLen;
	}

	public List<TAttributInfo> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<TAttributInfo> attrList) {
		this.attrList = attrList;
	}

	@Override
	public String toString() {
		return "TFieldInfo{" +
				"access=" + (int)access +
				", name=" + (int)name +
				", descriptor=" + (int)descriptor +
				", attrLen=" + (int)attrLen +
				", attrList=" + attrList +
				'}';
	}
}

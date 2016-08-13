package org.jvm2llvm.tcclass;

import java.util.List;

/**
 * Created by Alain on 17/05/2015.
 */
public class TAttributInfo {

	private final char name;
	private final long len;
	private final List<Byte> info;
	private TAttrDetails details;

	public TAttributInfo(char name, long len, List<Byte> info) {
		this.name = name;
		this.len = len;
		this.info = info;
	}

	public char getName() {
		return name;
	}

	public long getLen() {
		return len;
	}

	public List<Byte> getInfo() {
		return info;
	}

	public TAttrDetails getDetails() {
		return details;
	}

	public void setDetails(TAttrDetails details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "TAttributInfo{" +
				"name=" + (int)name +
				", len=" + (int)len +
				", info=" + info +
				", details=" + details +
				'}';
	}
}

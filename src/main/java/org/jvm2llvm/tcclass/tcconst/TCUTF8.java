package org.jvm2llvm.tcclass.tcconst;

import org.jvm2llvm.tcclass.TCpInfo;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by Alain on 17/05/2015.
 */
public class TCUTF8 extends TCpInfo {

	private final byte buf[];

	public TCUTF8(byte buf[]) {
		this.buf=buf;
	}

	public byte[] getBuf() {
		return buf;
	}

	@Override
	public String toString() {
		String s;
		if(est_printable(buf))
		{
			s=chaine(buf);
		}
		else {
			s = Arrays.toString(buf);
		}
		return "TCUTF8{" +
				"buf=" + s +
				'}';
	}

	private String chaine(byte[] buf) {
		String buf2;
		buf2="";
		if(buf!=null) {
			buf2+="'";
			buf2+=getString();
			/*for (int i=0;i<buf.length;i++)
			{
				char c;
				c= (char) (buf[i]&0xFF);
				buf2+=c;
			}*/
			buf2+="'";
		}
		return buf2;
	}

	private boolean est_printable(byte[] buf) {
		return true;
	}

	public String getString(){
		String buf="";
		try {
			buf=new String(this.buf, "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return buf;
	}
}

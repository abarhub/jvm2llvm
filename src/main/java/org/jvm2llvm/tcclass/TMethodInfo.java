package org.jvm2llvm.tcclass;

import com.google.common.base.Verify;
import org.jvm2llvm.EOpCode;

import java.io.PrintStream;
import java.util.List;

/**
 * Created by Alain on 17/05/2015.
 */
public class TMethodInfo {

	private char access;
	private char name;
	private char descriptor;
	private char attrLen;
	private List<TAttributInfo> attrList;

	public TMethodInfo() {
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
		return "TMethodInfo{" +
				"access=" + (int)access +
				", name=" + (int)name +
				", descriptor=" + (int)descriptor +
				", attrLen=" + (int)attrLen +
				", attrList=" + attrList +
				'}';
	}

	public void affiche(TClass tClass, PrintStream out) {
		out.println("nom:"+tClass.getUtf8(name));
		out.println("param:"+tClass.getUtf8(descriptor));
		out.println("access:"+(int)access);
		out.println("nb_attr:"+(int)attrLen);
		out.println("attr_list:");
		if(attrList!=null)
		{
			for(TAttributInfo attr:attrList)
			{
				if(attr.getDetails()!=null)
				{
					TAttrDetails detail = attr.getDetails();
					if(detail instanceof TAttrCode)
					{
						TAttrCode code;
						code= (TAttrCode) detail;
						out.println("* code:");
						out.println(" nb_stack:"+(int)code.getMax_stack());
						out.println(" nb_local:" + (int) code.getMax_local());
						out.println(" nb_code:"+code.getCode_len());
						out.println("* instr:");
						affiche_instr(tClass,code.getCode(),out);
					}
				}
			}
		}
	}

	private void affiche_instr(TClass tClass, byte[] code, PrintStream out) {
		int nb_instr;
		EOpCode op;
		String s;
		if(code!=null)
		{
			nb_instr=0;
			for(int i=0;i<code.length;i++)
			{
				op=getOpCode(code[i]);
				Verify.verifyNotNull(op,"code=%s,i=%s",code[i],i);
				s=op.getNom();
				if(op.getNbParam()>0)
				{
					s+="("+op.getNbParam()+")";
				}
				out.println(nb_instr+") "+s);
				if(op.getNbParam()>0)
					i+=op.getNbParam();
				nb_instr++;
			}
		}
	}

	private EOpCode getOpCode(byte b){
		for(EOpCode c:EOpCode.values())
		{
			if(c.getNo()==conv_byte(b))
				return c;
		}
		return null;
	}


	private int conv_byte(byte b)
	{
		int res;
		res=b&0xFF;
		//res = (b << 24) >>> 24;
		/*if(b>=0)
			return b;
		return b+127;*/
		return res;
	}
}

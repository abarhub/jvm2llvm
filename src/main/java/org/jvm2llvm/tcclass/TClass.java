package org.jvm2llvm.tcclass;

import com.google.common.base.Preconditions;
import org.jvm2llvm.tcclass.TAttributInfo;
import org.jvm2llvm.tcclass.TCpInfo;
import org.jvm2llvm.tcclass.TFieldInfo;
import org.jvm2llvm.tcclass.TMethodInfo;
import org.jvm2llvm.tcclass.tcconst.*;

import java.io.PrintStream;
import java.util.List;

/**
 * Created by Alain on 17/05/2015.
 */
public class TClass {

	private long magic;
	private char version1;
	private char version2;

	private int constLen;
	private List<TCpInfo> constList;

	private char acces;
	private char nom;
	private char parent;

	private int interfaceLen;
	private List<Character> interfaceList;

	private int fieldsLen;
	private List<TFieldInfo> fieldsList;

	private int methodLen;
	private List<TMethodInfo> methodList;

	private int attrLen;
	private List<TAttributInfo> attrList;

	public TClass() {
	}

	public long getMagic() {
		return magic;
	}

	public void setMagic(long magic) {
		this.magic = magic;
	}

	public char getVersion1() {
		return version1;
	}

	public void setVersion1(char version1) {
		this.version1 = version1;
	}

	public char getVersion2() {
		return version2;
	}

	public void setVersion2(char version2) {
		this.version2 = version2;
	}

	public char getAcces() {
		return acces;
	}

	public void setAcces(char acces) {
		this.acces = acces;
	}

	public char getNom() {
		return nom;
	}

	public void setNom(char nom) {
		this.nom = nom;
	}

	public char getParent() {
		return parent;
	}

	public void setParent(char parent) {
		this.parent = parent;
	}

	public int getConstLen() {
		return constLen;
	}

	public void setConstLen(int constLen) {
		this.constLen = constLen;
	}

	public List<TCpInfo> getConstList() {
		return constList;
	}

	public void setConstList(List<TCpInfo> constList) {
		this.constList = constList;
	}

	public int getInterfaceLen() {
		return interfaceLen;
	}

	public void setInterfaceLen(int interfaceLen) {
		this.interfaceLen = interfaceLen;
	}

	public List<Character> getInterfaceList() {
		return interfaceList;
	}

	public void setInterfaceList(List<Character> interfaceList) {
		this.interfaceList = interfaceList;
	}

	public int getFieldsLen() {
		return fieldsLen;
	}

	public void setFieldsLen(int fieldsLen) {
		this.fieldsLen = fieldsLen;
	}

	public List<TFieldInfo> getFieldsList() {
		return fieldsList;
	}

	public void setFieldsList(List<TFieldInfo> fieldsList) {
		this.fieldsList = fieldsList;
	}

	public int getMethodLen() {
		return methodLen;
	}

	public void setMethodLen(int methodLen) {
		this.methodLen = methodLen;
	}

	public List<TMethodInfo> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<TMethodInfo> methodList) {
		this.methodList = methodList;
	}

	public int getAttrLen() {
		return attrLen;
	}

	public void setAttrLen(int attrLen) {
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
		return "TClass{" +
				"magic='" + hex(magic) + '\'' +
				", version1=" + (int)version1 +
				", version2=" + (int)version2 +
				", constLen="+constLen+
				", constList="+constList+
				", acces=" + acces +
				", nom=" + nom +
				", parent=" + parent +
				", interfaceLen=" + interfaceLen +
				", interfaceList=" + interfaceList +
				", fieldsLen=" + fieldsLen +
				", fieldsList=" + fieldsList +
				", methodLen=" + methodLen +
				", methodList=" + methodList +
				", attrLen=" + attrLen +
				", attrList=" + attrList +
				'}';
	}

	private String hex(long n) {
	    // call toUpperCase() if that's required
	    return String.format("0x%8s", Long.toHexString(n)).replace(' ', '0');
	}

	public TCpInfo getCpInfo(int no)
	{
		if(no<=0)
			throw new IllegalArgumentException("no="+no);
		if(no>constList.size())
			throw new IllegalArgumentException("no="+no+";"+constList.size());
		return constList.get(no-1);
	}

	public void affiche(PrintStream out)
	{
		out.println("* class");
		out.println("magic:"+hex(magic));
		out.println("version_min:"+(int)version1);
		out.println("version_max:"+(int)version2);
		out.println("nb_const:"+constLen);
		out.println("nb_interf:"+interfaceLen);
		out.println("nb_field:"+fieldsLen);
		out.println("nb_method:"+methodLen);
		out.println("nb_attr:"+attrLen);
		out.println("access:"+(int)acces);
		out.println("nom:"+getClassRef(nom));
		out.println("parent:"+getClassRef(parent));
		out.println("cp_list:");
		if(constList!=null)
		{
			int i=1;
			for(TCpInfo tmp:constList)
			{
				if(tmp!=null)
				{
					if(tmp instanceof TCUTF8)
					{
						out.println(i+") utf8:"+((TCUTF8) tmp).getString());
						i++;
					}
					else if(tmp instanceof TCClassRef)
					{
						TCClassRef c;
						c= (TCClassRef) tmp;
						out.println(i + ") class(" + c.getIndex() + "):" + getUtf8(c.getIndex()));
						i++;
					}
					else if(tmp instanceof TCMethodRef)
					{
						TCMethodRef c;
						c= (TCMethodRef) tmp;
						out.println(i+") method("+c.getIndex_class()+","+c.getIndex_name()+"):"+getClassRef(c.getIndex_class())+";"+getNameAndType(c.getIndex_name(), true)+getNameAndType(c.getIndex_name(),false));
						i++;
					}
					else if(tmp instanceof TCFieldRef)
					{
						TCFieldRef c;
						c= (TCFieldRef) tmp;
						out.println(i+") field("+c.getIndex_class()+","+c.getIndex_name()+"):"+getClassRef(c.getIndex_class())+";"+getNameAndType(c.getIndex_name(), true)+getNameAndType(c.getIndex_name(), false));
						i++;
					}
					else if(tmp instanceof TCNameType)
					{
						TCNameType c;
						c= (TCNameType) tmp;
						out.println(i+") NameAndType("+c.getIndex_name()+","+c.getIndex_type()+"):"+getUtf8(c.getIndex_name())+";"+getUtf8(c.getIndex_type()));
						i++;
					}
					else if(tmp instanceof TCStringRef)
					{
						TCStringRef c;
						c= (TCStringRef) tmp;
						out.println(i + ") string(" + c.getIndex() + "):" + getUtf8(c.getIndex()));
						i++;
					}
					else
					{
						i++;
						Preconditions.checkState(false,tmp);
					}
				}
				else
				{
					i++;
				}
			}
		}
		out.println("interface_list:");
		if(interfaceList!=null)
		{

		}
		out.println("field_list:");
		if(fieldsList!=null)
		{

		}
		out.println("method_list:");
		if(methodList!=null)
		{
			int i=0;
			for(TMethodInfo tmp:methodList)
			{
				out.println("* method "+i+":");
				tmp.affiche(this,out);
				i++;
			}
		}
		out.println("attr_list:");
		if(attrList!=null)
		{

		}
	}

	public String getUtf8(int n)
	{
		TCpInfo cp;
		TCUTF8 u;

		cp=getCpInfo(n);
		Preconditions.checkNotNull(cp);
		Preconditions.checkState(cp instanceof TCUTF8,""+cp+"("+n+")");
		u= (TCUTF8) cp;
		return u.getString();
	}

	public String getClassRef(int n)
	{
		TCpInfo cp,cp3;
		TCUTF8 u;
		TCClassRef cp2;

		cp=getCpInfo(n);
		Preconditions.checkNotNull(cp);
		Preconditions.checkState(cp instanceof TCClassRef,""+cp+"("+n+")");

		cp2= (TCClassRef) cp;
		return getUtf8(cp2.getIndex());
	}

	public String getNameAndType(int n,boolean name)
	{
		TCpInfo cp,cp3;
		TCUTF8 u;
		TCNameType cp2;

		cp=getCpInfo(n);
		Preconditions.checkNotNull(cp);
		Preconditions.checkState(cp instanceof TCNameType,""+cp+"("+n+")");

		cp2= (TCNameType) cp;
		if(name)
			return getUtf8(cp2.getIndex_name());
		else
			return getUtf8(cp2.getIndex_type());
	}
}

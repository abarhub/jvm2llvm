package org.jvm2llvm;

import com.google.common.collect.Lists;
import org.jvm2llvm.tcclass.*;
import org.jvm2llvm.tcclass.tcconst.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Alain on 17/05/2015.
 */
public class ReadFile {

	public ReadFile() {
	}

	public TClass read(String file) throws IOException {
		List<Byte> contenu;
		TClass res;
		contenu=lecture(file);
		res=parse(contenu);

		return res;
	}

	private TClass parse(List<Byte> contenu) {
		long magic;
		TClass res;
		char version1,version2,len_cp,acces,this_class,super_class,len_interf,len_filed,len_methode,len_attr;
		List<TCpInfo> liste_const;
		int pos;
		ParcourtFichier p;
		List<Character> tab_interf;
		List<TFieldInfo> liste_fields;
		List<TMethodInfo> liste_methods;
		List<TAttributInfo> liste_attrs;

		p=new ParcourtFichier(contenu);
		magic=p.getT4();
		version1=p.getT2();
		version2=p.getT2();
		len_cp=p.getT2();

		liste_const=Lists.newArrayList();
		getConst(p,len_cp,liste_const);

		acces=p.getT2();
		this_class=p.getT2();
		super_class=p.getT2();

		len_interf=p.getT2();
		tab_interf = getListInterfaces(len_interf, p);

		len_filed=p.getT2();
		liste_fields = getListFields(len_filed, p);

		len_methode=p.getT2();
		liste_methods = getListMethods(len_methode, p);

		len_attr=p.getT2();
		liste_attrs = getListAttributs(len_attr, p);

		res=new TClass();
		res.setMagic(magic);
		res.setVersion1(version1);
		res.setVersion2(version2);
		res.setConstLen(len_cp);
		res.setConstList(liste_const);
		res.setAcces(acces);
		res.setNom(this_class);
		res.setParent(super_class);
		res.setInterfaceLen(len_interf);
		res.setInterfaceList(tab_interf);
		res.setFieldsLen(len_filed);
		res.setFieldsList(liste_fields);
		res.setMethodLen(len_methode);
		res.setMethodList(liste_methods);
		res.setAttrLen(len_attr);
		res.setAttrList(liste_attrs);

		complete_analyse(res);

		return res;
	}

	private void complete_analyse(TClass res) {
		if(res!=null)
		{
			if(res.getMethodList()!=null&&!res.getMethodList().isEmpty()) {
				for(TMethodInfo methode:res.getMethodList()) {
					if (methode.getAttrList() != null && !methode.getAttrList().isEmpty()) {
						for (TAttributInfo attr : methode.getAttrList()) {
							if (attr.getName() > 0) {
								TCpInfo cp = res.getCpInfo(attr.getName());
								if (cp != null) {
									if (cp instanceof TCUTF8) {
										TCUTF8 u = (TCUTF8) cp;
										String msg;
										msg = u.getString();
										if (msg != null && msg.equals("Code")) {
											TAttrCode code;
											code = analyse_code(res, attr);
											attr.setDetails(code);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private TAttrCode analyse_code(TClass res, TAttributInfo attr) {
		ParcourtFichier p;
		char name,max_stack,max_local,except_len,nb,attr_len2;
		long attr_len,code_len;
		List<Byte> liste_code;
		List<TAttributInfo> liste_attr;
		TAttrCode code;
		List<TExceptionTable> liste_exception;
		byte[] liste_code2;
		char start_pc;
		char end_pc;
		char handler_pc;
		char catch_type;
		TExceptionTable except;

		p=new ParcourtFichier(attr.getInfo());

		name=attr.getName();
		attr_len=attr.getLen();
		//name=p.getT2();
		//attr_len=p.getT4();
		max_stack=p.getT2();
		max_local=p.getT2();
		code_len=p.getT4();
		liste_code=p.getBytes(code_len);
		except_len=p.getT2();
		liste_exception=Lists.newArrayList();
		for(int i=0;i<except_len;i++) {
			start_pc=p.getT2();
			end_pc=p.getT2();
			handler_pc=p.getT2();
			catch_type=p.getT2();
			except=new TExceptionTable(start_pc,end_pc,handler_pc,catch_type);
			liste_exception.add(except);
		}
		attr_len2=p.getT2();
		liste_attr=getListAttributs(attr_len2,p);

		liste_code2=new byte[liste_code.size()];
		for(int i=0;i<liste_code.size();i++)
		{
			liste_code2[i]=liste_code.get(i);
		}

		code=new TAttrCode(name,attr_len,max_stack,max_local,code_len,liste_code2,liste_exception,attr_len2,liste_attr);

		return code;
	}

	private List<TAttributInfo> getListAttributs(char len_attr, ParcourtFichier p) {
		List<TAttributInfo> res;
		char acces,name,descriptor;
		long attr_len;
		TAttributInfo tmp;
		List<Byte> infos;

		res=Lists.newArrayList();

		for(int i=0;i<len_attr;i++)
		{
			name=p.getT2();
			attr_len=p.getT4();
			infos=p.getBytes(attr_len);

			tmp=new TAttributInfo(name,attr_len,infos);
			res.add(tmp);
		}

		return res;
	}

	private List<TMethodInfo> getListMethods(char len_methode, ParcourtFichier p) {
		List<TMethodInfo> res;
		char acces,name,descriptor,attr_len;
		TMethodInfo tmp;
		List<TAttributInfo> liste_attr;

		res=Lists.newArrayList();

		for(int i=0;i<len_methode;i++)
		{
			acces=p.getT2();
			name=p.getT2();
			descriptor=p.getT2();
			attr_len=p.getT2();
			liste_attr=getListAttributs(attr_len,p);

			tmp=new TMethodInfo();
			tmp.setAccess(acces);
			tmp.setName(name);
			tmp.setDescriptor(descriptor);
			tmp.setAttrLen(attr_len);
			tmp.setAttrList(liste_attr);

			res.add(tmp);
		}

		return res;
	}

	private List<TFieldInfo> getListFields(char len_field, ParcourtFichier p) {
		List<TFieldInfo> res;
		char acces,name,descriptor,attr_len;
		TFieldInfo tmp;
		List<TAttributInfo> liste_attr;

		res=Lists.newArrayList();

		for(int i=0;i<len_field;i++)
		{
			acces=p.getT2();
			name=p.getT2();
			descriptor=p.getT2();
			attr_len=p.getT2();
			liste_attr=getListAttributs(attr_len,p);

			tmp=new TFieldInfo();
			tmp.setAccess(acces);
			tmp.setName(name);
			tmp.setDescriptor(descriptor);
			tmp.setAttrLen(attr_len);
			tmp.setAttrList(liste_attr);

			res.add(tmp);
		}

		return res;
	}

	private List<Character> getListInterfaces(char len_interf, ParcourtFichier p) {
		List<Character> tab_interf;
		if(len_interf>0)
		{
			tab_interf= Lists.newArrayList();
			for(int i=0;i<len_interf;i++)
			{
				tab_interf.add(p.getT2());
			}
		}
		else
		{
			tab_interf=null;
		}
		return tab_interf;
	}

	private void getConst(ParcourtFichier contenu, char len_cp, List<TCpInfo> liste_const) {
		//int pos;
		byte type;
		//pos=10;
		for(int pos0=0;pos0<len_cp-1;pos0++)
		{
			type= (byte) contenu.getT1();
			//pos0++;
			//pos++;
			if(type==1)
			{// Utf 8
				int len_utf8;
				byte buf[];
				TCUTF8 tmp;
				len_utf8=contenu.getT2();
				//pos+=2;
				buf=contenu.getBytes(len_utf8);//get4(contenu,pos,len_utf8);
				tmp=new TCUTF8(buf);
				liste_const.add(tmp);
			}
			else if(type==3)
			{// int
				//byte buf[];
				/*buf=get4(contenu,pos,4);
				pos+=4;
				ByteBuffer wrapped = ByteBuffer.wrap(buf); // big-endian by default
				int num = wrapped.getInt();*/
				int num=contenu.getInt();
				TCInt tmp;
				tmp=new TCInt(num);
				liste_const.add(tmp);
			}
			else if(type==4)
			{// float
				/*byte buf[];
				buf = get4(contenu, pos, 4);
				pos += 4;
				ByteBuffer wrapped = ByteBuffer.wrap(buf); // big-endian by default
				float num = wrapped.getFloat();*/
				float num=contenu.getFloat();
				TCFloat tmp;
				tmp = new TCFloat(num);
				liste_const.add(tmp);
			}
			else if(type==5)
			{// long
				/*byte buf[];
				buf = get4(contenu, pos, 8);
				pos += 8;
				pos0++;
				ByteBuffer wrapped = ByteBuffer.wrap(buf); // big-endian by default
				long num = wrapped.getLong();*/
				long num=contenu.getLong();
				pos0++;
				TCLong tmp;
				tmp = new TCLong(num);
				liste_const.add(tmp);
			}
			else if(type==6)
			{// double
				/*byte buf[];
				buf = get4(contenu, pos, 8);
				pos += 8;
				pos0++;
				ByteBuffer wrapped = ByteBuffer.wrap(buf); // big-endian by default
				double num = wrapped.getDouble();*/
				double num=contenu.getDouble();
				pos0++;
				TCDouble tmp;
				tmp = new TCDouble(num);
				liste_const.add(tmp);
			}
			else if(type==7)
			{// class ref
				int index;
				index = contenu.getT2();//get3(contenu, pos, 2);
				//pos += 2;
				TCClassRef tmp;
				tmp=new TCClassRef(index);
				liste_const.add(tmp);
			}
			else if(type==8)
			{// string ref
				int index;
				index = contenu.getT2();//get3(contenu, pos, 2);
				//pos += 2;
				TCStringRef tmp;
				tmp=new TCStringRef(index);
				liste_const.add(tmp);
			}
			else if(type==9)
			{// field ref
				int index_class,index_name;
				index_class = contenu.getT2();//get3(contenu, pos, 2);
				index_name = contenu.getT2();//get3(contenu, pos, 2);
				//pos += 4;
				TCFieldRef tmp;
				tmp=new TCFieldRef(index_class,index_name);
				liste_const.add(tmp);
			}
			else if(type==10)
			{// methode ref
				int index_class,index_name;
				index_class = contenu.getT2();//get3(contenu, pos, 2);
				index_name = contenu.getT2();//get3(contenu, pos, 2);
				//pos += 4;
				TCMethodRef tmp;
				tmp=new TCMethodRef(index_class,index_name);
				liste_const.add(tmp);
			}
			else if(type==11)
			{// interface ref
				int index_class,index_name;
				index_class = contenu.getT2();//get3(contenu, pos, 2);
				index_name = contenu.getT2();//get3(contenu, pos, 2);
				//pos += 4;
				TCInterfaceRef tmp;
				tmp=new TCInterfaceRef(index_class,index_name);
				liste_const.add(tmp);
			}
			else if(type==12)
			{// name and type
				int index_name,index_type;
				index_name = contenu.getT2();//get3(contenu, pos, 2);
				index_type = contenu.getT2();//get3(contenu, pos, 2);
				//pos += 4;
				TCNameType tmp;
				tmp=new TCNameType(index_name,index_type);
				liste_const.add(tmp);
			}
			else if(type==15)
			{// method handle
				int type_descriptor,index;
				type_descriptor = contenu.getT1();//int) get2(contenu, pos, 1);
				index = contenu.getT2();//(int) get2(contenu, pos, 2);
				//pos += 3;
				TCMethodHandle tmp;
				tmp=new TCMethodHandle(type_descriptor,index);
				liste_const.add(tmp);
			}
			else if(type==16)
			{// method type
				int type_descriptor;
				type_descriptor = contenu.getT2();//get3(contenu, pos, 2);
				//pos += 2;
				TCMethodType tmp;
				tmp=new TCMethodType(type_descriptor);
				liste_const.add(tmp);
			}
			else if(type==18)
			{// InvokeDynamic
				int tmp2;
				if(1==1) {
					throw new IllegalArgumentException();
				}
				tmp2 = contenu.getT2();//get3(contenu, pos, 2);
				tmp2 = contenu.getT2();//get3(contenu, pos, 2);
				//pos += 4;
				TCInvokeDynamic tmp;
				tmp=new TCInvokeDynamic();
				liste_const.add(tmp);
			}
			else
			{
				throw new IllegalArgumentException("type="+type+",pos0="+pos0);
			}
		}
		//return pos;
	}

	private List<Byte> lecture(String file) throws IOException {
		InputStream is = null;
		      int i;
		      char c;
		//StringBuilder buf;
		List<Byte> liste;

		//buf=new StringBuilder();
		liste= Lists.newArrayList();
		      try{
		         // new input stream created
		         is = new FileInputStream(file);

			      byte[] data      = new byte[1024];
		         //System.out.println("Characters printed:");

		         // reads till the end of the stream
		         while((i=is.read(data))!=-1)
		         {
		            // converts integer to character
		            //c=(char)i;

			         for(int j=0;j<i;j++)
			         {
				         liste.add(data[j]);
			         }
		            // prints character
		            //System.out.print(c);

		         }
		      /*}catch(Exception e){

		         // if any I/O error occurs
		         e.printStackTrace();*/
		      }finally{

		         // releases system resources associated with this stream
		         if(is!=null)
		            is.close();
		      }
		return liste;
	}
}

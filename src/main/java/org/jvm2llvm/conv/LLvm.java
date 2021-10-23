package org.jvm2llvm.conv;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.backend.ssa.intermediaire.*;
import org.jvm2llvm.EOpCode;
import org.jvm2llvm.tcclass.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * Created by Alain on 25/05/2015.
 */
public class LLvm {

	public LLvm() {
	}
	
	public void conv(TClass classe){
		Preconditions.checkNotNull(classe);
		analyse(classe);
	}

	private void analyse(TClass classe) {
		Preconditions.checkNotNull(classe);
		if(classe.getMethodList()!=null&&!classe.getMethodList().isEmpty())
		{

			File file=new File("target/resultat/res.ll");

			try {
			file.getParentFile().mkdirs();
			Files.write("",file,Charsets.UTF_8);
			}catch(IOException e){
				System.err.println("Erreur pour ecrire le fichier "+file+" : "+e.getMessage());
			}

			for(TMethodInfo methode:classe.getMethodList())
			{
				analyse(classe,methode, file);
			}

			// fonction main
			try {
				Files.append(generationMain(), file, Charsets.UTF_8);
			}catch(IOException e){
				System.err.println("Erreur pour ecrire le fichier "+file+" : "+e.getMessage());
			}
		}
	}

	private void analyse(TClass classe, TMethodInfo methode, File file) {
		List<InstrJvm> liste_instr;
		Methode methode_llvm;
		Preconditions.checkNotNull(classe);
		Preconditions.checkNotNull(methode);

		StringBuilder res=new StringBuilder();
		liste_instr= Lists.newArrayList();
		if(methode.getAttrList()!=null) {
			for (TAttributInfo attr : methode.getAttrList()) {
				if (attr.getDetails() != null) {
					TAttrDetails detail = attr.getDetails();
					if (detail instanceof TAttrCode) {
						TAttrCode code;
						code = (TAttrCode) detail;
						analyse_instr(classe,methode,code.getCode(),liste_instr);
						System.out.println("analyse="+liste_instr);
						methode_llvm=convLlvm(classe, methode, liste_instr,code);
						System.out.println("methode="+methode_llvm);
						res.append(affiche_llvm(classe, methode,methode_llvm));
						res.append("\n");
						methode_llvm.calcul_basic_bloc();
					}
				}
			}
		}
		try {
			Files.append(res, file, Charsets.UTF_8);
		}catch(IOException e){
			System.err.println("Erreur pour ecrire le fichier "+file+" : "+e.getMessage());
		}
	}

	private String generationMain() {
		ByteArrayOutputStream buf=new ByteArrayOutputStream();
		PrintStream out=new PrintStream(buf);
		String res;
		out.println(";************ LLVM main ****************");
		out.println();
		out.println();

		out.println("define dso_local i32 @main() #0 {");
		out.println("entry:");

		out.println("\tret i32 0");

		out.println("}");

		out.println();
		out.println();
		out.println(";************ LLVM ****************");
		out.flush();

		res=buf.toString();
		System.out.println("LLVM main:\n"+ res);
		return res;
	}

	private String affiche_llvm(TClass classe, TMethodInfo methode, Methode methode_llvm) {
		String res;
		ByteArrayOutputStream buf=new ByteArrayOutputStream();
		PrintStream out=new PrintStream(buf);

		out.println(";************ LLVM ****************");
		out.println();
		out.println();

		out.println("define i32 @sum_"+nettoie_nom_methode(classe.getUtf8(methode.getName()))+"() {");
		out.println("entry:");
		//if()
		for(Instr instr:methode_llvm.getListe_instructions())
		{
			out.println("\t"+conv(instr));
		}
		out.println("\tret i32 0");

		out.println("}");

		out.println();
		out.println();
		out.println(";************ LLVM ****************");
		out.flush();

		res=buf.toString();
		System.out.println("LLVM:\n"+ res);
		return res;
	}

	private String nettoie_nom_methode(String s) {
		return s.replaceAll("<","_").replaceAll(">","_");
	}

	private String conv(Instr instr) {
		Preconditions.checkNotNull(instr);
		if(instr instanceof Affect)
		{
			Affect affect= (Affect) instr;
			Expr exp = affect.getExpr();
			if(exp instanceof Cst){
				exp=new ExprOpe(Operation.PLUSB,exp,new Cst(0));
			} else if(exp instanceof Var){
				exp=new ExprOpe(Operation.PLUSB,exp,new Cst(0));
			}
			return "%"+affect.getVar().getNomVar()+" = "+conv(exp);
		}
		return "";
	}

	private String conv(Expr expr) {
		Preconditions.checkNotNull(expr);
		if(expr instanceof Cst)
		{
			Cst c= (Cst) expr;
			return c.toString();
		}
		else if(expr instanceof ExprOpe)
		{
			ExprOpe e= (ExprOpe) expr;
			String s;
			boolean premier;
			s="";
			switch(e.getOp())
			{
				case PLUSB:
					s+="add nsw i32";
			}
			if(e.getListe_expr()!=null&&!e.getListe_expr().isEmpty())
			{
				s+=" ";
				premier=true;
				for(Expr e2:e.getListe_expr())
				{
					if(!premier)
						s+=",";
					s+=conv(e2);
					premier=false;
				}
			}
			return s;
		}
		else if(expr instanceof Var)
		{
			Var v= (Var) expr;
			return "%"+v.getNomVar();
		}
		else
		{
			Verify.verify(false,"expr="+expr);
		}
		return "";
	}

	private Methode convLlvm(TClass classe, TMethodInfo methode, List<InstrJvm> liste_instr, TAttrCode code) {
		//List<Instr> liste_instr2;
		Methode res;
		Instr instr2;
		Var var,var2,var3;
		Type type_int32;
		int no_temp;
		Affect affect;
		List<Var> pile;
		//res=Lists.newArrayList();
		res=new Methode(classe.getUtf8(methode.getName()));
		type_int32=new Type("int32");
		no_temp=0;
		pile=Lists.newArrayList();
		if(code.getMax_local()>0) {
			for(int i=0;i<code.getMax_local();i++) {
				var=new Var("local"+i,type_int32);
				res.getListe_variables().add(var);
			}
		}
		if(liste_instr!=null&&!liste_instr.isEmpty())
		{
			for(InstrJvm instr:liste_instr)
			{
				if(est_const(instr.getOp()))
				{
					/*var=new Var("temp"+no_temp,type_int32);
					res.getListe_variables().add(var);
					no_temp++;*/
					var=res.newVarTemp(type_int32);
					affect=new Affect(var,new Cst(val_const(instr.getOp())));
					res.getListe_instructions().add(affect);
					pile.add(var);
				}
				else if(est_store(instr.getOp()))
				{
					var=res.getListe_variables().get(val_store(instr.getOp()));
					Verify.verifyNotNull(var);
					var2=pile.get(pile.size()-1);
					Verify.verifyNotNull(var2);
					Verify.verify(var.getType()==var2.getType());
					pile.remove(pile.size()-1);
					affect=new Affect(var,var2);
					res.getListe_instructions().add(affect);
				}
				else if(est_load(instr.getOp()))
				{
					var=res.getListe_variables().get(val_load(instr.getOp()));
					Verify.verifyNotNull(var);
					var2=res.newVarTemp(type_int32);
					Verify.verifyNotNull(var2);
					Verify.verify(var.getType()==var2.getType());
					pile.add(var2);
					affect=new Affect(var2,var);
					res.getListe_instructions().add(affect);
				}
				else if(instr.getOp()==EOpCode.BIPUSH)
				{
					byte b;
					Verify.verifyNotNull(instr.getParam());
					Verify.verify(instr.getParam().length == 1);
					b=instr.getParam()[0];
					//var=res.getListe_variables().get(val_load(instr.getOp()));
					//Verify.verifyNotNull(var);
					var=res.newVarTemp(type_int32);
					Verify.verifyNotNull(var);
					//Verify.verify(var.getType()==var2.getType());
					pile.add(var);
					affect=new Affect(var,new Cst(b));
					res.getListe_instructions().add(affect);
				}
				else if(instr.getOp()==EOpCode.IADD)
				{
					var=pile.get(pile.size() - 2);
					Verify.verifyNotNull(var);
					var2=pile.get(pile.size()-1);
					Verify.verifyNotNull(var2);
					Verify.verify(var.getType()==var2.getType());
					pile.remove(pile.size()-1);
					pile.remove(pile.size()-1);
					var3=res.newVarTemp(type_int32);
					Verify.verifyNotNull(var3);
					pile.add(var3);
					affect=new Affect(var3,new ExprOpe(Operation.PLUSB,var,var2));
					res.getListe_instructions().add(affect);
				}
			}
		}
		return res;
	}

	private boolean est_load(EOpCode op)
	{
		if(op==EOpCode.ILOAD_0||op==EOpCode.ILOAD_1||op==EOpCode.ILOAD_2
				||op==EOpCode.ILOAD_3)
		{
			return true;
		}
		return false;
	}

	private int val_load(EOpCode op)
	{
		if(op==EOpCode.ILOAD_0)
			return 0;
		else if(op==EOpCode.ILOAD_1)
			return 1;
		else if(op==EOpCode.ILOAD_2)
			return 2;
		else if(op==EOpCode.ILOAD_3)
			return 3;
		Verify.verify(false);
		return 0;
	}

	private boolean est_store(EOpCode op)
	{
		if(op==EOpCode.ISTORE_0||op==EOpCode.ISTORE_1||op==EOpCode.ISTORE_2
				||op==EOpCode.ISTORE_3)
		{
			return true;
		}
		return false;
	}

	private int val_store(EOpCode op)
	{
		if(op==EOpCode.ISTORE_0)
			return 0;
		else if(op==EOpCode.ISTORE_1)
			return 1;
		else if(op==EOpCode.ISTORE_2)
			return 2;
		else if(op==EOpCode.ISTORE_3)
			return 3;
		Verify.verify(false);
		return 0;
	}

	private boolean est_const(EOpCode op)
	{
		if(op==EOpCode.ICONST_0||op==EOpCode.ICONST_1||op==EOpCode.ICONST_2
				||op==EOpCode.ICONST_3||op==EOpCode.ICONST_4||op==EOpCode.ICONST_5)
		{
			return true;
		}
		return false;
	}

	private int val_const(EOpCode op)
	{
		if(op==EOpCode.ICONST_0)
			return 0;
		else if(op==EOpCode.ICONST_1)
			return 1;
		else if(op==EOpCode.ICONST_2)
			return 2;
		else if(op==EOpCode.ICONST_3)
			return 3;
		else if(op==EOpCode.ICONST_4)
			return 4;
		else if(op==EOpCode.ICONST_5)
			return 5;
		Verify.verify(false);
		return 0;
	}

	private void analyse_instr(TClass classe, TMethodInfo methode, byte[] code, List<InstrJvm> liste_instr) {
		int nb_instr;
		EOpCode op;
		String s;
		InstrJvm instr;
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
				//out.println(nb_instr+") "+s);
				if(op.getNbParam()>0) {
					byte[] tab;
					tab=new byte[op.getNbParam()];
					System.arraycopy(code,i+1,tab,0,tab.length);
					instr=new InstrJvm(op,tab);
					i += op.getNbParam();
				}
				else {
					instr=new InstrJvm(op,null);
				}
				liste_instr.add(instr);
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

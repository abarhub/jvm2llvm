package org.jvm2llvm;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Hello world!
 * C:\Users\Alain\Documents\Visual Studio 2010\Projects\tjvm\java
 */
public class Tools
{
    public static void main( String[] args ) throws IOException {
	    List<List<String>> list;
	    File f,rep_out;
        //System.out.println( "Hello World!" );
	    //C:\Users\Alain\Documents\Visual Studio 2010\Projects\tjvm\java
	    //
	    f=new File("D:\\projet\\jvm2llvm\\doc\\tools\\opcodes3.csv");
	    rep_out=new File("D:\\projet\\jvm2llvm\\src\\main\\java\\org\\jvm2llvm");
	    list=read_csv(f);
	    System.out.println("liste="+list);
	    genere(list,rep_out);
	    System.out.println("Fin");
    }

	private static void genere(List<List<String>> list, File rep_out) {
		File f_c,f_h;
		String s,s1,s2,s3,s4,s0;
		int nb,no_param,nb2,nb3;

		//f_c=new File(rep_out,"opcode.c");
		//f_h=new File(rep_out,"opcode.h");
		f_h=new File(rep_out,"EOpCode.java");

		s="";

		s+="package org.jvm2llvm;\n";
		s+="\n\n";

		s+="// généré le "+new Date()+"\n\n";

		s+="public enum EOpCode {\n\t";
		nb=0;
		for(List<String> ligne:list)
		{
			if(ligne!=null&&ligne.size()>0)
			{
				s1=ligne.get(0);
				s2=ligne.get(1);
				if(!vide(s1)&&!vide(s2))
				{
					if(nb>0)
						s+=",";
					s0=s1.trim();
					s1=s1.trim().toUpperCase();
					no_param=0;
					s4=ligne.get(2);
					if(!vide(s4))
					{
						if(s4.contains(":"))
						{
							s4=s4.substring(0,s4.indexOf(':'));
							if(!vide(s4))
							{
								s4=s4.trim();
								try{
									no_param=Integer.parseInt(s4);
								}catch(NumberFormatException e){
									// on ignore l'erreur
									no_param=0;
								}
								no_param=Math.max(no_param,0);
							}
						}
					}
					s+=s1+"(0x"+s2+",\""+s0+"\","+no_param+")";
					nb++;
					if(nb%5==0)
					{
						s+="\n\t";
					}
				}
			}
		}
		s+=";\n\n";
		s+="\tprivate EOpCode(int no,String nom,int nbParam){\n" +
				"\t\tthis.no=(char)no;\n" +
				"\t\tthis.nom=nom;\n" +
				"\t\tthis.nbParam=nbParam;\n" +
				"\t}\n" +
				"\n" +
				"\tpublic char getNo() {\n" +
				"\t\treturn no;\n" +
				"\t}\n" +
				"\n" +
				"\tpublic String getNom() {\n" +
				"\t\treturn nom;\n" +
				"\t}\n" +
				"\n" +
				"\tpublic int getNbParam() {\n" +
				"\t\treturn nbParam;\n" +
				"\t}\n" +
				"\n" +
				"\tprivate final char no;\n" +
				"\tprivate final String nom;\n" +
				"\tprivate final int nbParam;\n";
		s+="}\n\n";

		/*s+="struct opcode_table{int opcode;char *name;int size_param;};\n";
		nb=0;
		s+="typedef struct opcode_table OPCODE_TABLE;\n\n";
		nb=list.size();
		s+="extern OPCODE_TABLE table_opcode["+nb+"];\n";
		s+="extern int len_tab_opcode;\n";
		//s+="const int len_tab_opcode="+nb+";\n";

		s+="\n#endif\n";*/
		try {
		 Files.write(s, f_h, Charsets.UTF_8);
		} catch( IOException e ) {
		 e.printStackTrace();
		}

		/*s="";

		s+="// généré le "+new Date()+"\n\n";

		s+="#include <stdio.h>\n";
		s+="#include <assert.h>\n";
		s+="#include \"opcode.h\"\n";
		s+="#include \"context.h\"\n";
		s+="#include \"opcode2.h\"\n\n";

		nb=0;
		s+="OPCODE_TABLE table_opcode[]={\n";
		for(List<String> ligne:list)
		{
			if(ligne!=null&&ligne.size()>0)
			{
				s1=ligne.get(0);
				s2=ligne.get(1);
				if(!vide(s1)&&!vide(s2))
				{
					if(nb>0)
						s+=",";
					s1=s1.trim();
					s3=s1.trim().toUpperCase();
					no_param=0;
					s4=ligne.get(2);
					if(!vide(s4))
					{
						if(s4.contains(":"))
						{
							s4=s4.substring(0,s4.indexOf(':'));
							if(!vide(s4))
							{
								s4=s4.trim();
								try{
									no_param=Integer.parseInt(s4);
								}catch(NumberFormatException e){
									// on ignore l'erreur
									no_param=0;
								}
								no_param=Math.max(no_param,0);
							}
						}
					}
					s+="{"+s3+",\""+s1+"\","+no_param+"}";
					nb++;
					//if(nb%10==0)
					{
						s+="\n";
					}
				}
			}
		}
		s+="};\n";
		s+="int len_tab_opcode="+nb+";\n\n";

		s+="void execute(JCONTEXT *context) {\n";
		s+="\tassert(context!=NULL);\n";
		s+="\tcontext->INSTR_DEBUT(context);\n";
		s+="\twhile(!context->EST_FINI(context)) {\n";
		s+="\t\tcontext->INSTR_ITERATION(context);\n";
		//s+="\t\tswitch(context->DONNE_INSTR_SUIVANTE(context)){\n";
		s+="\t\tremplit_opcode(context);\n";
		s+="\t\tswitch(context->opcode_courant){\n";
		nb=0;
		nb2=0;
		for(List<String> ligne:list)
		{
			if(ligne!=null&&ligne.size()>0)
			{
				s1=ligne.get(0);
				if(!vide(s1))
				{
					s2=ligne.get(4);
					if(!vide(s1)&&!vide(s2))
					{
						s1=s1.trim().toUpperCase();
						s+="\t\t\tcase "+s1+": context->"+s2+";break;\n";
						nb2++;
					}
					nb++;
				}
			}
		}
		s+="\t\t\tdefault: context->INSTR_AUTRE(context);break;\n";
		s+="\t\t}\n";
		s+="\t}\n";
		s+="\tcontext->INSTR_FIN(context);\n";
		s+="}\n";

		System.out.println("nb instr : total="+nb+", traite="+nb2+" ("+(nb2*100.0/nb)+"%)");

		try {
		 Files.write(s, f_c, Charsets.ISO_8859_1);
		} catch( IOException e ) {
		 e.printStackTrace();
		}*/
	}

	private static boolean vide(String s) {
		return s==null||s.length()==0||s.trim().length()==0;
	}

	private static List<List<String>> read_csv(File f) throws IOException {
		List<List<String>> list;
		List<String> list2;
		FileReader fi=null;
		list= Lists.newArrayList();

		//try (FileReader fi=new FileReader(f)) {
		try{
			fi=new FileReader(f);
			CSVReader reader = new CSVReader(fi,';', '\"');
		    String [] nextLine;
		    while ((nextLine = reader.readNext()) != null) {
		        // nextLine[] is an array of values from the line
		        //System.out.println(nextLine[0] + nextLine[1] + "etc...");
			    if(nextLine.length>0)
			    {
				    list2=Lists.newArrayList();
				    list.add(list2);
				    for(String s:nextLine)
				    {
					    list2.add(s);
				    }
			    }
		    }
		}finally{
			if(fi!=null)
				fi.close();
		}

		/*try (FileReader fi=new FileReader(f)) {
			BufferedReader br = new BufferedReader(fi);
			String line;
		    //return br.readLine();
			while((line=br.readLine())!=null)
			{
				if(line.length()>0)
				{

				}
			}
		}*/
		return list;
	}
}

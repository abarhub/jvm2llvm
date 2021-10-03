package org.jvm2llvm;

import org.jvm2llvm.conv.LLvm;
import org.jvm2llvm.tcclass.TClass;

import java.io.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!" );
        test1();
    }

	private static void test1() throws IOException {
		/*List<Byte> contenu;
		TClass res;
		//String content = Files.toString(new File("C:\\projet\\jvm2llvm\\target\\classes\\org\\jvm2llvm\\Test1.class"), Charsets.UTF_8);
		contenu=lecture("C:\\projet\\jvm2llvm\\target\\classes\\org\\jvm2llvm\\Test1.class");
		res=parse(contenu);*/
		ReadFile read;
		TClass res;
		LLvm llvm;

		read=new ReadFile();

		res=read.read("D:\\projet\\jvm2llvm\\target\\classes\\org\\jvm2llvm\\Test1.class");

		System.out.println("res="+res);

		res.affiche(System.out);

		llvm=new LLvm();

		llvm.conv(res);
	}

}

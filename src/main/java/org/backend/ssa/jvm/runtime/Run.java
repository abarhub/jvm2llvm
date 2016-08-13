package org.backend.ssa.jvm.runtime;

public class Run extends ClassLoader {

	public Class<?> defClass(String nom,byte b[],int n1,int n2)
	{
		return defineClass(nom,b,n1,n2);
	}
}

package org.jvm2llvm;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by Alain on 17/05/2015.
 */
public class ParcourtFichier {

	private final List<Byte> contenu;
	private int pos;

	public ParcourtFichier(List<Byte> contenu) {
		this.contenu = ImmutableList.copyOf(contenu);
		pos=0;
	}

	public char getT2()
	{
		return (char) get(2);
	}

	public char getT1()
	{
		return (char) get(1);
	}

	public int getT3()
	{
		return (int) get(3);
	}

	public long getT4()
	{
		return get(4);
	}

	public byte[] getBytes(int len)
	{
		byte tmp[];

		if(len<=0)
			throw new IllegalArgumentException();

		tmp=new byte[len];
		for(int i=0;i<len;i++)
		{
			tmp[i]=contenu.get(pos);
			pos++;
		}
		return tmp;
	}

	public List<Byte> getBytes(long len)
	{
		List<Byte> tmp;

		if(len<=0)
			throw new IllegalArgumentException();

		tmp= Lists.newArrayList();
		for(int i=0;i<len;i++)
		{
			tmp.add(contenu.get(pos));
			pos++;
		}
		return tmp;
	}

	public int getInt()
	{
		byte buf[];
		buf=getBytes(4);
		ByteBuffer wrapped = ByteBuffer.wrap(buf); // big-endian by default
		 int num = wrapped.getInt();
		return num;
	}

	public float getFloat()
	{
		byte buf[];
		buf=getBytes(4);
		ByteBuffer wrapped = ByteBuffer.wrap(buf); // big-endian by default
		float num = wrapped.getFloat();
		return num;
	}

	public long getLong()
	{
		byte buf[];
		buf=getBytes(8);
		ByteBuffer wrapped = ByteBuffer.wrap(buf); // big-endian by default
		long num = wrapped.getLong();
		return num;
	}

	public double getDouble()
	{
		byte buf[];
		buf=getBytes(8);
		ByteBuffer wrapped = ByteBuffer.wrap(buf); // big-endian by default
		double num = wrapped.getDouble();
		return num;
	}

	private long get(int len)
	{
		long tmp;

		if(len<=0||len>4)
			throw new IllegalArgumentException();

		tmp=0;
		for(int i=0;i<len;i++)
		{
			tmp<<=8;
			tmp+=(char)conv_byte(contenu.get(pos));
			pos++;
		}
		return tmp;
	}

	/*private String get(List<Byte> liste,int pos,int len){
			String s;

			s="";
			for(int i=0;i<len;i++)
			{
				s+=(char)conv_byte(liste.get(pos+i));
			}
			return s;
		}

		private long get2(List<Byte> liste,int pos,int len){
			long tmp;

			tmp=0;
			for(int i=0;i<len;i++)
			{
				tmp<<=8;
				tmp+=(char)conv_byte(liste.get(pos+i));
			}
			return tmp;
		}

		private char get3(List<Byte> liste,int pos,int len){
			char tmp;

			tmp=0;
			for(int i=0;i<len;i++)
			{
				tmp<<=8;
				tmp+=(char)conv_byte(liste.get(pos+i));
			}
			return tmp;
		}

		private byte[] get4(List<Byte> liste,int pos,int len){
				byte tmp[];

				tmp=new byte[len];
				for(int i=0;i<len;i++)
				{
					tmp[i]=liste.get(pos+i);
				}
				return tmp;
			}*/

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

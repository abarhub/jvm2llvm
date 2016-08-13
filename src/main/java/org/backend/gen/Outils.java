package org.backend.gen;

public class Outils {

	public static String conv(double valeur) {
		String res;
		//return "11000001110100110000000000000000b";
		//return "00111111100000000000000000000000b";
		//if(Math.signum(valeur)<0.0)
		{
			res=Integer.toBinaryString(Float.floatToIntBits((float)valeur));
		}
		return res;
	}

}

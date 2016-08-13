package org.jvm2llvm;


// généré le Sat May 23 14:21:46 CEST 2015

public enum EOpCode {
	AALOAD(0x32,"aaload",0),AASTORE(0x53,"aastore",0),ACONST_NULL(0x1,"aconst_null",0),ALOAD(0x19,"aload",1),ALOAD_0(0x2a,"aload_0",0)
	,ALOAD_1(0x2b,"aload_1",0),ALOAD_2(0x2c,"aload_2",0),ALOAD_3(0x2d,"aload_3",0),ANEWARRAY(0xbd,"anewarray",2),ARETURN(0xb0,"areturn",0)
	,ARRAYLENGTH(0xbe,"arraylength",0),ASTORE(0x3a,"astore",1),ASTORE_0(0x4b,"astore_0",0),ASTORE_1(0x4c,"astore_1",0),ASTORE_2(0x4d,"astore_2",0)
	,ASTORE_3(0x4e,"astore_3",0),ATHROW(0xbf,"athrow",0),BALOAD(0x33,"baload",0),BASTORE(0x54,"bastore",0),BIPUSH(0x10,"bipush",1)
	,BREAKPOINT(0xca,"breakpoint",0),CALOAD(0x34,"caload",0),CASTORE(0x55,"castore",0),CHECKCAST(0xc0,"checkcast",2),D2F(0x90,"d2f",0)
	,D2I(0x8e,"d2i",0),D2L(0x8f,"d2l",0),DADD(0x63,"dadd",0),DALOAD(0x31,"daload",0),DASTORE(0x52,"dastore",0)
	,DCMPG(0x98,"dcmpg",0),DCMPL(0x97,"dcmpl",0),DCONST_0(0x0e,"dconst_0",0),DCONST_1(0x0f,"dconst_1",0),DDIV(0x6f,"ddiv",0)
	,DLOAD(0x18,"dload",1),DLOAD_0(0x26,"dload_0",0),DLOAD_1(0x27,"dload_1",0),DLOAD_2(0x28,"dload_2",0),DLOAD_3(0x29,"dload_3",0)
	,DMUL(0x6b,"dmul",0),DNEG(0x77,"dneg",0),DREM(0x73,"drem",0),DRETURN(0xaf,"dreturn",0),DSTORE(0x39,"dstore",1)
	,DSTORE_0(0x47,"dstore_0",0),DSTORE_1(0x48,"dstore_1",0),DSTORE_2(0x49,"dstore_2",0),DSTORE_3(0x4a,"dstore_3",0),DSUB(0x67,"dsub",0)
	,DUP(0x59,"dup",0),DUP_X1(0x5a,"dup_x1",0),DUP_X2(0x5b,"dup_x2",0),DUP2(0x5c,"dup2",0),DUP2_X1(0x5d,"dup2_x1",0)
	,DUP2_X2(0x5e,"dup2_x2",0),F2D(0x8d,"f2d",0),F2I(0x8b,"f2i",0),F2L(0x8c,"f2l",0),FADD(0x62,"fadd",0)
	,FALOAD(0x30,"faload",0),FASTORE(0x51,"fastore",0),FCMPG(0x96,"fcmpg",0),FCMPL(0x95,"fcmpl",0),FCONST_0(0x0b,"fconst_0",0)
	,FCONST_1(0x0c,"fconst_1",0),FCONST_2(0x0d,"fconst_2",0),FDIV(0x6e,"fdiv",0),FLOAD(0x17,"fload",1),FLOAD_0(0x22,"fload_0",0)
	,FLOAD_1(0x23,"fload_1",0),FLOAD_2(0x24,"fload_2",0),FLOAD_3(0x25,"fload_3",0),FMUL(0x6a,"fmul",0),FNEG(0x76,"fneg",0)
	,FREM(0x72,"frem",0),FRETURN(0xae,"freturn",0),FSTORE(0x38,"fstore",1),FSTORE_0(0x43,"fstore_0",0),FSTORE_1(0x44,"fstore_1",0)
	,FSTORE_2(0x45,"fstore_2",0),FSTORE_3(0x46,"fstore_3",0),FSUB(0x66,"fsub",0),GETFIELD(0xb4,"getfield",2),GETSTATIC(0xb2,"getstatic",2)
	,GOTO(0xa7,"goto",2),GOTO_W(0xc8,"goto_w",4),I2B(0x91,"i2b",0),I2C(0x92,"i2c",0),I2D(0x87,"i2d",0)
	,I2F(0x86,"i2f",0),I2L(0x85,"i2l",0),I2S(0x93,"i2s",0),IADD(0x60,"iadd",0),IALOAD(0x2e,"iaload",0)
	,IAND(0x7e,"iand",0),IASTORE(0x4f,"iastore",0),ICONST_M1(0x2,"iconst_m1",0),ICONST_0(0x3,"iconst_0",0),ICONST_1(0x4,"iconst_1",0)
	,ICONST_2(0x5,"iconst_2",0),ICONST_3(0x6,"iconst_3",0),ICONST_4(0x7,"iconst_4",0),ICONST_5(0x8,"iconst_5",0),IDIV(0x6c,"idiv",0)
	,IF_ACMPEQ(0xa5,"if_acmpeq",2),IF_ACMPNE(0xa6,"if_acmpne",2),IF_ICMPEQ(0x9f,"if_icmpeq",2),IF_ICMPGE(0xa2,"if_icmpge",2),IF_ICMPGT(0xa3,"if_icmpgt",2)
	,IF_ICMPLE(0xa4,"if_icmple",2),IF_ICMPLT(0xa1,"if_icmplt",2),IF_ICMPNE(0xa0,"if_icmpne",2),IFEQ(0x99,"ifeq",2),IFGE(0x9c,"ifge",2)
	,IFGT(0x9d,"ifgt",2),IFLE(0x9e,"ifle",2),IFLT(0x9b,"iflt",2),IFNE(0x9a,"ifne",2),IFNONNULL(0xc7,"ifnonnull",2)
	,IFNULL(0xc6,"ifnull",2),IINC(0x84,"iinc",2),ILOAD(0x15,"iload",1),ILOAD_0(0x1a,"iload_0",0),ILOAD_1(0x1b,"iload_1",0)
	,ILOAD_2(0x1c,"iload_2",0),ILOAD_3(0x1d,"iload_3",0),IMPDEP1(0xfe,"impdep1",0),IMPDEP2(0xff,"impdep2",0),IMUL(0x68,"imul",0)
	,INEG(0x74,"ineg",0),INSTANCEOF(0xc1,"instanceof",2),INVOKEDYNAMIC(0xba,"invokedynamic",4),INVOKEINTERFACE(0xb9,"invokeinterface",4),INVOKESPECIAL(0xb7,"invokespecial",2)
	,INVOKESTATIC(0xb8,"invokestatic",2),INVOKEVIRTUAL(0xb6,"invokevirtual",2),IOR(0x80,"ior",0),IREM(0x70,"irem",0),IRETURN(0xac,"ireturn",0)
	,ISHL(0x78,"ishl",0),ISHR(0x7a,"ishr",0),ISTORE(0x36,"istore",1),ISTORE_0(0x3b,"istore_0",0),ISTORE_1(0x3c,"istore_1",0)
	,ISTORE_2(0x3d,"istore_2",0),ISTORE_3(0x3e,"istore_3",0),ISUB(0x64,"isub",0),IUSHR(0x7c,"iushr",0),IXOR(0x82,"ixor",0)
	,JSR(0xa8,"jsr",2),JSR_W(0xc9,"jsr_w",4),L2D(0x8a,"l2d",0),L2F(0x89,"l2f",0),L2I(0x88,"l2i",0)
	,LADD(0x61,"ladd",0),LALOAD(0x2f,"laload",0),LAND(0x7f,"land",0),LASTORE(0x50,"lastore",0),LCMP(0x94,"lcmp",0)
	,LCONST_0(0x9,"lconst_0",0),LCONST_1(0x0a,"lconst_1",0),LDC(0x12,"ldc",1),LDC_W(0x13,"ldc_w",2),LDC2_W(0x14,"ldc2_w",2)
	,LDIV(0x6d,"ldiv",0),LLOAD(0x16,"lload",1),LLOAD_0(0x1e,"lload_0",0),LLOAD_1(0x1f,"lload_1",0),LLOAD_2(0x20,"lload_2",0)
	,LLOAD_3(0x21,"lload_3",0),LMUL(0x69,"lmul",0),LNEG(0x75,"lneg",0),LOOKUPSWITCH(0xab,"lookupswitch",0),LOR(0x81,"lor",0)
	,LREM(0x71,"lrem",0),LRETURN(0xad,"lreturn",0),LSHL(0x79,"lshl",0),LSHR(0x7b,"lshr",0),LSTORE(0x37,"lstore",1)
	,LSTORE_0(0x3f,"lstore_0",0),LSTORE_1(0x40,"lstore_1",0),LSTORE_2(0x41,"lstore_2",0),LSTORE_3(0x42,"lstore_3",0),LSUB(0x65,"lsub",0)
	,LUSHR(0x7d,"lushr",0),LXOR(0x83,"lxor",0),MONITORENTER(0xc2,"monitorenter",0),MONITOREXIT(0xc3,"monitorexit",0),MULTIANEWARRAY(0xc5,"multianewarray",3)
	,NEW(0xbb,"new",2),NEWARRAY(0xbc,"newarray",1),NOP(0x0,"nop",0),POP(0x57,"pop",0),POP2(0x58,"pop2",0)
	,PUTFIELD(0xb5,"putfield",2),PUTSTATIC(0xb3,"putstatic",2),RET(0xa9,"ret",1),RETURN(0xb1,"return",0),SALOAD(0x35,"saload",0)
	,SASTORE(0x56,"sastore",0),SIPUSH(0x11,"sipush",2),SWAP(0x5f,"swap",0),TABLESWITCH(0xaa,"tableswitch",0),WIDE(0xc4,"wide",0)
	;

	private EOpCode(int no,String nom,int nbParam){
		this.no=(char)no;
		this.nom=nom;
		this.nbParam=nbParam;
	}

	public char getNo() {
		return no;
	}

	public String getNom() {
		return nom;
	}

	public int getNbParam() {
		return nbParam;
	}

	private final char no;
	private final String nom;
	private final int nbParam;
}


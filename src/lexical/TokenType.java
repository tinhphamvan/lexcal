package lexical;
/**
 * The {@code TokeType} enumeration represents types of tokens in subset of Java
 * language
 * 
 * @author Ira Korshunova
 * 
 */
public enum TokenType {
	
	
	STRINGnumber,
	
	Comment1line,
	
	LeftBlockCommentError,
	
	BLOCKCOMMENTnumber,//commment
	
	LeftBlockComment,
	
	RightBlockComment,
	
	COLEQnumber,
	
	LEnumber,

	GEnumber,

	NEnumber,
	
	DCONSTnumber,
	
	
	EOFnumber,

	SEMInumber,

	COLONnumber,

	COMMMAnumber,

	DOTnumber,

	LPARENnumber,

	RPARENnumber,

	LTnumber,

	GTnumber,

	EQnumber,

	MINUSnumber,

	PLUSnumber,

	TIMESnumber,

	DOTDOTnumber,
	
	WRITEnumber,
	
	WRITELNumber,

	ICONSTnumber,

	CCONSTnumber,

	SCONSTnumber,

	ANDnumber,

	ARRAYnumber,

	BEGINnumber,

	CONSTnumber,

	DIVnumber,

	DOWNTOnumber,

	ELSEnumber,

	ELSIFnumber,

	ENDnumber,

	ENDIFnumber,

	ENDLOOPnumber,

	ENDRECnumber,

	EXITnumber,

	FORnumber,

	FORWARDnumber,

	FUNCTIONnumber,

	IFnumber,
	
	ISnumber,

	LOOPnumber,

	NOTnumber,

	OFnumber,

	ORnumber,
	
	PROCEDUREnumber,

	PROGRAMnumber,
	
	RECORDnumber,

	REPEATnumber,

	RETURNnumber,

	THENnumber,

	TOnumber,
	
	TYPEnumber,

	UNTILnumber,

	VARnumber,

	WHILEnumber,
	
	TABnumber,
	
	LineComment,
	
	Intnumber,
		
	BLANKnumber,
	
	IDnumber,

	StringError;
	/**
	 * Determines if this token is auxiliary
	 * 
	 * @return {@code true} if token is auxiliary, {@code false} otherwise
	 */
	public boolean isAuxiliary() {
		return  this == LineComment || this == BLANKnumber || this == TABnumber;
				
	}
	
	public boolean isSpecial() {
		return  this == ANDnumber || this == ARRAYnumber || this == BEGINnumber || this == CONSTnumber || this == DIVnumber || this == DOWNTOnumber || this == ELSEnumber || this == ELSIFnumber || this == ENDnumber || this == ENDIFnumber || this == ENDLOOPnumber || this == ENDRECnumber || this == EXITnumber || this == FORnumber || this == FORWARDnumber || this == FUNCTIONnumber || this == IFnumber || this == ISnumber || this == LOOPnumber || this == NOTnumber || this == OFnumber || this == ORnumber || this == PROCEDUREnumber || this == PROGRAMnumber || this == RECORDnumber || this == REPEATnumber || this == RETURNnumber || this == THENnumber || this == TOnumber || this == TYPEnumber || this == UNTILnumber || this == VARnumber || this == WHILEnumber;
	}
	
	public boolean isIdentifier() {
		return this == IDnumber;
	}
}

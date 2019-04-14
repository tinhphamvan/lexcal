package lexical;

import java.util.List;

import lexical.LexicalAnalyzer;

public class SyntaxAnalyzer {
	int index = 0;
	List<Token> result;
	//TokenType tokenType = new TokenType();
	public SyntaxAnalyzer(){
		
	}
	
	public String getType(int index) {
		return this.result.get(index).tokenType.toString();
	}
	public String getValue(int index) {
		return this.result.get(index).tokenString;
	}
	
	public boolean validateCFG(List<Token> result) throws AnalyzerException{
		this.result=result;
		System.out.println(this.index);
		if (getType(this.index)==TokenType.PROGRAMnumber.toString()) {
			if(isProgram()) {
				this.index++;
			}else {
				return false;
			}
		}
		if(getType(this.index)==TokenType.VARnumber.toString()){
			this.index++;
			if(isVar()) {
				this.index++;
			}else {
				System.out.println("false");
				return false;
			}
		}
		//System.out.println("xong var index ="+this.index);
		if(getType(this.index)==TokenType.BEGINnumber.toString()){
			if(isBlock()) {
				return true; //stop here
			}else {
				return false;
			}
		}
		return false;
	}
	public boolean isBlock() {
		if(isBegin()) {
			this.index++;
		}else {
			return false;
		}
		
		//statement here
		if(stateMent()) {
			this.index++;
			System.out.println("statement true");
		}else {
			System.out.println("statement false");
			return false;
		}
		//System.out.println(getValue(this.index));
		//gap end
		if(isEndDot()) {
			this.index++;
			System.out.println("size ="+result.size());
			System.out.println("index="+this.index);
			if(this.index==result.size()-1) {
				return true;
			}
		}
		return false;
	}
	public boolean stateMent() {
		System.out.println("vao statement  ");
		if(getValue(this.index).toLowerCase().equals("write")||getValue(this.index).toLowerCase().equals("writeln")) {
			if(isWriteln()) {
				this.index++;
			}else {
				return false;
			}
			
		}
		
		if(getType(this.index)==TokenType.IDnumber.toString()&& !getValue(this.index).toLowerCase().equals("write") ) {
			if(isExpression()) {
				System.out.println("isExpre true");
				//this.index++;
				System.out.println("value = "+getValue(this.index));
			}else {
				return false;
			}
		}
		if(getType(this.index+1)==TokenType.ENDnumber.toString()) {
			return true;
		}
		this.index++;
		return stateMent();
//		if(getValue(this.index).toLowerCase().equals("write")||getValue(this.index).toLowerCase().equals("writeln")) {
//			if(isWriteln()) {
//				this.index++;
//			}else {
//				return false;
//			}
//			
//		}
//		
//		if(getType(this.index)==TokenType.IDnumber.toString()&& !getValue(this.index).toLowerCase().equals("write") ) {
//			if(isExpression()) {
//				System.out.println("isExpre true");
//				this.index++;
//			}else {
//				return false;
//			}
//		}
//		return false;
	}
	public boolean isWriteln() {
		if(getValue(this.index).toLowerCase().equals("write")||getValue(this.index).toLowerCase().equals("writeln")) {
			return true;
			//code here
			
		}
		return false;
	}
	public boolean isExpression() {
		System.out.println("vao expression");
		System.out.println("bat dau expre ="+getValue(this.index));
		if(getType(this.index)==TokenType.SEMInumber.toString()) {
			System.out.println("gap ; va dung");
			return true;
		}
		if(getType(this.index)==TokenType.IDnumber.toString()||getType(this.index)==TokenType.PLUSnumber.toString()||getType(this.index)==TokenType.ICONSTnumber.toString()) {
			System.out.println("gap bien");
			this.index++;
			
			if(getType(this.index)==TokenType.COLEQnumber.toString()&&getType(this.index+1)!=TokenType.SEMInumber.toString()) {
				System.out.println("gap :=");
				this.index++;
				return isExpression();
			}
			return isExpression();
		}
		
		System.out.println("false");
		return false;
	}
//	public boolean isExpression() {
//		System.out.println("vao expre");
//		if(getType(this.index)==TokenType.SEMInumber.toString()) {
//			System.out.println("gap ; va index ="+this.index);
//			return true;
//		}
//		if(getType(this.index)==TokenType.IDnumber.toString()) {
//			System.out.println("gap bien");
//			this.index++;
//			System.out.println("1"+getValue(this.index));
//			System.out.println("2"+getValue(this.index+1));
//			if(getType(this.index)==TokenType.COLEQnumber.toString()) {
//				System.out.println("gap :=");
//				this.index++;
//				if(getType(this.index)==TokenType.SEMInumber.toString()) {
//					System.out.println("gap ; va index ="+this.index);
//					return true;
//				}else if(getType(this.index)==TokenType.MINUSnumber.toString()||getType(this.index)==TokenType.PLUSnumber.toString()||getType(this.index)==TokenType.TIMESnumber.toString()) {//,
//					System.out.println("gap ,");
//					this.index++;	
//					return isExpression();		
//				}else {
//					return false;
//				}
//			}
//		}
//		return false;
//	}

	public boolean isProgram() {
		if(isWord(this.result.get(this.index),TokenType.PROGRAMnumber)) {
			System.out.println("gap program");
			this.index ++;
			// program name
			if(isWord(this.result.get(this.index),TokenType.IDnumber)) {
				System.out.println("gap ten CT");
				this.index ++;
				//gap (
				if (isWord(this.result.get(this.index),TokenType.SEMInumber)){// ;
					System.out.println("gap ; end isProgram with "+this.index);
					return true;
				}
			}
		}
		return false;
	}
	public boolean isBegin() {
		if(getType(this.index)==TokenType.BEGINnumber.toString()) {
			System.out.println("gap begin");
			return true;
		}
		return false;
	}
	public boolean isNum(List<Token> result, int index){
		if(isWord(result.get(index),TokenType.ICONSTnumber)) {
			return true;
		}
		return false;
	}
	public boolean isEndDot() {
		if(isWord(result.get(this.index),TokenType.ENDnumber)&& isWord(result.get(this.index+1),TokenType.DOTnumber)) {
			return true;
		}
		return false;
	}
	public boolean isIdentList() {
		if(getType(this.index)==TokenType.IDnumber.toString()) {
			System.out.println("gap bien");
			this.index++;
			if(getType(this.index)==TokenType.COLONnumber.toString()) {
				System.out.println("gap : va index ="+this.index);
				return true;
			}else if(getType(this.index)==TokenType.COMMMAnumber.toString()) {//,
				System.out.println("gap ,");
				this.index++;	
				return isIdentList();		
			}else {
				return false;
			}
		}
		return false;	
	}
	public boolean isVar(){

			if(isIdentList()) {
				this.index++;
				System.out.println("index = "+this.index);
				if(getValue(this.index).toLowerCase().equals("integer")) {
					this.index++;
					System.out.println("gap kieu du lieu");
					System.out.println("index="+getValue(this.index)+getType(this.index));
					
					if(getType(this.index)==TokenType.SEMInumber.toString()&&getType(this.index+1)!=TokenType.IDnumber.toString()) {
						System.out.println("gap ; va khac id index="+this.index);
						
						return true;
					}
					if(getType(this.index)==TokenType.SEMInumber.toString()&&getType(this.index+1)==TokenType.IDnumber.toString()) {
						System.out.println("lai gap id");
						this.index++;
						return isVar();
					}
				}
			}

		return false;
	}
	//Bat dau chuong trinh bang PROGRAM
	/*public boolean startProgram(List<Token> result, int index) {
		if (isProgram()) {
			return true;
		}
		return false;
//		if(isDeclare())
//		if( isBlock(result,this.index)) {
//			return true;
//		}else {
//			return false;
//		}
	}*/
//	public boolean isProgram() {
//		if(isWord(this.result.get(this.index),TokenType.PROGRAMnumber)) {
//			System.out.println("gap program");
//			this.index ++;
//			// program name
//			if(isWord(this.result.get(this.index),TokenType.IDnumber)) {
//				System.out.println("gap ten CT");
//				this.index ++;
//				//gap (
////				if(isWord(result.get(this.index),TokenType.LPARENnumber  )) {
////					System.out.println("gap (");
////					this.index ++;
////					// gap list ID
////					if(isListIDnumber2(result,this.index)) {
////						this.index ++;
////						System.out.println("chay xong isList ben ngoai");
////						if(isWord(result.get(this.index),TokenType.RPARENnumber  )) {
////							System.out.println("gap )");
////							this.index++;
////						}else {
////							return false;
////						}
////						//return true;
////					}else {
////						return false;}
////					}
//				if (isWord(this.result.get(this.index),TokenType.SEMInumber)){// ;
//					System.out.println("gap ; end isProgram with "+this.index);
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	
//	public boolean isBlock(List<Token> result, int index) {
//		System.out.println("vao isBlock: "+this.index);
//		if(isVar(result,this.index)) {
//			this.index++;
//		}
//		if(isBegin(result,this.index)) {
//			System.out.println("gap begin");
//			this.index++;
//			if(isStatement(result,this.index)) {
//				this.index++;
//				if(isEndDot(result,this.index)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	
//	public boolean isBlock() {
//	System.out.println("vao isBlock: "+this.index);
//	if(isVar()) {
//		this.index++;
//	}
//	if(isBegin(result,this.index)) {
//		System.out.println("gap begin");
//		this.index++;
//		if(isStatement(result,this.index)) {
//			this.index++;
//			if(isEndDot(result,this.index)) {
//				return true;
//			}
//		}
//	}
//	return false;
//	}

//	public boolean isStatement(List<Token> result, int index) {
//		if(isExpression(result,this.index)) {
//			
//		}
////		if(isExpression2(result,this.index)) {
////			
////		}
//		return true;
//	}
//	public boolean isExpression(List<Token> result, int index) {
//		if(isIdentList(result,this.index)) {
//			this.index++;
//			if(isWord(result.get(this.index),TokenType.COLEQnumber)) {
//				System.out.println("gap :");// gap :=
//				this.index++;
//				if(( isNum(result,this.index)||isIdentList(result,this.index))&& isWord(result.get(this.index+1),TokenType.SEMInumber)   ) {
//					System.out.println("xong bieu thuc simpe");
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//	public boolean isExpression2(List<Token> result, int index) {
//		if(isIdent(result,this.index)) {
//			this.index++;
//			if(isWord(result.get(index),TokenType.COLEQnumber)) {// gap :=
//				this.index++;
//				if(isIdent(result,this.index)) {
//					if()
//				}
//			}
//		}
//		return false;
//	}
	
	/*public boolean isIdentList(List<Token> result,int index) {
		if(isWord(result.get(this.index),TokenType.IDnumber)) {
			System.out.println("gap bien");
			this.index++;
			if(isWord(result.get(this.index),TokenType.COMMMAnumber)) {//,
				System.out.println("gap ,");
				this.index++;	
				isIdentList(result,this.index);		
			} else if(isWord(result.get(this.index),TokenType.COLONnumber)) {
				System.out.println("gap : va index ="+this.index);
				return true;
			}
		}
		return false;	
//		if(isWord(result.get(this.index),TokenType.IDnumber) && isWord(result.get(this.index+1),TokenType.COLONnumber)) {
//			return true;
//		}
//		if (isWord(result.get(this.index),TokenType.IDnumber) && isWord(result.get(this.index+1),TokenType.COMMMAnumber)){
//			 isIdent(result,this.index+1);
//		}
//		if(isWord(result.get(this.index),TokenType.RPARENnumber)) {//dieu kien dung
//			System.out.println("gap ) va index = "+this.index);
//			return true;
//		}
		//System.out.println("ket thuc ham list voi index = "+this.index);
		//return false;
	}*/
	
	//check list hay 1 element
	/*public boolean isListIDnumber(List<Token> result, int index) {
		if(isWord(result.get(this.index),TokenType.IDnumber)) {
			System.out.println("gap bien");
			this.index++;
			if(isWord(result.get(this.index),TokenType.COMMMAnumber)) {//,
				System.out.println("gap ,");
				this.index++;	
				if(isListIDnumber(result,this.index)){
					return true;
				}			
			} else if(isWord(result.get(this.index),TokenType.RPARENnumber)) {
				this.index++;
				System.out.println("gap ) ");
				return true;
			}else {
				return false;
			}
		}
		return false;	
	}*/
	/*public boolean isListIDnumber2(List<Token> result, int index) {
		System.out.println("bat dau ham isList voi index = "+this.index);
		if(isWord(result.get(this.index),TokenType.IDnumber)&&isWord(result.get(this.index + 1),TokenType.COMMMAnumber)) {
			
			System.out.println(this.index);
			System.out.println("gap bien");
			this.index++;
			this.index++;
			return isListIDnumber2(result,this.index);
			 
		}
//		if(isWord(result.get(this.index),TokenType.RPARENnumber)) {//dieu kien dung
//			System.out.println("gap ) va index = "+this.index);
//			return true;
//		}
		System.out.println("ket thuc ham list voi index = "+this.index);
		return true;	
	}*/

	
	//Comment here
//	public boolean block(List<Token> result, int index) {
//		System.out.println("vao block voi index ="+this.index);
//		//code here
//		isVar(result,index);
//		
//		return true;
//	}
	
	public boolean isKeyword(List<Token> result, int index) {
		if(isWord(result.get(this.index),TokenType.SEMInumber)||isWord(result.get(this.index),TokenType.SEMInumber)||isWord(result.get(this.index),TokenType.SEMInumber)) {
			return true;
		}
	return false;
	}
	
	//Comment here
	public boolean statement(List<Token> result, int index) {
		//code here
		return false;
	}
	
	//Comment here
	public boolean unsignedConstant(List<Token> result, int index) {
		//code here
		return false;
	}
	
	//Comment here
	public boolean expression(List<Token> result, int index) {
		//code here
		return false;
	}
	
	//Comment here
	public boolean simpleExpression(List<Token> result, int index) {
		//code here
		return false;
	}
	
	//Kiem tra tu dung voi TokenType hay khong?
	public boolean isWord(Token token, TokenType dataType) {
		if(token.tokenType == dataType ){
			return true;
		}
		return false;
	}
	
	
}

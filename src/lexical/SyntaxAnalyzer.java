package lexical;

import java.util.List;

import lexical.LexicalAnalyzer;

public class SyntaxAnalyzer {
	static int index = 0;
	//TokenType tokenType = new TokenType();
	public SyntaxAnalyzer(){
		
	}
	
	public boolean validateCFG(List<Token> result) throws AnalyzerException{
		System.out.println(this.index);
		if(startProgram(result, this.index)) {
//			if(block(result, this.index)) {
//				//tiep tuc
//				return true;
//			}
			return true;
		}
		
		return false;
	}
	
	//Bat dau chuong trinh bang PROGRAM
	public boolean startProgram(List<Token> result, int index) {
		if (isProgram(result,this.index)) {
			//return true;
		}
		if( isBlock(result,this.index)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isBlock(List<Token> result, int index) {
		System.out.println("vao isBlock: "+this.index);
		if(isVar(result,this.index)) {
			this.index++;
		}
		if(isBegin(result,this.index)) {
			System.out.println("gap begin");
			this.index++;
			if(isStatement(result,this.index)) {
				this.index++;
				if(isEndDot(result,this.index)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isBegin(List<Token> result, int index) {
		if(isWord(result.get(this.index),TokenType.BEGINnumber)) {
			return true;
		}
		return false;
	}
	public boolean isStatement(List<Token> result, int index) {
		if(isExpression(result,this.index)) {
			
		}
//		if(isExpression2(result,this.index)) {
//			
//		}
		return true;
	}
	public boolean isExpression(List<Token> result, int index) {
		if(isIdentList(result,this.index)) {
			this.index++;
			if(isWord(result.get(this.index),TokenType.COLEQnumber)) {
				System.out.println("gap :");// gap :=
				this.index++;
				if(( isNum(result,this.index)||isIdentList(result,this.index))&& isWord(result.get(this.index+1),TokenType.SEMInumber)   ) {
					System.out.println("xong bieu thuc simpe");
					return true;
				}
			}
		}
		return false;
	}
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
	public boolean isNum(List<Token> result, int index){
		if(isWord(result.get(index),TokenType.ICONSTnumber)) {
			return true;
		}
		return false;
	}
	public boolean isEndDot(List<Token> result, int index) {
		if(isWord(result.get(this.index),TokenType.ENDnumber)&& isWord(result.get(this.index+1),TokenType.DOTnumber)) {
			return true;
		}
		return false;
	}
	public boolean isIdentList(List<Token> result,int index) {
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
	}
	public boolean isProgram(List<Token> result, int index) {
		if(isWord(result.get(this.index),TokenType.PROGRAMnumber)) {
			this.index ++;
			// program name
			if(isWord(result.get(this.index),TokenType.IDnumber)) {
				this.index ++;
				//gap (
//				if(isWord(result.get(this.index),TokenType.LPARENnumber  )) {
//					System.out.println("gap (");
//					this.index ++;
//					// gap list ID
//					if(isListIDnumber2(result,this.index)) {
//						this.index ++;
//						System.out.println("chay xong isList ben ngoai");
//						if(isWord(result.get(this.index),TokenType.RPARENnumber  )) {
//							System.out.println("gap )");
//							this.index++;
//						}else {
//							return false;
//						}
//						//return true;
//					}else {
//						return false;}
//					}
				if (isWord(result.get(this.index),TokenType.SEMInumber)){// ;
					this.index++;
					System.out.println("gap ;");
					return true;
				}
			}
		}
		return false;
	}
	//check list hay 1 element
	public boolean isListIDnumber(List<Token> result, int index) {
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
	}
	public boolean isListIDnumber2(List<Token> result, int index) {
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
	}

	
	//Comment here
	public boolean block(List<Token> result, int index) {
		System.out.println("vao block voi index ="+this.index);
		//code here
		isVar(result,index);
		
		return true;
	}
	public boolean isVar(List<Token> result, int index){
		if(isWord(result.get(this.index),TokenType.VARnumber)) {
			System.out.println("gap var");
			this.index++;
			if(isIdentList(result,this.index)) {
				this.index++;
				if(isKeyword(result,this.index)) {
					System.out.println("gap keyword :"+this.index);
					this.index++;
					if(isWord(result.get(this.index),TokenType.SEMInumber)) {
						System.out.println("gap ;");
						return true;
					}
				}
//				if(isWord(result.get(this.index),TokenType.COLONnumber)) {
//					System.out.println("gap : ben ngoai");
//					this.index++;
//					
//				}
//				if gap , => goi isList()
//				else if gap : => gap type => gap ; => goi isList
//				else false
			}
		}
		return false;
	}
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

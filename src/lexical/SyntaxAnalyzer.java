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
			this.index++;
			if (isIdent(result,this.index)){
				return true;
			}
			
		}
		return false;
	}
	public boolean isIdent(List<Token> result, int index) {
		return false;
	}
	public boolean isIdentList(List<Token> result, int index) {
		return false;
	}
	public boolean name(List<Token> result, int index) {
		return false;
	}
	public boolean isProgram(List<Token> result, int index) {
		if(isWord(result.get(this.index),TokenType.PROGRAMnumber)) {
			this.index ++;
			// program name
			if(isWord(result.get(this.index),TokenType.IDnumber)) {
				this.index ++;
				//gap (
				if(isWord(result.get(this.index),TokenType.LPARENnumber  )) {
					System.out.println("gap (");
					this.index ++;
					// gap list ID
					if(isListIDnumber2(result,this.index)) {
						this.index ++;
						System.out.println("chay xong isList ben ngoai");
						if(isWord(result.get(this.index),TokenType.RPARENnumber  )) {
							System.out.println("gap )");
							this.index++;
						}else {
							return false;
						}
						//return true;
					}else {
						return false;}
					}
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
		
		return false;
	}
	public boolean isVar(List<Token> result, int index){
		if(isWord(result.get(this.index),TokenType.VARnumber)) {
			System.out.println("gap var");
			this.index++;
			System.out.println("index:"+result.get(index+1).tokenString+this.index);
			if(isWord(result.get(this.index),TokenType.IDnumber)) {
				System.out.println("gap bien");
				this.index++;
				return true;
//				if gap , => goi isList()
//				else if gap : => gap type => gap ; => goi isList
//				else false
			}
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

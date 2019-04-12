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
			System.out.println(this.index);
//			if(block(result, this.index)) {
				//tiep tuc
				return true;
//			}
		}
		return false;
	}
	
	//Bat dau chuong trinh bang PROGRAM
	public boolean startProgram(List<Token> result, int index) {
		if(isWord(result.get(this.index),TokenType.PROGRAMnumber)) {
			this.index ++;
			if(isWord(result.get(this.index),TokenType.IDnumber)) {
				this.index ++;
				if(isWord(result.get(this.index),TokenType.LPARENnumber  )) {
					this.index ++;
					if(isListIDnumber(result,this.index)) {
						return true;
					}
				}else if (isWord(result.get(this.index),TokenType.SEMInumber)){// ;
					this.index++;
					System.out.println(134);
					return true;
				}
			}
		}
		return false;
	}
	
	//check list hay 1 element
	public boolean isListIDnumber(List<Token> result, int index) {
		if(isWord(result.get(this.index),TokenType.IDnumber)) {
			this.index++;
			if(isWord(result.get(this.index),TokenType.COMMMAnumber)) {//,
				this.index++;	
				if(isListIDnumber(result,this.index)){
					return true;
				}			
			} else if(isWord(result.get(this.index),TokenType.RPARENnumber)) {
				return true;
			}else {
				return false;
			}
		}
		return false;	
	}

	
	//Comment here
	public boolean block(List<Token> result, int index) {
		//code here
		System.out.println(this.index);
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

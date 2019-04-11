package lexical;

import java.util.List;

import lexical.LexicalAnalyzer;

public class SyntaxAnalyzer {
	int index = 0;
	//TokenType tokenType = new TokenType();
	public SyntaxAnalyzer(){
		
	}
	
	public boolean validateCFG(List<Token> result) throws AnalyzerException{
		if(startProgram(result, index)) {
			if(block(result, index)) {
				//tiep tuc
				return true;
			}
			
		}
		return false;
	}
	
	//Bat dau chuong trinh bang PROGRAM
	public boolean startProgram(List<Token> result, int index) {
		if(isWord(result.get(index),TokenType.PROGRAMnumber)) {
			index ++;
			if(isWord(result.get(index),TokenType.IDnumber)) {
				index ++;
				if(isWord(result.get(index),TokenType.LPARENnumber  )) {
					index ++;
					if(isListIDnumber(result,index)) {
						return true;
					}
				}else if (isWord(result.get(index),TokenType.SEMInumber)){// ;
					index++;
					return true;
				}
			}
		}
		return false;
	}
	
	//check list hay 1 element
	public boolean isListIDnumber(List<Token> result, int index) {
		if(isWord(result.get(index),TokenType.IDnumber)) {
			index++;
			if(isWord(result.get(index),TokenType.COMMMAnumber)) {//,
				index++;	
				if(isListIDnumber(result,index)){
					return true;
				}			
			} else if(isWord(result.get(index),TokenType.RPARENnumber)) {
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
	
	//Kiem tra tu dung voi TokenType hay khong
	public boolean isWord(Token token, TokenType dataType) {
		if(token.tokenType == dataType ){
			return true;
		}
		return false;
	}
	
	
}

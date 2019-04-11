package lexical;

import java.util.List;

import lexical.LexicalAnalyzer;

public class SyntaxAnalyzer {
	int index = 0;
	//TokenType tokenType = new TokenType();
	public SyntaxAnalyzer(){
		
	}
	
	public boolean validateCFG(List<Token> result) throws AnalyzerException{
		if(isWord(result.get(index),TokenType.PROGRAMnumber)) {
			index ++;
			if(isWord(result.get(index),TokenType.IDnumber)) {
				index ++;
				if(isWord(result.get(index),TokenType.LPARENnumber  )) {
					index ++;
					//is list
					
					if(isListIDnumber(result,index)) {
//						System.out.println(index);
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
	public boolean isListIDnumber(List<Token> result, int index) {
//		System.out.println(index);
//		if(isWord(result.get(index),TokenType.IDnumber)) {
//			index++;
//			if(isWord(result.get(index),TokenType.COMMMAnumber)) {//,
//				index++;	
//				isListIDnumber(result,index);			
//			} 
//			if(isWord(result.get(index),TokenType.RPARENnumber)) {
//				System.out.println("adasd");	
//				return true;
//			}
//			
//		}
//		System.out.println("het");
//		return false;
		
		if(isWord(result.get(index),TokenType.RPARENnumber)){
			return true;
		}
		
		if(isWord(result.get(index),TokenType.COMMMAnumber)){
			index ++;
			if(!isWord(result.get(index),TokenType.IDnumber)){
				return false;
			}
		}
		if(isWord(result.get(index),TokenType.IDnumber)){
			index ++;
			if(!isWord(result.get(index),TokenType.COMMMAnumber) ){
				System.out.println("1");
				return false;
			}
			if(!isWord(result.get(index),TokenType.RPARENnumber)) {
				System.out.println("het");
				return false;
			}
		}
		if(isWord(result.get(result.size()),TokenType.RPARENnumber)) {
			
			return false;
		}
		return isListIDnumber(result, index + 1);
		
	}
//	public void print(List<Token> result) throws AnalyzerException{
//		for(int i = 0; i < result.size(); i++) {
//            System.out.println(result.get(i).getTokenString());
//        }
//	}

	public boolean isWord(Token token, TokenType dataType) {
		if(token.tokenType == dataType ){
			return true;
		}
		return false;
	}
	
	
}

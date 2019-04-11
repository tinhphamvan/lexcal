package lexical;

import java.util.List;

import lexical.LexicalAnalyzer;

public class SyntaxAnalyzer {
	int index = 0;
	public SyntaxAnalyzer(){
		
	}
	public void validateCFG(List<Token> result) throws AnalyzerException{
		
		for(int i=0;i<result.size();i++){
			if (result.get(i).toString() != null)
				System.out.println(result.get(i).toString());
		}
	}

	
	
}

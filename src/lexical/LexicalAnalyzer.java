package lexical;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class LexicalAnalyzer {

	/** Mapping from type of token to its regular expression */
	private Map<TokenType, String> regEx;

	/** List of tokens as they appear in the input source */
	private List<Token> result;
	
	boolean isComments = false;
	boolean isCommentsSupport = false;
	String str = "";
	
	Token previousToken = new Token(0, 0, str, null, 0, 0, isComments);
	
	/**
	 * Initializes a newly created {@code Lexer} object
	 * @return 
	 */
	public  LexicalAnalyzer() {
		regEx = new TreeMap<TokenType, String>();
		launchRegEx();
		result = new ArrayList<Token>();
	}
	public void display() throws AnalyzerException {
		
		for(int i=0;i<result.size();i++){
			if (result.get(i).toString() != null)
				System.out.println(result.get(i).toString());
		}
	}
	
	// remove some token not use
	public void removeAuxiliary(List<Token> result) {
		//TokenType tokenType = new TokenType();
		for(int i=0;i<result.size();i++){
			if (result.get(i).getTokenType() == TokenType.BLANKnumber
					 )
			{
				result.remove(i);
			}
			if(result.get(i).getTokenType() == TokenType.TABnumber){
				result.remove(i);
			}
				//System.out.println(result.get(i).toString());
		}
	}
	
	
	SyntaxAnalyzer s = new SyntaxAnalyzer();
	public void SyntaxAnalyzer() throws AnalyzerException {
		removeAuxiliary(result);
		if(s.validateCFG(result)) {
			System.out.println("BUILD SUCCESSFULLY");
		}else {
			System.out.println("BUILD FAIL");
		}
		
	}
	
	public boolean getCommentSupport() {
		return isCommentsSupport;
	}
	
	public Token getPriviousToken() {
		return previousToken;
	}
	/**
	 * Performs the tokenization of the input source code.
	 * 
	 * @param source
	 *            string to be analyzed
	 * @throws AnalyzerException
	 *             if lexical error exists in the source
	 * 
	 */
	public void tokenize(String source, int line) throws AnalyzerException {
		int position = 0;
		Token token = null;
		do {
			token = separateToken(source, position, line);
			if(token != null) {
				if(token.isComment() == true) {
					position = token.getEndIndex();
					isCommentsSupport = true;
					str += token.getTokenString();
					previousToken.setTokenType(TokenType.BLOCKCOMMENTnumber);
					previousToken.setBeginIndex(token.getBeginIndex());
					previousToken.setLineBegin(token.getLineEnd());
					
					previousToken.setTokenString(str);
//					break;
//					previousToken(fromIndex + 1, endIndex , lexema, tokenType, line ,line , isComments);
				}
				else {
					if(isCommentsSupport == true) {
						isCommentsSupport = false;
						position = token.getEndIndex();
						str += token.getTokenString();
						previousToken.setTokenString(str);
						previousToken.setEndIndex(token.getEndIndex());
						previousToken.setLineEnd(line);
						result.add(previousToken);
						str = "";
						previousToken = null;
//						System.out.println(position + "   " + source.length() );
						
					}
					else{
						
						position = token.getEndIndex();
						result.add(token);
					}
				}
				
			}
			
//			System.out.println(token.getIsComments());
			
		} while (token != null && position != source.length());
		
		if (position != source.length() ) {
			System.out.println(position + "   " + source.length() );
			throw new AnalyzerException("Lexical error at position # "+ (position + 1) + " line # " + line, position, line);
		}
	}

	/**
	 * Returns a sequence of tokens
	 * 
	 * @return list of tokens
	 */
	public List<Token> getTokens() {
		return result;
	}

	
	public List<Token> getFilteredTokens() {
		List<Token> filteredResult = new ArrayList<Token>();
		for (Token t : this.result) {
			if (!t.getTokenType().isAuxiliary()) {
				filteredResult.add(t);
			}
		}
		return filteredResult;
	}


	private Token separateToken(String source, int fromIndex, int line) throws AnalyzerException {
//		System.out.println(source);
		if (fromIndex < 0 || fromIndex >= source.length() ) {
			throw new IllegalArgumentException("Illegal index in the input stream!");
		}
		for (TokenType tokenType : TokenType.values()) {
//			System.out.println(tokenType);
			Pattern p = Pattern.compile(".{" + fromIndex + "}" + regEx.get(tokenType),
					Pattern.DOTALL);
//			System.out.println(source);
			Matcher m = p.matcher(source);
//			https://gist.github.com/salavert/4636374
			if (m.matches()) {
				String lexema = m.group(1);
				int endIndex = fromIndex + lexema.length() ;
				
				if(tokenType == TokenType.StringError) {
//					System.out.println(1);
					throw new AnalyzerException("Lexical error at position or string constant over the line boundary error at possition # "+ endIndex + " line # " + line, fromIndex, line);
				}
				
				if(tokenType == TokenType.LeftBlockCommentError) {
//					System.out.println(1);
					throw new AnalyzerException("Comments are enclosed in (* ... *), and cannot be nested # "+ endIndex + " line # " + line, fromIndex, line);
				}
				else {
					if (tokenType == TokenType.LeftBlockComment) {
						isComments = true;
					}
					
					
					if (tokenType == TokenType.RightBlockComment) {
						isComments = false;
					}
					return new Token(fromIndex + 1, endIndex , lexema, tokenType, line, line, isComments);
					
				}
				
				
				
//				return new Token(fromIndex + 1, endIndex , lexema, tokenType, line, isComments);
				
				
				
//				System.out.println(Pattern.DOTALL);
				
				
			}
//			else
//				throw new AnalyzerException("Undefined Token at position # "+ fromIndex, fromIndex);
//				return new Token(0, 9, "hhhh", TokenType.IDnumber , 8);
		}
		
		return null;
	}

	/**
	 * Creates map from token types to its regular expressions
	 * 
	 */
	private void launchRegEx() {
		regEx.put(TokenType.StringError, "(\").*");
		regEx.put(TokenType.Comment1line, "(\\/\\/.*).*");
		regEx.put(TokenType.BLOCKCOMMENTnumber, "(\\(\\*.*?\\*\\)).*");// block comment
		regEx.put(TokenType.STRINGnumber, "(\"([^\"]*)\").*");
		regEx.put(TokenType.VARnumber, "\\b(var)\\b.*");
		regEx.put(TokenType.TABnumber, "(\\t).*");
		regEx.put(TokenType.EOFnumber, "(\\n).*");
		regEx.put(TokenType.SEMInumber, "(;).*");
		regEx.put(TokenType.COLONnumber, "(:).*");
		regEx.put(TokenType.COMMMAnumber, "(,).*");
		regEx.put(TokenType.DOTnumber, "(\\.).*");
		regEx.put(TokenType.LPARENnumber, "(\\().*");
		regEx.put(TokenType.RPARENnumber, "(\\)).*");
		regEx.put(TokenType.GTnumber, "(>).*");
		regEx.put(TokenType.LTnumber, "(<).*");
		regEx.put(TokenType.EQnumber, "(=).*");
		regEx.put(TokenType.MINUSnumber, "(\\-{1}).*");
		regEx.put(TokenType.PLUSnumber, "(\\+{1}).*");
		regEx.put(TokenType.TIMESnumber, "(\\*).*");  			//Test
		regEx.put(TokenType.DOTDOTnumber, "(\\.\\.).*");  			//Test
		regEx.put(TokenType.COLEQnumber, "(\\:=).*");  			//Test
		regEx.put(TokenType.LEnumber, "(<=).*");				//Test
		regEx.put(TokenType.GEnumber, "(>=).*");				//Test
		regEx.put(TokenType.NEnumber, "(<>).*");				//Test
		regEx.put(TokenType.IDnumber, "\\b([a-zA-Z_][a-zA-Z0-9_-]*)\\b.*"); //Test
		regEx.put(TokenType.DCONSTnumber, "\\b(\\d{1,9}\\.\\d{1,32})\\b.*");// double constant
		
		//regEx.put(TokenType.CCONSTnumber, "\\b('\\d{1}')\\b.*");//char constant
		//regEx.put(TokenType.SCONSTnumber, "\\b([a-z]+)\\b.*");// string constant
		regEx.put(TokenType.ICONSTnumber, "\\b(\\d{1,9})\\b.*");
		regEx.put(TokenType.Intnumber, "\\b(int)\\b.*");
		regEx.put(TokenType.ANDnumber, "\\b(and)\\b.*");
		regEx.put(TokenType.ARRAYnumber, "\\b(array)\\b.*");
		regEx.put(TokenType.BEGINnumber, "\\b(begin)\\b.*");
		
		regEx.put(TokenType.DIVnumber, "\\b(div)\\b.*");
		regEx.put(TokenType.DOWNTOnumber, "\\b(downto)\\b.*");
		regEx.put(TokenType.ELSEnumber, "\\b(else)\\b.*");
		regEx.put(TokenType.ELSIFnumber, "\\b(elsif)\\b.*");
		regEx.put(TokenType.ENDnumber, "\\b(end)\\b.*");
		regEx.put(TokenType.ENDIFnumber, "\\b(endif)\\b.*");
		regEx.put(TokenType.ENDLOOPnumber, "\\b(endloop)\\b.*");
		regEx.put(TokenType.ENDRECnumber, "\\b(endrec)\\b.*");
		regEx.put(TokenType.EXITnumber, "\\b(exit)\\b.*");
		regEx.put(TokenType.FORnumber, "\\b(for)\\b.*");
		regEx.put(TokenType.FORWARDnumber, "\\b(forward)\\b.*");
		regEx.put(TokenType.FUNCTIONnumber, "\\b(function)\\b.*");
		regEx.put(TokenType.IFnumber, "\\b(if)\\b.*");
		regEx.put(TokenType.ISnumber, "\\b(is)\\b.*");
		regEx.put(TokenType.BLANKnumber, "( ).*");
		regEx.put(TokenType.LOOPnumber, "\\b(loop)\\b.*");
		regEx.put(TokenType.NOTnumber, "\\b(not)\\b.*");
		regEx.put(TokenType.OFnumber, "\\b(of)\\b.*");
		regEx.put(TokenType.ORnumber, "\\b(or)\\b.*");
		regEx.put(TokenType.PROCEDUREnumber, "\\b(procedure)\\b.*");
		regEx.put(TokenType.PROGRAMnumber, "\\b(program)\\b.*");
		regEx.put(TokenType.RECORDnumber, "\\b(record)\\b.*");
		regEx.put(TokenType.REPEATnumber, "\\b(repeat)\\b.*");
		regEx.put(TokenType.RETURNnumber, "\\b(return)\\b.*");
		regEx.put(TokenType.THENnumber, "\\b(then)\\b.*");
		regEx.put(TokenType.TOnumber, "\\b(to)\\b.*");
		regEx.put(TokenType.TYPEnumber, "\\b(type)\\b.*");
		regEx.put(TokenType.UNTILnumber, "\\b(until)\\b.*");
		regEx.put(TokenType.WHILEnumber, "\\b(while)\\b.*");
		regEx.put(TokenType.LeftBlockComment, "(\\(\\*.*).*");
		regEx.put(TokenType.LeftBlockCommentError, "(\\(\\*.*\\(\\*).*");
		regEx.put(TokenType.RightBlockComment, "(.*?\\*\\)).*");
		regEx.put(TokenType.LineComment, "(//(.*?)[\r$]?\n).*");
	}
	
}
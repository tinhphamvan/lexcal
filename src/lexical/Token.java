package lexical;


/**
 * The {@code Token} class represents token (lexeme). A token is a string of
 * characters, categorized according to the rules as a symbol. For example: <i>
 * Identifier, Comma, DoubleConstant</i>.
 * 
 * @author Ira Korshunova
 * 
 */

public class Token {

	
	private int beginIndex;
	private int lineBegin;
	private int endIndex;
	private int lineEnd;	
	private boolean isComment;
	public  TokenType tokenType;
	private String tokenString;

	

	public Token(int beginIndex, int endIndex, String tokenString, TokenType tokenType,int lineBegin, int lineEnd , boolean isComment) {
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
		this.tokenType = tokenType;
		this.tokenString = tokenString;
		this.lineBegin = lineBegin;
		this.lineEnd   = lineEnd; 
		this.isComment = isComment;
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public int getLineBegin() {
		return lineBegin;
	}

	public void setLineBegin(int lineBegin) {
		this.lineBegin = lineBegin;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public int getLineEnd() {
		return lineEnd;
	}

	public void setLineEnd(int lineEnd) {
		this.lineEnd = lineEnd;
	}

	public boolean isComment() {
		return isComment;
	}

	public void setComment(boolean isComment) {
		this.isComment = isComment;
	}
	
	public TokenType getTokenType() {
		return tokenType;
	}
	
	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}
	
	public String getTokenString() {
		return tokenString;
	}

	public void setTokenString(String tokenString) {
		this.tokenString = tokenString;
	}
	
	@Override
	public String toString() {
		if (!this.getTokenType().isAuxiliary())
			return tokenString + "  " + tokenType + "  " + lineBegin +"  "+ lineEnd + "  " +beginIndex + "  " + endIndex ;
		else
			return null;
	}
}

package lexical;


/**
 * The {@code AnalyzerException} class represents exceptions which may be caused
 * by lexical or syntax errors
 * 
 * @author Ira Korshunova
 * 
 */
@SuppressWarnings("serial")
public class AnalyzerException extends Exception {
	/**
	 * Position in the input source(lexer) or the number of token(parser), where
	 * the error occured
	 */
	private int errorPosition;
	
	private int errorLine;

	/** The detail message */
	private String message;

	/**
	 * Creates {@code AnalyzerException} object with specified error position
	 * 
	 * @param errorPosition
	 *            position of the error
	 */
	public AnalyzerException(int errorPosition, int errorLine) {
		this.errorPosition = errorPosition;
		this.errorLine 	   = errorLine;
	}

	/**
	 * Creates {@code AnalyzerException} object with specified error position
	 * and message
	 * 
	 * @param message
	 *            detailed message
	 * @param errorPosition
	 *            position of the error
	 */
	public AnalyzerException(String message, int errorPosition, int errorLine) {
		this.errorPosition = errorPosition;
		this.message = message;
		this.errorLine = errorLine;
	}


	/**
	 * Returns error's position in the input
	 * 
	 * @return error's position
	 */
	public int getErrorPosition() {
		return errorPosition;
	}
	
	public int getErrorLine() {
		return errorLine;
	}

	@Override
	public String getMessage() {
		return message;
	}
}

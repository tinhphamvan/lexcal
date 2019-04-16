	package lexical;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) throws AnalyzerException, IOException {
		// TODO Auto-generated method stub
		LexicalAnalyzer n = new LexicalAnalyzer();
		
		BufferedReader br = null;
	        try {   
	            br = new BufferedReader(new FileReader("test/Test04.txt"));       
	            String textInALine;
	            int line = 1;
	            while ((textInALine = br.readLine()) != null) {
	            	if (textInALine.trim().isEmpty())
	            		  continue;
	            	//System.out.println(textInALine);
	                n.tokenize(textInALine, line);
	                line ++;
	            }
	        } catch (IOException e) {
	        }
        if(n.getCommentSupport() == true) {
			throw new AnalyzerException("Comments that are never terminated # "+ n.getPriviousToken().getBeginIndex() + " line # " + n.getPriviousToken().getLineBegin(), n.getPriviousToken().getEndIndex(), n.getPriviousToken().getLineBegin());
		}
		//n.display();
		
        n.SyntaxAnalyzer();
	}

}

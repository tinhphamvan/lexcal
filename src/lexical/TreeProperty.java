package lexical;

import java.util.List;

public class TreeProperty {
    public String program;
	public List<String>  declare;
    public Token println;
    public String[] expression;

    public void tokendent(String program, List<String> declare, Token println, String[] expression) {
        this.program = program;
        this.declare = declare;
        this.println = println;
        this.expression = expression;
    }
    public String getProgram() {
  		return program;
  	}

  	public void setProgram(String program) {
  		this.program = program;
  	}

  	public List<String> getDeclare() {
  		return declare;
  	}

  	public void setDeclare(List<String> declare) {
  		this.declare = declare;
  	}

  	public Token getPrintln() {
  		return println;
  	}

  	public void setPrintln(Token println) {
  		this.println = println;
  	}

  	public String[] getExpression() {
  		return expression;
  	}

  	public void setExpression(String[] expression) {
  		this.expression = expression;
  	}
}

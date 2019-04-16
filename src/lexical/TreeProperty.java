package lexical;

import java.util.List;

public class TreeProperty {
    public Token program;
	public List<String>  declare;
    public Token println;
    public List<String> expression1;
    public List<String> expression2;

    public void tokendent(Token program, List<String> declare, Token println, List<String> expression1,List<String> expression2) {
        this.program = program;
        this.declare = declare;
        this.println = println;
        this.expression1 = expression1;
        this.expression2 = expression2;
    }
    public Token getProgram() {
  		return program;
  	}

  	public void setProgram(Token program) {
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

  	public List<String> getExpression1() {
  		return expression1;
  	}

  	public void setExpression1(List<String> expression1) {
  		this.expression1 = expression1;
  	}
  	public List<String> getExpression2() {
  		return expression2;
  	}

  	public void setExpression2(List<String> expression2) {
  		this.expression2 = expression2;
  	}
}

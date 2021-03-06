package lexical;

import java.util.ArrayList;
import java.util.List;

import lexical.LexicalAnalyzer;

public class SyntaxAnalyzer {
	int getExp = 0;
	int index = 0;
	String state = "";
	List<Token> result;
	List<String> variable = new ArrayList<String>();
	List<String> statement1 = new ArrayList<String>();
	List<String> statement2 = new ArrayList<String>();
	TreeProperty tree = new TreeProperty();
	public SyntaxAnalyzer(){
		
	}
	
	public String getType(int index) {
		return this.result.get(index).tokenType.toString();
	}
	public String getValue(int index) {
		return this.result.get(index).tokenString;
	}
	public int getLine(int index) {
		return this.result.get(index).lineBegin;
	}
	public int getPossiton(int index) {
		return this.result.get(index).beginIndex;
	}
	
	public boolean validateCFG(List<Token> result) throws AnalyzerException{
		this.result=result;
		
		///check progarm
//		if (getType(this.index)==TokenType.PROGRAMnumber.toString()) {
//			tree.setProgram()

			if(isProgram()) {
				this.index++;
				//check declare
				if(getType(this.index)==TokenType.VARnumber.toString()){
					this.index++;
					if(isVar()) {
						tree.setDeclare(variable);
						this.index++;
					}else {
						throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
						//return false;
					}
				}
				//check begin..end
				if(getType(this.index)==TokenType.BEGINnumber.toString()){
					if(isBlock()) {
						tree.setExpression1(this.statement1);
						tree.setExpression2(this.statement2);
						return true; //stop here
					}else {
						throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
						//return false;
					}
				}
			}
			/*else {
				return false;
			}
		}*/
		
		
		
		
		return false;
	}
	public boolean isBlock() throws AnalyzerException{
		//match begin
		if(isBegin()) {
			this.index++;
		}else {
			throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
			//return false;
		}
		
		//check statements
		if(stateMent()) {
		}else {
			throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
			//return false;
		}
		
		// match end. => return true => stop
		if(isEndDot()) {
			this.index++;
			if(this.index==result.size()-1) {
				return true;
			}
		}
		throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
		//return false;
	}
	public boolean stateMent() throws AnalyzerException{
		
		//check expression (a:=b;)
		if(getType(this.index)==TokenType.IDnumber.toString() ) {
			if(isExpr()) {
				getExp++;
			}else {
				throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
				//return false;
			}	
		}
		if(getType(this.index)==TokenType.WRITEnumber.toString() || getType(this.index)==TokenType.WRITELNumber.toString()) {
			this.index++;
			if(isWriteln()) {
			}
			else {
				throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
				//return false;
			}
		}
		else {
		
		}
		
		// stop by end.
		if(getType(this.index)==TokenType.ENDnumber.toString()) {
			return true;
		}
		this.index++;
		return stateMent();
	}
	
	public boolean isWriteln() throws AnalyzerException{
		if(getType(this.index)==TokenType.LPARENnumber.toString()) {
			this.index++;
			if(getType(this.index)==TokenType.IDnumber.toString() && variable.contains(getValue(this.index)) ||getType(this.index)==TokenType.CCONSTnumber.toString() ) {
				tree.setPrintln(result.get(this.index));
				this.index++;
				if(getType(this.index)==TokenType.RPARENnumber.toString()) {
					this.index++;
					if(getType(this.index)==TokenType.SEMInumber.toString()) {
						return true;
					}
				}
			}
			else {
				if(getType(this.index)==TokenType.RPARENnumber.toString()) {
					this.index++;
					if(getType(this.index)==TokenType.SEMInumber.toString()) {
						return true;
					}
				}
			}
			
		}
		throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
		//return false;
	}
	public boolean isExpr() throws AnalyzerException{

		// check: match IDnumber and it is declared
		if(getType(this.index)==TokenType.IDnumber.toString() && variable.contains(getValue(this.index))) {
			if(getExp == 0) {
				this.statement1.add(getValue(this.index));
			}else if(getExp == 1) {
				this.statement2.add(getValue(this.index));
			}
			
			this.index++;
			
			// if IDnumber is a integer variable
			if(variable.get(variable.size()-1).toLowerCase().equals("integer")) {

				//match :=
				if(getType(this.index)==TokenType.COLEQnumber.toString()) {
					if(getExp == 0) {
						this.statement1.add(getValue(this.index));
					}else if(getExp == 1) {
						this.statement2.add(getValue(this.index));
					}
					this.index++;
					// check: match IDnumber2 and it is declared
					// or match a number
					if((getType(this.index)==TokenType.IDnumber.toString()&&variable.contains(getValue(this.index)))||getType(this.index)==TokenType.ICONSTnumber.toString()) {
						if(getExp == 0) {
							this.statement1.add(getValue(this.index));
						}else if(getExp == 1) {
							this.statement2.add(getValue(this.index));
						}
						this.index++;

						//match ; => break
						if(getType(this.index)==TokenType.SEMInumber.toString()) {
							if(getExp == 0) {
								this.statement1.add(getValue(this.index));
							}else if(getExp == 1) {
								this.statement2.add(getValue(this.index));
							}
							return true;
						}
						// for a:= a + b;
						//match +-*/
						else if(getType(this.index)==TokenType.PLUSnumber.toString() || getType(this.index)==TokenType.MINUSnumber.toString() || getType(this.index)==TokenType.PLUSnumber.toString() || getType(this.index)==TokenType.TIMESnumber.toString() ) {
							if(getExp == 0) {
								this.statement1.add(getValue(this.index));
							}else if(getExp == 1) {
								this.statement2.add(getValue(this.index));
							}
							this.index++;
							//match IDnumber or number 
							if((getType(this.index)==TokenType.IDnumber.toString()&&variable.contains(getValue(this.index)))||getType(this.index)==TokenType.ICONSTnumber.toString()) {
								if(getExp == 0) {
									this.statement1.add(getValue(this.index));
								}else if(getExp == 1) {
									this.statement2.add(getValue(this.index));
								}
								this.index++;
								
								//match ; => break
								if(getType(this.index)==TokenType.SEMInumber.toString()) {
									if(getExp == 0) {
										this.statement1.add(getValue(this.index));
									}else if(getExp == 1) {
										this.statement2.add(getValue(this.index));
									}
									return true;
								}
							}
						}else {
							throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
							//return false;
						}
					}
				}
			}
			//check for string variable
			if(variable.get(variable.size()-1).toLowerCase().equals("string")) {
				//match :=
				if(getType(this.index)==TokenType.COLEQnumber.toString()) {
					if(getExp == 0) {
						this.statement1.add(getValue(this.index));
					}else if(getExp == 1) {
						this.statement2.add(getValue(this.index));
					}
					this.index++;
					// check: match IDnumber2 and it is declared
					// or match a number
					if((getType(this.index)==TokenType.IDnumber.toString()&&variable.contains(getValue(this.index)))||getType(this.index)==TokenType.CCONSTnumber.toString()) {
						if(getExp == 0) {
							this.statement1.add(getValue(this.index));
						}else if(getExp == 1) {
							this.statement2.add(getValue(this.index));
						}
						this.index++;
						//match ; => break
						if(getType(this.index)==TokenType.SEMInumber.toString()) {
							if(getExp == 0) {
								this.statement1.add(getValue(this.index));
							}else if(getExp == 1) {
								this.statement2.add(getValue(this.index));
							}
							return true;
						}
						// for a:= a + b;
						//match +-*/
						else if(getType(this.index)==TokenType.PLUSnumber.toString() || getType(this.index)==TokenType.MINUSnumber.toString() || getType(this.index)==TokenType.PLUSnumber.toString() || getType(this.index)==TokenType.TIMESnumber.toString() ) {
							if(getExp == 0) {
								this.statement1.add(getValue(this.index));
							}else if(getExp == 1) {
								this.statement2.add(getValue(this.index));
							}
							this.index++;
							//match IDnumber or number 
							if((getType(this.index)==TokenType.IDnumber.toString()&&variable.contains(getValue(this.index)))||getType(this.index)==TokenType.CCONSTnumber.toString()) {
								if(getExp == 0) {
									this.statement1.add(getValue(this.index));
								}else if(getExp == 1) {
									this.statement2.add(getValue(this.index));
								}
								this.index++;
								
								//match ; => break
								if(getType(this.index)==TokenType.SEMInumber.toString()) {
									if(getExp == 0) {
										this.statement1.add(getValue(this.index));
									}else if(getExp == 1) {
										this.statement2.add(getValue(this.index));
									}
									return true;
								}
							}
						}else {
							throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
							//return false;
						}
					}
				}
			}
			
			// CHECK WRITELN HERE
			//
			//
			//
			
			//if IDnumber is a string variable
/*			else if(variable.get(variable.size()-1).toLowerCase().equals("string")) {
				if(getType(this.index)==TokenType.COLEQnumber.toString()) {
					this.index++;
					
					// check: match IDnumber2 and it is declared
					// or match a number
					if((getType(this.index)==TokenType.IDnumber.toString()&&variable.contains(getValue(this.index)))||getType(this.index)==TokenType.ICONSTnumber.toString()) {
						this.index++;
						if(getType(this.index)==TokenType.SEMInumber.toString()) {
							return true;
						}
					}
				}
			}
			*/
//			else {
//				return false;
//			}
		}
		throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
		//return false;
	}
	
	public boolean isBegin() throws  AnalyzerException{
		if(getType(this.index)==TokenType.BEGINnumber.toString()) {
			return true;
		}
		throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
		//return false;
	}
	
	public boolean isEndDot() throws AnalyzerException {
		if(isWord(result.get(this.index),TokenType.ENDnumber)&& isWord(result.get(this.index+1),TokenType.DOTnumber)) {
			return true;
		}
		throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
		//return false;
	}
	
	public boolean isProgram() throws AnalyzerException{
		
		//match program
		if(getType(this.index)==TokenType.PROGRAMnumber.toString()) {
			this.index ++;
			
			// program name (IDnumber)
			if(getType(this.index)==TokenType.IDnumber.toString()) {
				tree.setProgram(result.get(this.index)); //get program name
				this.index ++;
				
				//match ; => break
				if (getType(this.index)==TokenType.SEMInumber.toString()){
					return true;
				}
			}
		} throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
		//return false;
	}
	
	public boolean isVar() throws AnalyzerException{
		
		//check list of variable
			if(isIdentList()) {
				this.index++;
				
				//match String or Integer 
				if(getValue(this.index).toLowerCase().equals("integer")||getValue(this.index).toLowerCase().equals("string")) {
					
					//add to list
					this.variable.add(getValue(this.index));	
					this.index++;
					
					//match ; => break
					if(getType(this.index)==TokenType.SEMInumber.toString()) {
						return true;
					}				
				}
			}
			throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
		//return false;
	}
	
	public boolean isIdentList() throws AnalyzerException{
		
		//match variable
		if(getType(this.index)==TokenType.IDnumber.toString()) {
			
			//add variable into arraylist
			this.variable.add(getValue(this.index));
			this.index++;
			
			//match : => break
			if(getType(this.index)==TokenType.COLONnumber.toString()) {
				return true;
			}
			
			//match , => call isIdentist()
			else if(getType(this.index)==TokenType.COMMMAnumber.toString()) {
				this.index++;	
				return isIdentList();		
			}else {
				throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
				//return false;
			}
		}
		throw new AnalyzerException("Error at line # " + getLine(this.index), this.index, getLine(this.index));
		//return false;	
	}
//	public boolean isExpression() {
//	System.out.println("vao expression");
//	System.out.println("bat dau expre ="+getValue(this.index));
//	if(getType(this.index)==TokenType.SEMInumber.toString()) {
//		System.out.println("gap ; va dung");
//		return true;
//	}
//	if((getType(this.index)==TokenType.IDnumber.toString()&&getType(this.index+1)!=TokenType.IDnumber.toString())||getType(this.index)==TokenType.PLUSnumber.toString()||getType(this.index)==TokenType.ICONSTnumber.toString()) {
//		System.out.println("gap bien");
//		this.index++;
//		
//		if(getType(this.index)==TokenType.COLEQnumber.toString()&&getType(this.index+1)!=TokenType.SEMInumber.toString()) {
//			System.out.println("gap :=");
//			this.index++;
//			//return isExpression();
//		}
//		return isExpression();
//	}
//	
//	System.out.println("false");
//	return false;
//}
//public boolean isExpression() {
//	System.out.println("vao expre");
//	if(getType(this.index)==TokenType.SEMInumber.toString()) {
//		System.out.println("gap ; va index ="+this.index);
//		return true;
//	}
//	if(getType(this.index)==TokenType.IDnumber.toString()) {
//		System.out.println("gap bien");
//		this.index++;
//		System.out.println("1"+getValue(this.index));
//		System.out.println("2"+getValue(this.index+1));
//		if(getType(this.index)==TokenType.COLEQnumber.toString()) {
//			System.out.println("gap :=");
//			this.index++;
//			if(getType(this.index)==TokenType.SEMInumber.toString()) {
//				System.out.println("gap ; va index ="+this.index);
//				return true;
//			}else if(getType(this.index)==TokenType.MINUSnumber.toString()||getType(this.index)==TokenType.PLUSnumber.toString()||getType(this.index)==TokenType.TIMESnumber.toString()) {//,
//				System.out.println("gap ,");
//				this.index++;	
//				return isExpression();		
//			}else {
//				return false;
//			}
//		}
//	}
//	return false;
//}

//	if(getType(this.index)==TokenType.SEMInumber.toString()&&getType(this.index+1)==TokenType.IDnumber.toString()) {
//		System.out.println("lai gap id");
//		this.index++;
//		return isVar();
//	}
	//Bat dau chuong trinh bang PROGRAM
	/*public boolean startProgram(List<Token> result, int index) {
		if (isProgram()) {
			return true;
		}
		return false;
//		if(isDeclare())
//		if( isBlock(result,this.index)) {
//			return true;
//		}else {
//			return false;
//		}
	}*/
//	public boolean isProgram() {
//		if(isWord(this.result.get(this.index),TokenType.PROGRAMnumber)) {
//			System.out.println("gap program");
//			this.index ++;
//			// program name
//			if(isWord(this.result.get(this.index),TokenType.IDnumber)) {
//				System.out.println("gap ten CT");
//				this.index ++;
//				//gap (
////				if(isWord(result.get(this.index),TokenType.LPARENnumber  )) {
////					System.out.println("gap (");
////					this.index ++;
////					// gap list ID
////					if(isListIDnumber2(result,this.index)) {
////						this.index ++;
////						System.out.println("chay xong isList ben ngoai");
////						if(isWord(result.get(this.index),TokenType.RPARENnumber  )) {
////							System.out.println("gap )");
////							this.index++;
////						}else {
////							return false;
////						}
////						//return true;
////					}else {
////						return false;}
////					}
//				if (isWord(this.result.get(this.index),TokenType.SEMInumber)){// ;
//					System.out.println("gap ; end isProgram with "+this.index);
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	
//	public boolean isBlock(List<Token> result, int index) {
//		System.out.println("vao isBlock: "+this.index);
//		if(isVar(result,this.index)) {
//			this.index++;
//		}
//		if(isBegin(result,this.index)) {
//			System.out.println("gap begin");
//			this.index++;
//			if(isStatement(result,this.index)) {
//				this.index++;
//				if(isEndDot(result,this.index)) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	
//	public boolean isBlock() {
//	System.out.println("vao isBlock: "+this.index);
//	if(isVar()) {
//		this.index++;
//	}
//	if(isBegin(result,this.index)) {
//		System.out.println("gap begin");
//		this.index++;
//		if(isStatement(result,this.index)) {
//			this.index++;
//			if(isEndDot(result,this.index)) {
//				return true;
//			}
//		}
//	}
//	return false;
//	}

//	public boolean isStatement(List<Token> result, int index) {
//		if(isExpression(result,this.index)) {
//			
//		}
////		if(isExpression2(result,this.index)) {
////			
////		}
//		return true;
//	}
//	public boolean isExpression(List<Token> result, int index) {
//		if(isIdentList(result,this.index)) {
//			this.index++;
//			if(isWord(result.get(this.index),TokenType.COLEQnumber)) {
//				System.out.println("gap :");// gap :=
//				this.index++;
//				if(( isNum(result,this.index)||isIdentList(result,this.index))&& isWord(result.get(this.index+1),TokenType.SEMInumber)   ) {
//					System.out.println("xong bieu thuc simpe");
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//	public boolean isExpression2(List<Token> result, int index) {
//		if(isIdent(result,this.index)) {
//			this.index++;
//			if(isWord(result.get(index),TokenType.COLEQnumber)) {// gap :=
//				this.index++;
//				if(isIdent(result,this.index)) {
//					if()
//				}
//			}
//		}
//		return false;
//	}
	
	/*public boolean isIdentList(List<Token> result,int index) {
		if(isWord(result.get(this.index),TokenType.IDnumber)) {
			System.out.println("gap bien");
			this.index++;
			if(isWord(result.get(this.index),TokenType.COMMMAnumber)) {//,
				System.out.println("gap ,");
				this.index++;	
				isIdentList(result,this.index);		
			} else if(isWord(result.get(this.index),TokenType.COLONnumber)) {
				System.out.println("gap : va index ="+this.index);
				return true;
			}
		}
		return false;	
//		if(isWord(result.get(this.index),TokenType.IDnumber) && isWord(result.get(this.index+1),TokenType.COLONnumber)) {
//			return true;
//		}
//		if (isWord(result.get(this.index),TokenType.IDnumber) && isWord(result.get(this.index+1),TokenType.COMMMAnumber)){
//			 isIdent(result,this.index+1);
//		}
//		if(isWord(result.get(this.index),TokenType.RPARENnumber)) {//dieu kien dung
//			System.out.println("gap ) va index = "+this.index);
//			return true;
//		}
		//System.out.println("ket thuc ham list voi index = "+this.index);
		//return false;
	}*/
	
	//check list hay 1 element
	/*public boolean isListIDnumber(List<Token> result, int index) {
		if(isWord(result.get(this.index),TokenType.IDnumber)) {
			System.out.println("gap bien");
			this.index++;
			if(isWord(result.get(this.index),TokenType.COMMMAnumber)) {//,
				System.out.println("gap ,");
				this.index++;	
				if(isListIDnumber(result,this.index)){
					return true;
				}			
			} else if(isWord(result.get(this.index),TokenType.RPARENnumber)) {
				this.index++;
				System.out.println("gap ) ");
				return true;
			}else {
				return false;
			}
		}
		return false;	
	}*/
	/*public boolean isListIDnumber2(List<Token> result, int index) {
		System.out.println("bat dau ham isList voi index = "+this.index);
		if(isWord(result.get(this.index),TokenType.IDnumber)&&isWord(result.get(this.index + 1),TokenType.COMMMAnumber)) {
			
			System.out.println(this.index);
			System.out.println("gap bien");
			this.index++;
			this.index++;
			return isListIDnumber2(result,this.index);
			 
		}
//		if(isWord(result.get(this.index),TokenType.RPARENnumber)) {//dieu kien dung
//			System.out.println("gap ) va index = "+this.index);
//			return true;
//		}
		System.out.println("ket thuc ham list voi index = "+this.index);
		return true;	
	}*/

	
	//Comment here
//	public boolean block(List<Token> result, int index) {
//		System.out.println("vao block voi index ="+this.index);
//		//code here
//		isVar(result,index);
//		
//		return true;
//	}
	
	
	//Kiem tra tu dung voi TokenType hay khong?
	public boolean isWord(Token token, TokenType dataType) {
		if(token.tokenType == dataType ){
			return true;
		}
		return false;
	}
	
	
}

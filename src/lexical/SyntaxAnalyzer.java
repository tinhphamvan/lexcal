package lexical;

import java.util.ArrayList;
import java.util.List;

import lexical.LexicalAnalyzer;

public class SyntaxAnalyzer {
	
	int index = 0;
	List<Token> result;
	List<String> variable = new ArrayList<String>();
	TreeProperty tree = new TreeProperty();
	public SyntaxAnalyzer(){
		
	}
	
	public String getType(int index) {
		return this.result.get(index).tokenType.toString();
	}
	public String getValue(int index) {
		return this.result.get(index).tokenString;
	}
	
	public boolean validateCFG(List<Token> result) throws AnalyzerException{
		this.result=result;
		
		///check progarm
		if (getType(this.index)==TokenType.PROGRAMnumber.toString()) {
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
						System.out.println("false");
						return false;
					}
				}
				//check begin..end
				if(getType(this.index)==TokenType.BEGINnumber.toString()){
					if(isBlock()) {
						return true; //stop here
					}else {
						return false;
					}
				}
			}else {
				return false;
			}
		}
		
		
		 for (String item : variable) { 		      
	           System.out.println(item); 		
	      }
		
		return false;
	}
	public boolean isBlock() {
		//match begin
		if(isBegin()) {
			this.index++;
		}else {
			return false;
		}
		
		//check statements
		if(stateMent()) {
			this.index++;
			System.out.println(this.index);
			System.out.println("statement true");
		}else {
			System.out.println(this.index);
			System.out.println("statement false");
			return false;
		}

		// match end. => return true => stop
		if(isEndDot()) {
			this.index++;
			if(this.index==result.size()-1) {
				return true;
			}
		}
		return false;
	}
	public boolean stateMent() {
		System.out.println("vao statement  ");
		
		//check expression (a:=b;)
		if(getType(this.index)==TokenType.IDnumber.toString() ) {
			
			if(isExpr()) {
				System.out.println("isExpre1 true");

			}else {
				return false;
			}	
		}
		// stop by end.
		if(getType(this.index+1)==TokenType.ENDnumber.toString()) {
			return true;
		}
		this.index++;
		return stateMent();
	}
	
	public boolean isWriteln() {
		if(getValue(this.index).toLowerCase().equals("write")||getValue(this.index).toLowerCase().equals("writeln")) {
			System.out.println(getValue(this.index));
			return true;
			//code here
			
		}
		return false;
	}
	public boolean isExpr() {

		// check: match IDnumber and it is declared
		if(getType(this.index)==TokenType.IDnumber.toString() && variable.contains(getValue(this.index))) {
			this.index++;

			// if IDnumber is a integer variable
			if(variable.get(variable.size()-1).toLowerCase().equals("integer")) {

				//match :=
				if(getType(this.index)==TokenType.COLEQnumber.toString()) {
					this.index++;
					// check: match IDnumber2 and it is declared
					// or match a number
					if((getType(this.index)==TokenType.IDnumber.toString()&&variable.contains(getValue(this.index)))||getType(this.index)==TokenType.ICONSTnumber.toString()) {
						this.index++;

						//match ; => break
						if(getType(this.index)==TokenType.SEMInumber.toString()) {
							return true;
						}
						// for a:= a + b;
						//match +-*/
						else if(getType(this.index)==TokenType.PLUSnumber.toString() || getType(this.index)==TokenType.MINUSnumber.toString() || getType(this.index)==TokenType.PLUSnumber.toString() || getType(this.index)==TokenType.TIMESnumber.toString() ) {
							this.index++;
							//match IDnumber or number 
							if((getType(this.index)==TokenType.IDnumber.toString()&&variable.contains(getValue(this.index)))||getType(this.index)==TokenType.ICONSTnumber.toString()) {
								this.index++;
								
								//match ; => break
								if(getType(this.index)==TokenType.SEMInumber.toString()) {
									return true;
								}
							}
						}else {
							return false;
						}
					}
				}
			}
			//check for string variable
			if(variable.get(variable.size()-1).toLowerCase().equals("string")) {
				//match :=
				System.out.println("gap");
				if(getType(this.index)==TokenType.COLEQnumber.toString()) {
					this.index++;
					System.out.println("gap");
					// check: match IDnumber2 and it is declared
					// or match a number
					System.out.println(getValue(this.index));
					System.out.println(getType(this.index));
					if((getType(this.index)==TokenType.IDnumber.toString()&&variable.contains(getValue(this.index)))||getType(this.index)==TokenType.CCONSTnumber.toString()) {
						this.index++;
						System.out.println("gap");
						//match ; => break
						if(getType(this.index)==TokenType.SEMInumber.toString()) {
							return true;
						}
						// for a:= a + b;
						//match +-*/
						else if(getType(this.index)==TokenType.PLUSnumber.toString() || getType(this.index)==TokenType.MINUSnumber.toString() || getType(this.index)==TokenType.PLUSnumber.toString() || getType(this.index)==TokenType.TIMESnumber.toString() ) {
							this.index++;
							//match IDnumber or number 
							if((getType(this.index)==TokenType.IDnumber.toString()&&variable.contains(getValue(this.index)))||getType(this.index)==TokenType.CCONSTnumber.toString()) {
								this.index++;
								
								//match ; => break
								if(getType(this.index)==TokenType.SEMInumber.toString()) {
									return true;
								}
							}
						}else {
							return false;
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
		System.out.println("het");
		return false;
	}
	
	public boolean isBegin() {
		if(getType(this.index)==TokenType.BEGINnumber.toString()) {
			return true;
		}
		return false;
	}
	
	public boolean isEndDot() {
		if(isWord(result.get(this.index),TokenType.ENDnumber)&& isWord(result.get(this.index+1),TokenType.DOTnumber)) {
			return true;
		}
		return false;
	}
	
	public boolean isProgram() {
		
		//match program
		if(getType(this.index)==TokenType.PROGRAMnumber.toString()) {
			this.index ++;
			
			// program name (IDnumber)
			if(getType(this.index)==TokenType.IDnumber.toString()) {
				tree.setProgram(getValue(this.index)); //get program name
				this.index ++;
				
				//match ; => break
				if (getType(this.index)==TokenType.SEMInumber.toString()){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isVar(){
		
		//check list of variable
			if(isIdentList()) {
				this.index++;
				System.out.println("index = "+this.index);
				
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
		return false;
	}
	
	public boolean isIdentList() {
		
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
				return false;
			}
		}
		return false;	
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

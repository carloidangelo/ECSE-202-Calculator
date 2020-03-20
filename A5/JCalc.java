/*
 ============================================================================
 Assignment 5 - Adding an Interpreter - JCalc

 Name            : Carlo D'Angelo
 Student ID      : 260803454
 Class			 : ECSE 202 – Introduction to Software Development
 ============================================================================
*/

import java.util.Objects; // need it for string comparison
import java.util.StringTokenizer; /* need it for parsing the input string and separating it 
								     into operators and operands */


import acm.program.ConsoleProgram; // need it to run a Console program

public class JCalc extends ConsoleProgram {
	public void run() {
		
		Stack operator_stack = new Stack(); // creates a Stack object
		Stack interpreter_stack = new Stack(); // creates a second Stack object
		Queue input_queue = new Queue(); // creates a Queue object
		Queue output_queue = new Queue(); // creates a second Queue object
		
		// program repeats
		while (true){
			String exp = readLine("Enter expression: ");    
			StringTokenizer st = new StringTokenizer(exp,"+-*/()",true); // +,-,*,/,(,) are all considered 
																	   // tokens when the string is parsed
			while (st.hasMoreTokens()){
				input_queue.enqueue(st.nextToken()); // fills input queue with operators and operands
				
			}
			
			// Shunting Yard algorithm 
			// while input queue is not empty
			while (input_queue.front != null){
				
				String str_value = input_queue.dequeue();
				
				// if string value matches (+ OR -)
				if ( Objects.equals(str_value,"+") || Objects.equals(str_value,"-") ){ // string comparison
		
					if (operator_stack.top == null){
						operator_stack.push(str_value);
						
					// if top operator on operator stack is left parenthesis
					}else if ( Objects.equals(operator_stack.top.svalue,"(") ){
						operator_stack.push(str_value);
						
					}else {
						while (operator_stack.top != null && 
								!Objects.equals(operator_stack.top.svalue,"(")){
							output_queue.enqueue(operator_stack.pop());
						}
						operator_stack.push(str_value);
					}	
				
				// if string value matches (* OR /)
				}else if ( Objects.equals(str_value,"*") || Objects.equals(str_value,"/") ){
					
					if (operator_stack.top == null){
						operator_stack.push(str_value);
					
					// if (* OR /) has higher precedence than top operator on operator stack
					}else if ( Objects.equals(operator_stack.top.svalue,"+") ||
									Objects.equals(operator_stack.top.svalue,"-") ){
						operator_stack.push(str_value);
						
					// if top operator on operator stack is left parenthesis
					}else if ( Objects.equals(operator_stack.top.svalue,"(") ){
						operator_stack.push(str_value);
						
					}else {
						while ( operator_stack.top != null && (Objects.equals(operator_stack.top.svalue,"*") || 
									Objects.equals(operator_stack.top.svalue,"/")) ){
							output_queue.enqueue(operator_stack.pop());
						}
						operator_stack.push(str_value);
					}
				
				// if string value matches left parenthesis
				}else if ( Objects.equals(str_value,"(")){
					operator_stack.push(str_value);
					
				// if string value matches right parenthesis
				}else if (Objects.equals(str_value,")")){
					
					// while top operator on operator stack is not left parenthesis
					while ( !Objects.equals(operator_stack.top.svalue,"(") ){
						output_queue.enqueue(operator_stack.pop());	
						}
					
					// getting rid of left parenthesis
					if(operator_stack.top.previousNode == null) {
						operator_stack.top = null;
						
					}else {
						ListNode newtop2 = operator_stack.top.previousNode;
						newtop2.nextNode = null;
						operator_stack.top = newtop2;		
					}		
					
				// if string value is an operand
				}else {
					output_queue.enqueue(str_value);
				}
			}
			
			// if there are still operators in the operator stack after input queue is empty
			while (operator_stack.top != null){
				output_queue.enqueue(operator_stack.pop());
			}
			
			// Interpreter Code
			while (output_queue.front != null){
				
				String token = output_queue.dequeue();
				
				// if token is an operator
				if ( Objects.equals(token,"+") ||
						Objects.equals(token,"-") ||
						Objects.equals(token,"*") || 
						Objects.equals(token,"/") ) {
					
					// pop last two operands 
					double value2 = Double.parseDouble(interpreter_stack.pop());
					double value1 = Double.parseDouble(interpreter_stack.pop());
					
					// depending on the operator, an operation is executed on the two operands
					// result is pushed onto stack
					// item remaining in the stack after exiting while loop is the final result
					if ( Objects.equals(token,"+") ){
						double total = value1 + value2;
						interpreter_stack.push(String.valueOf(total));
					}else if ( Objects.equals(token,"-") ){
						double total = value1 - value2;
						interpreter_stack.push(String.valueOf(total));
					}else if ( Objects.equals(token,"*") ){
						double total = value1 * value2;
						interpreter_stack.push(String.valueOf(total));
					}else {
						double total = value1 / value2;
						interpreter_stack.push(String.valueOf(total));
					}
				
				// if token is an operand
				}else {
					interpreter_stack.push(token);
				}
				
			}
			
			// Output
			println(exp + " = " + interpreter_stack.pop());
					
		}
		
	}
	
}
/*
 ============================================================================
 Assignment 4 - Introduction to Java Programming - In2pJ

 Name            : Carlo D'Angelo
 Student ID      : 260803454
 Class			 : ECSE 202 – Introduction to Software Development
 ============================================================================
*/

import java.util.Objects; // need it for string comparison
import java.util.StringTokenizer; /* need it for parsing the input string and separating it 
								     into operators and operands */
import acm.program.ConsoleProgram; // need it to run a Console program

public class In2pJ extends ConsoleProgram {
	public void run() {
		
		Stack operator_stack = new Stack(); // creates a Stack object
		Queue input_queue = new Queue(); // creates a Queue object
		Queue output_queue = new Queue(); // creates a second Queue object
		
		String exp = readLine("Enter string: ");    
		StringTokenizer st = new StringTokenizer(exp,"+-*/",true); // +,-,*,/ are all considered 
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
					
				}else {
					while (operator_stack.top != null){
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
					
				}else {
					while ( Objects.equals(operator_stack.top.svalue,"*") || 
								Objects.equals(operator_stack.top.svalue,"/") ){
						output_queue.enqueue(operator_stack.pop());
					}
					operator_stack.push(str_value);
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
		
		// Output
		print("Postfix: ");
		while (output_queue.front != null){
			print(output_queue.dequeue() + " ");
		}
		
	}
	
}
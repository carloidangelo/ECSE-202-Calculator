/*
 ============================================================================
 Assignment 5 - Adding an Interpreter - Stack

 Name            : Carlo D'Angelo
 Student ID      : 260803454
 Class			 : ECSE 202 – Introduction to Software Development
 ============================================================================
*/

public class Stack {
	
	// instance variable
	public ListNode top;
		
	// Constructor
	public Stack(){
		top = null;
	}
	
	// Push
	public void push(String input_string) {
		
		ListNode new_node = new ListNode(input_string);
		
		//if the queue is not empty
		if(top != null) {		
			
			top.nextNode = new_node;
			new_node.previousNode = top;		
		}
		
		top = new_node;	
	}
	
	// Pop
	public String pop() {
		
		// if the stack is empty
		if(top == null) {
				System.out.println("Error: the stack is empty");
				return null;
			}
			
		String node_svalue = top.svalue;	
		
		// if there is a single node in the stack
		if(top.previousNode == null) {
			
			top = null;
			
		// if there is more than one node in the stack
		}else {
			
			ListNode new_top = top.previousNode;
			new_top.nextNode = null;
			top = new_top;		
		}		
		
		return node_svalue;
	}
	
}
/*
 ============================================================================
 Assignment 5 - Adding an Interpreter - ListNode

 Name            : Carlo D'Angelo
 Student ID      : 260803454
 Class			 : ECSE 202 – Introduction to Software Development
 ============================================================================
*/

// defines the data object used by the Queue and Stack routines 
public class ListNode {
	
	// instance variables
	String svalue;
	ListNode nextNode;
	ListNode previousNode;
	
	// Constructor
	public ListNode(String input_string){
		svalue = input_string;
		nextNode = null;
		previousNode = null;
		
	}
	
}

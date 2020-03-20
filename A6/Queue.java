/*
 ============================================================================
 Assignment 6 – A Basic 4-Function Calculator - Queue

 Name            : Carlo D'Angelo
 Student ID      : 260803454
 Class			 : ECSE 202 – Introduction to Software Development
 ============================================================================
*/

public class Queue {
	
	// instance variables
	public ListNode front;
	public ListNode rear;

	// Constructor
	public Queue(){
		front = null;
		rear = null;
	}
	
	
	// Enqueue
	public void enqueue(String input_string) {
		
		ListNode new_node = new ListNode(input_string);
		
		// if the queue is empty
		if(rear == null) {
			
			rear = new_node;
			front = new_node;
			
		// if the queue is not empty
		}else {
			
			new_node.nextNode = rear;
			rear.previousNode = new_node;
			rear = new_node;
		}
	}
	
	// Dequeue
	public String dequeue() {
		
		// if there is no front node in the queue
		if(front == null) {
			
			System.out.println("Error: the queue is empty");
			return null;
		}
		
		String node_svalue = front.svalue;
		
		// if there is a single node in the queue
		if(front.previousNode == null) {
			
			front = null;
			rear = null;
			
		// if there is more than one node in the queue
		}else {
			
			ListNode new_front = front.previousNode;
			new_front.nextNode = null;
			front = new_front;
		}
		
		return node_svalue;
	}

}

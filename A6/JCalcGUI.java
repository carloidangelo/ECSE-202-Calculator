/*
 ============================================================================
 Assignment 6 – A Basic 4-Function Calculator - JCalcGUI

 Name            : Carlo D'Angelo
 Student ID      : 260803454
 Class			 : ECSE 202 – Introduction to Software Development
 ============================================================================
*/

import java.util.Objects;       // for string comparison
import java.util.StringTokenizer; /* for parsing the input string and separating it 
								     into operators and operands */
import java.text.DecimalFormat;   /* need it to display final result to a default 
								   precision of 6 decimal places */

import acm.program.*;

import acm.gui.TableLayout; // for TableLayout Class 
import java.awt.*;          // need it to change Font and Color
import java.awt.event.*;    // for Event Listeners
import javax.swing.*;       // for Swing interactors 

public class JCalcGUI extends Program {
	
	public void init() {
		setSize(500,500); 
		setLayout(new TableLayout(6, 5));  	// TableLayout has 6 rows and 5 columns
		expField = new JTextField();  	// creating two text fields: one for
		rstField = new JTextField();  	// the expression and the other for the result	 
		add(expField,"gridwidth=5 height=40");
		add(rstField,"gridwidth=5 height=40");
		addButtons(); 		// adds operator and operand buttons to the TableLayout
		addActionListeners(); 
		
		exp = ""; 		// important for displaying the expression in the top text field
		operator_stack = new Stack(); 		// creates a Stack object
		interpreter_stack = new Stack(); 	// creates a second Stack object
		input_queue = new Queue(); 		// creates a Queue object
		output_queue = new Queue(); 	// creates a second Queue object

	}

	/* Respond to a button action */
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		
		//if any button is pressed (apart from Equals and Clear)
		if (!(cmd.equals("C")) && !(cmd.equals("="))){
			for (int i = 0; i < 19; i++){
				if (cmd.equals(buttonlist[i])){
					exp += buttonlist[i];
					expField.setText(exp); /* displays expression in the top text field
											(depending on what buttons are pressed) */
				}
			}
			
		// if Clear button is pressed by user			
		}if (cmd.equals("C")){ 
			exp = "";
			expField.setText(exp); // clears operators and operands off
			rstField.setText(exp); // both text fields
			
		// if Equals button is pressed by user		
		}if (cmd.equals("=")) { 
			String expf = exp + "=";
			expField.setText(expf);
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
			
			String value = interpreter_stack.pop();
			
			// displays final result in lower text field to a default precision of 6 decimal places
			DecimalFormat round6dec = new DecimalFormat("#0.000000"); 
			String value2 = round6dec.format(Double.parseDouble(value));
			rstField.setText(value2);			
	
		}
	}
	
	private void addButtons() { 
		JButton buttons, equals;
		String constraint = "width=75" + " height=75"; 
		for (int i = 0; i < 19; i++){
			// exception for the Equals button
			if (Objects.equals(buttonlist[i],"=")){
				add(equals = new JButton("="),"gridwidth=2 height=75");
				equals.setFont(new Font("Calibri", Font.PLAIN, 20));
				break;
			}
			// creates almost all of the buttons
			add(buttons = new JButton(buttonlist[i]),constraint);
			buttons.setFont(new Font("Calibri", Font.PLAIN, 20));
			
			// changes the design of the Clear button
			if (Objects.equals(buttonlist[i],"C")){
				buttons.setBackground(Color.BLACK);
				buttons.setForeground(Color.WHITE);
			}
		}

	}
	
	// instance variables
	private JTextField expField;
	private JTextField rstField;
	private String exp;
	private Stack operator_stack;
	private Stack interpreter_stack;
	private Queue input_queue;
	private Queue output_queue;
	private String[] buttonlist = {"7","8","9","+","-","4","5","6","*",
									"/","1","2","3","(",")","C","0",".","="};
}

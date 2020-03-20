/*
 ============================================================================
 Assignment 1 - Introduction to C Programming

 Name            : Carlo D'Angelo
 Student ID      : 260803454
 Class			 : ECSE 202 – Introduction to Software Development
 ============================================================================
 */

#include <stdio.h>

int main(int argc,char *argv[]) {
	int a, b;
	if (argc == 3){ //if there are 3 arguments on the command line
		sscanf(argv[1],"%d",&a);
		sscanf(argv[2],"%d",&b);
	}
	else if (argc == 2){ //if there are only 2 arguments on the command line
		sscanf(argv[1],"%d",&a);
		b=2; //base becomes 2
	}
	else{ //any other case
		printf("wrong number of arguments\n");
		return (0); //ends the program right there
	}
	int i=0;
	int c=a; //since value of 'a' will change in the while loop, 'c' will
			 //hold the initial value of 'a'
	int t[sizeof(int)*8];
	while (a>0){
		t[i]=a%b; //remainder of a/b will be added to array t[]
		a=a/b;
		i+=1;
	}
	printf("The Base-%d form of %d is: ",b,c);
	char alpha[36]="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //an array of characters that
														   //represents the remainders
	for(int j = i-1; j >= 0 ; j--){ //starts iterating from the end of the t[] array
									//goes through every remainder from end to beginning
		printf("%c",alpha[t[j]]); //print the value in the alpha[] array corresponding
								  //to the remainders in the t[] array
	}
	printf("\n"); //print an extra line before the program ends
	return (0);
}

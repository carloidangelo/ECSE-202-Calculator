/*
 Assignment 3 - BTrees, Dynamic Memory Allocation

 Name            : Carlo D'Angelo
 Student ID      : 260803454
 Class			 : ECSE 202 – Introduction to Software Development
 ============================================================================
 This program assumes that
 a names and IDs data file (location and name) is passed as the first argument
 a marks data file is passed as the second argument
 a last name is passed as the third argument
*/

#define MAXNAMELENGTH 15
#include <stdio.h>
#include <stdlib.h>
#include <string.h> // for strcasecmp() function - need it to compare strings
					// strcasecmp() is case insensitive

//Define structure to hold student data
struct StudentRecord
{
	char FirstNames[MAXNAMELENGTH];
	char LastNames[MAXNAMELENGTH];
	int IDNums;
	int Marks;
};

//Define structure to hold student data records
struct BinaryTree{
	struct StudentRecord SRecord;
	struct BinaryTree* left;
	struct BinaryTree* right;
};

// addnode function - will be called in main() function
void addnode(struct BinaryTree** root, struct StudentRecord record)
{
	if(*root == NULL)
	{
		struct BinaryTree* cur_root = (struct BinaryTree*)malloc(sizeof(struct BinaryTree));
		cur_root -> SRecord = record;
		cur_root -> left = NULL;
		cur_root -> right = NULL;
		*root = cur_root;
	}
	else if(strcmp(record.LastNames,(*root) -> SRecord.LastNames) < 0)
	{
		addnode (&((*root)->left),record);
	}
	else
	{
		addnode (&((*root)->right),record);
	}
}

// traverse_binary_tree function - will be called in main() function
void traverse_binary_tree(struct BinaryTree* bt,struct StudentRecord *SRecords[])
{
	static int i = 0; // i must be a static integer so that it doesn't get
					  // re-initialized back to 0 with each subsequent function call

	if(bt -> left != NULL)
    	traverse_binary_tree(bt -> left, SRecords); //recursion

	SRecords[i++] = &(bt -> SRecord);

	if(bt -> right != NULL)
		traverse_binary_tree(bt -> right, SRecords); //recursion
}

// binary search function - will be called in main() function
int * binsearch(char lastname[],int arraylength,struct StudentRecord *SRecords[]){
	unsigned int Left=0, Right=arraylength, Mdl;
	while(Left < Right){
		Mdl = ((Left + Right-1)/2);
		if (strcasecmp (lastname, SRecords[Mdl] -> LastNames) == 0){ // runs when last names are the same
			// prints all the information in the corresponding record
			printf("The following record was found:\n");
			printf("Name: %s %s\n", SRecords[Mdl] -> FirstNames, SRecords[Mdl] -> LastNames);
			printf("Student ID: %d\n", SRecords[Mdl] -> IDNums);
			printf("Student Grade: %d", SRecords[Mdl] -> Marks);
			return NULL;
		}
		else if(strcasecmp(lastname,SRecords[Mdl]->LastNames)<0){
			Right = Mdl;
		}
		else{
			Left = Mdl+1;
		}
	}
	printf("No record found for student with last name %s.",lastname);
	return NULL;
}

int main(int argc, char * argv[]) {

	// Declares a student data record
	struct StudentRecord SRecord;

	// initializing a pointer that points to the top of an empty B-Tree - NULL pointer
	struct BinaryTree* root = NULL;

	int numrecords;

	FILE * NamesIDsDataFile;
	if((NamesIDsDataFile = fopen(argv[1], "r")) == NULL){
		printf("Can't read from file %s\n", argv[1]);
		exit(1);
	}
	FILE * MarksDataFile;
	if ((MarksDataFile = fopen(argv[2], "r")) == NULL){
		printf("Can't read from file %s\n", argv[2]);
		exit(1);
	}

	else{
	    	numrecords=0;
	    	// reads from NamesIDs and marks at the same time
			while (fscanf(NamesIDsDataFile,"%s%s%d",&(SRecord.FirstNames[0]),
			      				&(SRecord.LastNames[0]),
			      				&(SRecord.IDNums)) != EOF &&
					fscanf(MarksDataFile,"%d",&(SRecord.Marks)) != EOF) {
				addnode (&root, SRecord); // creates a B-Tree containing the student data records
				numrecords++;
	    	}
		}

	fclose(NamesIDsDataFile);
	fclose(MarksDataFile);

	// Creating an array of pointers - points to the student data records
	struct StudentRecord **pSRecords = malloc(numrecords*sizeof(struct StudentRecord **));

	// Links pointers in pointer array to their corresponding record in B-Tree
	// Creates a sorted array of pointers
	traverse_binary_tree (root, pSRecords);

	// user inputs a last name to find that person's corresponding record
	char lstname[MAXNAMELENGTH];
	if (argc == 4){ 			// number of arguments on command line must be four
		sscanf(argv[3], "%s", lstname); // user inputs a last name
	}
	else{
		printf("wrong number of arguments\n");
		return (0);
	}

	//finds correct record based on last name inputed
	binsearch (lstname, numrecords-1, pSRecords);

	return EXIT_SUCCESS;
}

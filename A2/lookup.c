/*
 ============================================================================
 Assignment 2 - Pointers, Sorting and Searching

 Name            : Carlo D'Angelo
 Student ID      : 260803454
 Class			 : ECSE 202 – Introduction to Software Development
 ============================================================================
 This program assumes that
 a names and IDs data file (location and name) is passed as the first argument
 a marks data file is passed as the second argument
 a last name is passed as the third argument
*/

#define MAXRECORDS 100
#define MAXNAMELENGTH 15
#include <stdio.h>
#include <stdlib.h>
#include <string.h> //For strcmp() function - need it to compare strings

//Define structure to hold student data
struct StudentRecord
{
	char FirstNames[MAXNAMELENGTH];
	char LastNames[MAXNAMELENGTH];
	int IDNums;
	int Marks;
};

//Swap function - will be called in qksort() function
void swap(int indx1,int indx2,struct StudentRecord *SRecords[]){
	struct StudentRecord *temp;
	temp = SRecords[indx1];
	SRecords[indx1] = SRecords[indx2];
	SRecords[indx2] = temp;
}

//Quick sort function - will be called in main() function
void qksort(int left,int right,struct StudentRecord *SRecords[]){
	unsigned int i,mid;
	struct StudentRecord *pivot;
	if (left >= right){
		return;
		}
	swap(left, (left+right)/2, SRecords);
	pivot = SRecords[mid = left];
	for(i = left + 1; i <= right; i++){
		if (strcmp(SRecords[i]->LastNames,pivot->LastNames)<0){ //compares last names
			swap(++mid, i, SRecords);
			}
		}
	swap(left, mid, SRecords);
	if(mid > left){
		qksort(left , mid-1, SRecords); //recursion
		}
	if(mid < right){
		qksort(mid+1, right, SRecords) ; //recursion
		}
}

//binary search function - will be called in main() function
int * binsearch(char lastname[],int arraylength,struct StudentRecord *SRecords[]){
	unsigned int Left=0, Right=arraylength, Mdl;
	while(Left < Right){
		Mdl = ((Left + Right-1)/2);
		if (strcmp(lastname,SRecords[Mdl]->LastNames) == 0){ //case when last names are the same
			//print all the information in the corresponding record
			printf("The following record was found:\n");
			printf("Name: %s %s\n",SRecords[Mdl]->FirstNames,SRecords[Mdl]->LastNames);
			printf("Student ID: %d\n",SRecords[Mdl]->IDNums);
			printf("Student Grade: %d",SRecords[Mdl]->Marks);
			return NULL;
		}
		else if(strcmp(lastname,SRecords[Mdl]->LastNames)<0){
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

	//Creating an array of pointers - points to the student data records
	struct StudentRecord *pSRecords[MAXRECORDS];

	//Creating an array of student data records
	struct StudentRecord SRecords[MAXRECORDS];
    	int numrecords, nummarks;


	//Read in Names and ID data
	FILE * NamesIDsDataFile;
	if((NamesIDsDataFile = fopen(argv[1], "r")) == NULL){
		printf("Can't read from file %s\n", argv[1]);
		exit(1);
	}

	numrecords=0;
    	while (fscanf(NamesIDsDataFile,"%s%s%d",&(SRecords[numrecords].FirstNames[0]),
		      				&(SRecords[numrecords].LastNames[0]),
		      				&(SRecords[numrecords].IDNums)) != EOF) {
	  numrecords++;
 	}

	fclose(NamesIDsDataFile);

	//Read in marks data
	FILE * MarksDataFile;
	if((MarksDataFile = fopen(argv[2], "r")) == NULL){
		printf("Can't read from file %s\n", argv[2]);
		exit(1);
	}
	nummarks=0;
	while(fscanf(MarksDataFile,"%d",&(SRecords[nummarks].Marks)) != EOF) {
	    nummarks++;
	}

	fclose(MarksDataFile);

	//Links pointers in pointer array to their corresponding record in record array
	int i;
	for (i = 0; i < numrecords; i++) {
		pSRecords[i] = &SRecords[i];
	}

	//sorts array of pointers - allows binary search function to work
	qksort(0, numrecords-1, pSRecords);

	//user inputs a last name to find that person's corresponding record
	char lstname[MAXNAMELENGTH];
	if (argc == 4){ 			//number of arguments on command line must be four
		sscanf(argv[3], "%s", lstname); //user inputs a last name
	}
	else{
		printf("wrong number of arguments\n");
		return (0);
	}

	//finds correct record based on last name inputed
	binsearch(lstname, numrecords-1, pSRecords);

	return EXIT_SUCCESS;
}

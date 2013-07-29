/*
 * File: RandomWriter.cpp
 * ----------------------
 * Name: Luke Englund
 * This file is the starter project for the random writer problem
 * on Assignment #2.
 * [TODO: extend the documentation]
 */

#include "genlib.h";
#include "simpio.h";
#include "random.h";
#include "vector.h";
#include "map.h";
#include <iostream>;
#include <fstream>;

/*  Function Declaration */
void GetMarkov (int & Markov);
void GetFile (ifstream & inFile);
void GetMarkovData (ifstream & inFile, int & Markov, Map<Vector<char> > & MarkovData);
void PrintRandomSentances(int & MarkovNum, Map<Vector<char> > & MarkovData);
void GetMostFrequentSeed(string & seed, Map<Vector<char> > & MarkovData);

/*  Constants */
const int NUM_CHARS = 5000;

/*
 *  Function:	GetMostFrequentSeed
 *  Usage:		void GetMostFrequentSeed(string & seed, Map<Vector<char> > & MarkovData)
 *  ------------------------------------------------------------------------------------
 *  Sifts through the Markov Data and determines which seed occured most frequently.
 */
void GetMostFrequentSeed(string & seed, Map<Vector<char> > & MarkovData) {
	
	cout << "Got the seed!" << endl;
	
	
	Map<Vector<char> >::Iterator itr = MarkovData.iterator();
	
	int frequency = 0;
	while (itr.hasNext()) {
		string key = itr.next();
		
		Vector<char> temp = MarkovData[key];
		if (temp.size() >= frequency) {
			frequency = temp.size();
			seed = key;
		}
	}
}


/*
 *  Function:	PrintRandomSentances
 *  Usage:		PrintRandomSentances (int & Markov, Map<Vector<char> > & MarkovData)
 *  --------------------------------------------------------------------------------
 *	Takes in the Markov Data given in the map and generates random sentances.
 */
void PrintRandomSentances(int & MarkovNum, Map<Vector<char> > & MarkovData) {
	
	string seed;
	
	GetMostFrequentSeed(seed, MarkovData);
	
	cout << "I'm gonna Write some fucking sentances";
	
	if (MarkovNum == 0) {
		
		for (int i = 0; i <  NUM_CHARS; i++) {
			int rand = RandomInteger(32, 125);
			char totRand = rand;
			cout << totRand;
		}
	} else {
		for (int i = 0; i < NUM_CHARS; i++) {
			
			Vector<char> next = MarkovData[seed];
			int rand = RandomInteger(0, next.size() - 1);
			
			cout << next[rand];
			
			seed = seed.substr(1) + next[rand];
		}
	}
}



/*
 *  Function:	GetMarkovData
 *  Usage:		GetMarkovData (ifstream & inFile, int Markov, Map<Vector<char> > MarkovData)
 *  ----------------------------------------------------------------------------------------
 *  Reads the file inFile and records Markov data of order Markov and stores it in the map MarkovData.
 */
void GetMarkovData (ifstream & inFile, int & MarkovNum, Map<Vector<char> > & MarkovData) {
	
	cout << "Got the data!" << endl;
	
	string seed = "";
	
	for (int i = 0; i < MarkovNum; i++) {
		if (inFile.peek() == EOF) break;
		
		seed += inFile.get();
	}
	
	char nextChar;
	
	while (inFile.peek() != EOF) {
		
		if (MarkovNum == 0) break;
				
		nextChar = inFile.get();
		if (MarkovData.containsKey(seed)) {
			Vector<char> next = MarkovData[seed];
			next.add(nextChar);
			MarkovData[seed] = next;
		} else {
			Vector<char> next;
			next.add(nextChar);
			MarkovData.add(seed, next);
		}
		
		seed = seed.substr(1) + nextChar;
	}
}

/*
 *  Function:	GetFile
 *  Usage:		GetFile (ifstream inFile)
 *  ----------------------------------
 *  Propts the user to input a file and attempts to open it.  If the file is unopenable then the user is reprompted.
 */
void GetFile (ifstream & inFile) {
	
	cout << "Got the file!" << endl;
		
	while (true) {
		cout << "Enter File Name: ";
		string fileName = GetLine();
		inFile.open(fileName.c_str());
		
		if (!inFile.fail()) break;
		
		inFile.clear();
		cout << "Invalid file name." << endl;
	}
}

/*
 *  Function:	GetMarkov
 *  Usage:		GetMarkov (int Markov)
 *  ----------------------------------
 *  Propts the user to enter a Markov integer.  Must be an integer between 0 and 10.
 */
void GetMarkov (int & MarkovNum) {
	
	cout << "Got the number!" << endl;
	
	while (true) {
		cout << "Please Enter Markov Number: ";
		MarkovNum = GetInteger();
		
		if ( (MarkovNum >= 0) && (MarkovNum <= 10) ) break;
		
		cout << "Invalid Number." << endl;
	}
}

/////////////////////////////////////////////////////////////////////

int main() {
	
	Randomize();

	int MarkovNum;
	GetMarkov (MarkovNum);
	
	ifstream inFile;
	GetFile (inFile);
	
	Map<Vector<char> > MarkovData;
	GetMarkovData (inFile, MarkovNum, MarkovData);
	
	PrintRandomSentances(MarkovNum, MarkovData);
		
	return 0;
}

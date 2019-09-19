#include <iostream>
#include<fstream>
#include<string>
#include <sstream>
using namespace std;


int main(int argc, char** argv)
{

	ifstream input1;
	ifstream input2;
	ofstream output;
	output.open(argv[3]);
	input1.open(argv[1]);
	input2.open(argv[2]);
	int data1;
	int data2;
	//1 empty file
	if (!(input1 >> data1)) {
		while (input2 >> data2) {
			output << data2 << endl;

		}
		input1.close();
		input2.close();
		output.close();
		return 0;
	
	}
	input1.close();
	input1.open(argv[1]);
	// 1 empty file
	if (!(input2 >> data2)) {
		while (input1 >> data1) {
			output << data1 << endl;

		}
		input1.close();
		input2.close();
		output.close();
		return 0;

	}
	input1.close();
	input2.close();
	input1.open(argv[1]);
	input2.open(argv[2]);

	// both empty
	if (!((input1 >> data1) || (input2 >> data2))) {
		input1.close();
		input2.close();
		output.close();
		return 0;
	
	}



	input1.close();
	input2.close();
	input1.open(argv[1]);
	input2.open(argv[2]);

	input1 >> data1;
	input2 >> data2;

	//cout << data1 << endl;
	//cout << data2 << endl;
	bool one = false;
	bool two = false;

	while (true) {
		if (data1 < data2) {
			output << data1 << endl;
			if (!(input1 >> data1)) {
				one = true;
				break;
			}
		
		}
		else {
			output << data2 << endl;
			if (!(input2 >> data2)) {
				//cout << "2 end" << endl;
				two = true;
				break;
			}
		}
	}

	if (one) {
		output << data2 << endl;
	}

	if (two) {
		output << data1 << endl;
	}

	//remainging case
	while (input1 >> data1) {
		output << data1 << endl;
	}
	while (input2 >> data2) {
		output << data2 << endl;
	}
	input1.close();
	input2.close();
	output.close();
	return 0;
	

    return 0;
}


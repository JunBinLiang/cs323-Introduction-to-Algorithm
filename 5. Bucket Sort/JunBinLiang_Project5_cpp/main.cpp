#include <iostream>
#include<fstream>
#include<string>
#include <sstream>
using namespace std;

int main(int argc, char** argv)
{
	ifstream input;
	ofstream output;
	ofstream debug;
	input.open(argv[1]);
	output.open(argv[2]);
	debug.open(argv[3]);
	int size = 0;
	int data;

	if (!(input >> data)) {
		cout << "Empty file" << endl;
		return 0;
	}

	input.close();
	input.open(argv[1]);
	int* bucket;

	while (input >> data) 
	{
		if (size < data) 
		{
			size = data;
		}
	} // detemine the size
	//cout << size << endl;
	input.close();
	input.open(argv[1]);
	bucket = new int[size + 1];
	for (int i = 0; i < size + 1; i++) 
	{
		bucket[i] = 0;
	}
	
	while (input >> data) {
		bucket[data]++;
		for (int i = 0; i < size + 1; i++)
		{
			debug << i << " ";
		}
		debug << endl;
		for (int i = 0; i < size + 1; i++)
		{
			debug << bucket[i] << " ";
		}
		debug << endl<<endl;
	}

	for (int i = 0; i < size + 1; i++) {
	
		int times = bucket[i];
		for (int j = 0; j < times; j++) {
			output << i << endl;
		}
	}

	output.close();
	debug.close();
	input.close();



    return 0;
}
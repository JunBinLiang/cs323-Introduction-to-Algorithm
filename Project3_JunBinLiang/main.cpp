#include <iostream>
using namespace std;


class VNT {
	private:
		int capacity;
		int size;
		int **table;
		int row;
		int col;
		int nextCol = 0;
		int nextRow = 0;
		int lastrow = 0;
		int lastcol = 0;
		const int MAX = 2147483647;

	public:
		VNT(int row, int col) {
			if (row <= 0 || col <= 0) {
				cout << "Row and Col must larger than 0" << endl;
				return;
			}
			this->capacity = row * col;
			this->size = 0;
			this->row = row;
			this->col = col;
			table = new int*[row];
			for (int i = 0; i < row; i++) {
				table[i] = new int[col];
			}

			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					table[i][j] = MAX;
				}
			}
			cout << "Construc the empty table" << endl;
			print();
		}

		
		/*
		example
		[
			[1, 4, 7, 11, 15],
			[2, 5, 8, 12, 19],
			[3, 6, 9, 16, 22],
			[10, 13, 14, 17, 24],
			[18, 21, 23, 26, 30]
		]
		*/

		void add(int num) {
			if (this->size >= capacity) {
				cout << "The VNT is full" << endl;
				return;
			}
			this->size++;
			cout << "Add " << num << " " << endl;
			if (nextCol == col) {
				this->nextCol = 0;
				this->nextRow++;
			}

			table[nextRow][nextCol] = num;
			this->lastrow = this->nextRow;
			this->lastcol = this->nextCol;
			this->nextCol++;
			int currentCol = this->nextCol - 1;

			//dealing only one with only one row
			if (this->nextRow == 0) {
				for (int i = currentCol; i > 0; i--) {
					if (table[0][i] > table[0][i - 1]) {
						return;
					}
					int temp = table[0][i];
					table[0][i] = table[0][i - 1];
					table[0][i - 1] = temp;
				}
				return;
			}

			//dealing when the col=0
			if (currentCol == 0) {
				for (int i = this->nextRow; i > 0; i--) {
					if (table[i][0] > table[i-1][0]) {
						return;
					}
					int temp = table[i][0];
					table[i][0] = table[i-1][0];
					table[i-1][0] = temp;
				}
				return;
			}

			// The most challenge part :  Dealing   nextRow!=0 && col!=0
			if (table[this->nextRow][currentCol] > table[this->nextRow - 1][currentCol] && table[this->nextRow][currentCol] > table[this->nextRow][currentCol-1]) {
				return;
			}// correct position

			int r = this->nextRow;
			int c = currentCol;
			
			while (r >= 0 && c >= 0)
			{
				if (r == 0 && c == 0) {
					return; // the min position
				}

				if (r == 0) {
					for (int i = c; i > 0; i--) {
						if (table[0][i] > table[0][i - 1]) {
							return;
						}
						int temp = table[0][i];
						table[0][i] = table[0][i - 1];
						table[0][i - 1] = temp;
					}
					return;
				}

				if (c == 0) {
					for (int i = r; i > 0; i--) {
						if (table[i][0] > table[i - 1][0]) {
							return;
						}
						int temp = table[i][0];
						table[i][0] = table[i - 1][0];
						table[i - 1][0] = temp;
					}
					return;
				}


				int left = table[r][c - 1];
				int up = table[r - 1][c];
				int current = table[r][c];

				if (table[r][c] >= left && table[r][c] >= up) {
					return; //safe position
				}

				if (current > up && current < left)
				{
					table[r][c - 1] = current;
					table[r][c] = left;
					r = r;
					c = c - 1;
					continue;
				}
				if (current<up&&current>left) 
				{
					table[r][c] = up;
					table[r - 1][c] = current;
					r = r - 1;
					c = c;
					continue;
				}
				if (current < up&&current < left)
				{
					if (up >= left) {
						table[r - 1][c] = current;
						table[r ][c] = up;
						r = r - 1;
						c = c;
						continue;
					}
					if (left >= up) {
						table[r ][c-1] = current;
						table[r][c ] = left;
						c = c - 1;
						r = r;
						continue;
					}
				
	
				}

			}
		}


		int getMin() {
			if (this->size == 0) {
				cout << "No more to remove" <<endl;
				return -1;
			}
			int min = table[0][0];
	
			table[0][0] = table[this->nextRow][this->nextCol - 1];
			table[this->nextRow][this->nextCol - 1] = MAX;
			int r = 0;
			int c = 0;
			this->size--;
			this->nextCol--;
			
			if (this->nextCol == 0) {
				this->nextCol = col;
				this->nextRow--;
			}
			if (this->row == 1) {//only 1 row
				for (int i = 0; i < this->size-1; i++) {
					if (table[0][i] < table[0][i + 1]) {
						break;
					}
					int temp = table[0][i];
					table[0][i] = table[0][i + 1];
					table[0][i + 1] = temp;
				}
				return min;
			}
			// only 1 column
			if (this->col == 1) {
				for (int i = 0; i < this->size - 1; i++) {
					if (table[i][0] < table[i + 1][0]) {
						break;
					}
					table[i][0] = table[i + 1][0];
				}
				return min;
			}


			//at least 2 col and 2 row
			//cout << "hhhh" << this->nextCol << endl;
			while (r <= this->nextRow&&c < this->nextCol) {
				if (r == this->nextRow) {
					for (int i = c; i < this->nextCol - 1; i++) {
						if (table[r][i] < table[r][i + 1]) {
							break;
						}
						int temp = table[r][i];
						table[r][i] = table[r][i + 1];
						table[r][i + 1] = temp;
					}
					break;
				}

				int current = table[r][c];
				int down = table[r + 1][c];
				int right = table[r][c + 1];
				
				if (c == this->nextCol-1&&r==this->nextRow-1) {
					for (int i = c; i < this->col - 1; i++) {
						if (table[r][i] < table[r][i + 1]) {
							break;
						}
						int temp = table[r][i];
						table[r][i] = table[r][i + 1];
						table[r][i + 1] = temp;
					}
					break;
				}


				if (c == this->col - 1) {
					//break;
				}

				////////////////////////////////////////////
				if (current <= right && current <= down) {
					break;
				}
				if (right > down) {
					table[r][c] = down;
					table[r + 1][c] = current;
					r = r + 1;
					c = c;
					continue;
				}
				if (down > right) {
					table[r][c] = right;
					table[r][c + 1] = current;
					c = c + 1;
					r = r;
					continue;
				}
			}
			return min;
		}

		
		void sort(int k[], int size) {
			VNT temp(1, size);
			for (int i = 0; i<size; i++)
				temp.add(k[i]);
			for (int i = 0; i<size; i++)
				k[i] = temp.getMin();
		}

		bool find(int num) 
		{
			if (row <= 0||col<=0) {
				return false;
			}
			int r = this->row;
			int c = this->col;

			while (r >= 0 && c < col) {
				if (table[r][c] == num) {
					return true;
				}
				else if (table[r][c] > num) {
					r--;
				}
				else {
					c++;
				}
			}
			return false;
		
		}


		void print() {
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					cout << table[i][j] << " ";
				}
				cout << endl;
			
			}
		
		}



};

int main()
{
	VNT A(5, 7);
	A.add(3); A.add(4); A.add(5); A.add(1); A.add(2); A.add(13); A.add(10);
	A.add(-4);
	cout << "Matrix is right now is" << endl;
	A.print();
	A.add(10); A.add(20); A.add(30);
	cout << "Matrix is right now is" << endl;
	A.print();
	A.add(-7);
	cout << "Matrix is right now is" << endl;
	A.print();
	A.add(70);
	A.add(60); 
	A.add(-10); A.add(-15); A.add(50); A.add(7); A.add(23);
	cout << "Matrix is right now is" << endl;
	A.print();
	cout << endl << endl << endl;


	cout << "Removing Right Now" << endl;
	cout << A.getMin() << endl;
	A.print();
	cout << A.getMin() << endl;
	A.print();
	cout << A.getMin() << endl;
	A.print();
	cout << "Removing Right Now" << endl;
	cout << A.getMin() << endl;
	cout << A.getMin() << endl;
	cout << A.getMin() << endl;
	cout << A.getMin() << endl;
	cout << A.getMin() << endl;
	cout << A.getMin() << endl;
	cout << A.getMin() << endl;
	cout << "The remaining matrix is" << endl << endl;
	A.print();
	cout << "Keep Remocing" << endl;
	cout << A.getMin() << endl;
	cout << A.getMin() << endl;
	cout << A.getMin() << endl;
	cout << A.getMin() << endl;
	cout << A.getMin() << endl;
	cout << A.getMin() << endl;
	cout << A.getMin() << endl;
	cout << A.getMin() << endl;
	cout << A.getMin() << endl;

	cout << A.getMin() << endl;
	cout << A.getMin() << endl;

	cout<<endl<<endl;
	
	int arr[10] = { 10,9,8,6,5,4,1,2,3,0 };
	cout << "Array to sort is" << endl;
	for (int i = 0; i < 10; i++) {
		cout << arr[i] << "  ";
	}
	cout << endl;
	A.sort(arr, 10);
	cout << "After sort is" << endl;
	for (int i = 0; i < 10; i++) {
		cout << arr[i] << "  ";
	}
	cout << endl;


    return 0;
}

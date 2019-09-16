#include <iostream>
#include<fstream>
#include<string>
#include <sstream>
using namespace std;

class ListNode {
public:
	int pro;
	ListNode* next = NULL;
	string myChar;
	ListNode* left = NULL;
	ListNode* right = NULL;

	ListNode() {
		//The default constructor for not initializing any field
		this->next = NULL;

	}

	ListNode(string myChar, int pro) {
		this->myChar = myChar;
		this->pro = pro;
		this->next = NULL;
	}

};



class LinkedList {
	private:
		
		ListNode* findSpot(ListNode *&newNode) {
			ListNode* current = head;
			while (current->next != NULL) {
				if ((newNode->pro) < (current->next->pro)) {
					break;
				}
				current = current->next;
			}
			return current;

		}

	public:
		ListNode * head;
		LinkedList() {//constructor for creating a dummy head
			head = new ListNode();
		}

		void listInsert(ListNode*& newNode) {
			ListNode* spot = findSpot(newNode);
			newNode->next = spot->next;
			spot->next = newNode;

		}

		string printList() { //the print function
			string result = "listHead->";
			ListNode* current = head->next;
			if (current != NULL) {
				string myChar = current->myChar;
				result = result + "('Dummy',0," + "'" + myChar + "'" + ")->";
			}
			else {
				result = result + "('Dummy','0','NULL')->NULL";
				return result;
			}
			while (current->next != NULL) {
				string newChar = current->myChar;
				int pro = current->pro;
				ostringstream s;
				s << pro;
				string newPro(s.str());
				string nextChar = current->next->myChar;
				result = result + "(" + "'" + newChar + "'," + newPro + ",'" + nextChar + ")->";
				current = current->next;
			}
			string newChar = current->myChar;
			int pro = current->pro;
			ostringstream s;
			s << pro;
			string newPro(s.str());
			result = result + "(" + "'" + newChar + "'," + newPro + ",'" + "'NULL'" + ")->";
			result = result + "NULL";
			cout << result << endl;
			//write to file
			return result;

		}

		~LinkedList() { //destructor for memory release
			while (head != NULL) {
				ListNode* old = head;
				head = head->next;
				delete old;
			}

		}


};


class HuffmanBinTree {
	LinkedList *list;
	ListNode *treeRoot = NULL;
	ifstream input;
	ofstream output1;
	ofstream output2;
	ofstream output3;

	void postOrderDelete(ListNode* root) {

		if (root == NULL) {
			return;
		}
		postOrderDelete(root->left);
		postOrderDelete(root->right);
		delete root;
	
	}

	void preOrderHelper(ListNode *root)
	{
		if (root == NULL) {
			return;
		}
		cout << root->myChar << "  " << root->pro << endl;
		preOrderHelper(root->left);
		preOrderHelper(root->right);

	}

	void inOrderHelper(ListNode *root)
	{
		if (root == NULL) {
			return;
		}
		inOrderHelper(root->left);
		cout << root->myChar << "  " << root->pro << endl;
		inOrderHelper(root->right);


	}


	void postOrderHelper(ListNode *root) {
		if (root == NULL) {
			return;
		}
		postOrderHelper(root->left);
		postOrderHelper(root->right);
		cout << root->myChar << "  " << root->pro << endl;


	}
	

	public:
		HuffmanBinTree() { // there should be 4 string to be the parameter for indicating the fileName 
			list = new LinkedList();
			this->input.open("input.txt");
		}

		void constructHuffmanList()
		{

			while (!((this->input).eof()))
			{

				string chr;
				int pro;
				input >> chr;
				input >> pro;
				if (input.eof()) break;
				ListNode* node = new ListNode(chr, pro);
				list->listInsert(node);//insert, then prints
				list->printList();
			}

		}

		void constructHuffmanBinTree() 
		{
			this->constructHuffmanList(); //construct the linkedlist first
			cout << endl << "Construct Tree" << endl<<endl;
			while (list->head->next != NULL&&list->head->next->next!=NULL) {
				int sum = list->head->next->pro + list->head->next->next->pro;
				string concatenation= list->head->next->myChar + list->head->next->next->myChar;
				ListNode* node = new ListNode(concatenation,sum);
				node->left = list->head->next;
				node->right = list->head->next->next;
				list->listInsert(node);
				list->head->next = list->head->next->next->next;
				list->printList();
				
			
			}
			this->treeRoot = list->head->next;
		
		}


		void preOrder() {
			preOrderHelper(treeRoot);
		}
		

		void inOrder() {
			inOrderHelper(treeRoot);
		}

		void postOrder() {
			postOrderHelper(treeRoot);
		}

		bool isLeaf(ListNode* root) {
			if (root == NULL) {
				return false;
			}
			if (root->left == NULL && root->right == NULL) {
				return true;
			}
			return false;
		}

		
		~HuffmanBinTree() { //Destructor
			input.close();
			postOrderDelete(treeRoot);
			//output1.close();
			//output2.close();
			//output3.close();
		}

};



int main()
{
	//cout<<argv[1]<<"dd"<<endl;
	//cout<<argv[2]<<"dd"<<endl;
	//ofstream output1;
	//ifstream input;
	//input.open("input.txt");
	//input.open(argv[1]);
	//output1.open(argv[2]);


	//string result = list.printList();
	//input.close();
	HuffmanBinTree tree;
	tree.constructHuffmanBinTree();
	tree.preOrder();
	cout << endl;
	tree.postOrder();
	cout << endl;
	tree.inOrder();
	
	//tree.constructLinkedList();
	

	return 0;
}

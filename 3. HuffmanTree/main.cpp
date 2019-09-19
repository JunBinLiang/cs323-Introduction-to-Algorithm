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
	string code = "";

	ListNode() {
		//The default constructor for not initializing any field
		this->next = NULL;

	}

	ListNode(string myChar, int pro) {
		this->myChar = myChar;
		this->pro = pro;
		this->next = NULL;
	}

	string toString() {
		string result = "";
		ostringstream s;
		s << pro;
		string newPro(s.str());
		
		string leftS = "NULL";
		string rightS = "NULL";
		string nextS = "NULL";
		if (left != NULL) {
			leftS = left->myChar;
		}
		if (right != NULL) {
			rightS = right->myChar;
		}
		if (next != NULL) {
			nextS = next->myChar;
		}
		result = result + myChar + " " + newPro + " "+nextS+" " + leftS + " " + rightS + " " ;
		return result;
	
	}


};



class LinkedList {
	private:
		
		ListNode* findSpot(ListNode *&newNode) {
			ListNode* current = head;
			while (current->next != NULL) {
				if ((current->next->pro)<(newNode->pro) ) {
					current = current->next;
					continue;
				}
				break;
				
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
			//cout << result << endl;
			//write to file
			return result;

		}

		~LinkedList() { //destructor for memory release
			//while (head != NULL) {
				//ListNode* old = head;
				//head = head->next;
				//delete old;
			//}

		}


};


class HuffmanBinTree {
	LinkedList *list;
	ListNode *treeRoot = NULL;
	ifstream input;
	ofstream output1;
	ofstream output2;
	ofstream output3;

	void postOrderDelete(ListNode* root) 
	{		//used by destructor

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
		output2 << root->toString() << endl;
		preOrderHelper(root->left);
		preOrderHelper(root->right);

	}

	void inOrderHelper(ListNode *root)
	{
		if (root == NULL) {
			return;
		}
		inOrderHelper(root->left);
		output2 << root->toString() << endl;
		inOrderHelper(root->right);


	}


	void postOrderHelper(ListNode *root) {
		if (root == NULL) {
			return;
		}
		postOrderHelper(root->left);
		postOrderHelper(root->right);
		output2 << root->toString() << endl;


	}

	void constructCodeHelper(ListNode *root,string code) {
		if (this->isLeaf(root)) {
			root->code = code;
			output1 << root->myChar << " " << root->code << endl;
			//cout << root->myChar << " " << root->code << endl;
			return;
		}
		root->code = code;
		constructCodeHelper(root->left, root->code+"0");
		constructCodeHelper(root->right, root->code+"1");
	}
	

	public:
		HuffmanBinTree(string s1,string s2,string s3,string s4) { // there should be 4 string to be the parameter for indicating the fileName 
			list = new LinkedList();
			this->input.open(s1);
			this->output1.open(s2);
			this->output2.open(s3);
			this->output3.open(s4);
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
				output3<<list->printList()<<endl;
			}
			output3 << endl;

		}

		void constructHuffmanBinTree() 
		{
			this->constructHuffmanList(); //construct the linkedlist first
			//cout << endl << "Construct Tree" << endl<<endl;
			while (list->head->next != NULL&&list->head->next->next!=NULL) {
				int sum = list->head->next->pro + list->head->next->next->pro;
				string concatenation= list->head->next->myChar + list->head->next->next->myChar;
				ListNode* node = new ListNode(concatenation,sum);
				node->left = list->head->next;
				node->right = list->head->next->next;
				list->listInsert(node);
				list->head->next = list->head->next->next->next;
				output3<<list->printList()<<endl;
				
			
			}
			output3 << endl;
			this->treeRoot = list->head->next;
		
		}


		void preOrder() {
			output2 << "preOrder Traversal" << endl;
			preOrderHelper(treeRoot);
			output2 << endl;
		}
		

		void inOrder() {
			output2 << "inOrder Traversal" << endl;
			inOrderHelper(treeRoot);
			output2 << endl;
		}

		void postOrder() {
			output2 << "postOrder Traversal" << endl;
			postOrderHelper(treeRoot);
			output2 << endl;
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
		void constructCharCode() {
			constructCodeHelper(treeRoot,"");
		}
		
		~HuffmanBinTree() { //Destructor
			input.close();
			postOrderDelete(treeRoot);
			output1.close();
			output2.close();
			output3.close();
			delete list;
		}

};



int main(int argc, char** argv)
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
	HuffmanBinTree tree(argv[1],argv[2],argv[3],argv[4]);
	tree.constructHuffmanBinTree();
	tree.preOrder();
	//cout << endl;
	tree.inOrder();
	//cout << endl;
	tree.postOrder();
	//cout << endl;
	tree.constructCharCode();
	
	//tree.constructLinkedList();
	

	return 0;
}
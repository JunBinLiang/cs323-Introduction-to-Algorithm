import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class ListNode{
	public int frequency;
	public ListNode next=null;
	public ListNode left=null;
	public ListNode right=null;
	public String mychar="";
	public String myCode="";
	
	public ListNode(String mychar,int frequency){
		this.mychar=mychar;
		this.frequency=frequency;
	}
	
	
}

class LinkedList{
	public ListNode dummyhead;
	
	public LinkedList(){
		dummyhead=new ListNode("dummy",0);
	}
	public void insert(ListNode newnode){
		ListNode spot=findSpot(newnode);
		newnode.next=spot.next;
		spot.next=newnode;
	}
	
	public ListNode findSpot(ListNode newnode)
	{
		ListNode current=dummyhead;
		while(current.next!=null){
			if(current.next.frequency<newnode.frequency){
				current=current.next;
				continue;
			}
			break;
		}
		return current;
	}
	
	public String printList()
	{
		String result="Listhead";
		ListNode current=dummyhead;
		while(current.next!=null){
			result+="->("+current.mychar+","+current.frequency+","+current.next.mychar+")";
			current=current.next;
		}
		result+="->("+current.mychar+","+current.frequency+","+"NULL"+")"+"->NULL\n";
		return result;
	}
	
}

class HuffmanTree{
	public LinkedList huffmanList;
	public ListNode treeRoot=null;
	public String charCode[]=new String[256];
	
	public HuffmanTree(LinkedList list){
		this.huffmanList=list;
	}
	
	public void constructHuffmanBinaryTree(){
		while(huffmanList.dummyhead.next!=null&&huffmanList.dummyhead.next.next!=null)
		{
			int sum=huffmanList.dummyhead.next.frequency+huffmanList.dummyhead.next.next.frequency;
			String concatenation=huffmanList.dummyhead.next.mychar+huffmanList.dummyhead.next.next.mychar;
			ListNode newNode=new ListNode(concatenation,sum);
			newNode.left=huffmanList.dummyhead.next;
			newNode.right=huffmanList.dummyhead.next.next;
			huffmanList.insert(newNode);
			huffmanList.dummyhead.next=huffmanList.dummyhead.next.next.next;
		}
		treeRoot=huffmanList.dummyhead.next;
		
	}
	
	public boolean isLeaf(ListNode node){
		if(node==null){
			return false;
		}
		if(node.left==null&&node.right==null){
			return true;
		}
		return false;
	}
	
	public void constructCharCode(ListNode node,String code){
		if(node==null){
			return;
		}
		if(isLeaf(node)){
			node.myCode=code;
			//System.out.println(node.mychar);
			//System.out.println(code);
			int index=(int)(node.mychar.charAt(0));
			charCode[index]=code;
			return;
		}
		constructCharCode(node.left,code+"0");
		constructCharCode(node.right,code+"1");
		
	}
	
	public void preorder(ListNode root, FileWriter debugfile){
		if(root==null){
			return;
		}
		try{
			debugfile.write(root.mychar+" "+root.frequency+" ");
			if(root.next==null){
				debugfile.write(" Null ");
			}else{
				debugfile.write(" "+root.next.mychar+" ");
			}
			if(root.left==null){
				debugfile.write(" Null ");
			}else{
				debugfile.write(" "+root.left.mychar+" ");
			}
			if(root.right==null){
				debugfile.write(" Null ");
			}else{
				debugfile.write(" "+root.right.mychar+" ");
			}
			debugfile.write("\n");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		preorder(root.left,debugfile);
		preorder(root.right,debugfile);
	}
	
	public void inorder(ListNode root, FileWriter debugfile)
	{
		if(root==null){
			return;
		}
		inorder(root.left,debugfile);
	
		try{
			debugfile.write(root.mychar+" "+root.frequency+" ");
			if(root.next==null){
				debugfile.write(" Null ");
			}else{
				debugfile.write(" "+root.next.mychar+" ");
			}
			if(root.left==null){
				debugfile.write(" Null ");
			}else{
				debugfile.write(" "+root.left.mychar+" ");
			}
			if(root.right==null){
				debugfile.write(" Null ");
			}else{
				debugfile.write(" "+root.right.mychar+" ");
			}
			debugfile.write("\n");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		inorder(root.right,debugfile);
	}
	
	public void postorder(ListNode root, FileWriter debugfile)
	{
		if(root==null){
			return;
		}
		postorder(root.left,debugfile);
		postorder(root.right,debugfile);
		try{
			debugfile.write(root.mychar+" "+root.frequency+" ");
			if(root.next==null){
				debugfile.write(" Null ");
			}else{
				debugfile.write(" "+root.next.mychar+" ");
			}
			if(root.left==null){
				debugfile.write(" Null ");
			}else{
				debugfile.write(" "+root.left.mychar+" ");
			}
			if(root.right==null){
				debugfile.write(" Null ");
			}else{
				debugfile.write(" "+root.right.mychar+" ");
			}
			debugfile.write("\n");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}

	
	
}



public class Main {
	 public static void main(String[] args) 
	 {
		 if(args.length<1){
			 System.out.println("2 Argument Required");
			 return;
		 }
		 String input=args[0];
		 int frequencyArray[]=new int[256];
		 File file = new File("./"+input); 
		 FileWriter debugfile;
		 LinkedList huffmanList=new LinkedList();
		  
		 String text="";
		 try{
			 Scanner read = new Scanner(file);
			 while(read.hasNext()){
				 text+=read.next()+" ";
			 }			 
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 //construct the frequency array
		 
		 for(int i=0;i<text.length();i++){
			 int index=(int)text.charAt(i);
			 frequencyArray[index]++;
		 }

		 
		 
		 try{
			 debugfile = new FileWriter("./Debug.txt"); 
			 printCountAry(frequencyArray,debugfile);
			 
			 //construc the huffmanList
			 for(int i=0;i<256;i++){
				 if(frequencyArray[i]>0){
					  char ascii=(char)i;
					  String mychar=""+ascii;
					  int frequency=frequencyArray[i];
					  ListNode newNode=new ListNode(mychar,frequency);
					  huffmanList.insert(newNode);
					  String  printString= huffmanList.printList();
					  debugfile.write(printString);
				 }
			 }
			 //construct the Huffman Binary Tree
			 HuffmanTree huffmanTree=new HuffmanTree(huffmanList);
			 huffmanTree.constructHuffmanBinaryTree();
			 huffmanTree.constructCharCode(huffmanTree.treeRoot,"");
			 
			 String  printString= huffmanList.printList();
			 debugfile.write(printString);
			 debugfile.write("\n");
			 debugfile.write("preorder Traverse \n");
			 debugfile.write("\n");
			 huffmanTree.preorder(huffmanTree.treeRoot,debugfile);
			 debugfile.write("\n");
			 debugfile.write("inorder Traverse \n");
			 debugfile.write("\n");
			 huffmanTree.inorder(huffmanTree.treeRoot,debugfile);
			 debugfile.write("\n");
			 debugfile.write("postorder Traverse \n");
			 debugfile.write("\n");
			 huffmanTree.postorder(huffmanTree.treeRoot,debugfile);
			 FileWriter compress;
			 FileWriter dompress;
			 // user Interface
			 while(true)
			 {
				 System.out.println("Yes or No");
				 Scanner scan = new Scanner(System.in); 
				 String inputString=scan.nextLine();
				 if(inputString.toLowerCase().equals("no")){
					 System.out.println("Program Exit");
					 break;
				 }
				 else if(inputString.toLowerCase().equals("yes")){
					System.out.println("Please Enter the file name, no .txt include, only the filename");
					scan = new Scanner(System.in); 
					String fileName=scan.nextLine();
					try{
						compress=new FileWriter("./"+fileName+"_Compress.txt");
						dompress=new FileWriter("./"+fileName+"_Dompress.txt");
						File file1 = new File("./"+fileName+".txt"); 
						Scanner read = new Scanner(file1);
						String compressText="";
						while(read.hasNext()){
							compressText+=read.next()+" ";
						}		
						//read the file and compress it
						//System.out.println(compressText);
						String bitcode="";
						for(int i=0;i<compressText.length();i++){
							int index=(int)compressText.charAt(i);
							String code=huffmanTree.charCode[index];
							bitcode+=code;
							compress.write(code);
						}

						//decompress the file
						
						ListNode root=huffmanTree.treeRoot;
						//System.out.println(huffmanTree.treeRoot.mychar);
						for(int i=0;i<bitcode.length();i++)
						{
							if(huffmanTree.isLeaf(root)){
								dompress.write(root.mychar);
								//System.out.println(root.mychar);
								root=huffmanTree.treeRoot;
								i--;
								continue;
							}
							if(bitcode.charAt(i)=='1'){
								root=root.right;
								continue;
							}
							if(bitcode.charAt(i)=='0'){
								root=root.left;
								continue;
							}
						}
						
						dompress.close();
						compress.close();
						
					}catch(Exception e){
						e.printStackTrace();
					}
					
					
				 }
				 else{
					 System.out.println("Invalid Input String");
					 continue;
				 } 
			 }
			 debugfile.close();
			  
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		  
	 }
	 
	 public static void printCountAry( int frequencyArray[],FileWriter debugfile)
	 {
		    try {  
                for(int i=0;i<256;i++)
				{
                    if(frequencyArray[i]>0) {
						 char ascii=(char)i;
                    	 debugfile.write(ascii+" "+frequencyArray[i]+'\n');   
                    }  
                } 
            } catch (IOException ex) {
                
            }
		 
	 }

}
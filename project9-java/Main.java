import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


class Node{
	public int jobId;
	public int jobTime;
	public int dependentCount;
	public Node next;
	
	public Node(int jobId, int jobTime, int dependentCount){
		this.jobId=jobId;
		//System.out.println(this.jobId);
		this.jobTime=jobTime;
		this.dependentCount=dependentCount;
		//.next=next;
	}
	
	public String print(){
		String ans="";
		ans=ans+jobId+" \n";
		return ans;
	}
}

class LinkedList{
	public Node dummyhead;
	public int size=0;
	public LinkedList(){
		dummyhead=new Node(-1,-1,-1);
	}
	public void insert(Node newnode){
		size++;
		Node spot=findSpot(newnode);
		newnode.next=spot.next;
		spot.next=newnode;
	}	
	
	public Node findSpot(Node newnode)
	{
		Node current=dummyhead;
		while(current.next!=null){
			if(current.next.dependentCount<newnode.dependentCount){
				current=current.next;
				continue;
			}
			break;
		}
		return current;
	}
	
	public Node removeFront(){
		size--;
		Node ans=dummyhead.next;
		dummyhead.next=ans.next;
		return ans;
	}
	
	public void printList(FileWriter debugfile){
		Node current=dummyhead.next;
		while(current!=null)
		{
			//do printint
			String content=current.print();
			//System.out.println(content);
			try{
				debugfile.write(content);
			}catch(Exception e){
				
			}
			current=current.next;
		}
	}
	
}

class Jobs{
	int jobTime=0;
	int onWhichProc=0;
	int onOpen=0;
	int parentCount=0;
	int dependentCount=0;
}

class Processor{
	int whichJob;
	int timeRemain;
	
	public Processor(){
		this.whichJob=0;
		this.timeRemain=0;
	}
}

public class Main {
	 public static void main(String[] args) 
	 {
		 if(args.length<5){
			 System.out.println("5 Argument Required, 2 input+processor+output+debug file");
			 return;
		 }
		 boolean hasCycle=false;
		 LinkedList list=new LinkedList();
		 int totalTime=0;
		 int processorUsed=0;
		 int currentTime=0;
		 int adjMatrix[][]; Jobs jobArray[]; Processor processorArray[]; int parentCountAry[];
		 int dependentCountArray[]; int schedualTalbe[][]; int onGraphArray[];
		 String dependencyInput=args[0];
		 String processingInput=args[1];
		 String output=args[3];
		 String debug=args[4];
		 int numProcessors=Integer.parseInt(args[2]); 
		 int numNodes=0;
		 File file1 = new File("./"+dependencyInput);
		 File file2 = new File("./"+processingInput);
		 FileWriter debugfile=null;
		 FileWriter outputfile=null;
		 if(numProcessors<=0){
			 System.out.println("Need 1 or more processors");
			 return;
		 }
		 try{
			 debugfile = new FileWriter("./"+debug); 
			 outputfile=new FileWriter("./"+output);  
			 Scanner read = new Scanner(file1);
			 numNodes=Integer.parseInt(read.next());
			 if(numProcessors>numNodes){//unlimited processors
				 numProcessors=numNodes;
			 }
			 adjMatrix=new int[numNodes+1][numNodes+1];
			 jobArray=new Jobs[numNodes+1];
			 onGraphArray=new int[numNodes+1];
			 for(int i=0;i<onGraphArray.length;i++){
				 onGraphArray[i]=1;
			 }
			 processorArray=new Processor[numProcessors+1];
			 for(int i=0;i<processorArray.length;i++){
				 processorArray[i]=new Processor();
			 }
			 parentCountAry=new int[numNodes+1];
			 dependentCountArray=new int[numNodes+1];
			 //loadMatrix
			 while(read.hasNext()){
				 int row=Integer.parseInt(read.next());
				 int col=Integer.parseInt(read.next());
				 adjMatrix[row][col]=1;  // row->col
			 }
			 read = new Scanner(file2);
			 //computeParent count
			 for(int nodeId=1;nodeId<=numNodes;nodeId++){
				 int sum=0;
				 for(int i=1;i<=numNodes;i++){
					 sum+=adjMatrix[i][nodeId];
				 }
				 parentCountAry[nodeId]=sum;
				 //System.out.println(sum);
			 }
			 
			 // compute the dependent count
			 for(int nodeId=1;nodeId<=numNodes;nodeId++){
				 int sum=0;
				 for(int i=1;i<=numNodes;i++){
					 sum+=adjMatrix[nodeId][i];
				 }
				 dependentCountArray[nodeId]=sum;
				 //System.out.println(sum);
			 }
			 
			 
			 //totalJobTimes
			 read.next();
			 for(int i=0;i<jobArray.length;i++){
				 jobArray[i]=new Jobs(); //initialize the jobs
			 }
			 while(read.hasNext()){
				 int nodeId=Integer.parseInt(read.next());
				 int jobTime=Integer.parseInt(read.next());
				 totalTime+=jobTime;
				 jobArray[nodeId].jobTime=jobTime;
				 jobArray[nodeId].onWhichProc=-1;
				 jobArray[nodeId].onOpen=0;
				 jobArray[nodeId].parentCount=parentCountAry[nodeId];
				 jobArray[nodeId].dependentCount=dependentCountArray[nodeId];
			 }
			 schedualTalbe=new int[numProcessors+1][totalTime+1];
			 //System.out.println(isGraphEmpty(onGraphArray));
			 while(!isGraphEmpty(onGraphArray))
			 {			 
				
			 // findOrphan()
				 for(int nodeId=1;nodeId<=numNodes;nodeId++)
				 {
					 if(jobArray[nodeId].parentCount<=0&&jobArray[nodeId].onOpen==0){
						 //System.out.println(nodeId);
						 //System.out.println(nodeId);
						 jobArray[nodeId].onOpen=1;
						 Node node=new Node(nodeId,jobArray[nodeId].jobTime,dependentCountArray[nodeId]);
						 list.insert(node);
					 }
				 }
				 list.printList(debugfile);
				 
				 //loadProcAry
				 for(int i=1;i<processorArray.length;i++){
					 if(processorArray[i].timeRemain<=0){ // available processor
						 if(list.size==0){
							 //no job on the list, nothing to remove
							 break;
						 }
						 Node newJob=list.removeFront();
						 processorArray[i].whichJob=newJob.jobId;
						 processorArray[i].timeRemain=newJob.jobTime;
						 //push on schedualTalbe
						 int time=currentTime;
						 int endTime=time+newJob.jobTime;
						 while(time<endTime){
							 schedualTalbe[i][time]=newJob.jobId;
							 time++;
						 }
					 }
				 }
				// System.out.println("after load");
				 
				 //checkcycle shoulb be implemented below
				 if(list.size==0&&!isGraphEmpty(onGraphArray)&&processorFinish(schedualTalbe,currentTime)){
					 System.out.println("Graph Has Cycle");
					 hasCycle=true;
				 }
				 
				 if(hasCycle){
					 System.out.println("There is cycle in the graph");
					 outputfile.write("\n\nHas Cycle");
						try{
							outputfile.close();
							debugfile.close();	 
						 }catch(Exception e){
							System.out.println("Close Fail"); 
						 }
					 return;
				 }
				  //print schedual table
				  printSchedualTable(schedualTalbe,outputfile);
				  currentTime++;
				  //no problem here
				 //update procTime
				 for(int i=1;i<processorArray.length;i++){
					processorArray[i].timeRemain--; 
					// System.out.println(processorArray[i].timeRemain);
				 }
		
		
				 //find done processor
				 for(int i=1;i<processorArray.length;i++)
				 {
					 if(processorArray[i].timeRemain<=0){
						 int whichJobId=processorArray[i].whichJob;
						 if(whichJobId>0){
							 processorArray[i].whichJob=0;
							 onGraphArray[whichJobId]=-1;
							
							//delete edge
							 for(int j=1;j<=numNodes;j++)
							 {
								 if(adjMatrix[whichJobId][j]==1){
									 adjMatrix[whichJobId][j]=0;
									 parentCountAry[j]--; 
									 jobArray[j].parentCount--;
								 }
							 }
						 }
					 }
				 }
						 
				 //System.out.println(isGraphEmpty(onGraphArray));
				 
			}
			printSchedualTable(schedualTalbe,outputfile);
			 
		 }catch(Exception e){
			 
		 }
		 
		 try{
			outputfile.close();
			debugfile.close();	 
		 }catch(Exception e){
			System.out.println("Close Fail"); 
		 }

	 }
	 
	 public static void printSchedualTable(int schedualTalbe[][],FileWriter outputfile){
		 try{
			 int columns=schedualTalbe[0].length;
			 String firstLine="    " ;
			 for(int i=0;i<columns;i++){
				 firstLine=firstLine+i+"|";
			 }
			 firstLine+="\n";
			 outputfile.write(firstLine);
			 for(int row=1;row<schedualTalbe.length;row++){
				 String line="p"+row+": ";
				 for(int col=0;col<schedualTalbe[0].length;col++){
					 line+=schedualTalbe[row][col]+"|";
				 }
				 line+="\n";
				 outputfile.write(line);
			 }	 
			 outputfile.write("\n\n");
		 }catch(Exception e){
			 
		 }
		 
	 }
	 
	 public static boolean processorFinish(int schedualTalbe[][],int time){
		 
		 for(int i=1;i<schedualTalbe.length;i++){
			 if(schedualTalbe[i][time]!=0){
				 return false;
			 }
		 }
		 return true;
		 
	 }
	 
	 
	 
	 public static boolean isGraphEmpty(int onGraphArray[]){
		 for(int i=1;i<=onGraphArray.length;i++){
			 if(onGraphArray[i]==1){
				 return false;
			 }
		 }
		 return true;
	 }


}
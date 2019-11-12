import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


class Point{
	double x;
	double y;
	int label;
	double distance=9999.0;
	
	public Point(double x,double y, int label){
		this.x=x;
		this.y=y;
		this.label=label;		
	}
	public String toString(){
		return x+" "+y+" "+label;
	}
}

class KMean{
	Point pointset[];
	Point centroids[];
	int K=0;
	Point imageArray[][];
	int change=0;
	FileWriter outputfile;
	public KMean(){
	}
	
	public void KMeansCluster(){
		//assign label
		int label=1;
		for(int i=0;i<pointset.length;i++)
		{
			Point p=pointset[i];
			imageArray[(int)p.x][(int)p.y]=p;

		}
		int front=0;
		int back=pointset.length-1;
		
		while(front<=back)
		{
			pointset[front].label=label;
			label++;
			front++;
			if(label>K){
				label=1;
			}
			pointset[back].label=label;
			back--;
			label++;
			if(label>K){
				label=1;
			}
			
		}
		
		
		//assign label
		int ithRun=1;
		while(true)
		{
			this.imageprint(ithRun);
			ithRun++;
			this.computeCentroid();
			for(int i=0;i<pointset.length;i++)
			{
				Point p=pointset[i];
				double mindistance=999999999;
				int minlable=0;
				for(int j=1;j<=K;j++){
					Point singelcentroid=centroids[j];
					if(singelcentroid==null){
						continue;
					}
					double dis=(p.x-singelcentroid.x)*(p.x-singelcentroid.x)+(p.y-singelcentroid.y)*(p.y-singelcentroid.y);
					if(dis<mindistance){
						mindistance=dis;
						minlable=singelcentroid.label;
					}
				}
				if(p.label!=minlable){
					p.label=minlable;
					change++;
				}
			}
			if(change<3){
				break;
			}
			change=0; //set change back
		}

		
	}
	
	public void imageprint(int ithRun){
		try{
			outputfile.write("This is "+ithRun+" run \n");
			for(int row=0;row<imageArray.length;row++){
				for(int col=0;col<imageArray[0].length;col++){
					if(imageArray[row][col]==null){
						outputfile.write("  ");
						continue;
					}
					outputfile.write(imageArray[row][col].label+" ");
				}
				outputfile.write("\n");
			}
		}catch(Exception e){
			 e.printStackTrace();
		}
	}
	
	public void computeCentroid(){
		double sumx[]=new double[K+1];
		double sumy[]=new double[K+1];
		int totalpoints[]=new int[K+1];
		for(int i=0;i<pointset.length;i++){
			Point p=pointset[i];
			sumx[p.label]+=p.x;
			sumy[p.label]+=p.y;
			totalpoints[p.label]++;
		}
		for(int i=1;i<=K;i++){
			if(totalpoints[i]==0){
				centroids[i]=null;
				//System.out.println("ssssssssssssssssssss");
				
			}
			centroids[i]=new Point(sumx[i]/totalpoints[i],sumy[i]/totalpoints[i],i);
			//System.out.println(centroids[i]);
		}
		
	}
	
	public void print(){
		for(int i=0;i<pointset.length;i++){
			System.out.println(pointset[i]);
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
		 
		 
		 System.out.println("Please Enter K");
		 Scanner in = new Scanner(System.in);
         String s = in.nextLine();
		 int K=Integer.parseInt(s);
		 String input=args[0];
		 File file = new File("./"+input);
		 int row=0;
		 int col=0;
		 int numpoints=0;
		 KMean kmean=new KMean();
		 
		 

		 try
		 {
			 Scanner read = new Scanner(file);
			 row=Integer.parseInt(read.next());
			 col=Integer.parseInt(read.next());
			 numpoints=Integer.parseInt(read.next());
			 int index=0;
			
			 kmean.outputfile=new FileWriter("./"+args[1]);
  			 kmean.centroids=new Point[K+1]; 
			 kmean.pointset=new Point[numpoints];  //initialize Point array
			 kmean.K=K;
			 kmean.imageArray=new Point[row][col];
			 //System.out.println(row);
			 //System.out.println(col);
			 //System.out.println(numpoints);
			 
			 while(read.hasNext())
			 {
				 int x=Integer.parseInt(read.next());
				 int y=Integer.parseInt(read.next());
				 Point p=new Point(x,y,0);
				 kmean.pointset[index]=p;
				 index++;
				 
			 }//load points	
			 
			 
			 kmean.KMeansCluster();
			 //kmean.print();
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 try{
			  kmean.outputfile.close();
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		

	 }
	 


}
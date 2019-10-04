import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class Main {
	 public static void main(String[] args) {
		//System.out.println("hi");
		int index;
		int counter=0;
		int min;
		int max;
		int numRow;
		int numCol;
		int startRow=0;
		int startCol=0;
		int greyScale=0;
		int length;
		
		if(args.length<3){
			System.out.println("There must be 3 input, inputFile,encodFile and decodeFile name");
			return;
		}
		String input=args[0];
		String encode=args[1];
		String decodeFile=args[2];
		
		
		
		try {
			 File file = new File("./"+input); 
			 Scanner read = new Scanner(file);
			 //FileWriter fw=new FileWriter("./output.txt");
			 String result="";
			 numRow=Integer.parseInt(read.next());
			 numCol=Integer.parseInt(read.next());
			 min=Integer.parseInt(read.next());
			 max=Integer.parseInt(read.next());
			 length=0;
			 //basic setup
			result+=numRow+" "+numCol+" "+min+" "+max+"\n";		 
			
			 for(int row=0;row<numRow;row++){
				 for(int col=0;col<numCol;col++)
				 {
					 if(col==0){
						 length=1;
						 startCol=col;
						 startRow=row;
						 greyScale=Integer.parseInt(read.next());
						 continue;
					 }
					 int data=Integer.parseInt(read.next());
					 if(data==greyScale){
						 length++;
						 continue;
					 }
					 result+=startRow+" "+startCol+" "+greyScale+" "+length+"\n";
					 startRow=row;
					 startCol=col;
					 greyScale=data;
					 length=1;
				 }
				 result+=startRow+" "+startCol+" "+greyScale+" "+length+"\n";
				 
			 }
			read.close();
			try {  
                FileWriter fw=new FileWriter("./"+encode);
				fw.write(result);
                fw.close();
                
            } catch (IOException ex) {
                //Logger.getLogger(Y23608047.class.getName()).log(Level.SEVERE, null, ex);
            }
			 
		} 
		catch (IOException ex) {
                //Logger.getLogger(Y23608047.class.getName()).log(Level.SEVERE, null, ex);
        
		}
		
		
		String decode="";
		try
		{
			File file = new File("./"+encode); 
			Scanner read = new Scanner(file);
			numRow=Integer.parseInt(read.next());
			numCol=Integer.parseInt(read.next());
			min=Integer.parseInt(read.next());
			max=Integer.parseInt(read.next());
			counter=0;
			decode+=numRow+" "+numCol+" "+min+" "+max+"\n";
			while(read.hasNext()){
				startRow=Integer.parseInt(read.next());
				startCol=Integer.parseInt(read.next());
				greyScale=Integer.parseInt(read.next());
				length=Integer.parseInt(read.next());
				for(int i=0;i<length;i++){
					decode=decode+greyScale+" ";
					counter++;
					if(counter%numCol==0){
						decode+="\n";
					}
				}

			}
			

			
		}
		catch(Exception e){
			
		}
		
		
		
		try {  
                FileWriter fw=new FileWriter("./"+decodeFile);
				fw.write(decode);
                fw.close();
                
        } catch (IOException ex) {
                //Logger.getLogger(Y23608047.class.getName()).log(Level.SEVERE, null, ex);
        }

		  
		//Below is decoding method
		  
	 }
}
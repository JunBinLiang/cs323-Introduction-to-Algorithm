


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String [ ] args)
	{
		
		 if(args.length<2){
			 System.out.println("2 Argument Required");
			 return;
		 }

		 String input=args[0];
		 String output=args[1];
		 File file = new File("./"+input); 
         String text="";
         int frequencyArray[]=new int[256];
         for(int i=0;i<256;i++) {
        	 frequencyArray[i]=0;
        	 //System.out.println(frequencyArray[i]);
         }
         
			try {
				Scanner read = new Scanner(file);
	              while(read.hasNext())
	              {         
	            	  text+=read.next();
	                  //System.out.println(read.next());
	                  
	              }
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				//System.out.println("error");
				e.printStackTrace();
			}
			
			//System.out.println(text);
			for(int i=0;i<text.length();i++) {
				//System.out.println((int)text.charAt(i));
				int index=(int)text.charAt(i);
				if(index<0||index>255) {
					continue;
				}
				frequencyArray[index]++;
			}
			
            try {  
                FileWriter fw=new FileWriter("./"+output);
                for(int i=0;i<256;i++){
                    if(frequencyArray[i]>0) {
                    	char ascii=(char)i;
                    	 fw.write(ascii+" "+frequencyArray[i]+'\n');   
                    }  
                }
                fw.close();
                
            } catch (IOException ex) {
                //Logger.getLogger(Y23608047.class.getName()).log(Level.SEVERE, null, ex);
            }
		 
         
    
	}
}
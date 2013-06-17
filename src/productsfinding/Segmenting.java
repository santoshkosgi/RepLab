package productsfinding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;


public class Segmenting {
	
	
	public static void main(String args[])
	{
		File f;
		BufferedReader br=null;
		BufferedWriter br1;
		BufferedWriter br2;
		BufferedWriter br3;
		String line=null;
		try
		{
			br = new BufferedReader(new FileReader(args[0]));
			br1=new BufferedWriter(new FileWriter(args[1]));
			br2=new BufferedWriter(new FileWriter(args[2]));
			br3=new BufferedWriter(new FileWriter(args[3]));
			while((line=br.readLine())!=null)
					{
                    String[] words=line.split(" ");
                    if(words.length==2)
                    {
                    	br2.write(line);
                    	br2.write("\n");
                    }
                    if(words.length==3)
                    {
                    	br3.write(line);
                    	br3.write("\n");	
                    }
                    if(words.length==1)
                    {
                    	br1.write(line);
                    	br1.write("\n");	
                    }
                    
					}
		br1.close();
		br2.close();
		br3.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

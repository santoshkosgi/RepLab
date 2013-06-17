package IndexSearcher;
import java.io.RandomAccessFile;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class lineoffsetmapping {
	
	
	public static void main(String[] args) {
		BufferedWriter out1;
		File file;
		FileWriter fstream;
		try
		{
			file = new File("/home/sandeep/Dropbox/Replab/28-03-2013/offset.txt");
			fstream = new FileWriter(file.getAbsoluteFile());
			out1 = new BufferedWriter(fstream);
		RandomAccessFile raf = new RandomAccessFile("/home/sandeep/Dropbox/Replab/28-03-2013/index/index.txt","r");
		StringBuffer tmpValue1=new StringBuffer("0");
		for(int x=tmpValue1.length();x<10;x++)
    	{
    				tmpValue1.append(" ");
    	}
		out1.write(tmpValue1.toString());
		out1.write("\n");
	     
	     StringBuffer tmpValue;
	     raf.seek(0);
	     String len;
		while (raf.readLine() != null)
		{
			len=Long.toString(raf.getFilePointer());
			tmpValue=new StringBuffer(len);
			for(int x=len.length();x<10;x++)
        	{
        				tmpValue.append(" ");
        	}
			out1.write(tmpValue.toString());
			out1.write("\n");
			
		}
		out1.close();
		raf.close();
		}
		catch(Exception e)
		{
			
		}

	}

}

package productsfinding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

public class datacleaning {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f;
		BufferedReader br=null;
		BufferedWriter br1;
		String line=null;
		String token=null;
		try
		{
			br = new BufferedReader(new FileReader(args[0]));
			br1=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF8"));
			while((line=br.readLine())!=null)
			{
				token=line.replaceAll("[^\\x00-\\x7F]", "");
				if(token.contains("0;;;;;;;;;;\"") || token.contains("4;;;;;;;;;;\""))
				{
				br1.write(token);
				br1.write("\n");
				}
			}
			br1.close();
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

}

package productsfinding;
import java.io.RandomAccessFile;




public class bsearch {
      
	public static void main(String[] args)
	{
		bsearch s=new bsearch();
		s.search("microsoft");
	}
	public String search (String term) {
		String buff=null;
		String posting=null;
		try
		{
		RandomAccessFile raf = new RandomAccessFile("/home/sandeep/Dropbox/Replab/28-03-2013/index/index.txt","r");
		RandomAccessFile raf1 = new RandomAccessFile("/home/sandeep/Dropbox/Replab/28-03-2013/offset.txt","r");
		int first=1,last,middle;
		boolean match=false;
		int offset=0;
		last=18792;
		raf.seek(0);
		raf1.seek(0);
		while(first<=last)
		{
			middle=first+(last-first)/2;
			if(middle==1)
				raf1.seek(0);
			else
			{
				offset=(middle-1)*11;
				raf1.seek(offset);
			}
			buff=raf1.readLine();
			Long move=Long.parseLong(buff.trim());
			raf.seek(move);
			posting = raf.readLine();
			buff = posting.split("\\{")[0];
			
			
			int compare=buff.compareTo(term);
				
				if(compare==0)
				{
					
					
					
					match=true;
					//System.out.println(buff);
					//System.out.println(posting);
					raf.close();
					return posting;
		
					
				}else if(compare>0)
				{
					
					last=middle-1;
				
				}
				else
				{
					
				  					
					first=middle+1;
				}
			
		}
		if(!match)
		{
			raf.close();
			raf1.close();
			
			return null;
		}
		
		raf.close();
		raf1.close();
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		//return the file offsets in which the string has occured.
		return posting;
	}

}

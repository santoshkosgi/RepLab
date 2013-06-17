package productsfinding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class clusterTweets {
	static ArrayList<HashSet<String>> productOffsets =new ArrayList<HashSet<String>>();
	
	/**
	 * @param args
	 */
	public int cluster_rearrange(int num,HashSet<String> temp_offset)
	{
	 if(num==0)
		 return 1;
	 else
	 {
		 for(int i=num;i>0;i--)
		 {
			System.out.println(i);
			Set<String> temp= new HashSet<String>();
			temp.addAll(productOffsets.get(i-1));
			int size=temp.size();
			System.out.println("original "+size);
			System.out.println("current"+temp_offset.size());
			temp.retainAll(temp_offset);
			System.out.println("inter"+temp.size());
			System.out.println("current"+temp_offset.size());
			if(temp.size()>((70/100.0)*temp_offset.size()))
			{
				System.out.println("its zero");
				return 0;
			}
			
		 }
	 }
	  return 1;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		bsearch s=new bsearch();
		clusterTweets t= new clusterTweets();
		BufferedReader br;
		String posting;
		String[] posting_list;
		int k=0;
		try
		{
			br = new BufferedReader(new FileReader("/home/sandeep/Dropbox/Replab/newrankings/apple/products.txt"));
			RandomAccessFile raf = new RandomAccessFile("/home/sandeep/Dropbox/Replab/data/All/Apple.txt","r");
			String line,id;
			BufferedWriter br1;
			
			while((line=br.readLine())!=null)
		    {
				HashSet<String> tempOffset =new HashSet<String>();
				StringBuffer temp= new StringBuffer();
				posting=s.search(line);
		    	posting = posting.split("\\{")[1]; // removing delimiter {
		    	
		    	if(posting.contains("@")){ //checking if it occured in mutiple company tweets.
		    		posting_list = posting.split("@");
		    		for(int i =0;i<posting_list.length;i++){
		    			id=posting_list[i].split(":")[0];
		    			if(id.equalsIgnoreCase("1"))
		    			{
		    				String line_offsets= posting_list[i].split(":")[2]; //getting line offsets of the string occurence
		    				line_offsets=line_offsets.replace("[","" );
		    				line_offsets=line_offsets.replace("]","" );
		    				String[] line_pos = line_offsets.split(",");
		    				// Storing all the line offsets in a set for every product.
		    				
		    				for(int j = 0; j<line_pos.length;j++ ){
		    					tempOffset.add(line_pos[j].trim());
		    					raf.seek(Long.parseLong(line_pos[j].trim()));
		    					temp.append(raf.readLine()); // storing all the tweets in the buffer
		    					temp.append("\n");
		    					
		    				}
		    				System.out.println("yes"+tempOffset.size()+line);
		    				if(k==0)
		    				{
		    					//System.out.println(k);
		    					br1=new BufferedWriter(new FileWriter("/home/sandeep/Dropbox/Replab/newrankings/apple/unigramProducts/"+line));
			    				br1.write(temp.toString());
		    					productOffsets.add(k, tempOffset);
		    					
		    					br1.close();
		    					 k++;
		    				}
		    				else
		    				{   
		    					int z=k;
		    				//System.out.println(productOffsets.size());
		    				System.out.println("value of k"+k);
		    				int flag=t.cluster_rearrange(k,tempOffset);
		    				if(flag==1) // if its a real product then get the tweets and create file. else leave it.
		    				{
		    				br1=new BufferedWriter(new FileWriter("/home/sandeep/Dropbox/Replab/newrankings/apple/unigramProducts/"+line));
		    				br1.write(temp.toString());
	    					productOffsets.add(z, tempOffset);
	    					
	    					br1.close();
	    					 k++;
		    				}
		    				}
		    				
		    				
		    				
		    			}
		    		}
		    	}
		    	else{
		    		id=posting.split(":")[0];
		    		if(id.equalsIgnoreCase("1"))
	    			{
	    				String line_offsets= posting.split(":")[2];
	    				line_offsets=line_offsets.replace("[","" );
	    				line_offsets=line_offsets.replace("]","" );
	    				String[] line_pos = line_offsets.split(",");
	    				for(int j = 0; j<line_pos.length;j++ ){
	    					tempOffset.add(line_pos[j].trim());
	    					raf.seek(Long.parseLong(line_pos[j].trim()));
	    					temp.append(raf.readLine());
	    					temp.append("\n");
	    				}
	    				System.out.println("yes"+tempOffset.size()+line);
	    				if(k==0)
	    				{
	    					br1=new BufferedWriter(new FileWriter("/home/sandeep/Dropbox/Replab/newrankings/apple/unigramProducts/"+line));
		    				br1.write(temp.toString());
	    					productOffsets.add(k, tempOffset);
	    					
	    					br1.close();
	    					 k++;
	    				}
	    				else
	    				{
	    				int flag=t.cluster_rearrange(k,tempOffset);
	    				if(flag==1)
	    				{
	    					int z=k;
	    				br1=new BufferedWriter(new FileWriter("/home/sandeep/Dropbox/Replab/newrankings/apple/unigramProducts/"+line));
	    				br1.write(temp.toString());
    					productOffsets.add(z, tempOffset);
    					br1.close();
    					 k++;
	    				}
	    				}
	    				
	    				
	    			}
		    	}
		   	
		    }
		raf.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

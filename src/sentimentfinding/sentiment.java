package sentimentfinding;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;

public class sentiment {
HashSet<String> product_names=new HashSet<String>();
public sentiment()
{
	
	BufferedReader br=null;
	String getproduct=null;
	try
	{
		br = new BufferedReader(new FileReader("/home/sandeep/Dropbox/Replab/newrankings/apple/products.txt"));	
		while((getproduct=br.readLine())!=null)
		{
			product_names.add(getproduct);
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
	/**
	 * @param args
	 */
	public String preprocess_tweet(String tweet) {
		
		// TODO Auto-generated method stub
		Twokenizer t= new Twokenizer();
		try
		{
			String part1="";
			String part2="";
			int weight_despite=0;
			int weight_but=0;
			int weight_if=0;
			int weight_not=0;
			List<String> tokens=t.tokenize(tweet);
				int i;
				for(i=0;i<tokens.size();i++)
				{
					if(tokens.get(i).equalsIgnoreCase("despite") || tokens.get(i).equalsIgnoreCase("till") || tokens.get(i).equalsIgnoreCase("until") || tokens.get(i).equalsIgnoreCase("inspite") || tokens.get(i).equalsIgnoreCase("though") || tokens.get(i).equalsIgnoreCase("although"))
					{
						weight_despite=1;
						break;
					}else if(tokens.get(i).equalsIgnoreCase("but") || tokens.get(i).equalsIgnoreCase("contrast") || tokens.get(i).equalsIgnoreCase("however") || tokens.get(i).equalsIgnoreCase("nevertheless") || tokens.get(i).equalsIgnoreCase("otherwise") || tokens.get(i).equalsIgnoreCase("yet") || tokens.get(i).equalsIgnoreCase("still") || tokens.get(i).equalsIgnoreCase("nonetheless") )
					{
						weight_but=1;
						break;
					}
					else if(tokens.get(i).equalsIgnoreCase("if"))
					{
						weight_if=1;
						break;
					}
					else if(tokens.get(i).equalsIgnoreCase("not") || tokens.get(i).equalsIgnoreCase("neither") || tokens.get(i).equalsIgnoreCase("never") || tokens.get(i).equalsIgnoreCase("no") || tokens.get(i).equalsIgnoreCase("nor"))
					{
						weight_not=1;
						
					}
					else
					{
						part1+=" "+tokens.get(i);
					}
				}
				for(int j=i+1;j<tokens.size();j++)
				{
					part2+=" "+tokens.get(j);
					
				}
				if(weight_if==1)
				{
					return "neutral";
				}else if(weight_despite==1)
				{
					return part1;
					
				}else if(weight_but==1)
				{
				
				return part2;
				
				}				
				if(weight_not==1 && (weight_but==1 || weight_despite==1))
				{
				return "negative";
				}
				
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return tweet;
	}

}

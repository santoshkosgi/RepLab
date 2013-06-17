import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
public class Tokenizer {
  public HashMap<String, Integer> tokenize(String str){ 
	  HashMap<String, Integer> stems = new HashMap<String, Integer>();
	  try {
	  str = str.toLowerCase();
	  StringTokenizer st = new  StringTokenizer(str, ",.!@#$%^&*()_+=-?><:;[]{}?~`\\ |/1234567890 \"'\n");
	  ArrayList<String> tokens = new ArrayList<String>();
	  FileReader f = new FileReader("/home/santoshkosgi/Downloads/sw3");
	  BufferedReader br = new BufferedReader(f);
	  String strLine;
	  ArrayList<String> stopwords = new ArrayList<String>();
	  while ((strLine = br.readLine()) != null)   {
		  stopwords.add(strLine);
	  }
	  while(st.hasMoreTokens()) { 
		  String key = st.nextToken();  
		  if(key.length()>=3 && !stopwords.contains(key)){
			  tokens.add(key);
		  }
		  }
	 //System.out.println(tokens);
	 
	 for(int i=0;i<tokens.size();i++){
		 int k;
		 if(!stems.containsKey(tokens.get(i))){
			//System.out.println(s.toString());
			stems.put(tokens.get(i),1);
		}
		else {
			k = stems.get(tokens.get(i));
			stems.remove(tokens.get(i));
			stems.put(tokens.get(i), ++k);
		}
	 }
	 //System.out.println(stems);
	 br.close();
	  }
	  
	  catch (Exception e){
		  
	  }
	  return stems;
	 
	   
    }
}
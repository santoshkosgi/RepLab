import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Index{
	public static void main(String[] argsv) throws IOException{
		TreeMap<String,ArrayList<Long>> index = new TreeMap<String,ArrayList<Long>>(); 
		ArrayList<Long> temp_list = new ArrayList<Long>();
		TreeMap<String,Integer> files_tree = new TreeMap<String,Integer>();
		File folder = new File("/home/santoshkosgi/Dropbox/Replab/data/");
		File[] listOfFiles = folder.listFiles();
		int file_count=0;
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	files_tree.put(file.getName(),0);
		    	file_count++;
		    }
		}
		for(int i = 1 ;i<=file_count;i++){
			Map.Entry<String, Integer> e= files_tree.pollFirstEntry();
			RandomAccessFile input = new RandomAccessFile("/home/santoshkosgi/Dropbox/Replab/data/"+e.getKey(), "r");
			File file = new File("/home/santoshkosgi/Downloads/replab/"+i+".txt");
			BufferedWriter outer = new BufferedWriter(new FileWriter(file));
			String line;
			long pos;
			long temp;
			int count=0;
			pos = input.getFilePointer();
			while((line = input.readLine())!=null){
				System.out.println(count++);
				String l = line.replaceAll("\\s+", " ");
				StringTokenizer st = new StringTokenizer(l,"  ");
				String k = new String();
				st.nextToken();
				st.nextToken();
				String token;
				HashMap<String, Integer> tokens_list = new HashMap<String, Integer>();
				Tokenizer t = new Tokenizer();
				while(st.hasMoreTokens()) {
					token=st.nextToken();
					if((token.indexOf("http")==-1)&&(token.indexOf("RT")==-1)&&(token.indexOf("@")==-1)){
						k=k.concat(" "+token);
					}
				}
				tokens_list = t.tokenize(k);
				Set<String> set=new HashSet<String>();
				set.addAll(tokens_list.keySet());
				for (String s: set){
					if(index.containsKey(s)){
						index.get(s).add(pos);
						temp=index.get(s).get(0)+(long)tokens_list.get(s);
						index.get(s).remove(0);
						index.get(s).add(0, temp);
					}
					else{
						temp_list.add((long)tokens_list.get(s));
						temp_list.add(pos);
						index.put(s, new ArrayList<Long>(temp_list));
						temp_list.clear();
					}
				}
				pos = input.getFilePointer();
			}
			for(Map.Entry<String,ArrayList<Long>> entry : index.entrySet()) {
		    	  temp_list.clear();
				  String key = entry.getKey();
		          //out1.write();
		    	  temp_list = entry.getValue();
		    	  temp = temp_list.get(0);
		    	  temp_list.remove(0);
		          StringBuffer data = new StringBuffer(key+"{"+i+":"+temp+":"+temp_list+"\n"); 
		          // write it to file 

		          outer.write(data.toString()); 
		      }
			index.clear();
			input.close();
			outer.close();
		}
		
	}
}
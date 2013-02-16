import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import cmu.arktweetnlp.Tagger;
import cmu.arktweetnlp.Tagger.TaggedToken;


public class Inedx1{
	public static void compute_index(String s, long pos){
		ArrayList<Long> temp_list = new ArrayList<Long>();
		long temp;
		if(index.containsKey(s)){
			index.get(s).add(pos);
			temp=index.get(s).get(0)+(long)1;
			index.get(s).remove(0);
			index.get(s).add(0, temp);
		}
		else{
			temp_list.add((long)1);
			temp_list.add(pos);
			index.put(s, new ArrayList<Long>(temp_list));
			temp_list.clear();
		}
		
	}
	public static TreeMap<String,ArrayList<Long>> index = new TreeMap<String,ArrayList<Long>>(); 
	public static void main(String[] argsv) throws IOException{
		ArrayList<Long> temp_list = new ArrayList<Long>();
		TreeMap<String,Integer> files_tree = new TreeMap<String,Integer>();
		File folder = new File("/home/santoshkosgi/Downloads/replab/data/");
		File[] listOfFiles = folder.listFiles();
		Tagger t1 = new Tagger();
		t1.loadModel("/home/santoshkosgi/Dropbox/Replab/data/model.irc.20121211.txt");
		
		int file_count=0;
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	files_tree.put(file.getName(),0);
		    	file_count++;
		    }
		}
		for(int i = 1 ;i<=file_count;i++){
			Map.Entry<String, Integer> e= files_tree.pollFirstEntry();
			RandomAccessFile input = new RandomAccessFile("/home/santoshkosgi/Downloads/replab/data/"+e.getKey(), "r");
			File file = new File("/home/santoshkosgi/Downloads/replab/out/"+i+".txt");
			BufferedWriter outer = new BufferedWriter(new FileWriter(file));
			String line;
			long pos;
			long temp;
			int count=0;
			String s;
			int pos_list_size;
			pos = input.getFilePointer();
			while((line = input.readLine())!=null){
				System.out.println(count++);
				String l = line.replaceAll("\\s+", " ");
				String l1 = l.replaceAll("[^\\x00-\\x7F]", "");
				StringTokenizer st = new StringTokenizer(l1,"  \n");
				String k = new String();
				String token;
				while(st.hasMoreTokens()) {
					token=st.nextToken();
					if((token.indexOf("http")==-1)&&(token.indexOf("RT")==-1)&&(token.indexOf("@")==-1)){
						k=k.concat(" "+token);
					}
				}
				k = k.toLowerCase();
				if(!k.isEmpty()){
					List<TaggedToken> pos_list = t1.tokenizeAndTag(k);
					pos_list_size = pos_list.size();
					for(int j =0;j<pos_list_size;j++){
						if(pos_list.get(j).tag.compareTo("NN")==0||pos_list.get(j).tag.compareTo("NNS")==0||pos_list.get(j).tag.compareTo("NNP")==0||pos_list.get(j).tag.compareTo("NNPS")==0){
							if(j+1<pos_list_size){
								if(pos_list.get(j+1).tag.compareTo("NN")==0||pos_list.get(j+1).tag.compareTo("NNS")==0||pos_list.get(j+1).tag.compareTo("NNP")==0||pos_list.get(j+1).tag.compareTo("NNPS")==0){
									if(j+2<pos_list_size){
										if(pos_list.get(j+2).tag.compareTo("NN")==0||pos_list.get(j+2).tag.compareTo("NNS")==0||pos_list.get(j+2).tag.compareTo("NNP")==0||pos_list.get(j+2).tag.compareTo("NNPS")==0){
											//three matching
											s = pos_list.get(j).token+" "+pos_list.get(j+1).token+" "+pos_list.get(j+2).token;
											j = j+3;
											compute_index(s.replace("#","").replaceAll("\\s+", " "),pos);
										}
										else{
											//two matching
											s = pos_list.get(j).token+" "+pos_list.get(j+1).token;
											j = j+3;
											compute_index(s.replace("#","").replaceAll("\\s+", " "),pos);
										}
									}
									else{
										//two matching
										s = pos_list.get(j).token+" "+pos_list.get(j+1).token;
										j = j+2;
										compute_index(s.replace("#","").replaceAll("\\s+", " "),pos);
									}
								}
								else{
									//one matching
									s = pos_list.get(j).token;
									compute_index(s.replace("#","").replaceAll("\\s+", ""),pos);
								}
							}
							else{
								//one matching
								s = pos_list.get(j).token;
								compute_index(s.replace("#","").replaceAll("\\s+", ""),pos);
							}
						}
						else if(pos_list.get(j).tag.compareTo("UH")==0){
							s = pos_list.get(j).token;
							compute_index(s.replace("#","").replaceAll("\\s+", ""),pos);
						}
					}
				}
				/*tokens_list = t.tokenize(k);
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
				}*/
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
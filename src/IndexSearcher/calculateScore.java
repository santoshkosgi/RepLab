package IndexSearcher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class calculateScore {

	/**
	 * @param args
	 */
	static <K,V extends Comparable<? super V>>
	SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
		SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
				new Comparator<Map.Entry<K,V>>() {
					@Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
						return e2.getValue().compareTo(e1.getValue());
					}
				}
				);
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}
	public static void main(String[] args) {
		File file;
		FileWriter fstream;
		BufferedWriter out1;
		try
		{
			file = new File("/home/sandeep/Dropbox/Replab/newrankings/samsung/prodcuts2.txt");
			fstream = new FileWriter(file.getAbsoluteFile());
			out1 = new BufferedWriter(fstream);


			Map<String,Double> Rankingdoc1 = new TreeMap<String,Double>();
			HashSet<String> s1=new HashSet<String>();
			HashSet<String> s2=new HashSet<String>();
			String line=null;
			Double tf_score=0.0;
			BufferedReader br=null;
			search sc=new search();
			String name="samsung";
			s1=sc.getIds(name);
			Double score=0.0;

			Iterator<String> it=s1.iterator();


			br = new BufferedReader(new FileReader("/home/sandeep/Dropbox/Replab/newrankings/samsung/trigram.txt"));
			while((line=br.readLine())!=null)
			{
				score=Double.parseDouble(line.split("=")[1]);
				s2=sc.getIds(line.split("=")[0]);
				if(s2!=null)
				{
					int actual_size=s2.size();

					s2.retainAll(s1);

					int intersection_size=s2.size();
					tf_score=score*intersection_size/(actual_size-intersection_size);
					if(actual_size<100 && actual_size>0 || score<20)
						Rankingdoc1.put(line.split("=")[0],0.0);
					else
						Rankingdoc1.put(line.split("=")[0],tf_score*1000);

					s2.clear();



				}
				else
				{
					if(score<20)
						Rankingdoc1.put(line.split("=")[0],0.0);
					else
						Rankingdoc1.put(line.split("=")[0],score*1000);
				}
			}
			SortedSet<Map.Entry<String,Double>> s3=entriesSortedByValues(Rankingdoc1);
			Iterator<Map.Entry<String,Double>> it1=s3.iterator();

			int k=0;


			while(it1.hasNext())
			{
				++k;


				out1.write(it1.next().toString().split("=")[0]);
				out1.newLine();
				/*if(k>(s3.size()*.2)){
					break;
				}*/

			}
			out1.close();




			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		// TODO Auto-generated method stub

	}

}

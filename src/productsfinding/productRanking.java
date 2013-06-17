package productsfinding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.Map;
import java.util.TreeSet;

public class productRanking {

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
		// TODO Auto-generated method stub
		Map<String,Double> Rankingdoc1 = new TreeMap<String,Double>();
		Map<String,Double> Rankingdoc2 = new TreeMap<String,Double>();
		Map<String,Double> Rankingdoc3 = new TreeMap<String,Double>();
		Map<String,Double> Rankingdoc4 = new TreeMap<String,Double>();
		File file;
		FileReader fstream;
		FileWriter fstream1;
		BufferedReader out1;
		BufferedWriter out;
		String line="";
		try
		{
			file = new File(args[0]);
			fstream = new FileReader(file.getAbsoluteFile());
			out1 = new BufferedReader(fstream); 
			int k=0;
			while((line=out1.readLine())!=null)
			{
				k++;
				int idx=0;
				int next=0;
				int docid=0;
				Double score=0.0;
				next = line.indexOf('{', idx );
				String word=line.substring(idx,next);
				line=line.substring(next+1,line.length());
				String[] scores=line.split("@");

				for(int i=0;i<scores.length;i++)
				{
					next=scores[i].indexOf(':',idx);
					docid=Integer.parseInt(scores[i].substring(idx,next));
					score=Double.parseDouble(scores[i].substring(next+1,scores[i].length()));

					//first split based on {
					//then split based on @ on remaining string
					//then split on : and on 1st parameter maintain a switch case.

					switch(docid)
					{
					case 1:
						Rankingdoc1.put(word, score);
						break;
					case 2:
						Rankingdoc2.put(word, score);
						break;
					case 3:
						Rankingdoc3.put(word, score);
						break;
					case 4:
						Rankingdoc4.put(word, score);

					}
				}
			}
			out1.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		SortedSet<Map.Entry<String,Double>> s1=entriesSortedByValues(Rankingdoc1);
		Iterator<Map.Entry<String,Double>> it=s1.iterator();
		try
		{
			file = new File("/home/sandeep/Dropbox/Replab/newrankings/ranking1.txt");
			fstream1 = new FileWriter(file.getAbsoluteFile());
			out = new BufferedWriter(fstream1);


			while(it.hasNext())
			{
				out.write(it.next().toString());
				out.write("\n");
				
			}
			
			out.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		s1=entriesSortedByValues(Rankingdoc2);
		it=s1.iterator();
		try
		{
			file = new File("/home/sandeep/Dropbox/Replab/newrankings/ranking2.txt");
			fstream1 = new FileWriter(file.getAbsoluteFile());
			out = new BufferedWriter(fstream1);


			while(it.hasNext())
			{
				out.write(it.next().toString());
				out.write("\n");
				
			}
			out.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		s1=entriesSortedByValues(Rankingdoc3);
		it=s1.iterator();
		try
		{
			file = new File("/home/sandeep/Dropbox/Replab/newrankings/ranking3.txt");
			fstream1 = new FileWriter(file.getAbsoluteFile());
			out = new BufferedWriter(fstream1);


			while(it.hasNext())
			{
				out.write(it.next().toString());
				out.write("\n");
				
			}
			out.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		s1=entriesSortedByValues(Rankingdoc4);
		it=s1.iterator();
		try
		{
			file = new File("/home/sandeep/Dropbox/Replab/newrankings/ranking4.txt");
			fstream1 = new FileWriter(file.getAbsoluteFile());
			out = new BufferedWriter(fstream1);


			while(it.hasNext())
			{
				out.write(it.next().toString());
				out.write("\n");
				
			}
			out.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

}

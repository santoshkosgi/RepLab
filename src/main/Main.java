package main;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

import classifier.ClassifierBuilder;
import classifier.WekaClassifier;
import util.Options;
import weka.classifiers.functions.VotedPerceptron;
import sentimentfinding.sentiment;
public class Main {
	public static void main(String[] args) throws Exception {
		sentiment m=new sentiment();
		BufferedReader br=null;
		BufferedWriter br1;
		BufferedWriter br2;
		BufferedWriter br3;
		String line="";
		try
		{
			br = new BufferedReader(new FileReader(args[0]));
			br1=new BufferedWriter(new FileWriter(args[1]));
			br2=new BufferedWriter(new FileWriter(args[2]));
			br3=new BufferedWriter(new FileWriter(args[3]));
			ClassifierBuilder clb = new ClassifierBuilder();
			Options opt = new Options();
			clb.setOpt(opt);
			opt.setSelectedFeaturesByFrequency(true);
			opt.setNumFeatures(30306);
			opt.setRemoveEmoticons(false);
			VotedPerceptron nb= new VotedPerceptron();
			WekaClassifier wc = clb.constructClassifier(nb);
			wc =clb.retrieveClassifier("weka.classifiers.functions.VotedPerceptron");
			while((line=br.readLine())!=null)
			{
				String modified=m.preprocess_tweet(line);
				String l = modified.replaceAll("\\s+", " ");
				String l1 = l.replaceAll("[^\\x00-\\x7F]", "");
				StringTokenizer st = new StringTokenizer(l1," ");
				String urlRemovedTweet = new String();
				String token;
				while(st.hasMoreTokens()) {
					token=st.nextToken();
					if((token.indexOf("http")==-1)&&(token.indexOf("RT")==-1)&&(token.indexOf("@")==-1)){
						urlRemovedTweet=urlRemovedTweet.concat(" "+token);
					}
				}
				if(modified.equalsIgnoreCase("neutral"))
				{
					br1.write(line+"2");
					br1.write("\n");
					continue;
				}
				if(modified.equalsIgnoreCase("negative"))
				{
					br2.write(line+"0");
					br2.write("\n");
					continue;
				}
				String classify=wc.classify(urlRemovedTweet);
				System.out.println(classify);
				if(classify.equalsIgnoreCase("0"))
				{
					br2.write(line+"0");	
					br2.newLine();
				}
				if(classify.equalsIgnoreCase("1"))
				{
					br3.write(line+"4");
					br3.newLine();
				}
			}
			br.close();
			br1.close();
			br2.close();
			br3.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


	}
}
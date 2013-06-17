import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

public class tf_idf{
	public static void main(String argv[]) throws IOException{
		InputStream    fis;
		BufferedReader br;
		String first,posting,new_posting;
		fis = new FileInputStream("/home/santoshkosgi/Downloads/replab/out3/1.txt");
		br = new BufferedReader(new InputStreamReader(fis));
		Writer output;
		double N = 4;//number of documents
		output = new BufferedWriter(new FileWriter("/home/santoshkosgi/Downloads/replab/out3/tf-idf1.txt", true));
		while((first = br.readLine())!=null){
			if(first.split("\\{").length>=2){
				new_posting = "";
				String word = first.split("\\{")[0];
				posting = first.split("\\{")[1];
				if(posting.contains("@")){//for words occured in more than one doc
					//sum of all tf's should be calculated
					String[] posting_split =  posting.split("@");
					int posting_length = posting_split.length;
					int sum_tf=0;
					for( int j = 0;j<posting_length;j++){
						sum_tf = sum_tf+Integer.parseInt(posting_split[j].split(":")[1]);
					}
					String[] temp = posting_split[0].split(":");
					double x = (double)(posting_length*(sum_tf-Integer.parseInt(temp[1])));
					//new_posting = temp[0]+":"+((1+(Double.parseDouble(temp[1])))*((N/x)));
					new_posting = temp[0]+":"+((1+(Double.parseDouble(temp[1])))*((1)));
					//double y = Double.parseDouble(temp[1])/(double)(sum_tf);
					//new_posting = ""+y;
					//System.out.println(x);
					//System.out.println(Math.log(N/x));
					for( int j = 1;j<posting_length;j++){
						temp = posting_split[j].split(":");
						if(Integer.parseInt(temp[1])>=10){
						x = (double)(posting_length*(sum_tf-Integer.parseInt(temp[1])));
						System.out.println(x);
						//y = Double.parseDouble(temp[1])/(double)(sum_tf);
						//new_posting = ""+y;
						//new_posting = new_posting+"@"+temp[0]+":"+((1+(Double.parseDouble(temp[1])))*((N/x)));
						new_posting = new_posting+"@"+temp[0]+":"+((1+(Double.parseDouble(temp[1])))*((1)));
						}
					}
					System.out.println(new_posting);
					output.append(word+"{"+new_posting+"\n");
				}
				else{
					String[] temp = posting.split(":");
					if(Integer.parseInt(temp[1])>=10){
					if(temp[1].compareToIgnoreCase("1")!=0){
					new_posting = temp[0]+":"+((1+(Long.parseLong(temp[1]))));
					output.append(word+"{"+new_posting+"\n");
					//output.append(word+"{"+"1"+"\n");
					}
					}
			}
		}
		
		}
		br.close();
		output.close();
	}
	}
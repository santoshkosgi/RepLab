import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


 
public class URLExpander {
 
     public static String expand(String url) {
          try {
        	  System.setProperty("http.proxyHost","proxy.iiit.ac.in");
        	  System.setProperty("http.proxyPort","8080");
               HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
               connection.setInstanceFollowRedirects(true);
               //while(connection.getResponseCode()!=202){
               if (connection.getResponseCode() >= 400) {
            	   return "";
               } else {
               connection.getInputStream().read();
               return connection.getURL().toString();
               }
               //return connection.getURL().toString();
             //  }
               //if(st==0){
            	 //  return "";
               //}
               //else{
            	 //  return connection.getURL().toString();
               //}
               
          } catch (MalformedURLException e) {
               e.printStackTrace();
          } catch (IOException e) {
               e.printStackTrace();
          }
 
          return null;
     }
     private static String punctChars = "['“\\\".?!,:;]";
 	private static String entity = "&(amp|lt|gt|quot);";
     private static String urlStart1  = "(https?://|www\\.)";
 	private static String commonTLDs = "(com|co\\.uk|org|net|info|ca|ly)";
 	private static String urlStart2  = "[A-Za-z0-9\\.-]+?\\." + commonTLDs + "(?=[/ \\W])";
 	private static String urlBody    = "[^ \\t\\r\\n<>]*?";
 	private static String urlExtraCrapBeforeEnd = "(" + punctChars + "|"+entity + ")+?";
 	private static String urlEnd     = "(\\.\\.+|[<>]|\\s|$)";
 	private static String url        = "\\b(" + urlStart1 + "|" + urlStart2 + ")" + urlBody + "(?=(" + urlExtraCrapBeforeEnd + ")?" + urlEnd + ")";
 	private static String normalEyes = "(?iu)[:=]";
	private static String wink = "[;]";
	private static String noseArea = "(|o|O|-)"; // rather tight precision, \\S might be reasonable...
	private static String happyMouths = "[D\\)\\]]";
	private static String sadMouths = "[\\(\\[]";
	private static String tongue = "[pP]";
	private static String otherMouths = "[doO/\\\\]"; // remove forward slash if http://'s aren't cleaned

	private static String emoticon = "(" + normalEyes + "|" + wink + ")" + noseArea + "(" + tongue + "|" + otherMouths + "|" + sadMouths + "|" + happyMouths + ")";
     public static void main(String[] argsv) throws IOException{
    	 //System.out.println(expand("http://t.co/fVNl1KTPB1"));
	    Pattern pattern = Pattern.compile(url);
	    TreeMap<String,Integer> files_tree = new TreeMap<String,Integer>();
 		File folder = new File("/home/santoshkosgi/Downloads/replab/All/");
 		File[] listOfFiles = folder.listFiles();
 		
 		int file_count=0;
 		for (File file : listOfFiles) {
 		    if (file.isFile()) {
 		    	files_tree.put(file.getName(),0);
 		    	file_count++;
 		    }
 		}
 		for(int i = 1 ;i<=file_count;i++){
 			int count = 0;
 			Map.Entry<String, Integer> e= files_tree.pollFirstEntry();
 			RandomAccessFile input = new RandomAccessFile("/home/santoshkosgi/Downloads/replab/All/"+e.getKey(), "r");
 			File file = new File("/home/santoshkosgi/Downloads/replab/expanded/"+i+".txt");
 			//System.out.println(e.getKey());
 			BufferedWriter outer = new BufferedWriter(new FileWriter(file));
 			String line;
 			while((line = input.readLine())!=null){
 				System.out.println(++count);
 				Matcher matcher = pattern.matcher(line);
 		    	//if(matcher.groupCount()!=0){
 				while (matcher.find()) {
 					//System.out.println(matcher.groupCount());
 		    	      //System.out.print("Start index: " + matcher.start());
 		    	      //System.out.print(" End index: " + matcher.end() + " ");
 		    	      String s = matcher.group();
 		    	      //System.out.println(s);
 		    	     String s1 = s;
 		    	      if(s.substring(s.length()-2).matches(emoticon)){
 		    	    	  s1 = s.substring(0, s.length()-2);
 		    	      }
 		    	      //System.out.println(s1);
 		    	      String x;
 		    	      x = expand(s1);
 		    	      //System.out.println(x);
 		    	      //System.out.println(line.replace(s, s1));
 		    	     line = line.replace(s, s1);
 		    	     /*String y = "";
 			    	 for(int i=0;i<x.split("[^a-zA-Z0-9]").length;i++){
 			    		 y = y+" "+x.split("[^a-zA-Z0-9]")[i];
 			    	 }
 			    	 System.out.println(y.replaceAll("\\s+", " "));*/
 			    	 System.out.println(line.replaceAll(s1, x));
 			    	 outer.write(line.replaceAll(s1, x));
 			    	 outer.newLine();
 			    	 
 		    	    }
 		    	//}
 		    	/*else{
 		    		System.out.println(line);
 		    		outer.write(line);
			    	 outer.newLine();
 		    	}*/
 			}
 			
 			input.close();
			outer.close();
 		}
     }
}
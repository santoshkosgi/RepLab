/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import twitter4j.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
public class SearchTweets {
	static File file;
	static FileWriter fstream;
	static BufferedWriter out1;
    /**
     * Usage: java twitter4j.examples.search.SearchTweets [query]
     *
     * @param args
     */
    public static void main(String[] args) {
    	
        if (args.length < 1) {
            System.out.println("java twitter4j.examples.search.SearchTweets [query]");
            System.exit(-1);
        }
        System.setProperty("http.proxyHost", "proxy.iiit.ac.in");
        System.setProperty("http.proxyPort", "8080");
        Twitter twitter = new TwitterFactory().getInstance();
        int i=0;
        try {
        	file = new File("/home/santoshkosgi/Dropbox/Replab/data/Google/googlechrome.txt");
			fstream = new FileWriter(file.getAbsoluteFile());
			out1 = new BufferedWriter(fstream); 
            Query query = new Query(args[0]);
            query.setLang("en");
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    ++i;
                    System.out.println(i);
                	out1.write(tweet.getText());
                    out1.write("\n");
                }
                if(i>2000)
                	break;
            } while ((query = result.nextQuery()) != null);
            out1.flush();
            out1.close();
            System.exit(0);
        } catch (Exception te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    }
}
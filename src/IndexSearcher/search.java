package IndexSearcher;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.TreeMap;




public class search {
	Stemmer stem=new Stemmer();	
	bsearch find =new bsearch();
	static  long startTime;
	static HashSet<String> stopwords=new HashSet<String>(1000,0.75f);
	String key,stop,stemword;
	StringTokenizer st;
	static int querylength=0;
	HashMap<String,Double> h2=new HashMap<String,Double>();
	TreeMap<Double,String> s=new TreeMap<Double,String>();
	TreeMap<Integer,Integer> sort=new TreeMap<Integer,Integer>();

	public search()
	{

		String stopword="iam|coord|corner|corners|accepts|accepted|adjust|add|active|actual|accept|access|accessed|abduct|aas|abandoned|abolish|abound|length|less|lesser|abandon|live|low|local|log|logo|long|longer|longest|law|lead|learn|leave|led|left|elev|latino|launch|laura|limited|likewise|asof|line|link|aboutus|backed|est|utc|type|svg|settlement|see|blank|com|cite|well|zip|www|air|jpg|image|gni|map|longd|longm|longs|latd|latm|lats|latns|latew|age|aaa|aaaa|aaaaa|aaaaaa|maintain|alone|title|nbsp|able|about|above|according|accordingly|actually|afterwards|again|against|ahead|allow|ago|allows|across|after|aint|all|along|already|although|always|amid|amidst|amongst|another|anybody|anyhow|anyone|anything|anyway|anyways|anywhere|apart|almost|also|among|and|any|are|arent|around|aside|ask|asking|back|backwards|backward|became|become|becomes|beside|besides|beyond|both|brief|because|been|before|being|below|between|but|for|can|came|come|comes|cant|cannot|could|couldve|couldnt|dear|did|didnt|does|down|downwards|during|each|enough|especially|everybody|exactly|everyone|entirely|everything|doesnt|dont|either|else|ever|every|for|from|fairly|farther|few|found|further|get|got|gives|given|goes|gone|hardly|hence|here|hereafter|herein|instead|had|has|hasnt|have|hed|hell|hes|her|hers|him|himself|his|how|howd|howll|hows|however|id|ill|im|ive|into|isnt|its|its|inside|indicates|indeed|instead|inward|just|knows|known|least|let|like|likely|lately|later|latter|last|little|look|looks|looking|keep|keeps|kept|may|many|mainly|maybe|made|makes|make|merely|mine|miss|more|moreover|mean|meanwhile|mostly|much|need|near|nearly|needs|nobody|none|per|perhaps|placed|please|plus|possible|presumably|probably|provided|provides|quite|might|mightve|mightnt|most|must|mustve|mustnt|neither|nor|normally|nothing|now|not|off|often|only|outside|other|others|overall|otherwise|once|our|ought|over|ourselves|own|particular|particularly|past|rather|really|reasonably|recent|recently|regarding|regardless|relatively|respectively|round|saw|seem|seemed|seen|several|said|say|says|shall|shant|since|somebody|someday|somehow|someone|sure|something|therein|she|such|shed|shell|shes|still|specified|specifying|specify|should|shouldve|shouldnt|since|some|take|tell|tries|tried|tends|truly|taken|taking|than|that|thatll|thats|the|their|them|then|there|theres|those|these|they|theyd|theyll|theyre|thanks|theyve|this|tis|too|twas|till|unless|unto|until|up|upon|upwards|use|used|useful|uses|using|usually|various|went|whereas|under|unlike|unlikely|unfortunately|wants|was|wasnt|wed|were|werent|what|whatd|whats|when|when|whend|whenll|whens|where|whered|wherell|wheres|which|while|who|whod|wholl|whos|whom|why|whyd|whyll|whys|will|with|wont|would|wouldve|wouldnt|yet|you|youd|youll|youre|youve|your|yours|yourself|include|includes|including";

		st=new StringTokenizer(stopword,"|");
		while(st.hasMoreTokens())
		{
			stopwords.add(st.nextToken());

		};

	}
	public ArrayList<String> split(String s) {
		ArrayList<String> out= new ArrayList<String>();
		int idx = 0;
		int next = 0;
		int i=0;
		while ((next = s.indexOf( ' ', idx )) > -1) {
			out.add( s.substring( idx, next ) );
			idx = next + 1;
			i++;
			if(i==180000)
				break;
		}
		if ( idx < s.length() && i<180000 ) {
			out.add( s.substring( idx ) );
		}
		return out;
	}
	public HashSet<String> calculatetfidf2(String term,int k)
	{

		String ids=find.search(term);
		HashSet<String> docids=new HashSet<String>();

		if(ids==null)
			return null;
		else
		{

			ArrayList<String> docs=split(ids);

			String document="";
			Double value;
			Double value1;
			int sum=docs.size();

			sort.put(sum+k,k);
			for(int r=0;r<sum;r=r+3)
			{

				docids.add(docs.get(r));



			}







		}
		return docids;
	}



	public HashSet<String> process_normal(String query,int k)
	{
		key=query.toLowerCase();
		st = new StringTokenizer(key," \n{}#@+$%&^*//(|=!_-;.)[]\",:?<>");
		int f=0;

		while(st.hasMoreTokens() && (stop = st.nextToken())!=null) { 
			stemword = stop.replaceAll("[^a-zA-Z0-9]+","");
			if(stopwords.contains(stemword))
			{
				continue;
			}
			if(stemword.length()>2)
			{
				
               
				++f;
			}
		}
		if(f==0)
			return null;
		else
			return calculatetfidf2(stemword,k);
	}

	public HashSet<String> getIds(String name1)
	{
		String query="";
		String[] name=name1.split(" ");
		ArrayList<HashSet<String>> docids=new ArrayList<HashSet<String>>();
		search s=new search();
		querylength=name.length;
		startTime=System.currentTimeMillis();
		//System.out.println(querylength);
		
		for(int i=0;i<name.length;i++)
		{
			query =name[i];
			docids.add(i,s.process_normal(query,i));
		}
		int size=docids.size();
		HashSet<String> s1=docids.get(0);
		
		//System.out.println("s1"+s1.size());
		
		if(s1!=null)
		{
			
		
		for(int z=1;z<size && size>1;z++)
		{
			
			HashSet<String> s2=docids.get(z);
			if(name[z].matches(".*\\d.*")){
				if(s2==null)
				{
				   
				}
				else
				{
					 s1.addAll(s2);
				}
				   
				} else{
						
			if(s2==null || s2.isEmpty())
			{
				
				s1.clear();
				break;
			}
			else
			{
				s1.retainAll(s2);
			}
				}
		}
		}
		return s1;

	}


}

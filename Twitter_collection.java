package dny_4;
import java.io.*;
import java.text.SimpleDateFormat;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetHomeTimeline {
	static Twitter twitter;
	 File file;
	public static void main(String[] args) {
		
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("Put your own Consumer KEy here")
		  .setOAuthConsumerSecret("Your creadentials pls")
		  .setOAuthAccessToken("Your creadentials pls")
		  .setOAuthAccessTokenSecret("Your creadentials pls");
		TwitterFactory tf = new TwitterFactory(cb.build());
		 twitter = tf.getInstance();
		 
		 try {
			
			 Twitter twitter = TwitterFactory.getSingleton();
			    Query query = new Query(" Saint-Denis");
			    query.setSince("2015-09-18");//("2015-09-21");
				   // query.getSince();
			//	query.setUntil("2015-12-1");
			    query.count(1000);
			    QueryResult result = twitter.search(query);
		
			    int counter=result.getCount();
			    new File("ballot").mkdir();
			    File file = new File("lemon41.json");
			    FileWriter writer = new FileWriter(file);
			    List<TweetToJSON> tlist=new ArrayList<TweetToJSON>();
			    
			  for (Status status : result.getTweets())
			  {
			    	 
			     System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
			        
			     String rawJSON = TwitterObjectFactory.getRawJSON(status);
			     
			     TweetToJSON t=new TweetToJSON();
			     
			     t.setId(Long.toString((status.getId())));
			     
			     String[] mySecondStringArray= new String[counter];
			   
			   
			     Date dt= new Date();
			     dt=status.getCreatedAt();
			     SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
			     Date dt2=new Date();
			     String date_string= sdf.format(dt2);
			     t.setCreated_at(date_string);
			     t.setLang(status.getLang());
			  
			     if(status.getLang().equals("en"))
			     {
			    	 t.setText_en(status.getText());
			     }
			     else if(status.getLang().equals("ru"))
			     {
			    	 t.setText_ru(status.getText());
			     }
			     else
			     {
			    	 t.setText_de(status.getText());
			     }
			     for (URLEntity url : status.getURLEntities())
			     {
			         t.appendtwitter_url(url.getExpandedURL());
			     }
			     for (HashtagEntity hsh : status.getHashtagEntities())
			     {
			    	 t.appendTwitter_hashtag(hsh.getText());
			     }
			     
			     tlist.add(t);
			    }
			    Gson gson = new GsonBuilder().setPrettyPrinting().create();
			    System.out.println(gson.toJson(tlist));
			    writer.write(gson.toJson(tlist));
			    writer.flush();
			    writer.close();     
			 
	            System.out.print("\ndone.");
	            System.exit(0);
	        }
	        catch (IOException ioe) 
	        {
	            ioe.printStackTrace();
	            System.out.println("Failed to store tweets: " + ioe.getMessage());
	        } 
	        catch (TwitterException te) 
	        {
	            te.printStackTrace();
	            System.out.println("Failed to get timeline: " + te.getMessage());
	            System.exit(-1);
	        }
	}//eof setup
}
	
 

package com.uk.trading.data_fetch_modules;

import java.io.IOException;

import com.uk.trading.utils.PropertiesValue;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterFetch {
	
	private TwitterFactory tf;

	public TwitterFetch() throws IOException{
		PropertiesValue props = new PropertiesValue();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(props.getPropValues("twitter_consumer_key"))
		  .setOAuthConsumerSecret(props.getPropValues("twitter_consumer_secret"))
		  .setOAuthAccessToken(props.getPropValues("twitter_access_token"))
		  .setOAuthAccessTokenSecret(props.getPropValues("twitter_access_token_secret"));
			tf = new TwitterFactory(cb.build());
	}
	
	public void fetchTweets(String twitterHandle, String startDate, String endDate){
		Twitter twitter = tf.getInstance();
		Query query = new Query("from:TeslaMotors since:"+startDate);
	    QueryResult result;
		try {
			result = twitter.search(query);
			 for (Status status : result.getTweets()) {
			        System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
			 }
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	    

	
		//twitter.searc
		
		
		
		
	}
	
}

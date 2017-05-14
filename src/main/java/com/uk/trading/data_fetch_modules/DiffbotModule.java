package com.uk.trading.data_fetch_modules;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.uk.trading.models.Document;
import com.uk.trading.models.Price;

public class DiffbotModule {

	public List<Document> diffbotCrawlCSVReader(String filePath) throws ParseException{
		 DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
			try{
			List<Document> documents = new ArrayList<Document>();
			 CSVParser csvFileParser = CSVFormat.DEFAULT.parse(new FileReader(new File(filePath)));
			 List<CSVRecord> r = csvFileParser.getRecords();
			 System.out.println(r.get(0));
			 r.remove(0);
//			 System.out.println(csvFileParser.getRecords().size());
			
			for (CSVRecord record : r) {
				System.out.println();
				//System.out.println(record.get(5));
				//values=[type, title, author, authorUrl, confidence, date, diffbotUri, discussion.confidence, discussion.diffbotUri, discussion.numPages, discussion.numPosts, discussion.pageUrl, discussion.participants, discussion.provider, discussion.title, discussion.type, estimatedDate, height, humanLanguage, icon, id, naturalHeight, naturalWidth, numPages, numPosts, pageUrl, parentId, participants, primary, provider, publisherCountry, publisherRegion, resolvedPageUrl, siteName, text, url, width]]
				Document document = new Document();
				document.setContent(record.get(13));
				System.out.println();
				document.setTitle(record.get(1));
				if(record.get(4).equals("")){
				
					System.out.println("SKIPPED");
					continue;
					
				}else{
					java.util.Date date = format.parse(record.get(4));
					document.setDate(date);
				}
			
				documents.add(document);
//				 
//				  price.setOpen(Double.parseDouble(record.get(1)));
//				  price.setDate(date);
//				  price.setHigh(Double.parseDouble(record.get(2)));
//				  price.setLow(Double.parseDouble(record.get(3)));
//				  price.setClose(Double.parseDouble(record.get(4)));
//				  prices.add(price);
//				  if(price.getOpen()<price.getClose()){
//					  price.setPositive(true);
//				  }
//				  price.setVolume(Integer.parseInt(record.get(5)));
//				  price.setFluctuation(price.getClose()-price.getOpen());
			  //  price.setVolume(Double.parseDouble(record.get(5)));
			 }
			
			System.out.println("done");
			return documents;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
			
		} 
	}
}

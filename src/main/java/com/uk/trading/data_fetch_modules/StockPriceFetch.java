package com.uk.trading.data_fetch_modules;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.uk.trading.models.Price;
import com.uk.trading.utils.PropertiesValue;

public class StockPriceFetch {

	public void getStockPriceByDate(String date){
		PropertiesValue props = new PropertiesValue();
		try {
			props.getPropValues("xignite_key");
			
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
	
	public List<Price> getStockPriceFromCSV(String filePath) throws ParseException{
		 try {
			 
			 DateFormat format = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
			
			List<Price> prices = new ArrayList<Price>();
			 CSVParser csvFileParser = CSVFormat.DEFAULT.parse(new FileReader(new File(filePath)));
			 List<CSVRecord> r = csvFileParser.getRecords();
//			 System.out.println(r.size());
//			 System.out.println(csvFileParser.getRecords().size());
			 System.out.println(r.size());
			 System.out.println(r.size());
			 r.remove(0);
			 System.out.println(csvFileParser.getRecords().size());
			for (CSVRecord record : r) {
				System.out.println(record);
				  Price price = new Price();
				  
				  java.util.Date date = format.parse(record.get(0));
				  
				  price.setOpen(Double.parseDouble(record.get(1)));
				  price.setDate(date);
				  price.setHigh(Double.parseDouble(record.get(2)));
				  price.setLow(Double.parseDouble(record.get(3)));
				  price.setClose(Double.parseDouble(record.get(4)));
				  prices.add(price);
				  if(price.getOpen()<price.getClose()){
					  price.setPositive(true);
				  }
				  price.setVolume(Integer.parseInt(record.get(5)));
				  price.setFluctuation(price.getClose()-price.getOpen());
			  //  price.setVolume(Double.parseDouble(record.get(5)));
			 }
			
			System.out.println("done");
			return prices;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
			
		} 
	}
	
}

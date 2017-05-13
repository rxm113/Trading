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
	
	public void getStockPriceFromCSV(String filePath){
		 try {
			 
			 DateFormat format = new SimpleDateFormat("dd-mmm-yy", Locale.ENGLISH);
			
			
			 CSVParser csvFileParser = CSVFormat.DEFAULT.parse(new FileReader(new File(filePath)));
			 
		
			for (CSVRecord record : csvFileParser) {
			     for (String field : record) {
			         System.out.print("\"" + field + "\", ");
			     
//			     Price price = new Price();
//			     Date date = (Date) format.parse(field);
//			     price.setDate(date);
//			     price.setClose(close);
			     }
			     System.out.println();
			 }
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
	}
	
}

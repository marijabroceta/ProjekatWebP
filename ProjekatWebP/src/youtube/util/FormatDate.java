package youtube.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class FormatDate {

	public static String dateToString(Date date) {
		SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
		String dateString;
		dateString = formatDate.format(date);
		
		return dateString;
	}
	
	public static Date stringToDate(String date) {
		try {
			DateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
			return formatDate.parse(date);
		}catch(ParseException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static Date stringToDateWrite(String date)  {
				
		try {
			
			DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			return formatDate.parse(date);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public static String dateToStringWrite(Date date) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String dateString;
		dateString = formatDate.format(date);
		
		return dateString;
	}
}

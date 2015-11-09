package tools.db;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
	public static String toDateString(Date date){
		String pattern ="yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
		
	}
}

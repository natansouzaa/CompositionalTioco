package gui.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
	public DateTime() {
		
	}
	
	public static LocalDateTime getDataTime() {
		return LocalDateTime.now();
	}
	
	public static String getCurrentData() {
		LocalDateTime currentDate = getDataTime();

		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		return dateFormat.format(currentDate);
	}
	
	
	public static String getCurrentTime() {
		LocalDateTime currentDate = getDataTime();
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
		
		return timeFormat.format(currentDate);
	}
}

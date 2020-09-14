package fr.mistergoliath.worldaszip.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Date {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    
    public static String getDateFormatted() {
    	return dateTimeFormatter.format(LocalDateTime.now());
    }
    
}

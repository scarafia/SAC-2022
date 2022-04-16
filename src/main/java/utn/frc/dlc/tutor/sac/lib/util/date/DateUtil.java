/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frc.dlc.tutor.sac.lib.util.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author scarafia
 */
public abstract class DateUtil {
  
  private static final String ISO_SHORT_DATE_FORMAT = "yyyy-MM-dd";
  private static final String ISO_LONG_DATE_FORMAT = "yyyy-MM-ddTHH:mm:ss";
  
  private static final String CUSTOM_LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  
  public static String dateToString(Date date) {
    DateFormat df = new SimpleDateFormat(ISO_SHORT_DATE_FORMAT);
    return df.format(date);
  }
  
  public static String dateTimeToString(Date date) {
    DateFormat df = new SimpleDateFormat(CUSTOM_LONG_DATE_FORMAT);
    return df.format(date);
  }
  
  public static Date stringToDate(String date) throws Exception {
    DateFormat df = new SimpleDateFormat(CUSTOM_LONG_DATE_FORMAT);
    return df.parse(date);
  }
  
}

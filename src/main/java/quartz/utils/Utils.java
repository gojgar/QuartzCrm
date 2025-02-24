/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quartz.utils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 *
 * @author alexandru.gojgar
 */
public class Utils {
    public static String getNrOfDays(String dataMontaj){
        String dataInceput = dataMontaj.substring(0, dataMontaj.indexOf(" -"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate ld = LocalDate.now();
        LocalDate ldPersonal = LocalDate.parse(dataInceput, formatter);

        return String.valueOf(DAYS.between(ld, ldPersonal));
    }

    public static String getDataInceputFormatted(String dataMontaj){
        String dataInceput = dataMontaj.substring(0, dataMontaj.indexOf(" -"));
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate ld = LocalDate.parse(dataInceput, formatter);
        return ld.format(formatterDate);
    }

    public static String getDataSfarsitFormatted(String dataMontaj){
        String dataSfarsit = dataMontaj.substring(dataMontaj.indexOf("- ") + 2);
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate ld = LocalDate.parse(dataSfarsit, formatter);
        return ld.format(formatterDate);
    }

    public static String formatValues(double suma) {
      // Create a DecimalFormat instance with a dot as the thousands separator
      DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
      decimalFormat.setGroupingUsed(true);
      decimalFormat.setGroupingSize(3);

      return decimalFormat.format(suma).replace(",", ".");
    }

    public static int formatPercentage(double part, double total) {
      return (int) ((part / total) * 100);
    }

    public static String formatAgent(String agent) {
      return agent.substring(0, 1).toUpperCase() + agent.substring(1);
    }

    public static String getCurrentMonth() {
      LocalDate currentDate = LocalDate.now();

      // Create a DateTimeFormatter with the Romanian locale
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", new Locale("ro"));

      // Format the month name in Romanian
      return currentDate.format(formatter);
    }

  public static String getCurrentYear() {
    LocalDate currentDate = LocalDate.now();

    return String.valueOf(currentDate.getYear());
  }
}

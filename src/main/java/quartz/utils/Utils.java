/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quartz.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
}

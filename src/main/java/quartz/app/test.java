package quartz.app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class test {
    public static void main(String[] args) {
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime ldt1 = ldt.plusMinutes(30);

        System.out.println(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}

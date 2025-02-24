/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quartz.app;

import java.sql.SQLException;
import java.util.ArrayList;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author alexandru.gojgar
 */
public class JobMorning implements org.quartz.Job{
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        VanzariBean vb = new VanzariBean();
        ArrayList<User> listaVanzari;
        try {
          listaVanzari = vb.vedeEmails();
          for(User user: listaVanzari) {
            new SendMailQuartz("smtp.gmail.com", 587, "contact.inovo@gmail.com", "mjqvemprrmlinexr"
              , user.getEmail(), "Incarcare oferte", "Buna,<br/><br/>Te rog sa incarci ofertele pe care le-ai facut in ziua precedenta.<br/>"
            , "Succes");
          }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

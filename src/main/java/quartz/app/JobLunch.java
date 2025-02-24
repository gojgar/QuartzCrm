/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quartz.app;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alexandru.gojgar
 */
public class JobLunch implements org.quartz.Job{
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
      VanzariBean vb = new VanzariBean();
      ArrayList<User> listaVanzari;
      try {
        listaVanzari = vb.vedeEmails();
        for(User user: listaVanzari) {
          new SendMailQuartz("smtp.gmail.com", 587, "contact.inovo@gmail.com", "mjqvemprrmlinexr"
            , user.getEmail(), "Reminder incarcare oferte", "Buna,<br/><br/>Ai incarcat ofertele din ziua precedenta?<br/><br/> Daca nu, te rog sa o faci.<br/>"
          , "Succes");
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
}

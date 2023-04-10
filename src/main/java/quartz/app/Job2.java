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
public class Job2 implements org.quartz.Job{
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        ClientBean cb = new ClientBean();
        ArrayList<Event> listaClienti;
        try {
            listaClienti = cb.getExprEvent();
            for(Event al : listaClienti) {
                    new SendMailQuartz("smtp.gmail.com", 587, "contact.inovo@gmail.com", "mjqvemprrmlinexr"
                            , al.getEmail(), "Instiintare eveniment", "Buna,<br/><br/>Urmeaza sa ai o intalnire cu un client de la ora: " + al.getDataStart().substring(al.getDataStart().indexOf("T") + 1) + "!<br/>Descriere eveniment: " + al.getDescriere() + "<br/>");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
}

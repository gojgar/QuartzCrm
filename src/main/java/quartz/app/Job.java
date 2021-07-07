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
import quartz.utils.Utils;

/**
 *
 * @author alexandru.gojgar
 */
public class Job implements org.quartz.Job{

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        ClientBean cb = new ClientBean();
        ArrayList<Client> listaClienti;
        try {
            listaClienti = cb.getClient();
            for(Client al : listaClienti) {
                if(Utils.getNrOfDays(al.getDataMontaj()).equals("7")) {
                    cb.addRowEmailRegistru(al.getClient(), al.getEmail());
                    new SendMailQuartz("smtp.gmail.com", 587, "contact.inovo@gmail.com", "mjqvemprrmlinexr"
                            , "alexandrugojgar@gmail.com", "Instiintare INOVO", "Buna ziua,<br/><br/>Va reamintim ca in perioada " + Utils.getDataInceputFormatted(al.getDataMontaj()) + " - " + Utils.getDataSfarsitFormatted(al.getDataMontaj()) + " se va livra si efectua montajul mobilierului dumneavoastra.<br/><br/>Va rugam ca spatiul rezervat montajului sa fie pregatit.");
                }
            }
        } catch (SQLException ex) {
        }
        
    }
    
}

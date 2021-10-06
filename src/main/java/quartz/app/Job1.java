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
public class Job1 implements org.quartz.Job{
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        ClientBean cb = new ClientBean();
        ArrayList<Client> listaClienti;
        try {
            listaClienti = cb.getClientFeedBack();
            for(Client al : listaClienti) {
                if(cb.getDays(al.getId())) {
                    new SendMailQuartz("smtp.gmail.com", 587, "contact.inovo@gmail.com", "mjqvemprrmlinexr"
                            , al.getEmail(), "Feedback INOVO", "Buna ziua,<br/><br/>Feedback-ul dumneavoastra este foarte important pentru noi, de aceea, din dorinta de a ne imbunatati permanent serviciile, va rugam sa aveti amabilitatea de a completa urmatorul chestionar (dureaza numai 1 minut din timpul dumneavoastra).<br/><br/>https://docs.google.com/forms/d/e/1FAIpQLSe73Dw-ugcpbMZqSS41I6RaG-GBjRo1e1_C1gpX7tNs_aupIw/viewform?usp=sf_link");
                    
                }
            }
        } catch (SQLException ex) {
        }
        
    }
}

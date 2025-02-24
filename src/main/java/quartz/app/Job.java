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
        VanzariBean vb = new VanzariBean();
        ArrayList<Vanzari> listaVanzari;
        try {
          listaVanzari = vb.getVanzariPerAgent(Utils.getCurrentMonth(), Utils.getCurrentYear(), 3, "admin");
            for(Vanzari al : listaVanzari) {
              if(vb.vedeEmailAgent(al.getAgent()) != null && vb.vedeEmailAgent(al.getAgent()).length() > 0) {
                double sumaPart = Double.parseDouble(al.getSumaContractePerAgent()) + Double.parseDouble(al.getDiferentaRecalculariPerAgent());
                double sumaLuna = Math.round(sumaPart - 0.19 * sumaPart);
                double sumaRamasa;
                double sumaTotala;

                if(al.getAgent().equals("mihaela")) {
                  sumaRamasa = Double.parseDouble("175000") - sumaLuna;
                  sumaTotala = Double.parseDouble("175000");
                } else {
                  sumaRamasa = Double.parseDouble("250000") - sumaLuna;
                  sumaTotala = Double.parseDouble("250000");
                }
                    new SendMailQuartz("smtp.gmail.com", 587, "contact.inovo@gmail.com", "mjqvemprrmlinexr"
                            , vb.vedeEmailAgent(al.getAgent()), "Situație vanzari final de " + Utils.getCurrentMonth() + " INOVO",
                      "Buna " + Utils.formatAgent(al.getAgent()) + ",<br/><br/>" + (sumaRamasa > 0.0? "&#128546; Nu ai atins target-ul, mai aveai inca " + Utils.formatPercentage(sumaRamasa, sumaTotala) + "%. Luna viitoare iti uram succes!": "Bravooooo &#128522;<br/><br/> Ai depasit target-ul cu " + Utils.formatPercentage(Math.abs(sumaRamasa), sumaTotala) +
                        "%" ) + ".<br/>", ((sumaRamasa > 0.0) ? "Succes": "Felicitari"));

              }
            }
        } catch (SQLException ex) {
        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quartz.app;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import quartz.utils.Utils;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alexandru.gojgar
 */
public class Job15 implements org.quartz.Job{

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
                            , vb.vedeEmailAgent(al.getAgent()), "Situație vanzari " + Utils.getCurrentMonth() + " INOVO",
                      "Buna " + Utils.formatAgent(al.getAgent()) + ",<br/><br/>Suntem la jumatatea lunii, iar pana acum ai realizat " + Utils.formatValues(sumaLuna) + " RON " +
                        "din obiectivul tau lunar de " + (al.getAgent().equals("mihaela")? "175.000": "250.000") + " RON, adica " +
                        Utils.formatPercentage(sumaLuna, sumaTotala) + "%. " + ((sumaRamasa > 0.0)? "Mai ai de atins " + Utils.formatValues(sumaRamasa) + " RON, adica " + Utils.formatPercentage(sumaRamasa, sumaTotala) +
                        "% pentru a-ti indeplini targetul.": "Ti-ai depasit target-ul cu " + Utils.formatValues(Math.abs(sumaRamasa)) + " RON, adica " + Utils.formatPercentage(Math.abs(sumaRamasa), sumaTotala) + "%.") +
                        "<br/><br/>" +
                        ((sumaRamasa < sumaTotala / 2)? "Te incurajez sa continui in acelasi ritm si sa iti concentrezi eforturile pe oportunitatile existente." : "Te incurajez sa te concentrezi pe clientii calificati produselor INOVO." )+ "<br/>"
                    , ((sumaRamasa > 0.0) ? "Succes": "Felicitari"));

              }
            }
        } catch (SQLException ex) {
        }

    }

}

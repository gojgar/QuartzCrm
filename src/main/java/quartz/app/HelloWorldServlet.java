/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quartz.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author alexandru.gojgar let's try
 */
@WebServlet(name = "HelloWorldServlet", urlPatterns = {"/HelloWorldServlet"})
public class HelloWorldServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SchedulerException, ParseException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            // mail sales
            JobDetail jobSales15 = new JobDetail();
            jobSales15.setName("jobSales15");
            jobSales15.setJobClass(Job15.class);

            JobDetail jobSales25 = new JobDetail();
            jobSales25.setName("jobSales25");
            jobSales25.setJobClass(Job25.class);

            JobDetail jobSalesLastDayMonth = new JobDetail();
            jobSalesLastDayMonth.setName("jobSalesLastDayMonth");
            jobSalesLastDayMonth.setJobClass(Job.class);

            // mail reminder
            JobDetail jobReminderMorning = new JobDetail();
            jobReminderMorning.setName("jobReminderMorning");
            jobReminderMorning.setJobClass(JobMorning.class);

            JobDetail jobReminderLunch = new JobDetail();
            jobReminderLunch.setName("jobReminderLunch");
            jobReminderLunch.setJobClass(JobLunch.class);

            // trigger sales
            CronTrigger triggerSales15 = new CronTrigger();
            triggerSales15.setName("triggerSales15");
            triggerSales15.setCronExpression(new ClientBean().getCronExprVanzari15());

            CronTrigger triggerSales25 = new CronTrigger();
            triggerSales25.setName("triggerSales25");
            triggerSales25.setCronExpression(new ClientBean().getCronExprVanzari25());

            CronTrigger triggerSalesLastDayMonth = new CronTrigger();
            triggerSalesLastDayMonth.setName("triggerSalesLastDayMonth");
            triggerSalesLastDayMonth.setCronExpression(new ClientBean().getCronExprVanzariL());

            // trigger reminder
            CronTrigger triggerReminderMorning = new CronTrigger();
            triggerReminderMorning.setName("triggerReminderMorning");
            triggerReminderMorning.setCronExpression(new ClientBean().getCronExprReminderMorning());

            CronTrigger triggerReminderLunch = new CronTrigger();
            triggerReminderLunch.setName("triggerReminderLunch");
            triggerReminderLunch.setCronExpression(new ClientBean().getCronExprReminderLunch());

            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobSales15, triggerSales15);
            scheduler.scheduleJob(jobSales25, triggerSales25);
            scheduler.scheduleJob(jobSalesLastDayMonth, triggerSalesLastDayMonth);
            scheduler.scheduleJob(jobReminderMorning, triggerReminderMorning);
            scheduler.scheduleJob(jobReminderLunch, triggerReminderLunch);

        } catch (SchedulerException ex) {
            ex.printStackTrace();
            out.print("Error1");
        } catch (ParseException ex) {
            ex.printStackTrace();
            out.print("Error2");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SchedulerException ex) {
            Logger.getLogger(HelloWorldServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(HelloWorldServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HelloWorldServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SchedulerException ex) {
            Logger.getLogger(HelloWorldServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(HelloWorldServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HelloWorldServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

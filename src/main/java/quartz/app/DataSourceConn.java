/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quartz.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author alexandru.gojgar
 */
public class DataSourceConn {
    private final Connection conn;
    private final DataSource dataSource;
    
    public DataSourceConn(DataSource dataSource, Connection conn){
        this.dataSource = dataSource;
        this.conn = conn;
    }
    
    public static DataSource getDataSource(String lookupName) {
        InitialContext ctx;
        DataSource dataSource = null;
        try {
            ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup(lookupName);
            // "jdbc/DVHEsignConnDS"
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        return dataSource;
    }
    
    public List<ArrayList<Object>> getResultsClients() throws SQLException, Exception {
        ResultSet rs = null;
        String error = null;
        String queryString = "SELECT CONCAT(a.nume,' ', a.prenume) denumire_client, a.email, b.data_montaj FROM clienti a, recalculare b WHERE a.id = b.id_client";
        List<ArrayList<Object>> continut_baza_de_date = null;
        try (PreparedStatement stmt = conn.prepareStatement(queryString)){
            continut_baza_de_date = new ArrayList<>(); 
            rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                ArrayList<Object> rand = new ArrayList<>();
                for (int coloana = 0; coloana < 3; coloana++) {
                    rand.add(rs.getObject(coloana + 1));
                }
                continut_baza_de_date.add(rand);
            }
        } catch (SQLException sqle) {
            error = "SQLException: Interogarea nu a fost posibila.";
            throw new SQLException(error);
        } catch (Exception e) {
            error = "A aparut o exceptie in timp ce se extrageau datele.";
            throw new Exception(error);
        }
        
        return continut_baza_de_date;
    }

    public List<ArrayList<Object>> getResultsEvents(String dataStart, String dataEnd) throws SQLException, Exception {
        ResultSet rs = null;
        String error = null;
        String queryString = "SELECT e.name, e.start, l.email FROM events e, login l where e.start >= '" + dataStart + "' and e.start < '" + dataEnd + "' and e.agent = l.username";
        List<ArrayList<Object>> continut_baza_de_date = null;
        System.out.println("query" + queryString);
        try (PreparedStatement stmt = conn.prepareStatement(queryString)){
            continut_baza_de_date = new ArrayList<>();
            rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                ArrayList<Object> rand = new ArrayList<>();
                for (int coloana = 0; coloana < 3; coloana++) {
                    rand.add(rs.getObject(coloana + 1));
                }
                continut_baza_de_date.add(rand);
            }
        } catch (SQLException sqle) {
            error = "SQLException: Interogarea nu a fost posibila.";
            sqle.printStackTrace();
            throw new SQLException(error);
        } catch (Exception e) {
            e.printStackTrace();
            error = "A aparut o exceptie in timp ce se extrageau datele.";
            throw new Exception(error);
        }

        return continut_baza_de_date;
    }
    public List<ArrayList<Object>> getResultsClientsFeedBack() throws SQLException, Exception {
        ResultSet rs = null;
        String error = null;
        String queryString = "SELECT CONCAT(nume,' ', prenume) denumire_client, email, id FROM clienti";
        List<ArrayList<Object>> continut_baza_de_date = null;
        try (PreparedStatement stmt = conn.prepareStatement(queryString)){
            continut_baza_de_date = new ArrayList<>(); 
            rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                ArrayList<Object> rand = new ArrayList<>();
                for (int coloana = 0; coloana < 3; coloana++) {
                    rand.add(rs.getObject(coloana + 1));
                }
                continut_baza_de_date.add(rand);
            }
        } catch (SQLException sqle) {
            error = "SQLException: Interogarea nu a fost posibila.";
            throw new SQLException(error);
        } catch (Exception e) {
            error = "A aparut o exceptie in timp ce se extrageau datele.";
            throw new Exception(error);
        }
        
        return continut_baza_de_date;
    }
    
    public boolean difData(String id) throws SQLException, Exception {
        ResultSet rs = null;
        String error = null;
        boolean rez = false;
        String queryString = "select DATEDIFF(CURDATE(),data_introducere) DIF "
                + "from clienti where id = " + id;
        try (PreparedStatement stmt = conn.prepareStatement(queryString)){
            rs = stmt.executeQuery(queryString);
            if (rs.next()) {
                //System.out.println("DIF: " + String.valueOf(rs.getObject(1)));
                if(Double.parseDouble(String.valueOf(rs.getObject(1))) == 2.0) {
                    rez = true;
                }
            }
        } catch (SQLException sqle) {
            error = "SQLException: Interogarea nu a fost posibila.";
            throw new SQLException(error);
        } catch (Exception e) {
            error = "A aparut o exceptie in timp ce se extrageau datele.";
            throw new Exception(error);
        }
        
        return rez;
    }
    
    public void adaugaEmailTrimis(String client, String email) throws SQLException, Exception {
        String error = null;
        if (conn != null) {
            try {
                PreparedStatement stmt;
                String x1 = "insert into registru_email(nume_client, email, data_actuala) "
                        + "values(?, ?, date_format(CURRENT_DATE(),'%d.%m.%Y'))";
                stmt = conn.prepareStatement(x1);
                stmt.setString(1,client);
                stmt.setString(2,email);
                stmt.executeUpdate();
                
            } catch (SQLException sqle) {
                error = "ExceptieSQL: Nu s-a putut adauga emailul.";
                conn.rollback();
                throw new SQLException(sqle);
            }
        } else {
            error = "Exceptie: Conexiunea cu baza de date a fost pierduta.";
            conn.rollback();
            throw new Exception(error);
        }
    }
    
    public String getCronExpr() throws SQLException, Exception {
        ResultSet rs = null;
        String error = null;
        String queryString = "SELECT valoare from config where nume = 'CRON_EXPR'";
        try (PreparedStatement stmt = conn.prepareStatement(queryString)){
            rs = stmt.executeQuery(queryString);
            if (rs.next()) {
                return rs.getObject(1).toString();
            }
        } catch (SQLException sqle) {
            error = "SQLException: Interogarea nu a fost posibila.";
            throw new SQLException(error);
        } catch (Exception e) {
            error = "A aparut o exceptie in timp ce se extrageau datele.";
            throw new Exception(error);
        }
        return null;
    }
    
    public String getCronExprFeedback() throws SQLException, Exception {
        ResultSet rs = null;
        String error = null;
        String queryString = "SELECT valoare from config where nume = 'CRON_EXPR_FEEDBACK'";
        try (PreparedStatement stmt = conn.prepareStatement(queryString)){
            rs = stmt.executeQuery(queryString);
            if (rs.next()) {
                return rs.getObject(1).toString();
            }
        } catch (SQLException sqle) {
            error = "SQLException: Interogarea nu a fost posibila.";
            throw new SQLException(error);
        } catch (Exception e) {
            error = "A aparut o exceptie in timp ce se extrageau datele.";
            throw new Exception(error);
        }
        return null;
    }
    public String getCronExprEvent() throws SQLException, Exception {
        ResultSet rs = null;
        String error = null;
        String queryString = "SELECT valoare from config where nume = 'CRON_EXPR_EVENT'";
        try (PreparedStatement stmt = conn.prepareStatement(queryString)){
            rs = stmt.executeQuery(queryString);
            if (rs.next()) {
                return rs.getObject(1).toString();
            }
        } catch (SQLException sqle) {
            error = "SQLException: Interogarea nu a fost posibila.";
            throw new SQLException(error);
        } catch (Exception e) {
            error = "A aparut o exceptie in timp ce se extrageau datele.";
            throw new Exception(error);
        }
        return null;
    }
}

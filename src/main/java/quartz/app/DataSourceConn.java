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

    public List<ArrayList<Object>> vedeEmails() throws SQLException, Exception {
    ResultSet rs = null;
    String error = null;
    String queryString = "select b.email from login b, locatie l where b.id = l.user_id and l.location_id <> 3 and b.username not in ('geanina', 'ioana', 'adminGhencea', 'adminJiului') and b.status <> 'N' and b.email is not null";
    List<ArrayList<Object>> continut_baza_de_date = null;
    try (PreparedStatement stmt = conn.prepareStatement(queryString)){
      continut_baza_de_date = new ArrayList<>();
      rs = stmt.executeQuery(queryString);
      while (rs.next()) {
        ArrayList<Object> rand = new ArrayList<>();
        for (int coloana = 0; coloana < 1; coloana++) {
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
    public List<ArrayList<Object>> vedeEmailAgent(String agent) throws SQLException, Exception {
      ResultSet rs = null;
      String error = null;
      String queryString = "select IFNULL(b.email, '') from login b, locatie l where b.id = l.user_id and b.username = '" + agent + "' and l.location_id <> 3 and b.username not in ('geanina', 'ioana', 'adminGhencea', 'adminJiului') and b.status <> 'N' and b.email is not null";
      List<ArrayList<Object>> continut_baza_de_date = null;
      try (PreparedStatement stmt = conn.prepareStatement(queryString)){
        continut_baza_de_date = new ArrayList<>();
        rs = stmt.executeQuery(queryString);
        while (rs.next()) {
          ArrayList<Object> rand = new ArrayList<>();
          for (int coloana = 0; coloana < 1; coloana++) {
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

    public List<ArrayList<Object>> vedeVanzariPerAgent(String luna, String an, int locationId, String agent, String denumireLocatie) throws SQLException, Exception {
      ResultSet rs = null;
      String error = null;
      String monthStart = "";
      String monthEnd = "";
      switch(luna) {
        case "ianuarie": {
          monthStart = "01";
          monthEnd = "01";
          break;
        }
        case "februarie": {
          monthStart = "02";
          monthEnd = "02";
          break;
        }
        case "martie": {
          monthStart = "03";
          monthEnd = "03";
          break;
        }
        case "aprilie": {
          monthStart = "04";
          monthEnd = "04";
          break;
        }
        case "mai": {
          monthStart = "05";
          monthEnd = "05";
          break;
        }
        case "iunie": {
          monthStart = "06";
          monthEnd = "06";
          break;
        }
        case "iulie": {
          monthStart = "07";
          monthEnd = "07";
          break;
        }
        case "august": {
          monthStart = "08";
          monthEnd = "08";
          break;
        }
        case "septembrie": {
          monthStart = "09";
          monthEnd = "09";
          break;
        }
        case "octombrie": {
          monthStart = "10";
          monthEnd = "10";
          break;
        }
        case "noiembrie": {
          monthStart = "11";
          monthEnd = "11";
          break;
        }
        case "decembrie": {
          monthStart = "12";
          monthEnd = "12";
          break;
        }
        default: {
          monthStart = "01";
          monthEnd = "12";
          break;
        }
      }

      String queryString = "SELECT IFNULL(a.suma_totala, 0) suma_totala, IFNULL(a.agent, l.username) agent"
        + ", IFNULL(a.nr_contracte_agent, 0) nr_contracte_agent, IFNULL(b.suma_restanta, 0) diferenta_valori"
        + ", IFNULL(nr_contracte_agent1, 0) nr_recalculari, IFNULL(o.nr, 0) nr_oferte"
        + ", IFNULL(rof.suma, 0) sumaTF, IFNULL(rof.nr, 0) nr_contracteTF, IFNULL(a.valoare_accesorii, 0) valoare_accesorii, IFNULL(b.diferenta_valori_accesorii, 0) diferenta_valori_accesorii "
        + "FROM " +
        "(SELECT l.username FROM login l, locatie ll " +
        "WHERE l.id = ll.user_id "+ ((locationId == 3)? "" : " AND ll.location_id = " + locationId) + ((agent.equals("admin") || agent.equals("cosmin") || agent.equals("admin" + denumireLocatie))? "": " and l.username = '" + agent + "'") + ") l " +
        "LEFT JOIN "
        + "(SELECT COUNT(*) nr, c.agent FROM clienti c "
        + "WHERE c.data_introducere>= '" + an + "-" + monthStart + "-01' "
        + "AND c.data_introducere <= CONCAT( LAST_DAY('" + an + "-" + monthEnd + "-01'), ' 23:59:59') "
        + ((locationId == 3)? "" : " AND c.location_id = " + locationId) + ((agent.equals("admin") || agent.equals("admin" + denumireLocatie))? "": ((agent.equals("cosmin"))? " and c.is_project = 'D' " : " and c.agent = '" + agent + "'")) + " GROUP BY agent) o "
        +  " on l.username = o.agent " +
        "LEFT JOIN "
        +   "(SELECT COUNT(*) nr, SUM(ro.valoare) suma, ro.agent FROM registru_oferte ro "
        +    "WHERE str_to_date(ro.data_actuala,  '%d.%m.%Y') BETWEEN str_to_date('01." + monthStart + "." + an + "',  '%d.%m.%Y') "
        +    "AND CONCAT( LAST_DAY(str_to_date('01." + monthStart + "." + an + "',  '%d.%m.%Y')), ' 23:59:59')"
        +    ((locationId == 3)? "" : " AND ro.location_id = " + locationId) + ((agent.equals("admin") || agent.equals("cosmin") || agent.equals("admin" + denumireLocatie))? "": " and ro.agent = '" + agent + "'") + " GROUP BY agent) rof "
        +    "on l.username = rof.agent "
        + "LEFT JOIN "
        + "(SELECT SUM(p.valoare) suma_totala, p.agent, SUM(p.nr) nr_contracte_agent, sum(p.valoare_accesorii) valoare_accesorii FROM "
        + "(SELECT COUNT(*) nr, STR_TO_DATE(data_actuala, '%d.%m.%Y') trr, c.Valoare, cl.agent, c.id_client, c.valoare_accesorii, "
        + "(select e.valoare_finala + e.valoare_finala_accesorii - h.valoare - h.valoare_accesorii FROM registru_contracte d, recalculare e, contract h "
        + "WHERE d.id_client = e.id_client AND d.id_client = b.id_client AND d.id_client = h.id_client and d.status = 'INCHIS' "
        + "AND c.data_introducere >= '" + an + "-" + monthStart + "-01' AND c.data_introducere <= CONCAT( LAST_DAY('" + an + "-" + monthEnd + "-01'), ' 23:59:59')) diferenta_valori "
        + "FROM registru_contracte b, contract c, clienti cl "
        + "WHERE b.id_client = c.id_client and c.id_client = cl.id and b.status = 'INCHIS' AND str_to_date(b.data_actuala,  '%d.%m.%Y') BETWEEN str_to_date('01." + monthStart + "." + an + "',  '%d.%m.%Y') "
        + "AND CONCAT( LAST_DAY(str_to_date('01." + monthStart + "." + an + "',  '%d.%m.%Y')), ' 23:59:59')"
        + ((locationId == 3)? "" : " AND cl.location_id = " + locationId) + ((agent.equals("admin") || agent.equals("admin" + denumireLocatie))? "": ((agent.equals("cosmin"))? " and cl.is_project = 'D' " : " and cl.agent = '" + agent + "'")) + " GROUP BY trr, valoare, agent, diferenta_valori, id_client, valoare_accesorii) p GROUP BY agent) a "
        + "ON l.username = a.agent "
        + "LEFT JOIN "
        + "(SELECT SUM(o.diferenta_valori) suma_restanta, o.agent, SUM(o.nr) nr_contracte_agent1, sum(o.diferenta_valori_accesorii) diferenta_valori_accesorii FROM "
        + "(SELECT COUNT(*) nr, cl.agent , "
        + "(select e.valoare_finala - h.valoare FROM registru_contracte d, recalculare e, contract h "
        + "WHERE d.id_client = e.id_client AND d.id_client = m.id_client AND d.id_client = h.id_client and d.status = 'INCHIS' "
        + "AND m.data_introducere >= '" + an + "-" + monthStart + "-01' AND m.data_introducere <= CONCAT( LAST_DAY('" + an + "-" + monthEnd + "-01'), ' 23:59:59')) diferenta_valori, "
        + "(select e.valoare_finala_accesorii - h.valoare_accesorii FROM registru_contracte d, recalculare e, contract h "
        + "WHERE d.id_client = e.id_client AND d.id_client = m.id_client AND d.id_client = h.id_client and d.status = 'INCHIS' "
        + "AND m.data_introducere >= '" + an + "-" + monthStart + "-01' AND m.data_introducere <= CONCAT( LAST_DAY('" + an + "-" + monthEnd + "-01'), ' 23:59:59')) diferenta_valori_accesorii "
        + "FROM registru_contracte b, montaj m, contract c, clienti cl "
        + "WHERE b.id_client = m.id_client AND b.id_client = c.id_client and c.id_client = cl.id and b.status = 'INCHIS' "
        + "AND m.data_introducere >= '" + an + "-" + monthStart + "-01' AND m.data_introducere <= CONCAT( LAST_DAY('" + an + "-" + monthEnd + "-01'), ' 23:59:59')"
        + ((locationId == 3)? "" : " AND cl.location_id = " + locationId) + ((agent.equals("admin") || agent.equals("admin" + denumireLocatie))? "": ((agent.equals("cosmin"))? " and cl.is_project = 'D' " : " and cl.agent = '" + agent + "'")) + " GROUP BY agent, diferenta_valori, diferenta_valori_accesorii) o GROUP BY agent) b "
        + "on l.username = b.agent";
      List<ArrayList<Object>> continut_baza_de_date = null;
      try (PreparedStatement stmt = conn.prepareStatement(queryString)){
        continut_baza_de_date = new ArrayList<>();
        rs = stmt.executeQuery(queryString);
        while (rs.next()) {
          ArrayList<Object> rand = new ArrayList<>();
          for (int coloana = 0; coloana < 10; coloana++) {
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

    public List<ArrayList<Object>> vedeTabelaShowroom(int id) throws SQLException, Exception {
      ResultSet rs = null;
      String error = null;
      String queryString = "select denumire from showroom where id = " + id;
      List<ArrayList<Object>> continut_baza_de_date = null;
      try (PreparedStatement stmt = conn.prepareStatement(queryString)){
        continut_baza_de_date = new ArrayList<>();
        rs = stmt.executeQuery(queryString);
        while (rs.next()) {
          ArrayList<Object> rand = new ArrayList<>();
          for (int coloana = 0; coloana < 1; coloana++) {
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

    public String getCronExprVanzari15() throws SQLException, Exception {
        ResultSet rs = null;
        String error = null;
        String queryString = "SELECT valoare from config where nume = 'CRON_EXPR_VANZARI_15'";
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

    public String getCronExprVanzari25() throws SQLException, Exception {
        ResultSet rs = null;
        String error = null;
        String queryString = "SELECT valoare from config where nume = 'CRON_EXPR_VANZARI_25'";
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
    public String getCronExprVanzariL() throws SQLException, Exception {
        ResultSet rs = null;
        String error = null;
        String queryString = "SELECT valoare from config where nume = 'CRON_EXPR_VANZARI_L'";
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

    public String getCronExprReminderMorning() throws SQLException, Exception {
      ResultSet rs = null;
      String error = null;
      String queryString = "SELECT valoare from config where nume = 'CRON_EXPR_REMINDER_1'";
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

    public String getCronExprReminderLunch() throws SQLException, Exception {
      ResultSet rs = null;
      String error = null;
      String queryString = "SELECT valoare from config where nume = 'CRON_EXPR_REMINDER_2'";
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

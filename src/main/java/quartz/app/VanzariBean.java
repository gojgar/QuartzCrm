package quartz.app;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VanzariBean {
  public ArrayList<Vanzari> getVanzariPerAgent(String luna, String an, int locatie, String agent) throws SQLException {
    ArrayList<Vanzari> list_client = new ArrayList<Vanzari>();
    DataSource ds = null;
    Connection conn = null;
    List<ArrayList<Object>> resultSet = null;

    try {

      ds = DataSourceConn.getDataSource("jdbc/DVHAuthConnDS");
      conn = ds.getConnection();
      conn.setAutoCommit(false);
      DataSourceConn dsConn = new DataSourceConn(ds, conn);
      String denumireLocatie = (String)dsConn.vedeTabelaShowroom(locatie).get(0).get(0);
      resultSet = dsConn.vedeVanzariPerAgent(luna, an, locatie, agent, denumireLocatie);


      for(ArrayList<Object> al : resultSet){
        Vanzari clientBean = new Vanzari();
        clientBean.setSumaContractePerAgent(al.get(0).toString());
        clientBean.setAgent(al.get(1).toString());
        clientBean.setNrContractePerAgent(al.get(2).toString());
        clientBean.setDiferentaRecalculariPerAgent(al.get(3).toString());
        clientBean.setNrRecalculariPerAgent(al.get(4).toString());
        clientBean.setSumaTotalaPerAgent(Double.parseDouble(al.get(0).toString()) + Double.parseDouble(al.get(3).toString())
          + Double.parseDouble(al.get(8).toString()) + Double.parseDouble(al.get(9).toString()));
        clientBean.setNrOferte(al.get(5).toString());
        clientBean.setSumaOferteTf(Double.parseDouble(al.get(6).toString()));
        clientBean.setNrOferteTf(al.get(7).toString());
        clientBean.setSumaAccesorii(Double.parseDouble(al.get(8).toString()));
        clientBean.setSumaDiferentaAccesorii(Double.parseDouble(al.get(9).toString()));

        list_client.add(clientBean);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      if (conn != null) conn.close();
    }
    return list_client;
  }

  public String vedeEmailAgent(String agent)  throws SQLException {
    String agentEmail = "";
    DataSource ds = null;
    Connection conn = null;
    List<ArrayList<Object>> resultSet = null;

    try {

      ds = DataSourceConn.getDataSource("jdbc/DVHAuthConnDS");
      conn = ds.getConnection();
      conn.setAutoCommit(false);
      DataSourceConn dsConn = new DataSourceConn(ds, conn);
      if(dsConn.vedeEmailAgent(agent) != null && dsConn.vedeEmailAgent(agent).size() > 0
          && dsConn.vedeEmailAgent(agent).get(0) != null && dsConn.vedeEmailAgent(agent).get(0).size() > 0) {
        agentEmail = (String) dsConn.vedeEmailAgent(agent).get(0).get(0);
      }


    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      if (conn != null) conn.close();
    }
    return agentEmail;
  }

  public ArrayList<User> vedeEmails()  throws SQLException {
    ArrayList<User> list_client = new ArrayList<User>();
    DataSource ds = null;
    Connection conn = null;
    List<ArrayList<Object>> resultSet = null;

    try {

      ds = DataSourceConn.getDataSource("jdbc/DVHAuthConnDS");
      conn = ds.getConnection();
      conn.setAutoCommit(false);
      DataSourceConn dsConn = new DataSourceConn(ds, conn);

      resultSet = dsConn.vedeEmails();
      for(ArrayList<Object> al: resultSet) {
        User user = new User();
        user.setEmail(al.get(0).toString());
        list_client.add(user);
      }


    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      if (conn != null) conn.close();
    }
    return list_client;
  }

}

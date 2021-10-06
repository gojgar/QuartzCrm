/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quartz.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author alexandru.gojgar
 */
public class ClientBean {
    public ArrayList<Client> getClient() throws SQLException {
        ArrayList<Client> list_client = new ArrayList<Client>();
        DataSource ds = null;
        Connection conn = null;
        List<ArrayList<Object>> resultSet = null;

        try {
            
            ds = DataSourceConn.getDataSource("jdbc/DVHAuthConnDS");
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            DataSourceConn dsConn = new DataSourceConn(ds, conn);
            
            resultSet = dsConn.getResultsClients();
            
            for(ArrayList<Object> al : resultSet){
                Client clientBean = new Client();
                clientBean.setClient(al.get(0).toString());
                clientBean.setEmail(al.get(1).toString());
                clientBean.setDataMontaj(al.get(2).toString());
                list_client.add(clientBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (conn != null) conn.close();
        }
        return list_client;
     }
    
    public ArrayList<Client> getClientFeedBack() throws SQLException {
        ArrayList<Client> list_client = new ArrayList<Client>();
        DataSource ds = null;
        Connection conn = null;
        List<ArrayList<Object>> resultSet = null;

        try {
            
            ds = DataSourceConn.getDataSource("jdbc/DVHAuthConnDS");
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            DataSourceConn dsConn = new DataSourceConn(ds, conn);
            
            resultSet = dsConn.getResultsClientsFeedBack();
            
            for(ArrayList<Object> al : resultSet){
                Client clientBean = new Client();
                clientBean.setClient(al.get(0).toString());
                clientBean.setEmail(al.get(1).toString());
                clientBean.setId(al.get(2).toString());
                list_client.add(clientBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (conn != null) conn.close();
        }
        return list_client;
     }
    
    public void addRowEmailRegistru(String client, String email) throws SQLException {
        DataSource ds = null;
        Connection conn = null;

        try {
            
            ds = DataSourceConn.getDataSource("jdbc/DVHAuthConnDS");
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            DataSourceConn dsConn = new DataSourceConn(ds, conn);
            
            dsConn.adaugaEmailTrimis(client, email);
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (conn != null) conn.close();
        }
     }
    
    public String getCronExpr() throws SQLException {
        DataSource ds = null;
        Connection conn = null;
        String result = null;

        try {
            
            ds = DataSourceConn.getDataSource("jdbc/DVHAuthConnDS");
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            DataSourceConn dsConn = new DataSourceConn(ds, conn);
            
            result = dsConn.getCronExpr();
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (conn != null) conn.close();
        }
        
        return result;
     }
    
    public String getCronExprFeedback() throws SQLException {
        DataSource ds = null;
        Connection conn = null;
        String result = null;

        try {
            
            ds = DataSourceConn.getDataSource("jdbc/DVHAuthConnDS");
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            DataSourceConn dsConn = new DataSourceConn(ds, conn);
            
            result = dsConn.getCronExprFeedback();
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (conn != null) conn.close();
        }
        
        return result;
     }
    
    public boolean getDays(String idClient) throws SQLException {
        DataSource ds = null;
        Connection conn = null;
        boolean result = false;

        try {
            
            ds = DataSourceConn.getDataSource("jdbc/DVHAuthConnDS");
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            DataSourceConn dsConn = new DataSourceConn(ds, conn);
            
            result = dsConn.difData(idClient);
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (conn != null) conn.close();
        }
        
        return result;
     }
}

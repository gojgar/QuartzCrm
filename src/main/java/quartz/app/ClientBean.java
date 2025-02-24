/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quartz.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author alexandru.gojgar
 */
public class ClientBean {
    public String getCronExprVanzari15() throws SQLException {
        DataSource ds = null;
        Connection conn = null;
        String result = null;

        try {

            ds = DataSourceConn.getDataSource("jdbc/DVHAuthConnDS");
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            DataSourceConn dsConn = new DataSourceConn(ds, conn);

            result = dsConn.getCronExprVanzari15();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (conn != null) conn.close();
        }

        return result;
     }

    public String getCronExprVanzari25() throws SQLException {
      DataSource ds = null;
      Connection conn = null;
      String result = null;

      try {

        ds = DataSourceConn.getDataSource("jdbc/DVHAuthConnDS");
        conn = ds.getConnection();
        conn.setAutoCommit(false);
        DataSourceConn dsConn = new DataSourceConn(ds, conn);

        result = dsConn.getCronExprVanzari25();

      } catch (Exception e) {
        e.printStackTrace();
      }finally{
        if (conn != null) conn.close();
      }

      return result;
    }

    public String getCronExprVanzariL() throws SQLException {
        DataSource ds = null;
        Connection conn = null;
        String result = null;

        try {

            ds = DataSourceConn.getDataSource("jdbc/DVHAuthConnDS");
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            DataSourceConn dsConn = new DataSourceConn(ds, conn);

            result = dsConn.getCronExprVanzariL();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (conn != null) conn.close();
        }

        return result;
     }

    public String getCronExprReminderMorning() throws SQLException {
      DataSource ds = null;
      Connection conn = null;
      String result = null;

      try {

        ds = DataSourceConn.getDataSource("jdbc/DVHAuthConnDS");
        conn = ds.getConnection();
        conn.setAutoCommit(false);
        DataSourceConn dsConn = new DataSourceConn(ds, conn);

        result = dsConn.getCronExprReminderMorning();

      } catch (Exception e) {
        e.printStackTrace();
      }finally{
        if (conn != null) conn.close();
      }

      return result;
    }

    public String getCronExprReminderLunch() throws SQLException {
      DataSource ds = null;
      Connection conn = null;
      String result = null;

      try {

        ds = DataSourceConn.getDataSource("jdbc/DVHAuthConnDS");
        conn = ds.getConnection();
        conn.setAutoCommit(false);
        DataSourceConn dsConn = new DataSourceConn(ds, conn);

        result = dsConn.getCronExprReminderLunch();

      } catch (Exception e) {
        e.printStackTrace();
      }finally{
        if (conn != null) conn.close();
      }

      return result;
    }

  }

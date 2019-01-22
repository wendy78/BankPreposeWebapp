package com.iawtr.web.dto.bdch;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConnectionTool {
	private String url = "jdbc:oracle:thin:@//a:1521/orcl";
    private String username = "jygsxk";
    private String password = "jygsxk";
    private Connection connection = null;

    public Connection getConn() {
        try {
            Class.forName("oracle.jdbc.OracleDriver").newInstance();
            connection = DriverManager.getConnection(url, username, password);
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }


    public ResultSet query(Connection conn, String sql) {
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            pStatement = conn.prepareStatement(sql);
            rs = pStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return rs;
    }

    public boolean queryUpdate(Connection conn, String sql) {
        PreparedStatement pStatement = null;
        int rs = 0;
        try {
            pStatement = conn.prepareStatement(sql);
            rs = pStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (rs > 0) {
            return true;
        }
        return false;
    }



    public static void main(String[] args) throws SQLException {
        ConnectionTool pgtool = new ConnectionTool();
        Connection myconn = pgtool.getConn();
        //pgtool.queryUpdate(myconn, "select * from dj_djb");
        ResultSet rs = pgtool.query(myconn, "select * from dj_djb");
        while(rs.next()){                       
            String name = rs.getString("slbh");
            System.out.println("�����ţ�"+name);
            myconn.close();
        }
    }

}

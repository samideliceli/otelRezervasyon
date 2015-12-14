package otelrezervasyon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dbConnection {

    private String host = "jdbc:derby://localhost:1527/otelDb";
    private String userName = "graylight";
    private String userPass = "pass";
    static Connection con;
    static Statement st;

    public void dbBaglan() {
        try {
            con = DriverManager.getConnection(host, userName, userPass);
            st = (Statement) con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(personelİslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

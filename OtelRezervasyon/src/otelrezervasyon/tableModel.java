package otelrezervasyon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import static otelrezervasyon.dbConnection.con;
import static otelrezervasyon.dbConnection.st;

public class tableModel extends DefaultTableModel {
    
    private int sutunSayisi, satirSayisi, i, satir, sutun;
    private Object[] row;
    private dbConnection db;
    ResultSet rs;
    
    public tableModel() {
        db = new dbConnection();
    }
    
    public void tabloyuOlustur(String sql, Vector v, DefaultTableModel tb) {
        try {
            tb.setRowCount(0);
            tb.setColumnCount(0);
            v = new Vector();
            db.dbBaglan();
            rs = st.executeQuery(sql);
            sutunSayisi = rs.getMetaData().getColumnCount();
            for (int s = 1; s <= sutunSayisi; s++) {
                tb.addColumn(rs.getMetaData().getColumnName(s));
            }
            while (rs.next()) {
                row = new Object[sutunSayisi];
                for (i = 1; i <= sutunSayisi; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                v.addElement(row);
                tb.addRow(row);
                satirSayisi = tb.getRowCount();
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(tableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

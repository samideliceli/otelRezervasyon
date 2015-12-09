package otelrezervasyon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import static otelrezervasyon.dbConnection.con;
import static otelrezervasyon.dbConnection.st;

public class hizmetler extends javax.swing.JPanel {

    private dbConnection db;
    private int sutunSayisi, satirSayisi, i, satir, sutun;
    private DefaultTableModel tb;
    private Object sonDeger;
    private Object[] row;
    private Object[][] deger;
    private String sutunAdi;

    public hizmetler() {
        initComponents();
        db = new dbConnection();
        db.dbBaglan();
        tb = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                if (col == 0) {
                    return false;
                }
                return true;
            }
        };
        guncelle.setEnabled(false);
        try (ResultSet rs = st.executeQuery("SELECT * FROM SERVIS")) {
            sutunSayisi = rs.getMetaData().getColumnCount();
            for (int s = 1; s <= sutunSayisi; s++) {
                tb.addColumn(rs.getMetaData().getColumnName(s));
            }

            while (rs.next()) {
                row = new Object[sutunSayisi];
                for (i = 1; i <= sutunSayisi; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                tb.addRow(row);
                servisTablosu.setModel(tb);
                satirSayisi = tb.getRowCount();
            }
            con.close();
        } catch (SQLException hata) {
            Logger.getLogger(OtelRezervasyon.class
                    .getName()).log(Level.SEVERE, null, hata);
        }
        deger = new Object[satirSayisi][sutunSayisi];
        for (satir = 0; satir < satirSayisi; satir++) {
            for (sutun = 0; sutun < sutunSayisi; sutun++) {
                deger[satir][sutun] = tb.getValueAt(satir, sutun);
            }
        }
        tb.addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                satir = e.getFirstRow();
                sutun = e.getColumn();
                TableModel model = (TableModel) e.getSource();
                sonDeger = model.getValueAt(satir, sutun);
                sutunAdi = model.getColumnName(sutun);
                if (sonDeger.equals(deger[satir][sutun])) {
                    guncelle.setEnabled(false);
                } else {
                    guncelle.setEnabled(true);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        servisTablosu = new javax.swing.JTable();
        guncelle = new javax.swing.JButton();

        servisTablosu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(servisTablosu);

        guncelle.setText("Güncelle");
        guncelle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guncelleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(guncelle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(guncelle)
                .addContainerGap(33, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void guncelleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guncelleActionPerformed
        db.dbBaglan();
        try {
            if (st.executeUpdate("UPDATE SERVIS SET " + sutunAdi + " = '" + sonDeger + "' WHERE " + sutunAdi + " = '" + deger[satir][sutun] + "'") != 0) {
                JOptionPane.showMessageDialog(null, "Veri Başarıyla Güncellendi!");
                deger[satir][sutun] = sonDeger;
            } else {
                JOptionPane.showMessageDialog(null, "Hata Oluştu!");
            }
            guncelle.setEnabled(false);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(hizmetler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_guncelleActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton guncelle;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable servisTablosu;
    // End of variables declaration//GEN-END:variables
}

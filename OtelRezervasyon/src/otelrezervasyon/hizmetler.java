package otelrezervasyon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
        tabloyuCek();
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

    public void tabloyuCek() {
        db.dbBaglan();
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
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        servisTablosu = new javax.swing.JTable();
        guncelle = new javax.swing.JButton();
        yeniServiAdi = new javax.swing.JTextField();
        yeniServisUcret = new javax.swing.JTextField();
        servisEkle = new javax.swing.JButton();

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

        servisEkle.setText("Ekle");
        servisEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servisEkleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(guncelle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(servisEkle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(yeniServisUcret)
                    .addComponent(yeniServiAdi))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(yeniServiAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(yeniServisUcret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(servisEkle)
                        .addGap(18, 18, 18)
                        .addComponent(guncelle)))
                .addContainerGap(71, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void guncelleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guncelleActionPerformed
        db.dbBaglan();
        try {
            String sql = ("UPDATE SERVIS SET " + sutunAdi + " = (?) WHERE " + sutunAdi + " = (?) ");
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, sonDeger);
            ps.setObject(2, deger[satir][sutun]);
            if (ps.executeUpdate() != 0) {
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

    private void servisEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servisEkleActionPerformed
        String servisAdi = yeniServiAdi.getText();
        String servisUcreti = yeniServisUcret.getText();
        db.dbBaglan();
        try {
            String sql = ("INSERT INTO SERVIS (ISIM,UCRET) VALUES (?,?)");
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, servisAdi);
            ps.setString(2, servisUcreti);
            ps.executeUpdate();
            yeniServiAdi.setText("");
            yeniServisUcret.setText("");
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(hizmetler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_servisEkleActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton guncelle;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton servisEkle;
    private javax.swing.JTable servisTablosu;
    private javax.swing.JTextField yeniServiAdi;
    private javax.swing.JTextField yeniServisUcret;
    // End of variables declaration//GEN-END:variables
}

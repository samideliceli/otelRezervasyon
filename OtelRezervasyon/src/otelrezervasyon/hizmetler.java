package otelrezervasyon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
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
    private boolean yenilendi;

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
                if(!yenilendi){
                    yenilendi=!yenilendi;
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
            }}
        });
    }

    public void tabloyuCek() {
        db.dbBaglan();
        yenilendi=true;
        tb.getDataVector().removeAllElements();
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
            Logger.getLogger(personelİslemleri.class
                    .getName()).log(Level.SEVERE, null, hata);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        servisTablosu = new javax.swing.JTable();
        guncelle = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        yeniServiAdi = new javax.swing.JTextField();
        yeniServisUcret = new javax.swing.JTextField();
        servisEkle = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Servisler"));

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

        jButton1.setText("Sil");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(guncelle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guncelle)
                    .addComponent(jButton1)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Servis Ekle"));

        yeniServisUcret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yeniServisUcretActionPerformed(evt);
            }
        });

        servisEkle.setText("Ekle");
        servisEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servisEkleActionPerformed(evt);
            }
        });

        jLabel1.setText("İsim");

        jLabel2.setText("Ücret");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(yeniServiAdi, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(yeniServisUcret)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(servisEkle, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 85, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addContainerGap(133, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(yeniServiAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(yeniServisUcret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(servisEkle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addContainerGap(225, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(50, Short.MAX_VALUE))
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
            String sql = ("INSERT INTO SERVIS (ISIM,UCRET,SERVIS_ID) VALUES (?,?,?)");
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, servisAdi);
            ps.setString(2, servisUcreti);
            ps.setInt(3, 2*Integer.valueOf(servisUcreti));
            ps.executeUpdate();
            yeniServiAdi.setText("");
            yeniServisUcret.setText("");
            tabloyuCek();
          
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(hizmetler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_servisEkleActionPerformed

    private void yeniServisUcretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniServisUcretActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yeniServisUcretActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton guncelle;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton servisEkle;
    private javax.swing.JTable servisTablosu;
    private javax.swing.JTextField yeniServiAdi;
    private javax.swing.JTextField yeniServisUcret;
    // End of variables declaration//GEN-END:variables
}

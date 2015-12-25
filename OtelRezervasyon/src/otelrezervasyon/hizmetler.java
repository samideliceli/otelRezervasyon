package otelrezervasyon;

import java.awt.event.KeyAdapter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import static otelrezervasyon.dbConnection.con;

public class hizmetler extends javax.swing.JPanel {
    
    private dbConnection db;
    private int sutunSayisi, satirSayisi, i, satir, sutun;
    private DefaultTableModel tb;
    private Object sonDeger;
    private Object[] row;
    private TableModelListener tml;
    private String sutunAdi, sql, servis_id;
    private KeyAdapter adapter, adapter2;
    private Vector veri;
    private tableModel t;
    private ResultSet rs;
    
    public hizmetler() {
        initComponents();
        db = new dbConnection();
        t = new tableModel();
        veri = new Vector();
        sql = "SELECT * FROM SERVIS";
        servisEkle.setEnabled(false);
        guncelle.setEnabled(false);
        sil.setEnabled(false);
        sifirla.setEnabled(false);
        adapter = new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                super.keyReleased(evt);
                if (yeniServiAdi.getText().equals("") || yeniServisUcret.getText().equals("")) {
                    servisEkle.setEnabled(false);
                    sifirla.setEnabled(false);
                } else {
                    servisEkle.setEnabled(true);
                    sifirla.setEnabled(true);
                }
            }
        };
        
        yeniServiAdi.addKeyListener(adapter);
        yeniServisUcret.addKeyListener(adapter);
        tb = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        
        t.tabloyuOlustur(sql, veri, tb);
        servisTablosu.setModel(tb);
        
    }
    
    public boolean sayiMi(Object input) {
        try {
            Integer.parseInt(input.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        servisTablosu = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        servisEkle = new javax.swing.JButton();
        yeniServiAdi = new javax.swing.JTextField();
        yeniServisUcret = new javax.swing.JTextField();
        guncelle = new javax.swing.JButton();
        sil = new javax.swing.JButton();
        sifirla = new javax.swing.JButton();

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
        servisTablosu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                servisTablosuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(servisTablosu);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Servis İşlemleri"));

        jLabel1.setText("İsim");

        jLabel2.setText("Ücret");

        servisEkle.setText("Ekle");
        servisEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servisEkleActionPerformed(evt);
            }
        });

        yeniServisUcret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yeniServisUcretActionPerformed(evt);
            }
        });

        guncelle.setText("Güncelle");
        guncelle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guncelleActionPerformed(evt);
            }
        });

        sil.setText("Seçili Satırı Sil");
        sil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                silActionPerformed(evt);
            }
        });

        sifirla.setText("İşlemleri Sıfırla");
        sifirla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sifirlaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(yeniServisUcret, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(40, 40, 40)
                                .addComponent(yeniServiAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(servisEkle, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(guncelle)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sifirla, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yeniServiAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(yeniServisUcret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(servisEkle)
                    .addComponent(guncelle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sifirla)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void servisEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servisEkleActionPerformed
        String servisAdi = yeniServiAdi.getText();
        String servisUcreti = yeniServisUcret.getText();
        db.dbBaglan();
        try {
            String sql = ("INSERT INTO SERVIS (SERVIS,UCRET) VALUES (?,?)");
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, servisAdi);
            ps.setString(2, servisUcreti);
            if (sayiMi(servisUcreti)) {
                ps.executeUpdate();
            } else {
                JOptionPane.showMessageDialog(null, "Lütfen Ücrete Sayısal Bir Değer Giriniz!");
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(hizmetler.class.getName()).log(Level.SEVERE, null, ex);
        }
        sifirlaActionPerformed(evt);
    }//GEN-LAST:event_servisEkleActionPerformed

    private void yeniServisUcretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniServisUcretActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yeniServisUcretActionPerformed

    private void servisTablosuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_servisTablosuMouseClicked
        try {
            db.dbBaglan();
            sil.setEnabled(true);
            String gorevSql = " Select * From SERVIS ";
            PreparedStatement ps = con.prepareStatement(gorevSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.executeQuery();
            ps.getResultSet().absolute(servisTablosu.getSelectedRow() + 1);
            yeniServiAdi.setText(ps.getResultSet().getString("SERVIS"));
            yeniServisUcret.setText(ps.getResultSet().getString("UCRET"));
            servis_id = ps.getResultSet().getString("SERVIS_ID");
            sifirla.setEnabled(true);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(personelİslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        guncelle.setEnabled(true);
    }//GEN-LAST:event_servisTablosuMouseClicked

    private void guncelleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guncelleActionPerformed
        db.dbBaglan();
        String sql2 = ("UPDATE SERVIS SET SERVIS ='" + yeniServiAdi.getText() + "', UCRET = " + Integer.valueOf(yeniServisUcret.getText()) + " WHERE SERVIS_ID= " + servis_id + "");
        try {
            PreparedStatement ps = con.prepareStatement(sql2);
            
            if (ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "\"Mesaj\", \"Veri Başarıyla Güncellendi!");
                
            } else {
                JOptionPane.showMessageDialog(null, "\"Mesaj\", \"Hata Oluştu!");
            }
            sifirlaActionPerformed(evt);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(gorevEkle.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }//GEN-LAST:event_guncelleActionPerformed

    private void sifirlaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sifirlaActionPerformed
        guncelle.setEnabled(false);
        yeniServiAdi.setText("");
        yeniServisUcret.setText("");
        servisEkle.setEnabled(false);
        sifirla.setEnabled(false);
        sil.setEnabled(false);
        t.tabloyuOlustur(sql, veri, tb);
    }//GEN-LAST:event_sifirlaActionPerformed

    private void silActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_silActionPerformed
        db.dbBaglan();
        String sql2 = ("DELETE FROM SERVIS WHERE SERVIS_ID= " + servis_id + "");
        try {
            PreparedStatement ps = con.prepareStatement(sql2);
            
            if (ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "\"Mesaj\", \"Veri Başarıyla silindi!");
                
            } else {
                JOptionPane.showMessageDialog(null, "\"Mesaj\", \"Hata Oluştu!");
            }
            sifirlaActionPerformed(evt);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(gorevEkle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_silActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton guncelle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton servisEkle;
    private javax.swing.JTable servisTablosu;
    private javax.swing.JButton sifirla;
    private javax.swing.JButton sil;
    private javax.swing.JTextField yeniServiAdi;
    private javax.swing.JTextField yeniServisUcret;
    // End of variables declaration//GEN-END:variables
}

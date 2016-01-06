package otelrezervasyon;

import java.awt.event.KeyAdapter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static otelrezervasyon.dbConnection.con;

public class demirbaslar extends javax.swing.JPanel {

    private dbConnection db;
    private tableModel t;
    private DefaultTableModel tb;
    private Vector v;
    private String sql, d_id;

    public demirbaslar() {
        initComponents();
        db = new dbConnection();
        t = new tableModel();
        v = new Vector();
        tb = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        KeyAdapter adapter = new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                super.keyReleased(evt);
                if (demirbasAdi.getText().equals("") || demribasUcreti.getText().equals("")) {
                    demirbasEkle.setEnabled(false);
                    sifirla.setEnabled(false);
                } else {
                    demirbasEkle.setEnabled(true);
                    sifirla.setEnabled(true);
                }
            }
        };

        demirbasAdi.addKeyListener(adapter);
        demribasUcreti.addKeyListener(adapter);
        sifirla.setEnabled(false);
        demirbasEkle.setEnabled(false);
        guncelle.setEnabled(false);
        sil.setEnabled(false);
        sql = "SELECT * FROM DEMIRBASLAR";
        t.tabloyuOlustur(sql, v, tb);
        demirbasTablosu.setModel(tb);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        sifirla = new javax.swing.JButton();
        sil = new javax.swing.JButton();
        guncelle = new javax.swing.JButton();
        demirbasEkle = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        demribasUcreti = new javax.swing.JTextField();
        demirbasAdi = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        demirbasTablosu = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Demirbaş Kayıt"));

        sifirla.setText("İşlemleri Sfırıla");
        sifirla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sifirlaActionPerformed(evt);
            }
        });

        sil.setText("Seçili Satırı Sil");
        sil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                silActionPerformed(evt);
            }
        });

        guncelle.setText("Güncelle");
        guncelle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guncelleActionPerformed(evt);
            }
        });

        demirbasEkle.setText("Ekle");
        demirbasEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                demirbasEkleActionPerformed(evt);
            }
        });

        jLabel2.setText("Bedel :");

        jLabel1.setText("İsim :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(40, 40, 40))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(demirbasEkle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(guncelle)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(demirbasAdi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(demribasUcreti, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sil, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sifirla, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 30, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(demirbasAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(demribasUcreti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guncelle)
                    .addComponent(demirbasEkle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sil, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sifirla, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Demirbaşlar"));

        demirbasTablosu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Demirbaş", "Ücreti"
            }
        ));
        demirbasTablosu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                demirbasTablosuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(demirbasTablosu);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void demirbasEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_demirbasEkleActionPerformed
        String demirbas = demirbasAdi.getText();
        String ucret = demribasUcreti.getText();
        String sql = "INSERT INTO DEMIRBASLAR (ISIM,BEDEL) VALUES (?,?)";
        db.dbBaglan();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, demirbas);
            ps.setObject(2, ucret);
            if (sayiMi(ucret)) {
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Demirbaş Eklendi.");
            } else {
                JOptionPane.showMessageDialog(null, "Lütfen Ücrete Sayısal Bir Değer Giriniz!");
            }
            demirbasAdi.setText("");
            demribasUcreti.setText("");
            con.close();
            sifirlaActionPerformed(evt);
        } catch (SQLException ex) {
            Logger.getLogger(demirbaslar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_demirbasEkleActionPerformed

    private void sifirlaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sifirlaActionPerformed
        guncelle.setEnabled(false);
        demirbasAdi.setText("");
        demribasUcreti.setText("");
        demirbasEkle.setEnabled(false);
        sifirla.setEnabled(false);
        sil.setEnabled(false);
        t.tabloyuOlustur(sql, v, tb);
    }//GEN-LAST:event_sifirlaActionPerformed

    private void silActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_silActionPerformed
        db.dbBaglan();
        String sql = ("DELETE FROM DEMIRBASLAR WHERE DEMIRBAS_ID= " + d_id + "");
        try {
            PreparedStatement ps = con.prepareStatement(sql);

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

    private void demirbasTablosuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_demirbasTablosuMouseClicked
        try {
            db.dbBaglan();
            sil.setEnabled(true);
            String d = " Select * From DEMIRBASLAR ";
            PreparedStatement ps = con.prepareStatement(d, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.executeQuery();
            ps.getResultSet().absolute(demirbasTablosu.getSelectedRow() + 1);
            demirbasAdi.setText(ps.getResultSet().getString("ISIM"));
            demribasUcreti.setText(ps.getResultSet().getString("BEDEL"));
            d_id = ps.getResultSet().getString("DEMIRBAS_ID");
            sifirla.setEnabled(true);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(personelİslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        guncelle.setEnabled(true);
    }//GEN-LAST:event_demirbasTablosuMouseClicked

    private void guncelleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guncelleActionPerformed
        db.dbBaglan();
        String sql = ("UPDATE DEMIRBASLAR SET ISIM ='" + demirbasAdi.getText() + "', BEDEL = " + Integer.valueOf(demribasUcreti.getText()) + " WHERE DEMIRBAS_ID= " + d_id + "");
        try {
            PreparedStatement ps = con.prepareStatement(sql);

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
    public boolean sayiMi(Object input) {
        try {
            Integer.parseInt(input.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField demirbasAdi;
    private javax.swing.JButton demirbasEkle;
    private javax.swing.JTable demirbasTablosu;
    private javax.swing.JTextField demribasUcreti;
    private javax.swing.JButton guncelle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton sifirla;
    private javax.swing.JButton sil;
    // End of variables declaration//GEN-END:variables
}

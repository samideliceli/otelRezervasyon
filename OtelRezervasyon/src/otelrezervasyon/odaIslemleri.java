package otelrezervasyon;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static otelrezervasyon.dbConnection.con;
import static otelrezervasyon.dbConnection.st;

public class odaIslemleri extends javax.swing.JPanel {

    private dbConnection db;

    public odaIslemleri() {
        initComponents();
        db = new dbConnection();
        demirbasEkle.setEnabled(false);
        hasarlıDemirbaslar.setEnabled(false);
        servisTurleri.setEnabled(false);
        demirbasTurleri.setEnabled(false);
        servisleriCek();
        demirbaslariCek();
        try {
            db.dbBaglan();
            bosOdalariDoldur();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(odaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void servisleriCek() {
        try {
            db.dbBaglan();
            String servisler = ("SELECT SERVIS FROM SERVIS");
            PreparedStatement ps = con.prepareStatement(servisler, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.executeQuery();
            while (ps.getResultSet().next()) {
                servisTurleri.addItem(ps.getResultSet().getString("SERVIS"));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(muhasebe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void bosOdalariDoldur() throws SQLException {

        Color background;

        int eskiKat = 0;
        try {
            String sql = "SELECT oda_no,kat,tip_id FROM oda";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                final String odaNo = rs.getString("oda_no");
                int kat = Integer.valueOf(odaNo.substring(0, 1));

                int sira = Integer.valueOf(odaNo) - (kat * 100);

                if (eskiKat != kat) {
                    JLabel katText = new JLabel();
                    katText.setText("KAT " + kat);
                    katText.setFont(new Font("Century Schoolbook L", Font.PLAIN, 12));
                    katText.setBounds(15, 60 * (kat - 1), 55, 30);
                    katText.setForeground(new Color(33, 41, 47));
                    eskiKat = kat;
                    jPanel7.add(katText);
                }

                final boolean musBos = odaBosmu(Integer.valueOf(odaNo), "musteri_otel_bilgileri");

                if (musBos) {
                    background = new Color(91, 155, 30);
                } else {
                    background = new Color(189, 54, 47);
                }

                final String odaTip = rs.getString("tip_id");
                int fiyat = 0;
                String tur = "---";
                int kapasite = 0;

                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery("SELECT fiyat,kapasite FROM oda_tipi WHERE tip_id=" + odaTip);

                try {
                    while (rs2.next()) {
                        fiyat = rs2.getInt("fiyat");
                        kapasite = rs2.getInt("kapasite");

                    }
                } finally {
                    rs2.close();
                }

                final int a = fiyat;
                final int kapasiteOda = kapasite;
                final String turOda = tur;
                ActionListener ac = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        odano.setText("Oda No : " + odaNo);
                        demirbasEkle.setEnabled(true);
                        demirbasTurleri.setEnabled(true);
                        if (musBos) {
                            hasarlıDemirbaslar.setEnabled(false);
                            servisTurleri.setEnabled(false);
                            jLabel1.setText("Oda Durumu : BOŞ");
                        } else {
                            jLabel1.setText("Oda Durumu : DOLU");
                            hasarlıDemirbaslar.setEnabled(true);
                            servisTurleri.setEnabled(true);
                            String sql = "SELECT DEMIRBAS FROM ODADEMIRBASAITLIK WHERE ODA_NO = (?)";
                            try {
                                db.dbBaglan();
                                PreparedStatement ps = con.prepareStatement(sql);
                                ps.setObject(1, odaNo);
                                ps.executeQuery();
                                hasarlıDemirbaslar.removeAllItems();
                                while (ps.getResultSet().next()) {
                                    hasarlıDemirbaslar.addItem(ps.getResultSet().getString("DEMIRBAS"));
                                }
                                con.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(odaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                };

                JButton oda = new JButton();
                oda.setText(odaNo);
                oda.setForeground(Color.WHITE);
                oda.setBackground(background);
                oda.setBounds((55 * sira) + 10, 30 + ((kat - 1) * 60), 55, 30);
                jPanel7.add(oda);
                oda.addActionListener(ac);
            }

        } catch (SQLException ex) {
            Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean odaBosmu(int odaNo, String tablo) {

        st = dbConnection.getSt();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        ResultSet rs3 = null;

        java.sql.Date sqldate2 = null, sqldate = null;
        try {
            Date date = new Date();
            String date2 = formatter.format(date.getTime());
            Date datex = formatter.parse(date2);
            sqldate = new java.sql.Date(datex.getTime());

        } catch (ParseException ex) {
            Logger.getLogger(musteriIslemleri.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Statement st2 = con.createStatement();

            st2 = dbConnection.getCon().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs3 = st2.executeQuery("SELECT oda_no FROM musteri_otel_bilgileri where ("
                    + "baslangic_tarihi >= '" + sqldate + "' AND bitis_tarihi <='" + sqldate + "') and oda_no=" + odaNo);

            int i = 0;
            while (rs3.next()) {
                i++;

            }

            return i == 0;

        } catch (SQLException ex) {
            Logger.getLogger(musteriEkle.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

    public void demirbaslariCek() {
        try {
            db.dbBaglan();
            String demirbaslar = ("SELECT ISIM FROM DEMIRBASLAR");
            PreparedStatement ps = con.prepareStatement(demirbaslar, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.executeQuery();
            while (ps.getResultSet().next()) {
                demirbasTurleri.addItem(ps.getResultSet().getString("ISIM"));
            }
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(muhasebe.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        odano = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        demirbasTurleri = new javax.swing.JComboBox();
        demirbasEkle = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        servisTurleri = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        hasarlıDemirbaslar = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        jMenuItem1.setText("jMenuItem1");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Oda Listesi"));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 302, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Oda İşlemleri"));

        odano.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        odano.setText("Oda No : ");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Demirbaş Ekle"));

        demirbasEkle.setText("Ekle");
        demirbasEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                demirbasEkleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(demirbasTurleri, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(demirbasEkle, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(demirbasTurleri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(demirbasEkle))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Servis Ekle"));

        servisTurleri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servisTurleriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(servisTurleri, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(servisTurleri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Hasar Ekle"));

        hasarlıDemirbaslar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hasarlıDemirbaslarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hasarlıDemirbaslar, 0, 104, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(hasarlıDemirbaslar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(odano)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(odano, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void servisTurleriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servisTurleriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_servisTurleriActionPerformed

    private void demirbasEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_demirbasEkleActionPerformed
        db.dbBaglan();
        String sql = "INSERT INTO ODADEMIRBASAITLIK (ODA_NO,DEMIRBAS) VALUES (?,?)";
        String sql2 = "SELECT oda_no,kat,tip_id FROM oda";
        String sql3 = "SELECT DEMIRBAS FROM ODADEMIRBASAITLIK WHERE ODA_NO = (?)";

        try {
            ResultSet rs = st.executeQuery(sql2);
            while (rs.next()) {
                final String odaNo = rs.getString("oda_no");
                PreparedStatement ps = con.prepareStatement(sql);
                PreparedStatement ps2 = con.prepareStatement(sql3);
                ps.setObject(1, odaNo);
                ps.setObject(2, demirbasTurleri.getSelectedItem());
                ps2.setObject(1, odaNo);
                ps2.executeQuery();
                hasarlıDemirbaslar.removeAllItems();
                while (ps2.getResultSet().next()) {

                    hasarlıDemirbaslar.addItem(ps2.getResultSet().getString("DEMIRBAS"));
                }
                if (ps.executeUpdate() != 0) {
                    JOptionPane.showMessageDialog(null, "Demirbaş Odaya Eklendi!");
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Hata!");
                    break;
                }
            }
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(odaIslemleri.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_demirbasEkleActionPerformed

    private void hasarlıDemirbaslarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hasarlıDemirbaslarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hasarlıDemirbaslarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton demirbasEkle;
    private javax.swing.JComboBox demirbasTurleri;
    private javax.swing.JComboBox hasarlıDemirbaslar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel odano;
    private javax.swing.JComboBox servisTurleri;
    // End of variables declaration//GEN-END:variables
}

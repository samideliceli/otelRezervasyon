package otelrezervasyon;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static otelrezervasyon.dbConnection.con;
import static otelrezervasyon.dbConnection.st;

public class odaIslemleri extends javax.swing.JPanel {
    
    private dbConnection db;
    private DefaultListModel dlm, dlm2, dlm3;
    
    public odaIslemleri() {
        initComponents();
        db = new dbConnection();
        dlm = new DefaultListModel();
        dlm2 = new DefaultListModel();
        dlm3 = new DefaultListModel();
        odaDemirbaslar.setModel(dlm);
        odaServisler.setModel(dlm2);
        hasarListesi.setModel(dlm3);
        demirbasEkle.setEnabled(false);
        demirbasTurleri.setEnabled(false);
        servisTurleri.setEnabled(false);
        odaDemirbaslar.setEnabled(false);
        odaServisler.setEnabled(false);
        odayaHizmetEkle.setEnabled(false);
        hasarListesi.setEnabled(false);
        hasariEkle.setEnabled(false);
        hasariSil.setEnabled(false);
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
                ActionListener ac = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        hasariEkle.setEnabled(false);
                        hasariSil.setEnabled(false);
                        odaDemirbaslar.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if (odaDemirbaslar.isSelectionEmpty()) {
                                    hasariEkle.setEnabled(false);
                                } else {
                                    hasariEkle.setEnabled(true);
                                }
                            }
                        });
                        hasarListesi.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if (hasarListesi.isSelectionEmpty()) {
                                    hasariSil.setEnabled(false);
                                } else {
                                    hasariSil.setEnabled(true);
                                }
                            }
                        });
                        odano.setText("Oda No : " + odaNo);
                        demirbasEkle.setEnabled(true);
                        demirbasTurleri.setEnabled(true);
                        String sql = "SELECT DEMIRBAS FROM ODADEMIRBASAITLIK WHERE ODA_NO = (?)";
                        String servis = servisTurleri.getSelectedItem().toString();
                        String sql2 = "SELECT SERVIS FROM SERVIS_KULLANAN WHERE ODA_NO =  (?)";
                        String sql3 = "SELECT ISIM FROM HASAR_LISTESI WHERE ODA_NO = (?)";
                        
                        if (musBos) {
                            try {
                                odaDemirbaslar.setEnabled(false);
                                odaServisler.setEnabled(false);
                                odayaHizmetEkle.setEnabled(false);
                                servisTurleri.setEnabled(false);
                                hasarListesi.setEnabled(false);
                                dlm.removeAllElements();
                                dlm2.removeAllElements();
                                dlm3.removeAllElements();
                                jLabel1.setText("Oda Durumu : BOŞ");
                                db.dbBaglan();
                                PreparedStatement ps = con.prepareStatement(sql);
                                ps.setObject(1, odaNo);
                                ps.executeQuery();
                                while (ps.getResultSet().next()) {
                                    dlm.addElement(ps.getResultSet().getString("DEMIRBAS"));
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(odaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            dlm.removeAllElements();
                            dlm2.removeAllElements();
                            dlm3.removeAllElements();
                            odayaHizmetEkle.setEnabled(true);
                            odaDemirbaslar.setEnabled(true);
                            odaServisler.setEnabled(true);
                            servisTurleri.setEnabled(true);
                            hasarListesi.setEnabled(true);
                            jLabel1.setText("Oda Durumu : DOLU");
                            try {
                                db.dbBaglan();
                                PreparedStatement ps = con.prepareStatement(sql);
                                ps.setObject(1, odaNo);
                                ps.executeQuery();
                                while (ps.getResultSet().next()) {
                                    dlm.addElement(ps.getResultSet().getString("DEMIRBAS"));
                                }
                                PreparedStatement ps2 = con.prepareStatement(sql2);
                                ps2.setObject(1, odaNo);
                                ps2.executeQuery();
                                while (ps2.getResultSet().next()) {
                                    dlm2.addElement(ps2.getResultSet().getString("SERVIS"));
                                }
                                PreparedStatement ps3 = con.prepareStatement(sql3);
                                ps3.setObject(1, odaNo);
                                ps3.executeQuery();
                                while (ps3.getResultSet().next()) {
                                    dlm3.addElement(ps3.getResultSet().getString("ISIM"));
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
                
                oda.setBounds(
                        (55 * sira) + 10, 30 + ((kat - 1) * 60), 55, 30);
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
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        demirbasTurleri = new javax.swing.JComboBox();
        demirbasEkle = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        odaDemirbaslar = new javax.swing.JList();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        hasarListesi = new javax.swing.JList();
        hasariEkle = new javax.swing.JButton();
        hasariSil = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        odayaHizmetEkle = new javax.swing.JButton();
        servisTurleri = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        odaServisler = new javax.swing.JList();
        jPanel11 = new javax.swing.JPanel();

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Oda İşlemleri"));

        odano.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        odano.setText("Oda No :  ODA SEÇİNİZ");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Odaya Demirbaş Ekle"));

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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(demirbasTurleri, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(demirbasEkle, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(demirbasTurleri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(demirbasEkle)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Demirbaşlar"));

        jScrollPane1.setViewportView(odaDemirbaslar);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Hasarlı Demirbaşlar"));

        jScrollPane3.setViewportView(hasarListesi);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        hasariEkle.setText("->");
        hasariEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hasariEkleActionPerformed(evt);
            }
        });

        hasariSil.setText("Sil");
        hasariSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hasariSilActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hasariEkle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hasariSil)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(hasariEkle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hasariSil)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Odaya Hizmet Ver"));

        odayaHizmetEkle.setText("Ekle");
        odayaHizmetEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                odayaHizmetEkleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(odayaHizmetEkle, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                    .addComponent(servisTurleri, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(servisTurleri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(odayaHizmetEkle)
                .addGap(40, 40, 40))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Alınan Hizmetler"));

        jScrollPane2.setViewportView(odaServisler);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(odano)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(odano, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void demirbasEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_demirbasEkleActionPerformed
        db.dbBaglan();
        String sql = "INSERT INTO ODADEMIRBASAITLIK (ODA_NO,DEMIRBAS) VALUES (?,?)";
        try {
            boolean varMi = dlm.contains(demirbasTurleri.getSelectedItem());
            String odaNo = odano.getText().substring(9, 12);
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, odaNo);
            ps.setObject(2, demirbasTurleri.getSelectedItem());
            if (!varMi) {
                if (ps.executeUpdate() != 0) {
                    JOptionPane.showMessageDialog(null, "Demirbaş Odaya Eklendi!");
                    dlm.addElement(demirbasTurleri.getSelectedItem());
                } else {
                    JOptionPane.showMessageDialog(null, "Hata!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Demirbaş Odada Zaten Mevcut!");
            }
            
            con.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(odaIslemleri.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_demirbasEkleActionPerformed

    private void odayaHizmetEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_odayaHizmetEkleActionPerformed
        db.dbBaglan();
        String odaNo = odano.getText().substring(9, 12);
        String servis = servisTurleri.getSelectedItem().toString();
        String sql = "INSERT INTO SERVIS_KULLANAN (SERVIS,ODA_NO) VALUES (?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, servis);
            ps.setObject(2, odaNo);
            if (ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Servis Odaya Eklendi.");
                dlm2.addElement(servisTurleri.getSelectedItem());
            } else {
                JOptionPane.showConfirmDialog(null, "Servis Eklenirken Hata Oluştu.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(odaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_odayaHizmetEkleActionPerformed

    private void hasariEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hasariEkleActionPerformed
        try {
            db.dbBaglan();
            String demirbas = odaDemirbaslar.getSelectedValue().toString();
            String odaNo = odano.getText().substring(9, 12);
            String sql = "INSERT INTO HASAR_LISTESI (ODA_NO,ISIM) VALUES (?,?)";
            boolean varMi = dlm3.contains(demirbas);
            if (!varMi) {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setObject(1, odaNo);
                ps.setObject(2, demirbas);
                ps.executeUpdate();
                dlm3.addElement(demirbas);
            } else {
                JOptionPane.showMessageDialog(null, "Demirbaş Zaten Hasar Listesinde!");
            }
            
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(odaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_hasariEkleActionPerformed

    private void hasariSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hasariSilActionPerformed
        try {
            db.dbBaglan();
            String demirbas = hasarListesi.getSelectedValue().toString();
            String odaNo = odano.getText().substring(9, 12);
            String sql = "DELETE FROM HASAR_LISTESI WHERE ISIM = (?) AND ODA_NO = (?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, demirbas);
            ps.setObject(2, odaNo);
            ps.executeUpdate();
            dlm3.removeElement(demirbas);
            hasariSil.setEnabled(false);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(odaIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_hasariSilActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton demirbasEkle;
    private javax.swing.JComboBox demirbasTurleri;
    private javax.swing.JList hasarListesi;
    private javax.swing.JButton hasariEkle;
    private javax.swing.JButton hasariSil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList odaDemirbaslar;
    private javax.swing.JList odaServisler;
    private javax.swing.JLabel odano;
    private javax.swing.JButton odayaHizmetEkle;
    private javax.swing.JComboBox servisTurleri;
    // End of variables declaration//GEN-END:variables
}

package otelrezervasyon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import static otelrezervasyon.dbConnection.con;
import static otelrezervasyon.dbConnection.st;

public class muhasebe extends javax.swing.JPanel {

    private dbConnection db;
    private DefaultTableModel tbGelir, tbGider;
    private tableModel t;
    private Object[] row;
    private int gesutunSayisi, gesatirSayisi, i, gisutunSayisi, gisatirSayisi, gunlukToplamGelir = 0, gunlukToplamGider = 0, aylikToplamGelir = 0, aylikToplamGider = 0;
    private Vector gelir, gider;
    private String sql, sql2;
    private Date gununTarihi;
    private java.sql.Date sqldate, asqldate;
    private ResultSet rs, rs2, rs3, rs4;
    private KeyAdapter adapter;
    private SimpleDateFormat formatter;

    public muhasebe() {
        initComponents();
        gununTarihi = new Date();
        sqldate = new java.sql.Date(gununTarihi.getTime());

        System.out.println(asqldate);
        db = new dbConnection();
        t = new tableModel();
        sql = "SELECT * FROM GELIR";
        sql2 = "SELECT * FROM GIDER";
        gunlukGelir.setEditable(false);
        aylikGelir.setEditable(false);
        gunlukGider.setEditable(false);
        aylikGider.setEditable(false);
        gelirEkle.setEnabled(false);
        giderEkle.setEnabled(false);
        gelirListele.setEnabled(false);
        giderListele.setEnabled(false);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        gelirilkTarih.setFormats(dateFormat);
        gelirikinciTarih.setFormats(dateFormat);
        gelirGiderCek();
        adapter = new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                super.keyReleased(evt);
                if (gelirTutar.getText().equals("")) {
                    gelirEkle.setEnabled(false);
                } else {
                    gelirEkle.setEnabled(true);
                }
                if (giderTutar.getText().equals("")) {
                    giderEkle.setEnabled(false);
                } else {
                    giderEkle.setEnabled(true);
                }
            }
        };
        ActionListener acl = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (gelirilkTarih.getDate() == null || gelirikinciTarih.getDate() == null) {
                    gelirListele.setEnabled(false);
                } else {
                    gelirListele.setEnabled(true);
                }
            }
        };

        ActionListener acl2 = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (giderilkTarih.getDate() == null || giderikinciTarih.getDate() == null) {
                    giderListele.setEnabled(false);
                } else {
                    giderListele.setEnabled(true);
                }
            }
        };

        gelirilkTarih.addActionListener(acl);
        gelirikinciTarih.addActionListener(acl);

        giderListele.addActionListener(acl2);
        giderikinciTarih.addActionListener(acl2);
        giderTutar.addKeyListener(adapter);
        gelirTutar.addKeyListener(adapter);
        tbGelir = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                if (col == 0 || col == 1) {
                    return false;
                }
                return true;
            }
        };
        tbGider = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                if (col == 0 || col == 1) {
                    return false;
                }
                return true;
            }
        };
        turleriCek();
        t.tabloyuOlustur(sql, gelir, tbGelir);
        gelirTablosu.setModel(tbGelir);
        t.tabloyuOlustur(sql2, gider, tbGider);
        giderTablosu.setModel(tbGider);
    }

    public void turleriCek() {
        try {
            db.dbBaglan();

            String gelirTipleri = ("SELECT * FROM GELIR_TIP");
            PreparedStatement ps = con.prepareStatement(gelirTipleri, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            String giderTipleri = ("SELECT * FROM GIDER_TIP");
            PreparedStatement ps2 = con.prepareStatement(giderTipleri, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.executeQuery();
            ps2.executeQuery();
            while (ps.getResultSet().next()) {
                gelirTur.addItem(ps.getResultSet().getString("GELIR_TURU"));
            }
            while (ps2.getResultSet().next()) {
                giderTur.addItem(ps2.getResultSet().getString("GIDER_TURU"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(muhasebe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gelirGiderCek() {
        try {
            formatter = new SimpleDateFormat("dd-MM-yyyy");
            db.dbBaglan();
            rs = st.executeQuery("SELECT * FROM GELIR WHERE TARIH ='" + sqldate + "'");
            while (rs.next()) {
                gunlukToplamGelir = gunlukToplamGelir + rs.getInt("GELIR");
            }
            gunlukGelir.setText(String.valueOf(gunlukToplamGelir) + " TL");
            Statement st2 = con.createStatement();
            rs2 = st2.executeQuery("SELECT * FROM GIDER WHERE TARIH ='" + sqldate + "'");
            while (rs2.next()) {
                gunlukToplamGider = gunlukToplamGider + rs2.getInt("GIDER");
            }
            gunlukGider.setText(String.valueOf(gunlukToplamGider) + " TL");
            Statement st3 = con.createStatement();
            String s = sqldate.toString().substring(0, 8);
            String tarih = s.concat("01");
            Date date = formatter.parse(tarih);
            asqldate = new java.sql.Date(date.getTime());
            rs3 = st3.executeQuery("SELECT * FROM GELIR WHERE TARIH >='" + asqldate + "' AND TARIH <='" + sqldate + "'");
            while (rs3.next()) {
                aylikToplamGelir = aylikToplamGelir + rs3.getInt("GELIR");
            }
            aylikGelir.setText(String.valueOf(aylikToplamGelir) + " TL");
            Statement st4 = con.createStatement();
            rs4 = st4.executeQuery("SELECT * FROM GIDER WHERE TARIH >='" + asqldate + "' AND TARIH <='" + sqldate + "'");
            while (rs4.next()) {
                aylikToplamGider = aylikToplamGider + rs4.getInt("GIDER");
            }
            aylikGider.setText(String.valueOf(aylikToplamGider) + " TL");
        } catch (SQLException ex) {
            Logger.getLogger(muhasebe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(muhasebe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        gelirTablosu = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        gelirilkTarih = new org.jdesktop.swingx.JXDatePicker();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        gelirikinciTarih = new org.jdesktop.swingx.JXDatePicker();
        gelirListele = new javax.swing.JButton();
        gelirSifirla = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        gunlukGelir = new javax.swing.JTextField();
        aylikGelir = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        giderTablosu = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        giderilkTarih = new org.jdesktop.swingx.JXDatePicker();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        giderikinciTarih = new org.jdesktop.swingx.JXDatePicker();
        giderListele = new javax.swing.JButton();
        giderSifirla = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        gunlukGider = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        aylikGider = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        gelirTur = new javax.swing.JComboBox();
        gelirTutar = new javax.swing.JTextField();
        gelirEkle = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        giderTur = new javax.swing.JComboBox();
        giderTutar = new javax.swing.JTextField();
        giderEkle = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(600, 342));

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Gelir Tablosu"));

        gelirTablosu.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(gelirTablosu);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Tarihe Göre Arama"));

        jLabel6.setText("İlk Tarih :");

        jLabel12.setText("İkinci Tarih :");

        gelirListele.setText("Listele");
        gelirListele.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gelirListeleActionPerformed(evt);
            }
        });

        gelirSifirla.setText("Sıfırla");
        gelirSifirla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gelirSifirlaActionPerformed(evt);
            }
        });

        jLabel5.setText("Günlük Gelir :");

        jLabel7.setText("Aylık Gelir :");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gelirilkTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(gelirListele))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gelirSifirla)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(gelirikinciTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(gunlukGelir, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(aylikGelir))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel12))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(gelirilkTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gelirikinciTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(gelirListele)
                            .addComponent(gelirSifirla)
                            .addComponent(gunlukGelir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(aylikGelir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Gider Tablosu"));

        giderTablosu.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(giderTablosu);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Tarihe Göre Arama"));
        jPanel9.setPreferredSize(new java.awt.Dimension(415, 158));

        giderilkTarih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giderilkTarihActionPerformed(evt);
            }
        });

        jLabel13.setText("İlk Tarih :");

        jLabel14.setText("İkinci Tarih :");

        giderListele.setText("Listele");
        giderListele.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giderListeleActionPerformed(evt);
            }
        });

        giderSifirla.setText("Sıfırla");
        giderSifirla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giderSifirlaActionPerformed(evt);
            }
        });

        jLabel8.setText("Günlük Gider :");

        jLabel10.setText("Aylık Gider :");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(41, 41, 41))
                            .addComponent(giderilkTarih, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel14))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(giderSifirla)
                                    .addComponent(giderikinciTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(giderListele)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(gunlukGider, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(aylikGider, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addGap(20, 20, 20)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(giderikinciTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(giderilkTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(giderListele)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(giderSifirla)
                        .addComponent(gunlukGider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(aylikGider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Gelir Ekle"));

        jLabel1.setText("Tutar :");

        jLabel2.setText("Tür :");

        gelirEkle.setText("Ekle");
        gelirEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gelirEkleActionPerformed(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rsz_add-icon.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(61, 61, 61)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(gelirTur, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9))
                            .addComponent(gelirTutar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(gelirEkle)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(gelirTur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gelirTutar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(gelirEkle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Gider Ekle"));

        jLabel3.setText("Tür :");

        jLabel4.setText("Tutar :");

        giderEkle.setText("Ekle");
        giderEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giderEkleActionPerformed(evt);
            }
        });

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rsz_add-icon.png"))); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(61, 61, 61)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(giderTur, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11))
                            .addComponent(giderTutar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(giderEkle)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(giderTur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(giderTutar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(giderEkle)
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void gelirEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gelirEkleActionPerformed
        Object tur = gelirTur.getSelectedItem();
        Object tutar = gelirTutar.getText();
        db.dbBaglan();
        String sql3 = ("INSERT INTO GELIR (GELIR_TURU,TARIH,GELIR) VALUES (?,?,?)");
        try {
            PreparedStatement ps = con.prepareStatement(sql3);
            ps.setObject(1, tur);
            ps.setObject(2, Calendar.getInstance());
            ps.setObject(3, tutar);
            ps.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(muhasebe.class.getName()).log(Level.SEVERE, null, ex);
        }
        gelirTutar.setText("");
        gelirEkle.setEnabled(false);
        t.tabloyuOlustur(sql, gelir, tbGelir);
        gunlukToplamGelir = gunlukToplamGelir + Integer.valueOf((String) tutar);
        gunlukGelir.setText(String.valueOf(gunlukToplamGelir) + " TL");
        aylikToplamGelir = aylikToplamGelir + Integer.valueOf((String) tutar);
        aylikGelir.setText(String.valueOf(aylikToplamGelir) + " TL");
    }//GEN-LAST:event_gelirEkleActionPerformed

    private void giderEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giderEkleActionPerformed
        Object tur = giderTur.getSelectedItem();
        Object tutar = giderTutar.getText();
        db.dbBaglan();
        String sql3 = ("INSERT INTO GIDER (GIDER_TURU,TARIH,GIDER) VALUES (?,?,?)");
        try {
            PreparedStatement ps = con.prepareStatement(sql3);
            ps.setObject(1, tur);
            ps.setObject(2, Calendar.getInstance());
            ps.setObject(3, tutar);
            ps.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(muhasebe.class.getName()).log(Level.SEVERE, null, ex);
        }
        giderTutar.setText("");
        giderEkle.setEnabled(false);
        t.tabloyuOlustur(sql2, gider, tbGider);
        gunlukToplamGider = gunlukToplamGider + Integer.valueOf((String) tutar);
        gunlukGider.setText(String.valueOf(gunlukToplamGider) + " TL");
        aylikToplamGider = aylikToplamGider + Integer.valueOf((String) tutar);
        aylikGider.setText(String.valueOf(aylikToplamGider) + " TL");
    }//GEN-LAST:event_giderEkleActionPerformed

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        gelirEkle frame = new gelirEkle();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                gelirTur.removeAllItems();
                turleriCek();
            }
        });

        frame.setVisible(true);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        giderEkle frame = new giderEkle();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                gelirTur.removeAllItems();
                turleriCek();
            }
        });

        frame.setVisible(true);
    }//GEN-LAST:event_jLabel11MouseClicked

    private void giderilkTarihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giderilkTarihActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_giderilkTarihActionPerformed

    private void gelirListeleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gelirListeleActionPerformed
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date sqlDate, sqlDate2;

        try {
            String date = formatter.format(gelirilkTarih.getDate()).toString();
            String date2 = formatter.format(gelirikinciTarih.getDate()).toString();

            Date datex = formatter.parse(date);
            Date datey = formatter.parse(date2);

            sqlDate = new java.sql.Date(datex.getTime());
            sqlDate2 = new java.sql.Date(datey.getTime());
            String sorgu = "SELECT * FROM GELIR WHERE TARIH BETWEEN '" + sqlDate + "' AND '" + sqlDate2 + "'";
            db.dbBaglan();
            st.executeQuery(sorgu);
            t.tabloyuOlustur(sorgu, gelir, tbGelir);
            gelirTablosu.setModel(tbGelir);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(muhasebe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(muhasebe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_gelirListeleActionPerformed

    private void gelirSifirlaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gelirSifirlaActionPerformed
        t.tabloyuOlustur(sql, gelir, tbGelir);
        gelirTablosu.setModel(tbGelir);
        gelirilkTarih.setDate(null);
        gelirikinciTarih.setDate(null);
        gelirListele.setEnabled(false);
    }//GEN-LAST:event_gelirSifirlaActionPerformed

    private void giderListeleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giderListeleActionPerformed
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date sqlDate, sqlDate2;

        try {
            String date = formatter.format(giderilkTarih.getDate()).toString();
            String date2 = formatter.format(giderikinciTarih.getDate()).toString();

            Date datex = formatter.parse(date);
            Date datey = formatter.parse(date2);

            sqlDate = new java.sql.Date(datex.getTime());
            sqlDate2 = new java.sql.Date(datey.getTime());
            String sorgu = "SELECT * FROM GIDER WHERE TARIH BETWEEN '" + sqlDate + "' AND '" + sqlDate2 + "'";
            db.dbBaglan();
            st.executeQuery(sorgu);
            t.tabloyuOlustur(sorgu, gider, tbGider);
            giderTablosu.setModel(tbGider);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(muhasebe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(muhasebe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_giderListeleActionPerformed

    private void giderSifirlaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giderSifirlaActionPerformed
        t.tabloyuOlustur(sql2, gider, tbGider);
        giderTablosu.setModel(tbGider);
        giderilkTarih.setDate(null);
        giderikinciTarih.setDate(null);
        giderListele.setEnabled(false);
    }//GEN-LAST:event_giderSifirlaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aylikGelir;
    private javax.swing.JTextField aylikGider;
    private javax.swing.JButton gelirEkle;
    private javax.swing.JButton gelirListele;
    private javax.swing.JButton gelirSifirla;
    private javax.swing.JTable gelirTablosu;
    private javax.swing.JComboBox gelirTur;
    private javax.swing.JTextField gelirTutar;
    private org.jdesktop.swingx.JXDatePicker gelirikinciTarih;
    private org.jdesktop.swingx.JXDatePicker gelirilkTarih;
    private javax.swing.JButton giderEkle;
    private javax.swing.JButton giderListele;
    private javax.swing.JButton giderSifirla;
    private javax.swing.JTable giderTablosu;
    private javax.swing.JComboBox giderTur;
    private javax.swing.JTextField giderTutar;
    private org.jdesktop.swingx.JXDatePicker giderikinciTarih;
    private org.jdesktop.swingx.JXDatePicker giderilkTarih;
    private javax.swing.JTextField gunlukGelir;
    private javax.swing.JTextField gunlukGider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}

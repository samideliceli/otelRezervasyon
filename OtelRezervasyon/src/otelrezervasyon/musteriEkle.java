/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otelrezervasyon;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author delilog
 */
public class musteriEkle extends javax.swing.JFrame {

    private final KeyAdapter adapter;
    
    
    
    private String tcText;
    private String emailText;
    private String isimText;
    private String soyIsimText;
    private String ilkTarihText;
    private String ikinciTarihText;
    private String onOdemeText;
    private String telefonText;
    private final textKontrolleri kontrol;
    private dbConnection db;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement st;
    /**
     * Creates new form musteriEkle
     */
    public musteriEkle() {
        kontrol = new textKontrolleri();
        initComponents();
        db=new dbConnection();
        buttonGroup1.add(jRadioButton1);
        buttonGroup1.add(jRadioButton2);
        
        kaydet.setEnabled(false);
        adapter = new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                super.keyReleased(evt);
                if (degerlerDoluMu()) {
                        if (degerKontrolleri()){
                                kaydet.setEnabled(true); 
                        }
                        else {
                            kaydet.setEnabled(false);
                        }
                } else{ 
                    kaydet.setEnabled(false);
                }
            }  
        };
        
        isim.addKeyListener(adapter);
        soyisim.addKeyListener(adapter);
        tc.addKeyListener(adapter);
        telefon.addKeyListener(adapter);
        email.addKeyListener(adapter);
        baslangicTarih.addKeyListener(adapter);
        bitisTarih.addKeyListener(adapter);
        onOdeme.addKeyListener(adapter);
        
        
        }
      private boolean degerKontrolleri() {
          tcText=tc.getText();
          emailText=email.getText();
          isimText=isim.getText();
          soyIsimText=soyisim.getText();
          ilkTarihText=baslangicTarih.getText();
          ikinciTarihText=bitisTarih.getText();
          onOdemeText=onOdeme.getText();
          telefonText=telefon.getText();
           
          return kontrol.onOdemeKontrol(onOdemeText)
                  && kontrol.telefonKontrol(telefonText) && kontrol.tcKontrol(tcText) && kontrol.emailKontrol(emailText) && 
                  kontrol.adSoyadKontrol(isimText,soyIsimText) &&
                  kontrol.tarihKontrol(ilkTarihText,ikinciTarihText);
        
     }
    
    private boolean degerlerDoluMu() {
                
                if(isim.getText().equals("")){
                    return false;
                }
                else if (soyisim.getText().equals("")) {
                    return false;
                    
                }
                else if (tc.getText().equals("")) {
                    return false;
                    
                 }
                else if (telefon.getText().equals("")) {
                    return false;
                    
                }
                else if (email.getText().equals("")) {
                    return false;
                }
                else if (baslangicTarih.getText().equals("")) {
                    return false;
                }
                else if (bitisTarih.getText().equals("")) {
                    return false;
                }
                else if (onOdeme.getText().equals("")) {
                    return false;
                }
                
                return true;
               }
    
    private int otelBilgileriniKaydet(String tc) {
        
        String sql="";
        db.dbBaglan();
        st=dbConnection.getSt();
        try {
            rs=st.executeQuery("SELECT * FROM musteri WHERE tc='"+tcText+"'");  
            
            while(rs.next())
            {
                int id = rs.getInt(1);
                System.out.println(""+id);
                return id;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }

    
    private void musteriKaydet() {
     String sql="Insert INTO musteri (AD,SOYAD,CINSIYET,TC,TELEFON,EMAIL,INDIRIM_MIKTARI) "
                + "VALUES (?,?,?,?,?,?,?)";
        db.dbBaglan();
        String cinsiyet;
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        
        if(this.cinsiyet.getSelectedIndex()==0){
            cinsiyet="e";
        }
        else{
            cinsiyet="k";
        }
        
        try {
            ps =db.getCon().prepareStatement(sql);
            
            ps.setString(1, isimText);
            ps.setString(2, soyIsimText);
            ps.setString(3, cinsiyet);
            ps.setString(4, tcText);
            ps.setString(5, telefonText);
            ps.setString(6, emailText);
            ps.setInt(7, 0);
            ps.executeUpdate();
            
            
            
            int id = otelBilgileriniKaydet(tcText);
            Date date = formatter.parse(ilkTarihText);
            java.sql.Date sqldate;
            sqldate = new java.sql.Date(date.getTime());
            String sql2="Insert INTO musteri_otel_bilgileri (MUSTERI_ID,ODA_NO,ON_ODEME_TUTAR,BASLANGIC_TARIHI,BITIS_TARIHI,"
                    + "ILISKI_KESIM,KONAK_SAYISI) VALUES (?,?,?,?,?,?,?)";
            
            ps =db.getCon().prepareStatement(sql2);
            ps.setInt(1, id);
            ps.setInt(2, 10);
            ps.setInt(3, Integer.valueOf(onOdemeText));
            ps.setDate(4,  sqldate);
            ps.setDate(5, sqldate);
            ps.setBoolean(6, false);
            ps.setInt(7, 5);
            ps.execute();
            
            db.getCon().close();
            
            
            WindowEvent closingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);

            
        } catch (SQLException ex) {
            Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    private void rezervasyonKaydet() {  
    
        
        String sql="Insert INTO rezervasyon (AD,SOYAD,CINSIYET,TC,TELEFON,"
                + "EMAIL,BASLANGIC_TARIHI,BITIS_TARIHI,ODEME_TUTARI,ODA_NO) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        db.dbBaglan();
        String cinsiyet;
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        
        if(this.cinsiyet.getSelectedIndex()==0){
            cinsiyet="e";
        }
        else{
            cinsiyet="k";
        }
        
        try {
            ps =db.getCon().prepareStatement(sql);
            
            
            Date date = formatter.parse(ilkTarihText);
            java.sql.Date sqldate;
            sqldate = new java.sql.Date(date.getTime());
            
            Date date2 = formatter.parse(ikinciTarihText);
            java.sql.Date sqldate2;
            sqldate2 = new java.sql.Date(date.getTime());
            
            
            ps.setString(1, isimText);
            ps.setString(2, soyIsimText);
            ps.setString(3, cinsiyet);
            ps.setString(4, tcText);
            ps.setString(5, telefonText);
            ps.setString(6, emailText);
            ps.setDate(7, sqldate);
            ps.setDate(8, sqldate2);
            ps.setInt(9,Integer.valueOf(onOdemeText));
            ps.setInt(10, 15);
            ps.executeUpdate();
            
            db.getCon().close();
            
            
            WindowEvent closingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);

            
        } catch (SQLException ex) {
            Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        kaydet = new javax.swing.JButton();
        email = new javax.swing.JTextField();
        telefon = new javax.swing.JTextField();
        soyisim = new javax.swing.JTextField();
        isim = new javax.swing.JTextField();
        tc = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cinsiyet = new javax.swing.JComboBox<>();
        baslangicTarih = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        bitisTarih = new javax.swing.JTextField();
        onOdeme = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Müşteri Ekle");
        setResizable(false);

        jLabel2.setText("TC");

        jLabel3.setText("İsim");

        jLabel4.setText("Yetişkin");

        jLabel5.setText("Telefon");

        jLabel6.setText("E-mail");

        jLabel7.setText("Başlangıç");

        kaydet.setText("KAYDET");
        kaydet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kaydetActionPerformed(evt);
            }
        });

        isim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isimActionPerformed(evt);
            }
        });

        jLabel1.setText("Cinsiyet");

        cinsiyet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Erkek", "Kadın" }));
        cinsiyet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cinsiyetActionPerformed(evt);
            }
        });

        jLabel8.setText("Bitiş");

        jRadioButton1.setText("Müşteri");

        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Rezervasyon");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        onOdeme.setText("45");
        onOdeme.setEnabled(false);
        onOdeme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onOdemeActionPerformed(evt);
            }
        });

        jLabel10.setText("Ön ödeme");

        jPanel1.setBackground(new java.awt.Color(44, 136, 228));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setToolTipText("Oda Seçim");

        jLabel11.setBackground(new java.awt.Color(242, 12, 55));
        jLabel11.setForeground(new java.awt.Color(201, 1, 19));
        jLabel11.setText("  1");

        jLabel12.setText("  1");

        jLabel13.setText("  1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(256, 256, 256))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(183, Short.MAX_VALUE))
        );

        jLabel14.setText("Seçilen Oda: 15");

        jLabel15.setText("Soyisim");

        jLabel16.setText("Çocuk");

        jLabel17.setText("Bebek");

        jLabel18.setText("Ücret:");

        jLabel19.setText("300 ₺");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel7))
                                        .addGap(16, 16, 16))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(18, 18, 18)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tc, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                    .addComponent(onOdeme, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                    .addComponent(baslangicTarih)
                                    .addComponent(telefon)
                                    .addComponent(isim)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jRadioButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(soyisim)
                                    .addComponent(cinsiyet, 0, 199, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(bitisTarih, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                        .addComponent(email))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(6, 6, 6))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                            .addComponent(jTextField3))
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel16)
                                        .addGap(6, 6, 6)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addGap(93, 93, 93)
                        .addComponent(kaydet, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(isim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(tc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1)
                                    .addComponent(cinsiyet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(telefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(baslangicTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(bitisTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(soyisim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(onOdeme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel4)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton1))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kaydet)
                    .addComponent(jLabel14)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addGap(12, 12, 12)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void isimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_isimActionPerformed

    private void cinsiyetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cinsiyetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cinsiyetActionPerformed

    private void onOdemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onOdemeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_onOdemeActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void kaydetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kaydetActionPerformed
       
           boolean rezervasyonMu=jRadioButton2.isSelected();
        
        if(rezervasyonMu){
        
        rezervasyonKaydet();
        }
        else{
        musteriKaydet();
        }
        
       
        
        
        
    }//GEN-LAST:event_kaydetActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(musteriEkle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(musteriEkle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(musteriEkle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(musteriEkle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new musteriEkle().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField baslangicTarih;
    private javax.swing.JTextField bitisTarih;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cinsiyet;
    private javax.swing.JTextField email;
    private javax.swing.JTextField isim;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JButton kaydet;
    private javax.swing.JTextField onOdeme;
    private javax.swing.JTextField soyisim;
    private javax.swing.JTextField tc;
    private javax.swing.JTextField telefon;
    // End of variables declaration//GEN-END:variables

  

    

   

   

    
    

    

   
}

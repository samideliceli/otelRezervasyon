/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otelrezervasyon;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import static otelrezervasyon.dbConnection.con;

/**
 *
 * @author delilog
 */
public class musteriIslemleri extends javax.swing.JPanel {

    private final tableModel t;
    private String sql;
    private final DefaultTableModel tb;
    private final dbConnection db;
    private Vector veri;
    String ilkTelefon;
    int toplam_oda_ucret,on_odeme;
    int musteriID;
    static String tc;
    
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    /**
     * Creates new form musteriIslemleri
     */
    boolean isSelected=false;
    public musteriIslemleri() {
        initComponents();
        db = new dbConnection();
        t = new tableModel();
        sql = " Select ad,soyad,cinsiyet,tc,telefon,email,oda_no,on_odeme_tutar,toplam_oda_ucret,baslangic_tarihi,bitis_tarihi,"
                + "konak_sayisi from musteri,musteri_otel_bilgileri Where musteri.musteri_id=musteri_otel_bilgileri.musteri_id AND musteri_otel_bilgileri.iliski_kesim=false";
        
        tb = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                if (col == 0) {
                    return false;
                }
                return true;
            }
        };
        
        t.tabloyuOlustur(sql, veri, tb);
        musteriTablo.setModel(tb);
        
        
        iliskiRadio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ıe) {
                
                isSelected=!isSelected;
            
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        odaNo = new javax.swing.JTextField();
        tarihIlk = new javax.swing.JTextField();
        tarihIki = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        iliskiRadio = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        musteriTablo = new javax.swing.JTable();
        musteriAd = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        musteriSoyad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        musteriTelefon = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        musteriMail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        guncelle = new javax.swing.JButton();
        onOdeme = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        ilisikKes = new javax.swing.JButton();
        musteriyeCevir = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1000, 445));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Arama"))));

        jLabel2.setText("İlk Tarih");

        jLabel3.setText("İkinci Tarih");

        jLabel4.setText("Oda No");

        tarihIlk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tarihIlkActionPerformed(evt);
            }
        });

        tarihIki.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tarihIkiActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Müşteri", "Rezervasyon" }));

        jLabel1.setText("Tip");

        jButton2.setText("Arama");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        iliskiRadio.setText("İlişkisi kesilmişler");
        iliskiRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iliskiRadioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(tarihIlk, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel2)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel3))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tarihIki, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel4)
                        .addGap(75, 75, 75)
                        .addComponent(jLabel1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(odaNo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iliskiRadio)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(iliskiRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tarihIlk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tarihIki, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(odaNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Müşteri Listesi"));
        jPanel2.setPreferredSize(new java.awt.Dimension(674, 416));

        musteriTablo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                musteriTabloMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(musteriTablo);

        musteriAd.setEnabled(false);

        jLabel5.setText("Ad");

        musteriSoyad.setEnabled(false);

        jLabel6.setText("Soyad");

        musteriTelefon.setEnabled(false);

        jLabel7.setText("E-mail");

        musteriMail.setEnabled(false);
        musteriMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                musteriMailActionPerformed(evt);
            }
        });

        jLabel8.setText("Telefon");

        guncelle.setText("Güncelle");
        guncelle.setEnabled(false);
        guncelle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guncelleActionPerformed(evt);
            }
        });

        onOdeme.setEnabled(false);

        jLabel9.setText("Ön Ödeme");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(musteriAd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(musteriSoyad, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(musteriMail, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(musteriTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(onOdeme, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(guncelle)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel5)
                .addGap(82, 82, 82)
                .addComponent(jLabel6)
                .addGap(102, 102, 102)
                .addComponent(jLabel7)
                .addGap(122, 122, 122)
                .addComponent(jLabel8)
                .addGap(80, 80, 80)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(musteriAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(musteriSoyad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(musteriTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(musteriMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(guncelle)
                    .addComponent(onOdeme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Diğer"));

        jButton1.setText("Yeni Müşteri Ekle");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        ilisikKes.setText("İlişik Kes");
        ilisikKes.setEnabled(false);
        ilisikKes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ilisikKesActionPerformed(evt);
            }
        });

        musteriyeCevir.setText("Müşteriye Çevir");
        musteriyeCevir.setEnabled(false);
        musteriyeCevir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                musteriyeCevirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ilisikKes, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(musteriyeCevir)
                .addGap(48, 48, 48))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(ilisikKes)
                    .addComponent(musteriyeCevir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1238, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 446, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tarihIlkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tarihIlkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tarihIlkActionPerformed

    private void tarihIkiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tarihIkiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tarihIkiActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        
    musteriEkle frame = new musteriEkle();
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.pack();
    frame.setAlwaysOnTop(true);
    frame.setLocationRelativeTo(null);
    frame.addWindowListener(new java.awt.event.WindowAdapter() { 
        @Override 
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {

            t.tabloyuOlustur(sql, veri, tb);
            musteriTablo.setModel(tb);

        }
    
    }); 
    frame.setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
      
        
       SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
      boolean tarihVar = false;
      int seciliIndex=jComboBox1.getSelectedIndex();
      if(seciliIndex==0){
             musteriyeCevir.setEnabled(false);
             sql = " SELECT ad,soyad,cinsiyet,tc,telefon,email,oda_no,on_odeme_tutar,toplam_oda_ucret,baslangic_tarihi,bitis_tarihi,"
                + "konak_sayisi FROM musteri,musteri_otel_bilgileri WHERE musteri.musteri_id=musteri_otel_bilgileri.musteri_id ";
        
             if(isSelected){
              sql=sql+" AND musteri_otel_bilgileri.iliski_kesim=true";
             }
             else{
             sql=sql+" AND musteri_otel_bilgileri.iliski_kesim=false";
             }
      }
        
      else{
          
          
           sql = " SELECT ad,soyad,cinsiyet,tc,telefon,email,oda_no,odeme_tutari,toplam_oda_ucret,baslangic_tarihi,bitis_tarihi"
                   + " FROM rezervasyon";
           
        ilisikKes.setEnabled(false);
        musteriAd.setEnabled(false);
        musteriMail.setEnabled(false);
        musteriSoyad.setEnabled(false);
        musteriMail.setEnabled(false);
        musteriTelefon.setEnabled(false);
        onOdeme.setEnabled(false);
        
        
      }
       
      if(!tarihIki.getText().equals("") && !tarihIlk.getText().equals("")){
          tarihVar=true;
          java.sql.Date sqldate2=null,sqldate=null;
           try {
               Date date;
               date = formatter.parse(tarihIlk.getText());
                sqldate = new java.sql.Date(date.getTime());
            
               Date date2 = formatter.parse(tarihIki.getText());
               sqldate2 = new java.sql.Date(date2.getTime());
               
           } catch (ParseException ex) {
               Logger.getLogger(musteriIslemleri.class.getName()).log(Level.SEVERE, null, ex);
           }
            
           
          if(seciliIndex==0){
          sql=sql+" AND (baslangic_tarihi>='"+sqldate+"' AND bitis_tarihi<='"+sqldate2+"')";
          }
          else{
          sql=sql+" WHERE baslangic_tarihi>='"+sqldate+"' AND bitis_tarihi<='"+sqldate2+"'";
 
          }
               
      }
      
      String odaNosu=odaNo.getText();
      
      if(!odaNosu.equals("")){
         
         if(seciliIndex==0){
          sql=sql+" AND (musteri_otel_bilgileri.oda_no="+odaNosu+")";
          }
         else{
                if(tarihVar){
                    
                   sql=sql+" AND (rezervasyon.oda_no="+odaNosu+")";
                   
                }
                else{
                    
                    sql=sql+" where rezervasyon.oda_no="+odaNosu;
                }
          }
          
          
      }
      
      
        t.tabloyuOlustur(sql, veri, tb);
        musteriTablo.setModel(tb);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void musteriTabloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_musteriTabloMouseClicked
       int i= jComboBox1.getSelectedIndex();
       
       if(i==0){
        
       
           
        musteriAd.setEnabled(true);
        musteriMail.setEnabled(true);
        musteriSoyad.setEnabled(true);
        musteriMail.setEnabled(true);
        musteriTelefon.setEnabled(true);
        onOdeme.setEnabled(true);
        
        try {
            db.dbBaglan();
            String sql2=" SELECT musteri.musteri_id,ad,soyad,cinsiyet,tc,telefon,email,oda_no,on_odeme_tutar,baslangic_tarihi,bitis_tarihi,"
                    + "konak_sayisi,toplam_oda_ucret FROM musteri,musteri_otel_bilgileri WHERE musteri.musteri_id=musteri_otel_bilgileri.musteri_id";
            
             if(!iliskiRadio.isSelected()){
                ilisikKes.setEnabled(true);
                sql2=sql2+" AND iliski_kesim=false";
                }else{
                    ilisikKes.setEnabled(false);
                }   
            
            PreparedStatement ps = con.prepareStatement(sql2, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.executeQuery();
            
           
            ps.getResultSet().absolute(musteriTablo.getSelectedRow()+1);
            
            ilkTelefon=ps.getResultSet().getString("TELEFON");
            toplam_oda_ucret=ps.getResultSet().getInt("toplam_oda_ucret");
            on_odeme=ps.getResultSet().getInt("on_odeme_tutar");
            
            
            musteriAd.setText(ps.getResultSet().getString("AD"));
            musteriSoyad.setText(ps.getResultSet().getString("SOYAD"));
            musteriMail.setText(ps.getResultSet().getString("EMAIL"));
            musteriTelefon.setText(ps.getResultSet().getString("TELEFON"));
            onOdeme.setText(ps.getResultSet().getString("on_odeme_tutar"));
            musteriID=ps.getResultSet().getInt("Musteri_id");
            
            tc=ps.getResultSet().getString("TC");
            
            
            guncelle.setEnabled(true);
        } catch (SQLException ex) {
            Logger.getLogger(musteriIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }}
       
       else{
                
           try {
               db.dbBaglan();
               String sql = " SELECT baslangic_tarihi"
                       + " FROM rezervasyon";
               
               PreparedStatement ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                       ResultSet.CONCUR_READ_ONLY);
               ps.executeQuery();
               
               
               ps.getResultSet().absolute(musteriTablo.getSelectedRow()+1);
               Date basTarih= ps.getResultSet().getDate("baslangic_tarihi");
               
               Date bugun=new Date();
               java.sql.Date sqldate = new java.sql.Date(bugun.getTime());
               
               
               
               if(sqldate.toString().equals(basTarih.toString())){
               
               musteriyeCevir.setEnabled(true);
               }
               
               
           } catch (SQLException ex) {
               Logger.getLogger(musteriIslemleri.class.getName()).log(Level.SEVERE, null, ex);
           }
             
       }
    }//GEN-LAST:event_musteriTabloMouseClicked

    private void musteriMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_musteriMailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_musteriMailActionPerformed

    private void guncelleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guncelleActionPerformed
        // TODO add your handling code here:
        db.dbBaglan();
        String musteriAdText=musteriAd.getText();
        String musteriSoyadText=musteriSoyad.getText();
        String emailText=musteriMail.getText();
        String onOdemeText=onOdeme.getText();
        String musteriTel=musteriTelefon.getText();
        
        
        boolean ad,soyad,telefon,email,onOdemeK;
        
        textKontrolleri kontrol=new textKontrolleri();
        
        Color gri=new Color(255,255,255);
        Color kirmizi=new Color(215,134,130);
        
        if(kontrol.adSoyadKontrol(musteriAdText, "aaa")){
             musteriAd.setBackground(gri);
             ad=true;
        }
        else{
            musteriAd.setBackground(kirmizi);
            ad=false;
        }
        
        if(kontrol.adSoyadKontrol(musteriSoyadText, "aaa")){
             musteriSoyad.setBackground(gri);
             soyad=true;
        }
        else{
            musteriSoyad.setBackground(kirmizi);
            soyad=false;
        }
        
        
        if(kontrol.emailKontrol(emailText)){
         musteriMail.setBackground(gri);
         email=true;
        }
        else{
            musteriMail.setBackground(kirmizi);
            email=false;
        }
        
        int onOdemeMiktar=Integer.valueOf(onOdemeText);
        
        
        if(kontrol.onOdemeKontrol(onOdemeText) && onOdemeMiktar<=toplam_oda_ucret && onOdemeMiktar>=on_odeme){
         onOdeme.setBackground(gri);
         onOdemeK=true;
        } 
        else{
            onOdeme.setBackground(kirmizi);
            onOdemeK=false;
        }
        
        
        if(!ilkTelefon.equals(musteriTel)){
                if(kontrol.telefonKontrol(musteriTel)){
                 musteriTelefon.setBackground(gri);
                 telefon=true;
                }
        
                else{
                    musteriTelefon.setBackground(kirmizi);
                    telefon=false;
                }
          }
        else{
         musteriTelefon.setBackground(gri);
         telefon=true;
        }
        
        
        if(ad && soyad && telefon && email && onOdemeK){
            try {
                PreparedStatement ps;
                
                String sql2="UPDATE  musteri SET ad='"+musteriAdText+"', soyad='"+musteriSoyadText+
                "', telefon='"+musteriTel+"', email='"+ emailText+"' WHERE musteri_id="+musteriID;
                
                ps =dbConnection.getCon().prepareStatement(sql2);
                ps.execute();
                
                 String sql3="UPDATE musteri_otel_bilgileri SET on_odeme_tutar="+onOdemeMiktar+"WHERE musteri_id="+musteriID;
                    ps =dbConnection.getCon().prepareStatement(sql3);
                    ps.execute();
                
                t.tabloyuOlustur(sql, veri, tb);
                musteriTablo.setModel(tb);
            } catch (SQLException ex) {
                Logger.getLogger(musteriIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        
    }//GEN-LAST:event_guncelleActionPerformed

    private void ilisikKesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ilisikKesActionPerformed
    
    ilisikKesme frame = new ilisikKesme();
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.pack();
    frame.setAlwaysOnTop(true);
    frame.setLocationRelativeTo(null);
    frame.addWindowListener(new java.awt.event.WindowAdapter() { 
        @Override 
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {

            t.tabloyuOlustur(sql, veri, tb);
            musteriTablo.setModel(tb);

        }
    
    }); 
    frame.setVisible(true);
        



// TODO add your handling code here:
       
        
        
    }//GEN-LAST:event_ilisikKesActionPerformed

    private void musteriyeCevirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_musteriyeCevirActionPerformed
        try {
            // TODO add your handling code here:
            db.dbBaglan();
            String sql = " SELECT ad,soyad,cinsiyet,tc,telefon,email,oda_no,odeme_tutari,toplam_oda_ucret,baslangic_tarihi,bitis_tarihi"
                    + " FROM rezervasyon";
            
            PreparedStatement ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.executeQuery();
            
            
            ps.getResultSet().absolute(musteriTablo.getSelectedRow()+1);
            String ad= ps.getResultSet().getString("AD");
            String soyad=ps.getResultSet().getString("SOYAD");
            String mail=ps.getResultSet().getString("EMAIL");
            String tc= ps.getResultSet().getString("TC");
            String telefon=ps.getResultSet().getString("TELEFON");
            String cinsiyet=ps.getResultSet().getString("CINSIYET");
            Date dateBaslangic=ps.getResultSet().getDate("baslangic_tarihi");
            Date dateBitis=ps.getResultSet().getDate("bitis_tarihi");
            int onOdemeUcret=ps.getResultSet().getInt("odeme_tutari");
            int odaNo=ps.getResultSet().getInt("ODA_NO");
            int toplamOdaUcret=ps.getResultSet().getInt("toplam_oda_ucret");
            
            int id = idGetir(tc);
            
            if(id==-1){
            
            
            
            String sqlMusCevir="Insert INTO musteri (AD,SOYAD,CINSIYET,TC,TELEFON,EMAIL,INDIRIM_MIKTARI) "
                + "VALUES (?,?,?,?,?,?,?)";
        
       
             java.sql.Date sqldate = null,sqldate2 = null;
       
         
       

                
                sqldate = new java.sql.Date(dateBaslangic.getTime());

                sqldate2 = new java.sql.Date(dateBitis.getTime());

            
            
            
                ps =dbConnection.getCon().prepareStatement(sqlMusCevir);

                ps.setString(1, ad);
                ps.setString(2, soyad);
                ps.setString(3, cinsiyet);
                ps.setString(4, tc);
                ps.setString(5, telefon);
                ps.setString(6, mail);
                ps.setInt(7, 0);
                ps.executeUpdate();
                
                
                
                int id2 = idGetir(tc);
                String sqlMus2="Insert INTO musteri_otel_bilgileri (MUSTERI_ID,ODA_NO,"
                        + "ON_ODEME_TUTAR,BASLANGIC_TARIHI,BITIS_TARIHI,"
                        + "ILISKI_KESIM,KONAK_SAYISI,TOPLAM_ODA_UCRET) VALUES (?,?,?,?,?,?,?,?)";

                ps =dbConnection.getCon().prepareStatement(sqlMus2);
                ps.setInt(1, id2);
                ps.setInt(2, odaNo);
                ps.setInt(3, onOdemeUcret);
                ps.setDate(4,  sqldate);
                ps.setDate(5, sqldate2);
                ps.setBoolean(6, false);
                ps.setInt(7, 0);
                ps.setInt(8, toplamOdaUcret);
                ps.execute();
            
                
            }
            
            else{
             
                String sql2="UPDATE  musteri_otel_bilgileri SET ODA_NO="+odaNo
                        +",ON_ODEME_TUTAR="+onOdemeUcret+ ", BASLANGIC_TARIHI='"+dateBaslangic
                        +"', BITIS_TARIHI='"+dateBitis+"',TOPLAM_ODA_UCRET="+toplamOdaUcret+", ILISKI_KESIM=false, KONAK_SAYISI="+(10+1)
                        + " WHERE musteri_id="+id;
                
                ps =dbConnection.getCon().prepareStatement(sql2);
               
                ps.execute();
            }
            
            
            
                String sql2="DELETE FROM rezervasyon WHERE tc='"+tc+"'";
                
                ps =dbConnection.getCon().prepareStatement(sql2);
                ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(musteriIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        t.tabloyuOlustur(sql, veri, tb);
        musteriTablo.setModel(tb);
        
    }//GEN-LAST:event_musteriyeCevirActionPerformed

    private void iliskiRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iliskiRadioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_iliskiRadioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton guncelle;
    private javax.swing.JButton ilisikKes;
    private javax.swing.JRadioButton iliskiRadio;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField musteriAd;
    private javax.swing.JTextField musteriMail;
    private javax.swing.JTextField musteriSoyad;
    private javax.swing.JTable musteriTablo;
    private javax.swing.JTextField musteriTelefon;
    private javax.swing.JButton musteriyeCevir;
    private javax.swing.JTextField odaNo;
    private javax.swing.JTextField onOdeme;
    private javax.swing.JTextField tarihIki;
    private javax.swing.JTextField tarihIlk;
    // End of variables declaration//GEN-END:variables

    private int idGetir(String tc) {
        
        String sql="";
        
        Statement st=dbConnection.getSt();
        try {
            ResultSet rs=st.executeQuery("SELECT * FROM musteri WHERE tc='"+tc+"'");  
            
            while(rs.next())
            {
                int id = rs.getInt(1);
                return id;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
}

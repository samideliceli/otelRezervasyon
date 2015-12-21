/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otelrezervasyon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class personelİslemleri extends javax.swing.JPanel {

    private String tc,isim,soyIsim,sgkNo,gorev,telefon,eposta,cinsiyet,izinHakki;
    private final KeyAdapter adapter;
    dbConnection baglan;
    textKontrolleri txtkontrol;
    private tableModel t;
    private String sql;
    private DefaultTableModel tb;
    private Vector veri;
    
    public personelİslemleri() {
        initComponents();
        baglan = new dbConnection();
        
        tabloyuDoldur();
        gorevCBEkle();
        txtkontrol = new textKontrolleri();
        adapter = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e); //To change body of generated methods, choose Tools | Templates.
                if (kaydetHazirMi()){
                   jButtonPersonelKaydet.setEnabled(true);
                }
                else{
                    jButtonPersonelKaydet.setEnabled(false);
                }
            }
           
        };
        
        jTextFieldIsim.addKeyListener(adapter);
        jTextFieldSgkNo.addKeyListener(adapter);
        jTextFieldTc.addKeyListener(adapter);
        jTextFieldSoyAd.addKeyListener(adapter);
        
        jComboBoxGorev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              izinHakkıGorevID();
            }
        });

    }
    private boolean kaydetHazirMi(){
        tc = jTextFieldTc.getText();
        isim = jTextFieldIsim.getText();
        soyIsim = jTextFieldSoyAd.getText();

        if(textDoluMu()){
            if(txtkontrol.adSoyadKontrol(isim, soyIsim) && txtkontrol.tcKontrol(tc) && txtkontrol.emailKontrol(eposta) && txtkontrol.telefonKontrol(telefon)){
                return true;
            }
            else{
                 return false;  
            }
        }
        else{
            return false;
        }
        
    }
    private boolean textDoluMu(){
        
        tc = jTextFieldTc.getText();
        isim = jTextFieldIsim.getText();
        soyIsim = jTextFieldSoyAd.getText();
        sgkNo = jTextFieldSgkNo.getText();
        telefon = jTextFieldTelefon.getText();
        eposta = jTextFieldPosta.getText();
        if(!(tc.equals("")||isim.equals("")||soyIsim.equals("")||sgkNo.equals("")||telefon.equals("")||eposta.equals(""))){//sorunu düzelt tekinin dolu olması yetiyor
            return true;
        }
        else{
            return false;
        }
              
    }
    private void tabloyuDoldur(){
    
        t = new tableModel();
        sql = " Select * From PERSONEL ";
        
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
        jTablePersonel.setModel(tb);
    }
    private void gorevCBEkle(){
        try {
            baglan.dbBaglan();
            String gorevSql = " Select AD From GOREV ";
            PreparedStatement ps = con.prepareStatement(gorevSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.executeQuery();
            ps.getResultSet().beforeFirst();
            while (ps.getResultSet().next()) {
                jComboBoxGorev.addItem(ps.getResultSet().getString("AD"));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(personelİslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void izinHakkıGorevID(){
        try {
            baglan.dbBaglan();
            String gorevSql = " Select AD,IZIN_HAKKI,GOREV_ID From GOREV";
            PreparedStatement ps = con.prepareStatement(gorevSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ps.executeQuery();
            ps.getResultSet().beforeFirst();
            while (ps.getResultSet().next()) {
                
                    if(ps.getResultSet().getString("AD").equals(jComboBoxGorev.getItemAt(jComboBoxGorev.getSelectedIndex()))){
                       
                        jTextFieldIzinHakki.setText(ps.getResultSet().getString("IZIN_HAKKI"));
                        izinHakki = ps.getResultSet().getString("GOREV_ID");
                    }
                
                
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(personelİslemleri.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldAranacakIsim = new javax.swing.JTextField();
        jButtonPersonelAra = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePersonel = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButtonPersonelKaydet = new javax.swing.JButton();
        jTextFieldIzinHakki = new javax.swing.JTextField();
        jTextFieldSgkNo = new javax.swing.JTextField();
        jTextFieldSoyAd = new javax.swing.JTextField();
        jTextFieldIsim = new javax.swing.JTextField();
        jTextFieldTc = new javax.swing.JTextField();
        jComboBoxGorev = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxCinsiyet = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldTelefon = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldPosta = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jButtonGörevEkle = new javax.swing.JButton();

        setToolTipText("");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Arama"));

        jLabel1.setBackground(new java.awt.Color(200, 241, 240));
        jLabel1.setText("İsim:");

        jButtonPersonelAra.setText("ARA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldAranacakIsim, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButtonPersonelAra, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldAranacakIsim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPersonelAra))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("PERSONEL LISTESI"));

        jTablePersonel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "TC", "İsim", "Soyisim", "Sgk No", "Görev", "İzin Hakkı"
            }
        ));
        jTablePersonel.setAutoscrolls(false);
        jScrollPane1.setViewportView(jTablePersonel);

        jButton1.setText("Güncelle");

        jButton2.setText("Sil");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(323, 323, 323)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Yeni Personel Kaydı"));

        jLabel2.setText("TC");

        jLabel3.setText("İsim");

        jLabel4.setText("Soyisim");

        jLabel5.setText("Sgk No");

        jLabel6.setText("Görev");

        jLabel7.setText("İzin Hakkı");

        jButtonPersonelKaydet.setText("KAYDET");
        jButtonPersonelKaydet.setEnabled(false);
        jButtonPersonelKaydet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPersonelKaydetActionPerformed(evt);
            }
        });

        jTextFieldIzinHakki.setEditable(false);

        jComboBoxGorev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxGorevActionPerformed(evt);
            }
        });

        jLabel8.setText("Cinsiyet");

        jComboBoxCinsiyet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kadın", "Erkek" }));

        jLabel9.setText("Telefon");

        jLabel10.setText("E-Posta");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldIsim, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                            .addComponent(jTextFieldTc)))
                    .addComponent(jButtonPersonelKaydet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldSgkNo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                            .addComponent(jTextFieldSoyAd)
                            .addComponent(jTextFieldIzinHakki)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldTelefon)
                            .addComponent(jTextFieldPosta)
                            .addComponent(jComboBoxCinsiyet, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxGorev, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldTc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldIsim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldSoyAd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldSgkNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPosta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxCinsiyet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxGorev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldIzinHakki, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonPersonelKaydet)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Görev Ekle"));

        jButtonGörevEkle.setText("Yeni Görev Ekle");
        jButtonGörevEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGörevEkleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonGörevEkle, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonGörevEkle)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6)))
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("PERSONEL");
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonPersonelKaydetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPersonelKaydetActionPerformed
        Calendar gun = new GregorianCalendar();
        
        if(jComboBoxCinsiyet.getSelectedIndex() == 1){
            cinsiyet = "Erkek";
        }
        else{
            cinsiyet = "Kadın";
        }
        
        baglan.dbBaglan();
         try {
            
            String sql = "INSERT INTO PERSONEL (AD,SOYAD,TC,SGK_NO,GOREV_ID,ISE_BASLAMA_TARIHI,CINSIYET,DURUM,KALAN_IZIN_HAKKI) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, isim);
            ps.setString(2, soyIsim);
            ps.setString(3, tc);
            ps.setString(4, sgkNo);
            ps.setInt(5, Integer.parseInt(izinHakki));
            ps.setDate(6,  new java.sql.Date(gun.getTimeInMillis()));
            ps.setString(7, cinsiyet);
            ps.setBoolean(8, true);
            ps.setInt(9,Integer.parseInt(jTextFieldIzinHakki.getText()));
            ps.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(personelİslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tabloyuDoldur();
        
    }//GEN-LAST:event_jButtonPersonelKaydetActionPerformed

    private void jComboBoxGorevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxGorevActionPerformed
        izinHakkıGorevID();
        
    }//GEN-LAST:event_jComboBoxGorevActionPerformed

    private void jButtonGörevEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGörevEkleActionPerformed
        JFrame frame = new JFrame("Görev Ekle");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new gorevEkle());
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() { 
            @Override 
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        
                jComboBoxGorev.removeAllItems();
                gorevCBEkle();
            } 
        }); 
        jComboBoxGorev.removeAllItems();
        gorevCBEkle();
    }//GEN-LAST:event_jButtonGörevEkleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonGörevEkle;
    private javax.swing.JButton jButtonPersonelAra;
    private javax.swing.JButton jButtonPersonelKaydet;
    private javax.swing.JComboBox<String> jComboBoxCinsiyet;
    private javax.swing.JComboBox<String> jComboBoxGorev;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePersonel;
    private javax.swing.JTextField jTextFieldAranacakIsim;
    private javax.swing.JTextField jTextFieldIsim;
    private javax.swing.JTextField jTextFieldIzinHakki;
    private javax.swing.JTextField jTextFieldPosta;
    private javax.swing.JTextField jTextFieldSgkNo;
    private javax.swing.JTextField jTextFieldSoyAd;
    private javax.swing.JTextField jTextFieldTc;
    private javax.swing.JTextField jTextFieldTelefon;
    // End of variables declaration//GEN-END:variables
}

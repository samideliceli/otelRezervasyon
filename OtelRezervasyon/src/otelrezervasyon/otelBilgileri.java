/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otelrezervasyon;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import static otelrezervasyon.dbConnection.con;

/**
 *
 * @author delilog
 */
public class otelBilgileri extends javax.swing.JPanel {

    /**
     * Creates new form otelBilgileri
     */
    
    Statement st;
    ResultSet rs;
    boolean kayitVar=false;
    private dbConnection db;
    String isim; 
    
    public otelBilgileri() {
        initComponents();
        db = new dbConnection();
        otelBilgileriniGetir();
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
        yildizSayisi = new javax.swing.JTextField();
        otelAdi = new javax.swing.JTextField();
        telefonNo = new javax.swing.JTextField();
        faxNo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        otelAdres = new javax.swing.JTextArea();
        kaydet = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        katSayisi = new javax.swing.JTextField();
        odaSayisi = new javax.swing.JTextField();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Otel Bilgileri"));

        yildizSayisi.setMinimumSize(new java.awt.Dimension(30, 33));
        yildizSayisi.setPreferredSize(new java.awt.Dimension(18, 33));

        otelAdi.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        otelAdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otelAdiActionPerformed(evt);
            }
        });

        telefonNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonNoActionPerformed(evt);
            }
        });

        otelAdres.setColumns(20);
        otelAdres.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        otelAdres.setRows(5);
        jScrollPane1.setViewportView(otelAdres);

        kaydet.setText("Kaydet");
        kaydet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kaydetActionPerformed(evt);
            }
        });

        jLabel5.setText("Adres :");

        jLabel4.setText("Fax :");

        jLabel3.setText("Telefon :");

        jLabel1.setText("Yıldız Sayısı:");

        jLabel2.setText("Otel İsmi :");

        jLabel6.setText("Kat Sayısı:");

        jLabel7.setText("Oda Sayısı:");

        katSayisi.setPreferredSize(new java.awt.Dimension(18, 33));

        odaSayisi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                odaSayisiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kaydet, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(faxNo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(yildizSayisi, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addGap(3, 3, 3)
                                .addComponent(odaSayisi, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addGap(3, 3, 3)
                                .addComponent(katSayisi, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                            .addComponent(telefonNo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(otelAdi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(otelAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(yildizSayisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(odaSayisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(katSayisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(telefonNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(faxNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(kaydet)
                .addGap(58, 58, 58))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void otelAdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otelAdiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_otelAdiActionPerformed

    private void telefonNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonNoActionPerformed

    
    private void otelBilgileriniGetir() { 
        db.dbBaglan();
        st=dbConnection.getSt();
        
        try {
            ResultSet rs=st.executeQuery("SELECT * FROM otel_bilgileri");
            
            while(rs.next())
                
            {
                isim=rs.getString("otel_adi");
                otelAdi.setText(isim);
                otelAdres.setText(rs.getString("otel_adres"));
                telefonNo.setText(rs.getString("otel_telefon"));
                faxNo.setText(rs.getString("otel_fax"));
                yildizSayisi.setText(rs.getString("otel_yildiz"));
                katSayisi.setText(rs.getString("kat_sayisi"));
                odaSayisi.setText(rs.getString("oda_sayisi"));
                
                
                
                kayitVar=true;
            }
            st.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(otelBilgileri.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    private void kaydetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kaydetActionPerformed
        try {                                       
            // TODO add your handling code here:
            Object ad = otelAdi.getText();
            Object adres = otelAdres.getText();
            Object tel = telefonNo.getText();
            Object fax = faxNo.getText();
            Object yildiz = yildizSayisi.getText();
            Object kat = katSayisi.getText();
            Object oda = odaSayisi.getText();
            db.dbBaglan();
            
           
            if(!kayitVar){
            String sql_otel = ("INSERT INTO OTEL_BILGILERI (OTEL_ADI,OTEL_ADRES,OTEL_TELEFON,OTEL_FAX,OTEL_YILDIZ,KAT_SAYISI,ODA_SAYISI) VALUES (?,?,?,?,?,?,?)");
            
                PreparedStatement ps = con.prepareStatement(sql_otel);
                ps.setObject(1, ad);
                ps.setObject(2, adres);
                ps.setObject(3, tel);
                ps.setObject(4, fax);
                ps.setObject(5, yildiz);
                ps.setObject(6, kat);
                ps.setObject(7, oda);
                ps.executeUpdate();
                
            } 
            
            else{
            
                PreparedStatement ps2;
                
                String sql2="UPDATE otel_bilgileri SET otel_adi='"+ad+"', otel_adres='"+adres+
                "', otel_telefon='"+tel+"', otel_fax='"+fax+"', otel_yildiz="+yildiz+", kat_sayisi="+kat+" WHERE otel_adi='"+isim+"'";
                
                ps2 =dbConnection.getCon().prepareStatement(sql2);
               
                ps2.execute();
                
            }
                
            con.close();
            
            
            Window w = SwingUtilities.getWindowAncestor(this);
            WindowEvent closingEvent = new WindowEvent(w, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
                
           
        } catch (SQLException ex) {
            Logger.getLogger(otelBilgileri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_kaydetActionPerformed

    private void odaSayisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_odaSayisiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_odaSayisiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField faxNo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField katSayisi;
    private javax.swing.JButton kaydet;
    private javax.swing.JTextField odaSayisi;
    private javax.swing.JTextField otelAdi;
    private javax.swing.JTextArea otelAdres;
    private javax.swing.JTextField telefonNo;
    private javax.swing.JTextField yildizSayisi;
    // End of variables declaration//GEN-END:variables

}

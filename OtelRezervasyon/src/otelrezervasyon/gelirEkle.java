package otelrezervasyon;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static otelrezervasyon.dbConnection.con;

public class gelirEkle extends javax.swing.JFrame {

    private dbConnection db;
    static String yeniGelirTur;
    private ArrayList<String> turler;
    private boolean gelirvarMi;

    public gelirEkle() {
        initComponents();
        db = new dbConnection();
        turler = new ArrayList<>();
        yeniGelirTuruEkle.setEnabled(false);
        KeyAdapter adapter = new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                super.keyReleased(evt);
                if (yeniGelir.getText().equals("")) {
                    yeniGelirTuruEkle.setEnabled(false);
                } else {
                    yeniGelirTuruEkle.setEnabled(true);
                }
            }
        };
        yeniGelir.addKeyListener(adapter);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        yeniGelir = new javax.swing.JTextField();
        yeniGelirTuruEkle = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Yeni Gelir Türü :");

        yeniGelirTuruEkle.setText("Ekle");
        yeniGelirTuruEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yeniGelirTuruEkleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(yeniGelir, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(yeniGelirTuruEkle)
                .addGap(137, 137, 137))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(yeniGelir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(yeniGelirTuruEkle)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void yeniGelirTuruEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yeniGelirTuruEkleActionPerformed
        yeniGelirTur = yeniGelir.getText();
        db.dbBaglan();
        String sql = ("INSERT INTO GELIR_TIP (GELIR_TURU) VALUES (?)");
        String sql2 = ("SELECT GELIR_TURU FROM GELIR_TIP");
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.executeQuery();
            ps.setObject(1, yeniGelirTur);
            while (ps2.getResultSet().next()) {
                String tur = ps2.getResultSet().getString("GELIR_TURU");
                turler.add(tur);
            }

            gelirvarMi = turler.contains(yeniGelirTur);

            if (gelirvarMi) {
                JOptionPane.showMessageDialog(null, "Bu Gelir Türü Veritabanında Zaten Mevcut!");
            } else {
                JOptionPane.showMessageDialog(null, "Yeni Gelir Türü Başarıyla Eklendi!");
                ps.executeUpdate();
            }
            yeniGelir.setText("");
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(gelirEkle.class.getName()).log(Level.SEVERE, null, ex);
        }
        WindowEvent closingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
    }//GEN-LAST:event_yeniGelirTuruEkleActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gelirEkle().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField yeniGelir;
    private javax.swing.JButton yeniGelirTuruEkle;
    // End of variables declaration//GEN-END:variables
}

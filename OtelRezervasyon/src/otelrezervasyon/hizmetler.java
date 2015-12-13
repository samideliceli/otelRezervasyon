package otelrezervasyon;

import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import static otelrezervasyon.dbConnection.con;
import static otelrezervasyon.dbConnection.st;

public class hizmetler extends javax.swing.JPanel {

    private dbConnection db;
    private int sutunSayisi, satirSayisi, i, satir, sutun;
    private DefaultTableModel tb;
    private Object sonDeger;
    private Object[] row;
    private Object[][] deger;
    private String sutunAdi;
    private KeyAdapter adapter;

    public hizmetler() {
        initComponents();
        db = new dbConnection();
        servisEkle.setEnabled(false);
        adapter = new KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                super.keyReleased(evt);
                if (yeniServiAdi.getText().toString().equals("") || yeniServisUcret.getText().toString().equals("")) {
                    servisEkle.setEnabled(false);
                } else {
                    servisEkle.setEnabled(true);
                }
            }
        };
        yeniServiAdi.addKeyListener(adapter);
        yeniServisUcret.addKeyListener(adapter);
        tb = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                if (col == 0) {
                    return false;
                }
                return true;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {

            }

        };
        guncelle.setEnabled(false);
        tabloyuCek();
        deger = new Object[satirSayisi][sutunSayisi];
        for (satir = 0; satir < satirSayisi; satir++) {
            for (sutun = 0; sutun < sutunSayisi; sutun++) {
                deger[satir][sutun] = tb.getValueAt(satir, sutun);
            }
        }
        tb.addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                satir = e.getFirstRow();
                sutun = e.getColumn();
                TableModel model = (TableModel) e.getSource();
                sonDeger = model.getValueAt(satir, sutun);
                sutunAdi = model.getColumnName(sutun);
                if (sonDeger.equals(deger[satir][sutun])) {
                    guncelle.setEnabled(false);
                } else {
                    guncelle.setEnabled(true);
                }
            }
        });
        yeniServiAdi.addKeyListener(new KeyAdapter() {
        });
    }

    public void tabloyuCek() {
        db.dbBaglan();
        try (ResultSet rs = st.executeQuery("SELECT * FROM SERVIS")) {
            sutunSayisi = rs.getMetaData().getColumnCount();
            for (int s = 1; s <= sutunSayisi; s++) {
                tb.addColumn(rs.getMetaData().getColumnName(s));
            }
            while (rs.next()) {
                row = new Object[sutunSayisi];
                for (i = 1; i <= sutunSayisi; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                tb.addRow(row);
                servisTablosu.setModel(tb);
                satirSayisi = tb.getRowCount();
            }
            con.close();
        } catch (SQLException hata) {
            Logger.getLogger(personelİslemleri.class
                    .getName()).log(Level.SEVERE, null, hata);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        servisTablosu = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        guncelle = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        yeniServiAdi = new javax.swing.JTextField();
        yeniServisUcret = new javax.swing.JTextField();
        servisEkle = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(784, 410));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 0), 1, true), "Servis Tablosu", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 102, 0)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(51, 102, 0))); // NOI18N

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
        jScrollPane1.setViewportView(servisTablosu);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 0), 1, true));

        guncelle.setText("Güncelle");
        guncelle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guncelleActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Yeni Servis", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 102, 0))); // NOI18N

        servisEkle.setText("Ekle");
        servisEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servisEkleActionPerformed(evt);
            }
        });

        jLabel1.setText("Servis Adı :");

        jLabel2.setText("Servis Ücreti :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(51, 51, 51)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(yeniServisUcret, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(yeniServiAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(servisEkle, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yeniServiAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(yeniServisUcret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(servisEkle)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(guncelle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(guncelle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void guncelleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guncelleActionPerformed
        db.dbBaglan();
        try {
            String sql = ("UPDATE SERVIS SET " + sutunAdi + " = (?) WHERE " + sutunAdi + " = (?) ");
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, sonDeger);
            ps.setObject(2, deger[satir][sutun]);
            if (ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Veri Başarıyla Güncellendi!");
                deger[satir][sutun] = sonDeger;
            } else {
                JOptionPane.showMessageDialog(null, "Hata Oluştu!");
            }
            guncelle.setEnabled(false);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(hizmetler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_guncelleActionPerformed

    private void servisEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_servisEkleActionPerformed
        String servisAdi = yeniServiAdi.getText();
        String servisUcreti = yeniServisUcret.getText();
        db.dbBaglan();
        try {
            String sql = ("INSERT INTO SERVIS (ISIM,UCRET) VALUES (?,?)");
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, servisAdi);
            ps.setString(2, servisUcreti);
            if (ps.executeUpdate() != 0) {
                JOptionPane.showMessageDialog(null, "Veriler Başarıyla Eklendi!");
            } else {
                JOptionPane.showMessageDialog(null, "Hata Oluştu!");
            }
            yeniServiAdi.setText("");
            yeniServisUcret.setText("");
            servisEkle.setEnabled(false);
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(hizmetler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_servisEkleActionPerformed

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
    private javax.swing.JTextField yeniServiAdi;
    private javax.swing.JTextField yeniServisUcret;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otelrezervasyon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author delilog
 */
public class musteriEkle extends javax.swing.JFrame {

    private final KeyAdapter adapter,tarihAdapter,tcAdapter;
    
    
    
    private String tcText;
    private String emailText;
    private String isimText;
    private String soyIsimText;
    private String ilkTarihText;
    private String ikinciTarihText;
    private String onOdemeText;
    private String telefonText;
    private int secilenOdaText;
    private final textKontrolleri kontrol;
    private final dbConnection db;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement st,st2;
    private boolean odaSecildi=false;
    private boolean eskiMusteri,rezVar=false;
    private boolean  kralOdaSecildi=false;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    
    private long toplamGun;
    
    
    
    /**
     * Creates new form musteriEkle
     */
    public musteriEkle() {
        kontrol = new textKontrolleri();
        initComponents();
        
        db=new dbConnection();
        db.dbBaglan();
        
        buttonGroup1.add(musteriRadio);
        buttonGroup1.add(jRadioButton2);
        
        buttonGroup2.add(normalRadio);
        buttonGroup2.add(kralRadio);
        
        
      
        kralRadio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ıe) {
            kralOdaSecildi=true;
            jLabel12.setVisible(false);
            kisiSayisiCombo.setVisible(false);
            }
        });
        
        normalRadio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ıe) {
                try {
                        kralOdaSecildi=false;
                        jLabel12.setVisible(true);
                        kisiSayisiCombo.setVisible(true);
                        kisiSayisiCombo.removeAllItems();

                        st=dbConnection.getSt();
                        rs=st.executeQuery("SELECT kapasite FROM oda_tipi "
                                + "WHERE tur='"+"Normal"+"'"+" ORDER BY kapasite ASC");


                        while(rs.next())   
                        {
                            int kapasite=rs.getInt("kapasite");
                            kisiSayisiCombo.addItem(kapasite+"");
                        }

                } catch (SQLException ex) {
                    Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            
            
            }
        });
        
        
         musteriRadio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ıe) {
            
            Date gununTarihi = new Date();
           
            String bugun=formatter.format(gununTarihi.getTime());
            baslangicTarih.setText(bugun);
            baslangicTarih.setEnabled(false);
                
                
                
            }
           
        });
        
         jRadioButton2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ıe) {
            
                baslangicTarih.setText("");
                baslangicTarih.setEnabled(true);
            
            }
        });
        
        kaydet.setEnabled(false);
        adapter = new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                super.keyReleased(evt);
                if (degerlerDoluMu()) {
                        if (degerKontrolleri() && odaSecildi){
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
        
        normalRadio.setSelected(true);
        
        
        tarihAdapter = new KeyAdapter() {
           @Override
           public void keyReleased(java.awt.event.KeyEvent evt) {
               super.keyReleased(evt);
               if (tarihlerDoluMu()) {
                       if (tarihKontrolleri()){
                               odaSorgula.setEnabled(true); 
                       }
                       else {
                           odaSorgula.setEnabled(false);
                           odaBilgileriniTemizle();
                           tc.setEnabled(false);
                           tc.setText("");
                       }
               } else{ 
                   odaBilgileriniTemizle();
                   odaSorgula.setEnabled(false);
                   tc.setEnabled(false);
                   tc.setText("");
               }
           }  

       };
        
       tcAdapter = new KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                super.keyReleased(evt);
                tcText=tc.getText();
                if (kontrol.tcKontrol(tcText)) {
                    
                    try {
                        
                        st=dbConnection.getSt();
                        rs=st.executeQuery("SELECT ad,soyad,cinsiyet,telefon,email FROM musteri "
                                + "WHERE tc='"+tcText+"'");
                        int i=0;
                        
                        while(rs.next())
                        {
                            i++;
                            
                            int id=otelBilgileriniKaydet(tcText);
                            
                            
                            
                            if(ilisikkesildimi(id) ){                  
                                isimText=rs.getString("ad");
                                isim.setText(isimText);
                                isim.setEnabled(false);

                                soyIsimText=rs.getString("soyad");
                                soyisim.setText(soyIsimText);
                                soyisim.setEnabled(false);

                                telefonText=rs.getString("telefon");
                                telefon.setText(telefonText);
                                telefon.setEnabled(false);

                                emailText=rs.getString("email");
                                email.setText(emailText);
                                email.setEnabled(false);

                                String cinsiyetText=rs.getString("cinsiyet");

                                if(cinsiyetText.equals("e")){

                                    cinsiyet.setSelectedIndex(0);

                                }
                                else{
                                   cinsiyet.setSelectedIndex(1);
                                }


                                kaydet.setEnabled(true);

                                baslangicTarih.setEnabled(false);
                                bitisTarih.setEnabled(false);

                                eskiMusteri=true;
                                iptal.setVisible(true);
                            }
                            else{
                            
                            JOptionPane.showMessageDialog(null, "Müşteri ilişiği kesilmemiş");
                           
                            isim.setEnabled(false);
                            soyisim.setText(soyIsimText);
                            soyisim.setEnabled(false);;
                            telefon.setText(telefonText);
                            telefon.setEnabled(false);
                            email.setEnabled(false);
                            }
                        }
                            
                           if(i==0){

                                eskiMusteri=false;

                                isim.setText("");
                                isim.setEnabled(true);

                                soyisim.setText("");
                                soyisim.setEnabled(true);

                                telefon.setText("");
                                telefon.setEnabled(true);

                                email.setText("");
                                email.setEnabled(true);
                             }
                           
                           if(rezervasyondaVar(tcText)){
                           JOptionPane.showMessageDialog(null, "Rezervasyonda kayıt var.");
                           
                            isim.setEnabled(false);
                            soyisim.setText(soyIsimText);
                            soyisim.setEnabled(false);;
                            telefon.setText(telefonText);
                            telefon.setEnabled(false);
                            email.setEnabled(false);
                           
                           }
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                    
                }
            }  

           

            
        };
        
        odaSorgula.setEnabled(false);
        
        verileriBosalt();
        
        isim.addKeyListener(adapter);
        soyisim.addKeyListener(adapter);
        tc.addKeyListener(tcAdapter);        
        telefon.addKeyListener(adapter);
        email.addKeyListener(adapter);
        baslangicTarih.addKeyListener(tarihAdapter);
        bitisTarih.addKeyListener(tarihAdapter);
      
        }
    
    
    
    
  
    
      private void verileriBosalt(){
          
          isim.setEnabled(false);
          isim.setText("");
          
          soyisim.setEnabled(false);
          soyisim.setText("");
          
          tc.setEnabled(false);
          tc.setText("");
          
          telefon.setEnabled(false);
          telefon.setText("");
          
          email.setEnabled(false);
          email.setText("");
          
          baslangicTarih.setEnabled(true);
          bitisTarih.setEnabled(true);
          
          kaydet.setEnabled(false);
          iptal.setVisible(false);
          
          odalar.removeAll();
          odalar.validate();
          odalar.repaint();
          secilenOda.setText("---");
          odaUcret.setText("---");
      }
    
      private boolean tarihlerDoluMu() {
      
             if (baslangicTarih.getText().equals("")) {
                    return false;
                }
                else if (bitisTarih.getText().equals("")) {
                    return false;
                }
          return true;
          
      }
      
       private boolean tarihKontrolleri() {
          ilkTarihText=baslangicTarih.getText();
          ikinciTarihText=bitisTarih.getText();
       return kontrol.tarihKontrol(ilkTarihText,ikinciTarihText);
       }
    
    
      private boolean degerKontrolleri() {
          tcText=tc.getText();
          emailText=email.getText();
          isimText=isim.getText();
          soyIsimText=soyisim.getText();
          
          onOdemeText=onOdeme.getText();
          telefonText=telefon.getText();
           
          return kontrol.onOdemeKontrol(onOdemeText)
                  && kontrol.telefonKontrol(telefonText) && kontrol.emailKontrol(emailText) && 
                  kontrol.adSoyadKontrol(isimText,soyIsimText);
        
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
                
                else if (onOdeme.getText().equals("")) {
                    return false;
                }
                
                
                return true;
               }
    
    
    
    private int otelBilgileriniKaydet(String tc) {
        
        String sql="";
        
        st=dbConnection.getSt();
        try {
            rs=st.executeQuery("SELECT * FROM musteri WHERE tc='"+tcText+"'");  
            
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
    
     private int konakSayisi(int id) {
       
        
        st=dbConnection.getSt();
        try {
            rs=st.executeQuery("SELECT konak_sayisi FROM musteri_otel_bilgileri WHERE musteri_id="+id);  
            
            while(rs.next())
            {
                int konakSayisi = rs.getInt("konak_sayisi");
                return konakSayisi;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    
     private boolean ilisikkesildimi(int id) {
         
         
        
        st=dbConnection.getSt();
        try {
            rs=st.executeQuery("SELECT iliski_kesim FROM musteri_otel_bilgileri WHERE musteri_id="+id);  
            
            while(rs.next())
            {
                boolean iliskiKesim = rs.getBoolean("iliski_kesim");
                return iliskiKesim;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return false; }
     
        private boolean rezervasyondaVar(String tc) {
        
                   
         st=dbConnection.getSt();
        try {
            rs=st.executeQuery("SELECT * FROM rezervasyon WHERE tc='"+tc+"'");  
            int i=0;
            while(rs.next())
            {
                i++;
                
            }
            
            if(i==0){
                return false;
            }{
                return true;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
        }  
          
        return false;
      }
    
    private void musteriKaydet() {
     String sql="Insert INTO musteri (AD,SOYAD,CINSIYET,TC,TELEFON,EMAIL,INDIRIM_MIKTARI) "
                + "VALUES (?,?,?,?,?,?,?)";
        
        String cinsiyet;
        
        onOdemeText=onOdeme.getText();
        java.sql.Date sqldate = null,sqldate2 = null;
       
         
       
        try {
            
            Date date = formatter.parse(ilkTarihText);
            sqldate = new java.sql.Date(date.getTime());

            Date date2 = formatter.parse(ikinciTarihText);
            sqldate2 = new java.sql.Date(date.getTime());
            
        } catch (ParseException ex) {
            Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        if(this.cinsiyet.getSelectedIndex()==0){
            cinsiyet="e";
        }
        else{
            cinsiyet="k";
        }
        
        try {
            
             if(eskiMusteri){
                 
                
                int id=otelBilgileriniKaydet(tcText) ;
                
                int konaksayisi=konakSayisi(id);
                 
                String sql2="UPDATE  musteri_otel_bilgileri SET ODA_NO="+secilenOdaText
                        +",ON_ODEME_TUTAR="+onOdemeText+ ", BASLANGIC_TARIHI='"+sqldate
                        +"', BITIS_TARIHI='"+sqldate2+"', ILISKI_KESIM=false, KONAK_SAYISI="+(konaksayisi+1)
                        + " WHERE musteri_id="+id;
                
                ps =dbConnection.getCon().prepareStatement(sql2);
               
                ps.execute();
            
            }else{
                 
                ps =dbConnection.getCon().prepareStatement(sql);

                ps.setString(1, isimText);
                ps.setString(2, soyIsimText);
                ps.setString(3, cinsiyet);
                ps.setString(4, tcText);
                ps.setString(5, telefonText);
                ps.setString(6, emailText);
                ps.setInt(7, 0);
                ps.executeUpdate();
                

                int id = otelBilgileriniKaydet(tcText);
              
                String sql2="Insert INTO musteri_otel_bilgileri (MUSTERI_ID,ODA_NO,"
                        + "ON_ODEME_TUTAR,BASLANGIC_TARIHI,BITIS_TARIHI,"
                        + "ILISKI_KESIM,KONAK_SAYISI) VALUES (?,?,?,?,?,?,?)";

                ps =dbConnection.getCon().prepareStatement(sql2);
                ps.setInt(1, id);
                ps.setInt(2, secilenOdaText);
                ps.setInt(3, Integer.valueOf(onOdemeText));
                ps.setDate(4,  sqldate);
                ps.setDate(5, sqldate2);
                ps.setBoolean(6, false);
                ps.setInt(7, 5);
                ps.execute();
            }
            dbConnection.getCon().close();
            
            
            WindowEvent closingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);

            
        } catch (SQLException ex) {
            Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    private void rezervasyonKaydet() {  
    
        
        String sql="Insert INTO rezervasyon (AD,SOYAD,CINSIYET,TC,TELEFON,"
                + "EMAIL,BASLANGIC_TARIHI,BITIS_TARIHI,ODEME_TUTARI,ODA_NO) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        
        String sql3 = ("INSERT INTO GELIR (GELIR_TURU,TARIH,GELIR) VALUES (?,?,?)");
        
        
        String cinsiyet;
        onOdemeText=onOdeme.getText();
        
       
        
        if(this.cinsiyet.getSelectedIndex()==0){
            cinsiyet="e";
        }
        else{
            cinsiyet="k";
        }
        
        try {
            ps =dbConnection.getCon().prepareStatement(sql);
            
            
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
            ps.setInt(10, secilenOdaText);
            ps.executeUpdate();
            
            
            Date gununTarihi = new Date();
            sqldate = new java.sql.Date(gununTarihi.getTime());
            
            PreparedStatement ps2 =dbConnection.getCon().prepareStatement(sql3);
            ps2.setString(1, "Ön ödeme");
            ps2.setDate(2, sqldate);
            ps2.setInt(3, Integer.valueOf(onOdemeText));
            ps2.executeUpdate();
            
            dbConnection.getCon().close();
            
            
            WindowEvent closingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);

            
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    
    
    private boolean odaBosmu(int odaNo, String tablo) { 
        
        st=dbConnection.getSt(); 
        ResultSet rs3 = null;
        
        java.sql.Date sqldate2=null,sqldate=null;
           try {
               Date date;
               date = formatter.parse(ilkTarihText);
                sqldate = new java.sql.Date(date.getTime());
            
               Date date2 = formatter.parse(ikinciTarihText);
               sqldate2 = new java.sql.Date(date2.getTime());
               
           } catch (ParseException ex) {
               Logger.getLogger(musteriIslemleri.class.getName()).log(Level.SEVERE, null, ex);
           }
        
      
        
        try {
            st2 = dbConnection.getCon().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
  
            rs3=st2.executeQuery("SELECT oda_no FROM "+tablo+" WHERE "
                    + "(baslangic_tarihi>='"+sqldate+"' AND bitis_tarihi<='"+sqldate2+"') "
            +"AND oda_no="+odaNo);  
           
           int i=0;
            while(rs3.next())
            {
              i++;
               
               
            }
            
            return i==0;
            
        } catch (SQLException ex) {
            Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
        return false;
              
    }
    
  private void bosOdalariDoldur(){
      
       
       st=dbConnection.getSt();
       Color background;
       
       
       int eskiKat=0;
        try {  
            String sql="SELECT oda_no,kat,tip_id FROM oda";
            
            rs=st.executeQuery(sql);
            
            while(rs.next())
            {
               final String odaNo=rs.getString("oda_no");
               int kat=Integer.valueOf(odaNo.substring(0,1));

               
               int sira=Integer.valueOf(odaNo)-(kat*100);
               
               if(eskiKat!=kat){
                   JLabel katText=new JLabel();
                   katText.setText("KAT "+kat);
                   katText.setFont(new Font("Century Schoolbook L", Font.PLAIN, 12));
                   katText.setBounds(15, 60*(kat-1), 55, 30);
                   katText.setForeground(new Color(33,41,47));
                   eskiKat=kat;
                   odalar.add(katText);
               }
               
               
               
               final boolean musBos=odaBosmu(Integer.valueOf(odaNo), "musteri_otel_bilgileri");
               final boolean odaBos=musBos && odaBosmu(Integer.valueOf(odaNo), "rezervasyon");
               
               if(odaBos){
               
                   background=new Color(91,155,30); 
               }else{
                   background=new Color(189,54,47);
               }
               
               
               final String odaTip=rs.getString("tip_id");
               int fiyat=0;
               String tur="---";
               int kapasite=0; 
               
            
               
               ResultSet  rs2;
               rs2 = st2.executeQuery("SELECT fiyat,kapasite,tur FROM oda_tipi WHERE tip_id="+odaTip);
               
                try {
                    while(rs2.next())
                    {
                        fiyat=rs2.getInt("fiyat");
                        kapasite=rs2.getInt("kapasite");
                        tur=rs2.getString("tur");
                    }
                }
                finally{
                    rs2.close();
                }
               
               final int a = fiyat;
               final int kapasiteOda=kapasite;
               final String turOda=tur;
                              
                ActionListener ac=new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent ae) {
                   
                     
                           if(odaBos){
                            onOdeme.setText((toplamGun*a*15/100)+"");
                            odaUcret.setText(a+"₺");
                            toplamUcret.setText(toplamGun*a+"");
                            secilenOda.setText(""+odaNo);
                            secilenOdaText=Integer.valueOf(odaNo);
                            //kisiSayisi.setText(kapasiteOda+"");
                            //odaTuru.setText(turOda);
                            odaSecildi=true;

                            tc.setEnabled(true);

                            if(degerKontrolleri()){

                            kaydet.setEnabled(true);
                            
                            }
                            
                           }
                           else{
                                JOptionPane.showMessageDialog(null, "Oda dolu");
                          
                           }
                       
                   
                   }
               };
               
               
               if(!kralOdaSecildi){
                    int kisiSayisi=Integer.valueOf(kisiSayisiCombo.getSelectedItem().toString());
                    if(kisiSayisi==kapasite){
                     
                        JButton oda=new JButton();
                        oda.setText(odaNo);
                        oda.setForeground(Color.WHITE);
                        oda.setBackground(background);
                        oda.setBounds((55*sira)+10, 30+((kat-1)*60), 55, 30);
                        
                        odalar.add(oda);
                        oda.addActionListener(ac);
                    }
                 }
                 
                 else{
                     
                     if(turOda.equals("Kral")){
                        JButton oda=new JButton();
                        oda.setText(odaNo);
                        oda.setForeground(Color.WHITE);
                        oda.setBackground(background);
                        oda.setBounds((55*sira)+10, 30+((kat-1)*60), 55, 30);
                     
                        odalar.add(oda);
                        oda.addActionListener(ac);
                     }
                 
                 }
               
               
            }
            
        } catch (SQLException ex) {
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
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
        musteriRadio = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        bitisTarih = new javax.swing.JTextField();
        onOdeme = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        odalar = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        secilenOda = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        odaUcret = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        odaSorgula = new javax.swing.JButton();
        iptal = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        normalRadio = new javax.swing.JRadioButton();
        kralRadio = new javax.swing.JRadioButton();
        kisiSayisiCombo = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        toplamUcret = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Müşteri Ekle");
        setResizable(false);

        jLabel2.setText("TC");

        jLabel3.setText("İsim");

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

        musteriRadio.setText("Müşteri");

        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Rezervasyon");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        onOdeme.setEnabled(false);
        onOdeme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onOdemeActionPerformed(evt);
            }
        });

        jLabel10.setText("Ön ödeme");

        odalar.setBackground(new java.awt.Color(199, 201, 203));
        odalar.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        odalar.setToolTipText("Oda Seçim");

        javax.swing.GroupLayout odalarLayout = new javax.swing.GroupLayout(odalar);
        odalar.setLayout(odalarLayout);
        odalarLayout.setHorizontalGroup(
            odalarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, odalarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(256, 256, 256))
        );
        odalarLayout.setVerticalGroup(
            odalarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(odalarLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(243, Short.MAX_VALUE))
        );

        secilenOda.setText("---");

        jLabel15.setText("Soyisim");

        jLabel18.setText("Günlük:");

        odaUcret.setText("---");

        jLabel11.setText("Seçilen Oda:");

        odaSorgula.setText("Sorgula");
        odaSorgula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                odaSorgulaActionPerformed(evt);
            }
        });

        iptal.setText("İptal");
        iptal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iptalActionPerformed(evt);
            }
        });

        jLabel12.setText("Kişi:");

        normalRadio.setText("Normal");
        normalRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalRadioActionPerformed(evt);
            }
        });

        kralRadio.setSelected(true);
        kralRadio.setText("Kral");

        kisiSayisiCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel13.setText("Toplam:");

        toplamUcret.setText("---");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(odalar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tc)
                                    .addComponent(onOdeme, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                                    .addComponent(baslangicTarih)
                                    .addComponent(telefon)
                                    .addComponent(isim)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(secilenOda))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(normalRadio)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(kralRadio)))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel18))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(kisiSayisiCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(iptal))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(odaUcret)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(toplamUcret)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(kaydet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(email, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bitisTarih, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(30, 30, 30)
                                .addComponent(soyisim, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(29, 29, 29)
                                .addComponent(cinsiyet, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(odaSorgula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jRadioButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(musteriRadio)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(isim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cinsiyet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(telefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(soyisim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(baslangicTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bitisTarih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(26, 26, 26)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(onOdeme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(86, 86, 86)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(secilenOda)
                            .addComponent(jLabel18)
                            .addComponent(odaUcret)
                            .addComponent(jLabel13)
                            .addComponent(toplamUcret)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(iptal)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(normalRadio)
                                .addComponent(kralRadio)
                                .addComponent(jLabel12)
                                .addComponent(kisiSayisiCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton2)
                            .addComponent(musteriRadio))
                        .addGap(44, 44, 44)
                        .addComponent(odaSorgula)
                        .addGap(18, 18, 18)
                        .addComponent(kaydet)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(odalar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void odaSorgulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_odaSorgulaActionPerformed
        try {
            // TODO add your handling code here:
            ilkTarihText=baslangicTarih.getText();
            ikinciTarihText=bitisTarih.getText();
            
           
            
            Date date = formatter.parse(ilkTarihText);
            Date date2 = formatter.parse(ikinciTarihText);
            
            odaBilgileriniTemizle();
            Date gununTarihi=new Date();
            String bugun=formatter.format(gununTarihi.getTime());
            Date date3 = formatter.parse(bugun);
            
            if(date2.getTime()< date.getTime()){
               
                JOptionPane.showMessageDialog(null, "İlk tarih ikinciden büyük.");
                
            }
            else if(date.getTime()<date3.getTime()){
            
                JOptionPane.showMessageDialog(null, "Bugünden daha önceye kayit yapılamaz.");
            }
            else{
                
                long difference=date2.getTime()- date.getTime();
                difference=((difference)/(1000*60*60*24))+1;
                toplamGun=difference;
                

                bosOdalariDoldur();

                odalar.validate();
                odalar.repaint();
            
            }
        } catch (ParseException ex) {
            Logger.getLogger(musteriEkle.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_odaSorgulaActionPerformed

    private void iptalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iptalActionPerformed
        // TODO add your handling code here:
        verileriBosalt();
    }//GEN-LAST:event_iptalActionPerformed

    private void normalRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalRadioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_normalRadioActionPerformed

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
            @Override
            public void run() {
                new musteriEkle().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField baslangicTarih;
    private javax.swing.JTextField bitisTarih;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cinsiyet;
    private javax.swing.JTextField email;
    private javax.swing.JButton iptal;
    private javax.swing.JTextField isim;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JButton kaydet;
    private javax.swing.JComboBox<String> kisiSayisiCombo;
    private javax.swing.JRadioButton kralRadio;
    private javax.swing.JRadioButton musteriRadio;
    private javax.swing.JRadioButton normalRadio;
    private javax.swing.JButton odaSorgula;
    private javax.swing.JLabel odaUcret;
    private javax.swing.JPanel odalar;
    private javax.swing.JTextField onOdeme;
    private javax.swing.JLabel secilenOda;
    private javax.swing.JTextField soyisim;
    private javax.swing.JTextField tc;
    private javax.swing.JTextField telefon;
    private javax.swing.JLabel toplamUcret;
    // End of variables declaration//GEN-END:variables

    private void odaBilgileriniTemizle() {
    
    odalar.removeAll();
            odalar.validate();
            odalar.repaint();
            secilenOda.setText("---");
            odaUcret.setText("---");
            onOdeme.setText("");
            toplamUcret.setText("---");
    
    }

      

    

   
}

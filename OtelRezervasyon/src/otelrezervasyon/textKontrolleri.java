/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otelrezervasyon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author delilog
 */
public class textKontrolleri {
    private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    private final String tcPattern="[0-9]{11}";
    
    private static final String sayiPatern="[0-9]+";
    
    private final String isimSoyisimPattern="[a-zA-ZöÖŞşİıçÇğĞüÜ ]+";
    
    private final String tarihPattern="^(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)$"; 
    
    private final String onOdemePattern="^[1-9][0-9]+";
    
    private final String telefonPattern ="^(0(\\d{3})(\\d{3})(\\d{2})(\\d{2}))$";
    
    public boolean tcKontrol(String tcText) {
      
      return tcText.matches(tcPattern);
        
    }
    public boolean emailKontrol(String emailText) {
      
      return emailText.matches(EMAIL_PATTERN);
        
    }
    
     public boolean adSoyadKontrol(String isimText,String soyIsimText) {
      boolean isimSonuc,soyisimSonuc;
      
      
      
      isimSonuc= isimText.matches(isimSoyisimPattern);
      soyisimSonuc=soyIsimText.matches(isimSoyisimPattern);
      
      return isimSonuc && soyisimSonuc;
     }
     
     
     public boolean tarihKontrol(String ilkTarihText,String ikinciTarihText) {
         
      boolean ilkTarihSonuc,ikinciTarihSonuc;
      
      
      String dateFormat="dd-MM-yyyy";
      
      ilkTarihSonuc=tarihGecerliMi(ilkTarihText, dateFormat);
      ikinciTarihSonuc=tarihGecerliMi(ikinciTarihText, dateFormat);
      
      return ilkTarihSonuc && ikinciTarihSonuc;
         
         
     }
     
     public boolean tarihGecerliMi(String dateToValidate, String dateFromat){
        
        if(dateToValidate.matches(tarihPattern)){

            SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
            sdf.setLenient(false);

            try {
                    Date date = sdf.parse(dateToValidate);
                    System.out.println(date);

            } catch (ParseException e) {

                    e.printStackTrace();
                    return false;
            }

            return true;
        }
        
        return false;
}
     
     public boolean onOdemeKontrol(String onOdemeText) {
         
        
         
         return onOdemeText.matches(onOdemePattern);
         
         
     }
     
      public boolean telefonKontrol(String telefonText) {
      
      return telefonText.matches(telefonPattern);
        
    }
    public static boolean sayiKontrol(String sayiText){
        return sayiText.matches(sayiPatern);
    }
}

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Hashing {
   
   public String getHash(String input) {
      StringBuilder result = new StringBuilder();
      try {
         MessageDigest md = MessageDigest.getInstance("SHA-256");
         md.update(input.getBytes());
         byte [] bytes = md.digest();
         for(int i=0;i<bytes.length;i++) {
            result.append(
                  Integer.toString((bytes[i]&0xff)+0x100,16).substring(1));
            
         }
      }catch (NoSuchAlgorithmException e) {
         e.getMessage();
      }
      
      
      return result.toString();
   }
}
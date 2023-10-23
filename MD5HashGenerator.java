   import java.security.MessageDigest;
   import java.security.NoSuchAlgorithmException;
   import java.nio.charset.StandardCharsets;

   public class MD5HashGenerator {
       public static String generateMD5Hash(String input) throws NoSuchAlgorithmException {
           MessageDigest md = MessageDigest.getInstance("MD5");
           byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
           BigInteger no = new BigInteger(1, messageDigest);
           String hashtext = no.toString(16);
           while (hashtext.length() < 32) {
               hashtext = "0" + hashtext;
           }
           return hashtext;
       }
   }

   

      

      

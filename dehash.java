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

      public class CombinationGenerator {
       public static void generateCombinations(String prefix, int maxLength) {
           if (prefix.length() > maxLength) {
               return;
           }
           System.out.println(prefix);
           for (char c = 'a'; c <= 'z'; c++) {
               generateCombinations(prefix + c, maxLength);
           }
       }
   }

      public class HashMatcher {
       public static String matchHash(String inputHash, int maxLength) throws NoSuchAlgorithmException {
           for (int i = 1; i <= maxLength; i++) {
               CombinationGenerator.generateCombinations("", i, inputHash);
           }
           // TODO: If a match is found in any thread, return the original text
       }
   }

      public class MultiThreadedHashMatcher {
       public static String matchHash(String inputHash, int maxLength) throws NoSuchAlgorithmException, InterruptedException {
           Thread[] threads = new Thread[maxLength];
           for (int i = 0; i < maxLength; i++) {
               final int length = i + 1;
               threads[i] = new Thread(() -> {
                   try {
                       CombinationGenerator.generateCombinations("", length, inputHash);
                   } catch (NoSuchAlgorithmException e) {
                       e.printStackTrace();
                   }
               });
               threads[i].start();
           }
           for (Thread thread : threads) {
               thread.join();
           }
           // TODO: If a match is found in any thread, return the original text
       }
   }

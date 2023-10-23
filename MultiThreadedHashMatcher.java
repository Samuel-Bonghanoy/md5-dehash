import java.security.NoSuchAlgorithmException;

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

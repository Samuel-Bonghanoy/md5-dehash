package com.mycompany.md5dehash;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Iterator;
 
public class MD5dehash {
     private static final String CHARSET = "abcdefghijklmnopqrstuvwxyz";
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 8;

    private static final String targetHash = "7ed7d5fbbb6c5a9efbd055cd84d46563"; // Replace with your MD5 hash
    private static AtomicBoolean found = new AtomicBoolean(false);

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(6);
        CountDownLatch latch = new CountDownLatch(1);

        for (int length = MIN_LENGTH; length <= MAX_LENGTH; length++) {
            if (found.get()) {
                break;
            }

            for (String password : generatePasswords(length)) {
                if (found.get()) {
                    break;
                }

                executor.execute(() -> {
                    String md5Hash = calculateMD5(password);
                    if (md5Hash.equals(targetHash)) {
                        found.set(true);
                        System.out.println("Original text found: " + password);
                        latch.countDown(); 
                    }
                });
            }
        }

        try {
            latch.await(); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        executor.shutdown();
    }

    private static Iterable<String> generatePasswords(int length) {
        return () -> new PasswordIterator(length, CHARSET);
    }

    private static String calculateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static class PasswordIterator implements Iterator<String> {
        private final int length;
        private final String charset;
        private int currentIndex = 0;
        private String currentPassword = "";

        public PasswordIterator(int length, String charset) {
            this.length = length;
            this.charset = charset;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < Math.pow(charset.length(), length);
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new UnsupportedOperationException("No more passwords to generate");
            }

            StringBuilder passwordBuilder = new StringBuilder();
            int index = currentIndex;

            for (int i = 0; i < length; i++) {
                int charIndex = index % charset.length();
                passwordBuilder.append(charset.charAt(charIndex));
                index /= charset.length();
            }

            currentIndex++;
            currentPassword = passwordBuilder.toString();
            return currentPassword;
        }
    }
}
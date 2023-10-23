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

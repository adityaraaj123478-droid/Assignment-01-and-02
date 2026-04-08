import java.util.*;

public class Assignment01and02 {
    public static void findTwoSum(int[] amounts, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < amounts.length; i++) {
            int complement = target - amounts[i];
            if (map.containsKey(complement)) {
                System.out.println("Suspicious Pair Found: " + amounts[i] + " + " + complement);
            }
            map.put(amounts[i], i);
        }
    }

    public static void main(String[] args) {
        int[] dailyTransactions = {500, 300, 200, 1000};
        findTwoSum(dailyTransactions, 500); // Should find 300 + 200
    }
}
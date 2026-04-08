import java.util.*;
import java.util.stream.Collectors;

public class Assignment01and02 {
    static class AutocompleteSystem {
        private Map<String, Integer> queryStats = new HashMap<>();

        public void updateFrequency(String query) {
            queryStats.put(query, queryStats.getOrDefault(query, 0) + 1);
        }

        public List<String> search(String prefix) {
            return queryStats.entrySet().stream()
                    .filter(e -> e.getKey().startsWith(prefix))
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(3)
                    .map(e -> e.getKey() + " (" + e.getValue() + " searches)")
                    .collect(Collectors.toList());
        }
    }

    public static void main(String[] args) {
        AutocompleteSystem ac = new AutocompleteSystem();
        ac.updateFrequency("java tutorial");
        ac.updateFrequency("java tutorial");
        ac.updateFrequency("javascript");
        ac.updateFrequency("java download");

        System.out.println("Suggestions for 'jav': " + ac.search("jav"));
    }
}
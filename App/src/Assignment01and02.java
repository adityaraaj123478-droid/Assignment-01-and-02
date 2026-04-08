
import java.util.*;

public class Assignment01and02 {
    static class MultiLevelCache {
        // L1 Cache with LRU (LinkedHashMap with accessOrder=true)
        private final int L1_SIZE = 2;
        private Map<String, String> l1 = new LinkedHashMap<>(L1_SIZE, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry eldest) { return size() > L1_SIZE; }
        };

        private Map<String, String> database = new HashMap<>();

        public MultiLevelCache() {
            database.put("vid1", "Action Movie Data");
            database.put("vid2", "Comedy Show Data");
        }

        public String getVideo(String id) {
            if (l1.containsKey(id)) {
                return "L1 HIT: " + l1.get(id);
            }

            String data = database.get(id);
            if (data != null) {
                l1.put(id, data);
                return "DB HIT (Promoted to L1): " + data;
            }
            return "404 Not Found";
        }
    }

    public static void main(String[] args) {
        MultiLevelCache cache = new MultiLevelCache();
        System.out.println(cache.getVideo("vid1"));
        System.out.println(cache.getVideo("vid1")); // Second time is a HIT
    }
}
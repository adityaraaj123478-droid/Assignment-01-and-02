import java.util.*;

public class Assignment01and02 {
    static class AnalyticsSystem {
        private Map<String, Integer> pageViews = new HashMap<>();
        private Map<String, Set<String>> uniqueVisitors = new HashMap<>();
        private Map<String, Integer> trafficSources = new HashMap<>();

        public void processEvent(String url, String userId, String source) {
            // Track total page views
            pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

            // Track unique visitors using a Set
            uniqueVisitors.computeIfAbsent(url, k -> new HashSet<>()).add(userId);

            // Track traffic sources
            trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
        }

        public void getDashboard() {
            System.out.println("\n--- Real-Time Analytics Dashboard ---");
            System.out.println("Top Pages:");

            // Sort pages by view count
            pageViews.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(5)
                    .forEach(e -> System.out.println(e.getKey() + " - " + e.getValue() +
                            " views (" + uniqueVisitors.get(e.getKey()).size() + " unique)"));

            System.out.println("\nTraffic Sources:");
            trafficSources.forEach((source, count) -> System.out.println(source + ": " + count));
        }
    }

    public static void main(String[] args) {
        AnalyticsSystem dashboard = new AnalyticsSystem();
        dashboard.processEvent("/news/breaking", "user1", "Google");
        dashboard.processEvent("/news/breaking", "user2", "Facebook");
        dashboard.processEvent("/news/breaking", "user1", "Google"); // Repeat user
        dashboard.processEvent("/home", "user3", "Direct");

        dashboard.getDashboard();
    }
}
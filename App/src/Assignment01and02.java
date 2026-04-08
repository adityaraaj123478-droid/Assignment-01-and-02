import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Assignment01and02 {

    // --- Problem 3: DNS Cache Implementation ---
    static class DNSEntry {
        String ipAddress;
        long expiryTime;

        DNSEntry(String ipAddress, int ttlInSeconds) {
            this.ipAddress = ipAddress;
            // Current time in ms + (TTL * 1000)
            this.expiryTime = System.currentTimeMillis() + (ttlInSeconds * 1000L);
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    static class DNSResolver {
        private Map<String, DNSEntry> cache = new ConcurrentHashMap<>();
        private int hits = 0;
        private int misses = 0;

        public String resolve(String domain) {
            long startTime = System.nanoTime();
            DNSEntry entry = cache.get(domain);

            if (entry != null && !entry.isExpired()) {
                hits++;
                long duration = System.nanoTime() - startTime;
                System.out.print("Cache HIT -> ");
                return entry.ipAddress + " (retrieved in " + (duration / 1_000_000.0) + "ms)";
            }

            // Cache Miss or Expired
            misses++;
            if (entry != null && entry.isExpired()) {
                System.out.print("Cache EXPIRED -> ");
                cache.remove(domain);
            } else {
                System.out.print("Cache MISS -> ");
            }

            // Simulate Upstream Query
            String ip = queryUpstreamDNS(domain);
            cache.put(domain, new DNSEntry(ip, 5)); // 5 second TTL for testing
            return "Query upstream -> " + ip;
        }

        private String queryUpstreamDNS(String domain) {
            // Mock IP generation
            return "172.217." + new Random().nextInt(255) + "." + new Random().nextInt(255);
        }

        public void getCacheStats() {
            double total = hits + misses;
            double hitRate = (total == 0) ? 0 : (hits / total) * 100;
            System.out.println("\n--- DNS Cache Stats ---");
            System.out.println("Hit Rate: " + String.format("%.2f", hitRate) + "%");
            System.out.println("Total Requests: " + (int)total);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DNSResolver resolver = new DNSResolver();

        // 1. Initial lookup (Miss)
        System.out.println(resolver.resolve("google.com"));

        // 2. Immediate lookup (Hit)
        System.out.println(resolver.resolve("google.com"));

        // 3. Wait for TTL to expire (Wait 6 seconds)
        System.out.println("\nWaiting for TTL to expire...");
        Thread.sleep(6000);

        // 4. Lookup after expiry (Expired/Miss)
        System.out.println(resolver.resolve("google.com"));

        resolver.getCacheStats();
    }
}
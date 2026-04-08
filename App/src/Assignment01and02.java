import java.util.concurrent.ConcurrentHashMap;

public class Assignment01and02 {
    static class TokenBucket {
        private final long maxTokens = 1000;
        private long tokens = 1000;
        private long lastRefillTime = System.currentTimeMillis();
        private final long refillInterval = 3600000; // 1 hour in ms

        public synchronized boolean allowRequest() {
            refill();
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }

        private void refill() {
            long now = System.currentTimeMillis();
            if (now > lastRefillTime + refillInterval) {
                tokens = maxTokens;
                lastRefillTime = now;
            }
        }

        public long getRemaining() { return tokens; }
    }

    static class RateLimiter {
        private Map<String, TokenBucket> clients = new ConcurrentHashMap<>();

        public void checkRateLimit(String clientId) {
            TokenBucket bucket = clients.computeIfAbsent(clientId, k -> new TokenBucket());
            if (bucket.allowRequest()) {
                System.out.println("Allowed (" + bucket.getRemaining() + " remaining)");
            } else {
                System.out.println("Denied (Limit exceeded for " + clientId + ")");
            }
        }
    }

    public static void main(String[] args) {
        RateLimiter limiter = new RateLimiter();
        limiter.checkRateLimit("client_123");
        limiter.checkRateLimit("client_123");
    }
}
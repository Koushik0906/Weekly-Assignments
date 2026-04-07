import java.util.*;

public class Problem06 {

    // clientId → TokenBucket
    private static HashMap<String, TokenBucket> clients = new HashMap<>();

    // Token Bucket class
    static class TokenBucket {

        int tokens;
        final int maxTokens;
        final int refillRate; // tokens per second
        long lastRefillTime;

        public TokenBucket(int maxTokens, int refillRate) {
            this.tokens = maxTokens;
            this.maxTokens = maxTokens;
            this.refillRate = refillRate;
            this.lastRefillTime = System.currentTimeMillis();
        }

        // Refill tokens based on time passed
        private void refill() {
            long now = System.currentTimeMillis();
            long elapsedTime = (now - lastRefillTime) / 1000;

            if (elapsedTime > 0) {
                int tokensToAdd = (int) (elapsedTime * refillRate);
                tokens = Math.min(maxTokens, tokens + tokensToAdd);
                lastRefillTime = now;
            }
        }

        // Try consuming a token
        public synchronized boolean allowRequest() {
            refill();

            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }

        public int getRemainingTokens() {
            refill();
            return tokens;
        }
    }

    public static void main(String[] args) {

        String clientId = "abc123";

        // Initialize client bucket (100 tokens, refill ~1000/hour ≈ 0.28/sec)
        clients.put(clientId, new TokenBucket(100, 1)); // simplified

        // Simulate requests
        for (int i = 1; i <= 105; i++) {
            System.out.println(checkRateLimit(clientId));
        }

        // Show status
        System.out.println("\nStatus:");
        System.out.println(getRateLimitStatus(clientId));
    }

    // O(1) rate limit check
    public static String checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId, new TokenBucket(100, 1));

        TokenBucket bucket = clients.get(clientId);

        if (bucket.allowRequest()) {
            return "Allowed → " + bucket.getRemainingTokens() + " requests remaining";
        } else {
            return "Denied → Rate limit exceeded. Try later.";
        }
    }

    // Status API
    public static String getRateLimitStatus(String clientId) {

        TokenBucket bucket = clients.get(clientId);

        if (bucket == null) {
            return "Client not found";
        }

        int remaining = bucket.getRemainingTokens();

        return "{remaining: " + remaining +
                ", limit: " + bucket.maxTokens + "}";
    }
}
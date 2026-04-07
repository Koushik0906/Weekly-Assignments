import java.util.*;

public class Problem03 {

    // DNS Entry class
    static class DNSEntry {
        String domain;
        String ipAddress;
        long expiryTime;

        DNSEntry(String domain, String ipAddress, int ttlSeconds) {
            this.domain = domain;
            this.ipAddress = ipAddress;
            this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    // LRU Cache using LinkedHashMap
    static class DNSCache extends LinkedHashMap<String, DNSEntry> {

        private final int capacity;

        DNSCache(int capacity) {
            super(capacity, 0.75f, true); // accessOrder = true → LRU
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
            return size() > capacity;
        }
    }

    private static DNSCache cache = new DNSCache(5);

    private static int hits = 0;
    private static int misses = 0;

    public static void main(String[] args) throws InterruptedException {

        // First request → MISS
        System.out.println(resolve("google.com"));

        // Second request → HIT
        System.out.println(resolve("google.com"));

        // Wait for expiry
        Thread.sleep(3000);

        // After expiry → MISS again
        System.out.println(resolve("google.com"));

        // Stats
        System.out.println("\n" + getCacheStats());
    }

    // Resolve domain
    public static String resolve(String domain) {

        long start = System.nanoTime();

        DNSEntry entry = cache.get(domain);

        // Cache HIT
        if (entry != null && !entry.isExpired()) {
            hits++;
            long time = (System.nanoTime() - start) / 1_000_000;
            return "Cache HIT → " + entry.ipAddress + " (" + time + " ms)";
        }

        // Cache MISS or EXPIRED
        misses++;

        String newIp = queryUpstreamDNS(domain);
        DNSEntry newEntry = new DNSEntry(domain, newIp, 2); // TTL = 2 seconds (demo)

        cache.put(domain, newEntry);

        long time = (System.nanoTime() - start) / 1_000_000;

        return "Cache MISS → " + newIp + " (" + time + " ms)";
    }

    // Simulated DNS lookup
    private static String queryUpstreamDNS(String domain) {

        // Simulate network delay
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Fake IP generator
        return "172.217." + new Random().nextInt(255) + "." + new Random().nextInt(255);
    }

    // Cache stats
    public static String getCacheStats() {

        int total = hits + misses;
        double hitRate = total == 0 ? 0 : (hits * 100.0 / total);

        return "Cache Stats → Hits: " + hits +
                ", Misses: " + misses +
                ", Hit Rate: " + String.format("%.2f", hitRate) + "%";
    }
}

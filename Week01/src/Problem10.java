import java.util.*;

public class Problem10 {

    // Video Data
    static class Video {
        String videoId;
        String content;

        public Video(String id, String content) {
            this.videoId = id;
            this.content = content;
        }
    }

    // LRU Cache using LinkedHashMap
    static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;

        public LRUCache(int capacity) {
            super(capacity, 0.75f, true); // access-order
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    }

    // Caches
    private static LRUCache<String, Video> L1 = new LRUCache<>(10000);
    private static LRUCache<String, Video> L2 = new LRUCache<>(100000);

    // L3 (Database simulation)
    private static HashMap<String, Video> database = new HashMap<>();

    // Access count for promotion
    private static HashMap<String, Integer> accessCount = new HashMap<>();

    // Stats
    private static int l1Hits = 0, l2Hits = 0, l3Hits = 0;

    public static void main(String[] args) {

        // Populate DB
        database.put("video_123", new Video("video_123", "Movie A"));
        database.put("video_999", new Video("video_999", "Movie B"));

        getVideo("video_123");
        getVideo("video_123"); // should hit L1
        getVideo("video_999");

        getStatistics();
    }

    public static Video getVideo(String videoId) {

        long start = System.currentTimeMillis();

        // L1
        if (L1.containsKey(videoId)) {
            l1Hits++;
            System.out.println("L1 HIT (0.5ms)");
            return L1.get(videoId);
        }

        System.out.println("L1 MISS");

        // L2
        if (L2.containsKey(videoId)) {
            l2Hits++;
            System.out.println("L2 HIT (5ms)");

            Video video = L2.get(videoId);

            promoteToL1(videoId, video);

            return video;
        }

        System.out.println("L2 MISS");

        // L3 (DB)
        if (database.containsKey(videoId)) {
            l3Hits++;
            System.out.println("L3 HIT (150ms)");

            Video video = database.get(videoId);

            L2.put(videoId, video);
            updateAccess(videoId);

            return video;
        }

        System.out.println("Video not found!");
        return null;
    }

    private static void promoteToL1(String videoId, Video video) {

        updateAccess(videoId);

        if (accessCount.get(videoId) > 2) { // threshold
            L1.put(videoId, video);
            System.out.println("Promoted to L1");
        }
    }

    private static void updateAccess(String videoId) {
        accessCount.put(videoId,
                accessCount.getOrDefault(videoId, 0) + 1);
    }

    public static void getStatistics() {

        int total = l1Hits + l2Hits + l3Hits;

        System.out.println("\n--- Cache Stats ---");

        if (total == 0) return;

        System.out.println("L1 Hit Rate: " +
                (l1Hits * 100.0 / total) + "%");

        System.out.println("L2 Hit Rate: " +
                (l2Hits * 100.0 / total) + "%");

        System.out.println("L3 Hit Rate: " +
                (l3Hits * 100.0 / total) + "%");
    }

    // Cache invalidation
    public static void invalidate(String videoId) {
        L1.remove(videoId);
        L2.remove(videoId);
        database.remove(videoId);

        System.out.println("Invalidated video: " + videoId);
    }
}

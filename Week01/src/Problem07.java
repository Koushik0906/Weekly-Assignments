import java.util.*;

public class Problem07 {

    // Global frequency map
    private static HashMap<String, Integer> frequencyMap = new HashMap<>();

    // Trie Node
    static class TrieNode {
        HashMap<Character, TrieNode> children = new HashMap<>();
        boolean isEnd;
    }

    private static TrieNode root = new TrieNode();

    public static void main(String[] args) {

        // Add initial queries
        addQuery("java tutorial");
        addQuery("javascript");
        addQuery("java download");
        addQuery("java 21 features");
        addQuery("java interview questions");

        // Search prefix
        System.out.println("Suggestions for 'jav':");
        List<String> results = search("jav");

        for (String s : results) {
            System.out.println(s + " (" + frequencyMap.get(s) + ")");
        }

        // Update frequency
        updateFrequency("java 21 features");
        updateFrequency("java 21 features");

        System.out.println("\nAfter updates:");
        System.out.println(search("java"));
    }

    // Add query to Trie + frequency map
    public static void addQuery(String query) {

        frequencyMap.put(query,
                frequencyMap.getOrDefault(query, 0) + 1);

        TrieNode node = root;

        for (char c : query.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }

        node.isEnd = true;
    }

    // Search top 10 suggestions
    public static List<String> search(String prefix) {

        TrieNode node = root;

        // Traverse prefix
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return new ArrayList<>();
            }
            node = node.children.get(c);
        }

        // Collect all words from this prefix
        List<String> results = new ArrayList<>();
        dfs(node, prefix, results);

        // Top 10 using max heap
        PriorityQueue<String> pq =
                new PriorityQueue<>((a, b) ->
                        frequencyMap.get(b) - frequencyMap.get(a));

        pq.addAll(results);

        List<String> topResults = new ArrayList<>();

        int k = 10;
        while (!pq.isEmpty() && k-- > 0) {
            topResults.add(pq.poll());
        }

        return topResults;
    }

    // DFS to collect words
    private static void dfs(TrieNode node, String prefix, List<String> results) {

        if (node.isEnd) {
            results.add(prefix);
        }

        for (char c : node.children.keySet()) {
            dfs(node.children.get(c), prefix + c, results);
        }
    }

    // Update frequency (new search)
    public static void updateFrequency(String query) {

        frequencyMap.put(query,
                frequencyMap.getOrDefault(query, 0) + 1);

        // Ensure it's in Trie
        addQuery(query);
    }
}

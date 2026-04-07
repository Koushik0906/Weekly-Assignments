import java.util.*;

public class Problem04 {

    // n-gram size
    private static final int N = 3; // (use 5 or 7 in real systems)

    // n-gram → set of document IDs
    private static HashMap<String, Set<String>> index = new HashMap<>();

    public static void main(String[] args) {

        String doc1 = "the quick brown fox jumps over the lazy dog";
        String doc2 = "the quick brown fox jumps high over the lazy dog";
        String doc3 = "java programming is fun and powerful";

        // Index existing documents
        addDocument("doc1", doc1);
        addDocument("doc2", doc2);
        addDocument("doc3", doc3);

        // Analyze a new document
        analyzeDocument("newDoc",
                "the quick brown fox jumps over the dog");
    }

    // Add document to index
    public static void addDocument(String docId, String content) {

        List<String> ngrams = generateNGrams(content);

        for (String gram : ngrams) {

            index.putIfAbsent(gram, new HashSet<>());
            index.get(gram).add(docId);
        }
    }

    // Analyze plagiarism
    public static void analyzeDocument(String docId, String content) {

        List<String> ngrams = generateNGrams(content);

        HashMap<String, Integer> matchCount = new HashMap<>();

        // Count matches
        for (String gram : ngrams) {

            if (index.containsKey(gram)) {

                for (String existingDoc : index.get(gram)) {
                    matchCount.put(existingDoc,
                            matchCount.getOrDefault(existingDoc, 0) + 1);
                }
            }
        }

        // Display results
        System.out.println("Analyzing document: " + docId);
        System.out.println("Total n-grams: " + ngrams.size());

        for (Map.Entry<String, Integer> entry : matchCount.entrySet()) {

            String existingDoc = entry.getKey();
            int matches = entry.getValue();

            double similarity = (matches * 100.0) / ngrams.size();

            System.out.println("Matched with " + existingDoc +
                    " → " + matches + " matches, Similarity: " +
                    String.format("%.2f", similarity) + "%");

            if (similarity > 50) {
                System.out.println("⚠ PLAGIARISM DETECTED with " + existingDoc);
            }
        }
    }

    // Generate n-grams
    public static List<String> generateNGrams(String text) {

        List<String> ngrams = new ArrayList<>();

        String[] words = text.split("\\s+");

        for (int i = 0; i <= words.length - N; i++) {

            StringBuilder gram = new StringBuilder();

            for (int j = 0; j < N; j++) {
                gram.append(words[i + j]).append(" ");
            }

            ngrams.add(gram.toString().trim());
        }

        return ngrams;
    }
}

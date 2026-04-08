import java.util.*;

public class Assignment01and02 {

    static class PlagiarismDetector {
        // Map: n-gram string -> Set of Document IDs that contain it
        private Map<String, Set<String>> ngramIndex = new HashMap<>();
        private Map<String, Integer> docSizes = new HashMap<>();

        public void addDocument(String docId, String text) {
            String[] words = text.toLowerCase().split("\\s+");
            int n = 5; // Using 5-grams
            int count = 0;

            for (int i = 0; i <= words.length - n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) sb.append(words[i + j]).append(" ");

                String ngram = sb.toString().trim();
                ngramIndex.computeIfAbsent(ngram, k -> new HashSet<>()).add(docId);
                count++;
            }
            docSizes.put(docId, count);
        }

        public void analyzeDocument(String newDocText) {
            String[] words = newDocText.toLowerCase().split("\\s+");
            int n = 5;
            Map<String, Integer> matchCounts = new HashMap<>();
            int totalNgrams = 0;

            for (int i = 0; i <= words.length - n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) sb.append(words[i + j]).append(" ");
                String ngram = sb.toString().trim();
                totalNgrams++;

                if (ngramIndex.containsKey(ngram)) {
                    for (String docId : ngramIndex.get(ngram)) {
                        matchCounts.put(docId, matchCounts.getOrDefault(docId, 0) + 1);
                    }
                }
            }

            System.out.println("Analysis Result (Total N-grams: " + totalNgrams + "):");
            for (Map.Entry<String, Integer> entry : matchCounts.entrySet()) {
                double similarity = (entry.getValue() * 100.0) / totalNgrams;
                System.out.print("-> Found " + entry.getValue() + " matches with " + entry.getKey());
                System.out.println(" | Similarity: " + String.format("%.1f", similarity) + "%" +
                        (similarity > 50 ? " [PLAGIARISM DETECTED]" : ""));
            }
        }
    }

    public static void main(String[] args) {
        PlagiarismDetector detector = new PlagiarismDetector();

        // Database
        detector.addDocument("essay_092.txt", "java is a high level class based object oriented programming language");
        detector.addDocument("essay_089.txt", "the quick brown fox jumps over the lazy dog");

        // Submission
        String submission = "java is a high level class based programming language which is popular";
        detector.analyzeDocument(submission);
    }
}
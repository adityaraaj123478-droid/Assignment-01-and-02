import java.util.*;

public class Assignment01and02 { // Renamed to match your file name
    private Map<String, Integer> registeredUsers = new HashMap<>();
    private Map<String, Integer> attemptFrequency = new HashMap<>();

    public void registerUser(String username, int userId) {
        registeredUsers.put(username.toLowerCase(), userId);
    }

    public boolean checkAvailability(String username) {
        String normalized = username.toLowerCase();
        if (registeredUsers.containsKey(normalized)) {
            attemptFrequency.put(normalized, attemptFrequency.getOrDefault(normalized, 0) + 1);
            return false;
        }
        return true;
    }

    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        int suffix = 1;
        while (suggestions.size() < 3) {
            String candidate = username.toLowerCase() + suffix;
            if (!registeredUsers.containsKey(candidate)) {
                suggestions.add(candidate);
            }
            suffix++;
        }
        return suggestions;
    }

    public static void main(String[] args) {
        Assignment01and02 system = new Assignment01and02();

        // Setup initial data
        system.registerUser("john_doe", 101);

        // Test Availability
        String testUser = "john_doe";
        if (!system.checkAvailability(testUser)) {
            System.out.println(testUser + " is already taken.");
            System.out.println("Suggestions: " + system.suggestAlternatives(testUser));
        }
    }
}
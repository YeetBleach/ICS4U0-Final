public class CipherEngine{;
    private String authValue = "";


    public String encrypt(String msg, int k1, String k2, int k3){
        return encryptUnicode(encryptCaesar(msg, k1, k2), k2, k3);
    }
    public String decrypt(String msg, int k1, String k2, int k3){
        return decryptCaesar(decryptUnicode(msg, k2, k3), k1, k2);
    }
    private String encryptCaesar(String msg, int k1, String k2) {
        StringBuilder result = new StringBuilder();
        msg = k2.equals("UPPER") ? msg.toUpperCase() : msg.toLowerCase(); //Upper or lower case based on k2
        for (char character : msg.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a'; // determine base character based on upper or lower case
                int originalAlphabetPosition = character - base;
                int newAlphabetPosition = (originalAlphabetPosition + k1) % 26; // wrap around
                char newCharacter = (char) (base + newAlphabetPosition);
                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }
    private String decryptCaesar(String msg, int k1, String k2){
        StringBuilder result = new StringBuilder();
        msg = k2.equals("UPPER") ? msg.toUpperCase() : msg.toLowerCase();
        for (char character : msg.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                int originalAlphabetPosition = character - base;
                int newAlphabetPosition = (originalAlphabetPosition - k1 + 26) % 26; // wrap around
                char newCharacter = (char) (base + newAlphabetPosition);
                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }


    //Method to mirror a string
    private String mirror(String msg){
        StringBuilder reversed = new StringBuilder(msg);
        return reversed.reverse().toString();
    }
    private String encryptUnicode(String msg, String k2, int k3){
        String[] msgArr = msg.split(" "); // Split the message into its words
        StringBuilder result = new StringBuilder();
        authValue = ""; // Reset authValue for each encryption

        for (String word : msgArr){
            StringBuilder wordBuilder = new StringBuilder();
            for (char c : word.toCharArray()) { // Split each word into its characters
                // Pad the unicode value to 3 digits
                String unicodeChar = String.format("%03d", (int) c);

                // mirror the unicode value
                String mirroredUnicode = mirror(unicodeChar);

                // shift by k3 through addition
                int shiftedValue = Integer.parseInt(mirroredUnicode) + k3;

                // Add to authValue for authentication
                authValue += String.valueOf(shiftedValue);

                // Convert back to character and append to word
                char encryptedChar = (char) shiftedValue;
                wordBuilder.append(encryptedChar);
            }
            result.append(wordBuilder).append(" ");
        }
        return result.toString().trim();
    }
    private String decryptUnicode(String msg, String k2, int k3){
        String[] msgArr = msg.split(" "); // Split the message into its words
        StringBuilder result = new StringBuilder();

        for (String word : msgArr){
            StringBuilder wordBuilder = new StringBuilder();
            for (char c : word.toCharArray()) { // Split each word into its characters

                // convert to unicode value and subtract k3
                int mirroredValue = (int) c - k3;

                // Pad to 3 digits and mirror it
                String mirroredStr = String.format("%03d", mirroredValue);
                String originalUnicodeStr = mirror(mirroredStr);

                //Convert back to character and append to word

                int originalUnicode = Integer.parseInt(originalUnicodeStr);
                char originalChar = (char) originalUnicode;
                wordBuilder.append(originalChar);
            }
            result.append(wordBuilder).append(" "); // Append complete word with space
        }
        return result.toString().trim(); // Remove trailing space
    }
    public String getAuthValue(){
        return authValue;
    }
}
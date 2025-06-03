import java.util.ArrayList;
public class CipherEngine{
    private String[] msgArr;
    private ArrayList <String> wordArr = new ArrayList<>();
    private ArrayList <String> uniArr = new ArrayList<>();
    private String intMsg  = "";
    public String encrypt(String msg, int k1, String k2, int k3){
        return encryptUnicode(encryptCaesar(msg, k1, k2), k2, k3);
    }
    //    public String decrypt(String msg, int k1, String k2, int k3){
//        return decryptCaesar(decryptUnicode(msg, k2, k3), k1, k2);
//    }
    private String encryptCaesar(String msg, int k1, String k2){
        String s=" ";
        for(int i=0; i<msg.length(); i++){
            char c = (char) (msg.charAt(i) + k1);
            if (c > 'z') {
                s += (char)(msg.charAt(i) - (26-k1));
            }
            else{
                s += (char) (msg.charAt(i) + k1);
            }
        }
        return k2.equals("UPPER") ? s.toUpperCase() : s.toLowerCase();
    }
    private String decryptCaesar(String msg, int k1, String k2){
        String s=" ";
        for(int i=0; i<msg.length(); i++){
            char c = (char) (msg.charAt(i) - k1);
            if (c < 'a') {
                c = (char) (c + 26);
            }
            else{
                s += (char) (msg.charAt(i) - k1);
            }
        }
        return k2.equals("UPPER") ? s.toUpperCase() : s.toLowerCase();
    }
    //Method to mirror a string
    private String mirror(String msg){
        StringBuilder reversed = new StringBuilder(msg);
        return reversed.reverse().toString();
    }
    private String encryptUnicode(String msg, String k2, int k3){
        msgArr = msg.split(" "); // Split the message into its words
        StringBuilder result = new StringBuilder();
        intMsg = ""; // Reset intMsg for each encryption

        for (String word : msgArr){
            StringBuilder wordBuilder = new StringBuilder();
            for (char c : word.toCharArray()) { // Split each word into its characters
                // Step 1: Convert character to Unicode value and pad to 3 digits
                String unicodeChar = String.format("%03d", (int) c);

                // Step 2: Mirror the Unicode value
                String mirroredUnicode = mirror(unicodeChar);

                // Step 3: Add k3 to the mirrored value (no wrapping needed if k3 is limited)
                int shiftedValue = Integer.parseInt(mirroredUnicode) + k3;

                // Add to integer message for tracking
                intMsg += String.valueOf(shiftedValue);

                // Convert back to character and append to word
                char encryptedChar = (char) shiftedValue;
                wordBuilder.append(encryptedChar);
            }
            result.append(wordBuilder).append(" "); // Append complete word with space
        }
        return result.toString().trim(); // Remove trailing space
    }
    public String decryptUnicode(String msg, String k2, int k3){
        msgArr = msg.split(" "); // Split the message into its words
        StringBuilder result = new StringBuilder();

        for (String word : msgArr){
            StringBuilder wordBuilder = new StringBuilder();
            for (char c : word.toCharArray()) { // Split each word into its characters
                int encryptedValue = (int) c;

                // Step 1: Subtract k3 to get the mirrored Unicode value
                int mirroredValue = encryptedValue - k3;

                // Step 2: Mirror back to get original 3-digit Unicode
                String mirroredStr = String.format("%03d", mirroredValue);
                String originalUnicodeStr = mirror(mirroredStr);

                // Step 3: Convert back to original character
                int originalUnicode = Integer.parseInt(originalUnicodeStr);
                char originalChar = (char) originalUnicode;
                wordBuilder.append(originalChar);
            }
            result.append(wordBuilder).append(" "); // Append complete word with space
        }
        return result.toString().trim(); // Remove trailing space
    }
    public String getIntMsg(){
        return intMsg;
    }
}
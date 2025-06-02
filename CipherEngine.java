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
        for (String word : msgArr){
            StringBuilder wordBuilder = new StringBuilder();
            for (char c : word.toCharArray()) { // Split each word into its characters
                String unicodeChar = Integer.toString((int) c + k3); // Convert each character to its Unicode value, add k3
                if (k2.equals("LOWER") && unicodeChar.length() < 3) { // When key2 is lowercase, ensure Unicode values are 3 digits
                    unicodeChar = "0" + unicodeChar;
                }
                intMsg += String.valueOf(Integer.parseInt(mirror(unicodeChar))); // Append the Unicode value to the integer message
                unicodeChar = String.valueOf(((char) Integer.parseInt(mirror(unicodeChar)))); //mirror the uni value and convert back to character then to string
                wordBuilder.append(unicodeChar); // Append the mirrored character to the word

            }
            result.append(wordBuilder).append(" "); // Append complete word with space
        }
        return result.toString().trim(); // Remove trailing space
    }

//    private String decryptUnicode(String msg, String k2, int k3){
//        msgArr = msg.split(""); // Split the message into its characters
//        StringBuilder result = new StringBuilder();
//
//
//    }

    public String getIntMsg(){
        return intMsg;
    }

}

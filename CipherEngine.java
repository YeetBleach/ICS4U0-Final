import java.util.ArrayList;

public class CipherEngine{
    private String[] msgArr;
    private ArrayList <String> mutatedMsg = new ArrayList<>();

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

    private String encryptAscii(String msg, int k3){
        msgArr = msg.split("(?<=\\w)(?=\\p{Punct})|(?<=\\p{Punct})(?=\\w)|\\s+");
        for (String word : msgArr) {
            //check is word is a letter
            if (word.matches("[a-zA-Z]+")) {
                
            }

        }

    }
}

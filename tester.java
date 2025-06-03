import java.io.FileWriter;
import java.io.IOException;


public class tester {
    public static void main(String[] args){
        CipherEngine cipher = new CipherEngine();
        String msg = "ɔĨϤϤĲȬtĲɞϤÄȬtĨȬͶɞĨȬ̦Ĩ˂̦ʸÎǰȬ̦ɔĨȬ`ʸƖɔĨɞȬĨÎǰʸÎĨ";
        int k1 = 3; // Caesar shift
        String k2 = "UPPER"; // Case transformation
        int k3 = 26; // Unicode shift
//        String encryptedMsg = cipher.encrypt(msg, k1, k2, k3);
        String encryptedMsg = cipher.decryptUnicode(msg, k2, k3);
        System.out.println(encryptedMsg);
        try (FileWriter writer = new FileWriter("output.txt")) {
            writer.write(encryptedMsg);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}

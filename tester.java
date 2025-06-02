import java.util.ArrayList;
import java.util.Arrays;


public class tester {
    public static void main(String[] args){
        CipherEngine cipher = new CipherEngine();
        String msg = "hello world we are testing the cipher engine";
        int k1 = 3; // Caesar shift
        String k2 = "UPPER"; // Case transformation
        int k3 = 32; // Unicode shift
        String encryptedMsg = cipher.encrypt(msg, k1, k2, k3);
        System.out.println(encryptedMsg);
        System.out.println(cipher.getIntMsg());
    }
}

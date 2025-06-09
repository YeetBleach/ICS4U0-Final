
public class KeyManager{
    private int key1, key3, verifyKey;
    private String key2;

    public void saveKey(int x, int y){
        key1=x;
        key3=y;
    }
    public void saveKey(String m){
        key2=m;
    }
    public void setVerifyKey(int x){
        verifyKey=x;
    }

    public int getKey1(){
        return key1;
    }
    public String getKey2(){
        return key2;
    }
    public int getKey3(){
        return key3;
    }
    public int getVerifyKey(){
        return verifyKey;
    }
}
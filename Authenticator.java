import java.util.ArrayList;
import java.math.BigInteger;

public class Authenticator{
    private BigInteger wholeCode;
    private KeyManager bank;
    private BigInteger authCode= new BigInteger("0");

    public void setKeys(KeyManager keys){
        bank=keys;
    }
    public void setEntireCode(String code){
        wholeCode=new BigInteger(code);
        authCode= new BigInteger("0");
    }

    public void setAuthCode(){
        int startNum = (Math.max(bank.getKey3(), bank.getKey1())-Math.min(bank.getKey3(), bank.getKey1()))/2;
        if (startNum==0){
        }else{
            startNum=startNum-1;
        }
        int length = bank.getVerifyKey();
        String codeString = wholeCode.toString();
        if (startNum >= 0 &&startNum+length>codeString.length()){
            String newCodeString=codeString.substring(startNum, codeString.length());
            authCode =new BigInteger(newCodeString);
        }
        else{
            String newCodeString=codeString.substring(startNum, startNum + length);
            authCode =new BigInteger(newCodeString);
        }
    }

    public BigInteger getAuthCode(){
        return authCode;
    }


    public boolean verify(String value){
        return authCode.equals(new BigInteger(value));
    }
}


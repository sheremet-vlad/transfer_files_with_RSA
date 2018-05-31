package mainPackage;

public class ConvertingString {
    private static final String DIR_NAME = "acceptFiles/";
    private static final String ADD_PART = "encry_";

    public static String addDirName(String fileName){
        return DIR_NAME.concat(fileName);
    }

    public static String addEncryptionPart(String filName){
        String temp = filName.substring(0,filName.indexOf('/')+1);
        String temp2 = filName.substring(filName.indexOf('/')+1,filName.length());
        return temp + ADD_PART + temp2;
    }
}

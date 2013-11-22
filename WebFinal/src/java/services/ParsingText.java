package services;

import java.util.StringTokenizer;

public class ParsingText {

    public ParsingText(){}
    
    public static String parsing(String text){
        String[] result = text.split(" ");
        String res = "";
        for (int j=0; j<result.length; j++){
            String singleWord = result[j];
            if('$' == singleWord.charAt(0) && '$' == singleWord.charAt(1)){
                singleWord = singleWord.replace("$$","");
                singleWord = "<a href=\"UploadedFile/"+singleWord+"\" >"+singleWord+"</>";
            }
            res = res + " " +singleWord;
        }
        return res;
    }
}

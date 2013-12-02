package services;

import java.io.File;

public class ParsingText {

    public ParsingText(){}
    
    /**
     * Funzione di parsing del testo in cerca di link o collegamenti
     * @param text
     * @param gruppo
     * @return 
     */
    public static String parsing(String text,int gruppo,String pt){
        String[] result = text.split(" ");
        String res = "";
        for (int j=0; j<result.length; j++){
            String singleWord = result[j];
            int length = singleWord.length();
            if(singleWord.startsWith("$$") && singleWord.endsWith("$$")){
                singleWord = singleWord.replace("$$","");
                //se e' un sito allora aggiungi l'http oppure vai direttamente al sito
                if(singleWord.startsWith("www.")){
                    singleWord = "http://" +singleWord;
                    singleWord = "<a href=\""+singleWord+"\" >"+singleWord+"</a>";                
                }else if(singleWord.startsWith("http://")){
                    singleWord = "<a href=\""+singleWord+"\" >"+singleWord+"</a>";                
                }else{
                    //se il file esite veramente all'interno della cartella carica il riferimento
                    if(checkExists(singleWord,pt)){
                        singleWord = "<a href=\"UploadedFile/"+gruppo+"/"+singleWord+"\" >"+singleWord+"</a>";
                    }
                }
            }
            res = res + " " +singleWord;
        }
        return res;
    }
    
    /**
     * Preso in input path e nome del file, restituisce un booleano in base all'esistenza del file
     * @param nome
     * @param pt
     * @return 
     */
    public static boolean checkExists(String nome,String pt) {
        nome = pt + "/"+nome;
        File file=new File(nome);
        boolean exists = file.exists();
        if (!exists) {
            return false;
        }else{
            return true;
        }
    }
}

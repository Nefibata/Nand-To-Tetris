package nand2t;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;


public class JackTokenizer {
    boolean flag=false;


    String nowLine;
    char[] cArr;
    StringBuffer stringBuffer;
    Deque<String> nowWords;
    String nowWord;
    String pattern = "^[0-9]*$";
    public static final String[] symbolArr = {"{", "}", "(", ")", "[", "]", ".",
            ",", ";", "+", "-", "*", "/", "&", "|", "<", ">", "=", "~","\""};
    public static final String[] keyWordArr = {"class", "method", "int", "function", "boolean",
            "constructor", "char", "void", "var", "static", "field", "let","do", "if", "else", "while", "return",
            "true", "false", "null", "this"};
    public static HashSet<String> symbolSet = new HashSet<>();
    public static HashSet<String> keyWordSet = new HashSet<>();
    public static Parser read;
    static {
        Collections.addAll(symbolSet, symbolArr);
        Collections.addAll(keyWordSet, keyWordArr);



    }
    public void readLine(){
        try {
            nowLine = read.in.readLine();
            while (nowLine==null)nowLine = read.in.readLine();
            nowLine = nowLine.trim();
            while (nowLine.equals("")||nowLine.charAt(0) == '/'||nowLine.charAt(0) == '*') {
                nowLine = read.in.readLine();
                nowLine = nowLine.trim();
            }
            if (nowLine.contains("//")){
                String[] a=nowLine.split("//");
                nowLine=a[0];
            }
            nowLine = nowLine.trim();
            cArr=nowLine.toCharArray();
            stringBuffer=new StringBuffer();
            for(char c:cArr) {
                // If the char is a operator, we add the space in the head
                if(symbolSet.contains(c+"")) {
                    stringBuffer.append(" ").append(c).append(" ");
                }else {
                    stringBuffer.append(c);
                }
            }
            nowLine=stringBuffer.toString();
            for (String s:
                    nowLine.split(" ")) {
                if (!(s==null||s.equals(""))){
                    if (s.equals("\"")) {
                        flag=!flag;
                        if (!flag){
                            nowWords.addLast(stringBuffer.toString().trim());
                        }
                        stringBuffer=new StringBuffer();
                        stringBuffer.append("\"");

                    }else {
                        if (flag){
                            stringBuffer.append(s);
                            stringBuffer.append(" ");
                        }else {

                            nowWords.addLast(s);
                        }
                    }


                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Constructor(String f){

    }
    public boolean hasMoreTokens(){
        if(!nowWords.isEmpty()){
            return true;
        }else {
           this.readLine();
            return !nowWords.isEmpty();
        }
    }
    public void advance(){
        if (hasMoreTokens()){
            nowWord=nowWords.removeFirst();
        }
    }
    public void advanceB(){
        if (nowWords.isEmpty()){
            String x=nowWord;
            hasMoreTokens();
            nowWord=nowWords.peekFirst();
            nowWords.addFirst(x);
        }else {
            if (hasMoreTokens()) {
                nowWord = nowWords.peekFirst();
            }
        }
    }
    public String tokenType(){
        if (keyWordSet.contains(nowWord))return "keyword";
        if(symbolSet.contains(nowWord))return "symbol";
        if ((nowWord.charAt(0)+"").equals("\""))return "stringConstant";
        if (nowWord.matches(pattern))return "intConstant";
        return "identifier";
    }
    public String keyword(){
        return nowWord;

    }
    public String symbol(){
        return nowWord;

    }
    public String identifier(){
        return nowWord;

    }
    public int intVal(){

        return Integer.parseInt(nowWord);

    }
    public String stringVal(){
        return nowWord;

    }
    public JackTokenizer(String f) throws FileNotFoundException {
        nowWords=new LinkedList<>();
JackTokenizer.read =new Parser(f);

    }
}

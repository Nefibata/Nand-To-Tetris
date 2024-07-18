package nand2t;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;


//第8个案例检查出我前面所有编译器的一个错误，读取命令的时候只考虑到注释单独存在的情况没考虑到注释跟在命令的后面这种情况，导致命令翻译错误，在此文件加以更正
//调用逻辑写的有问题，对于同一函数的多次调用和递归无法处理，提供一个思路将帧中的第一个ram储存此时的返回地址之后在跳转
//能力有限不会处理多个文件，提供一个思路，可以通过file类来获取文件夹里面文件的名称。
public class NAND2TVM2 {
    static Parser parser;

    HashMap<String,Integer> a=new HashMap<>();

    HashMap<String,String> h1=new HashMap<>();
    String nowF ;
    int sti=0;

    int si=0;
    int ei=0;
    int gt=0;
    int lt=0;

    static {
        try {
            parser = new Parser("C:\\Users\\zxk\\Desktop\\nand2tetris\\projects\\08\\FunctionCalls\\StaticsTest\\1.vm");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static CodeWriter codeWriter;

    static {
        try {
            codeWriter = new CodeWriter("C:\\Users\\zxk\\Desktop\\nand2tetris\\projects\\08\\FunctionCalls\\StaticsTest\\StaticsTest.asm");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static NAND2TVM2 nand2TVM2;

    static {
        try {
            nand2TVM2 = new NAND2TVM2();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public NAND2TVM2() throws FileNotFoundException {
    }

    public static void main(String[] args) throws IOException {
        while (true){
            String readLine=parser.in.readLine();
            if (readLine==null)break;
            if(readLine.equals(""))continue;
            readLine=readLine.trim();
            int gan=readLine.indexOf('/');
            if (gan!=-1&&gan!=0)readLine=readLine.substring(0,gan);
            if (readLine.charAt(0)=='/')continue;
            readLine=readLine.trim();
            String[] de=readLine.split(" ");
            if (de.length!=1){
                switch (de[0]){
                    case "push":
                        nand2TVM2.pushS(de);
                        break;
                    case "pop":
                        nand2TVM2.popS(de);
                        break;
                    case "label":
                        nand2TVM2.label(de);
                        break;
                    case "goto":
                        nand2TVM2.gotoS(de);
                        break;
                    case "if-goto":
                        nand2TVM2.ifGotoS(de);
                        break;
                    case "call":
                        nand2TVM2.callS(de);
                        break;
                    case "function":
                        nand2TVM2.functionS(de);
                        break;
                }

            }else {
                if (de[0].equals("return")){
                        nand2TVM2.returnS(de);

                }else {
                    nand2TVM2.reg(de[0]);
                }
            }

        }
        codeWriter.write.close();
    }
    public void reg(String s) throws IOException {
        switch (s){
            case "add":
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("M=M-1\r\n");
                codeWriter.write.append("A=M\r\n");
                codeWriter.write.append("D=M\r\n");
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("A=M-1\r\n");
                codeWriter.write.append("M=D+M\r\n");
                break;
            case "sub":
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("M=M-1\r\n");
                codeWriter.write.append("A=M\r\n");
                codeWriter.write.append("D=M\r\n");
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("A=M-1\r\n");
                codeWriter.write.append("M=M-D\r\n");
                break;
            case "neg":
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("A=M-1\r\n");
                codeWriter.write.append("M=-M\r\n");
                break;
            case "eq":
                ei++;
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("M=M-1\r\n");
                codeWriter.write.append("A=M\r\n");
                codeWriter.write.append("D=M\r\n");
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("A=M-1\r\n");
                codeWriter.write.append("D=M-D\r\n");
                codeWriter.write.append("@EQ").append(String.valueOf(ei)).append("\r\n");
                codeWriter.write.append("D;JEQ\r\n");
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("A=M-1\r\n");
                codeWriter.write.append("M=0\r\n");
                codeWriter.write.append("@EQS").append(String.valueOf(ei)).append("\r\n");
                codeWriter.write.append("0;JMP\r\n");
                codeWriter.write.append("(EQ").append(String.valueOf(ei)).append(")\r\n");
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("A=M-1\r\n");
                codeWriter.write.append("M=-1\r\n");
                codeWriter.write.append("(EQS").append(String.valueOf(ei)).append(")\r\n");
                break;
            case "gt":
                gt++;
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("M=M-1\r\n");
                codeWriter.write.append("A=M\r\n");
                codeWriter.write.append("D=M\r\n");
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("A=M-1\r\n");
                codeWriter.write.append("D=M-D\r\n");
                codeWriter.write.append("@GT").append(String.valueOf(gt)).append("\r\n");
                codeWriter.write.append("D;JGT\r\n");
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("A=M-1\r\n");
                codeWriter.write.append("M=0\r\n");
                codeWriter.write.append("@GTS").append(String.valueOf(gt)).append("\r\n");
                codeWriter.write.append("0;JMP\r\n");
                codeWriter.write.append("(GT").append(String.valueOf(gt)).append(")\r\n");
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("A=M-1\r\n");
                codeWriter.write.append("M=-1\r\n");
                codeWriter.write.append("(GTS").append(String.valueOf(gt)).append(")\r\n");
                break;
            case "lt":
                lt++;
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("M=M-1\r\n");
                codeWriter.write.append("A=M\r\n");
                codeWriter.write.append("D=M\r\n");
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("A=M-1\r\n");
                codeWriter.write.append("D=M-D\r\n");
                codeWriter.write.append("@LT").append(String.valueOf(lt)).append("\r\n");
                codeWriter.write.append("D;JLT\r\n");
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("A=M-1\r\n");
                codeWriter.write.append("M=0\r\n");
                codeWriter.write.append("@LTS").append(String.valueOf(lt)).append("\r\n");
                codeWriter.write.append("0;JMP\r\n");
                codeWriter.write.append("(LT").append(String.valueOf(lt)).append(")\r\n");
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("A=M-1\r\n");
                codeWriter.write.append("M=-1\r\n");
                codeWriter.write.append("(LTS").append(String.valueOf(lt)).append(")\r\n");
                break;
            case "and":
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("M=M-1\r\n");
                codeWriter.write.append("A=M\r\n");
                codeWriter.write.append("D=M\r\n");
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("A=M-1\r\n");
                codeWriter.write.append("M=M&D\r\n");
                break;
            case "or":
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("M=M-1\r\n");
                codeWriter.write.append("A=M\r\n");
                codeWriter.write.append("D=M\r\n");
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("A=M-1\r\n");
                codeWriter.write.append("M=M|D\r\n");
                break;
            case "not":
                codeWriter.write.append("@R0\r\n");
                codeWriter.write.append("A=M-1\r\n");
                codeWriter.write.append("M=!M\r\n");
                break;
        }
    }
    public void reg1(String n,String m) throws IOException {
        switch (n){
            case "local":
                codeWriter.write.append("@R1\r\n");
                codeWriter.write.append("D=M\r\n");
                codeWriter.write.append("@").append(m).append("\r\n");
                codeWriter.write.append("D=A+D\r\n");
                codeWriter.write.append("A=D\r\n");



                break;
            case "argument":
                codeWriter.write.append("@R2\r\n");
                codeWriter.write.append("D=M\r\n");
                codeWriter.write.append("@").append(m).append("\r\n");
                codeWriter.write.append("D=A+D\r\n");
                codeWriter.write.append("A=D\r\n");
                break;
            case "this":
                codeWriter.write.append("@R3\r\n");
                codeWriter.write.append("D=M\r\n");
                codeWriter.write.append("@").append(m).append("\r\n");
                codeWriter.write.append("D=A+D\r\n");
                codeWriter.write.append("A=D\r\n");
                break;
            case "that":
                codeWriter.write.append("@R4\r\n");
                codeWriter.write.append("D=M\r\n");
                codeWriter.write.append("@").append(m).append("\r\n");
                codeWriter.write.append("D=A+D\r\n");
                codeWriter.write.append("A=D\r\n");
                break;
            case "constant":
                codeWriter.write.append("D=0\r\n");
                int mmm=Integer.parseInt(m);
                if (mmm>24576){
                    int aaa=mmm/24576;
                    for (int x=0;x<aaa;x++){
                        codeWriter.write.append("@24576\r\n");
                        codeWriter.write.append("D=A+D\r\n");
                        mmm-=24576;
                    }
                }
                codeWriter.write.append("@").append(String.valueOf(mmm)).append("\r\n");


                break;
            case "static":
                if (a.containsKey(m)){
                    String b= String.valueOf(a.get(m));
                    codeWriter.write.append("@").append(b).append("\r\n");
                    codeWriter.write.append("D=A\r\n");
                }else {
                    a.put(m,16+si);
                    si++;
                    String b= String.valueOf(a.get(m));
                    codeWriter.write.append("@").append(b).append("\r\n");
                    codeWriter.write.append("D=A\r\n");
                }

                break;
            case "pointer":
                if (m.equals("0")){
                    codeWriter.write.append("@R3\r\n");
                    codeWriter.write.append("D=A\r\n");


                }else {
                    codeWriter.write.append("@R4\r\n");
                    codeWriter.write.append("D=A\r\n");
                }
                break;
            case "temp":
                codeWriter.write.append("@").append(String.valueOf(Integer.parseInt(m)+5)).append("\r\n");
                codeWriter.write.append("D=A\r\n");
                break;
        }
    }
    public void pushS(String[] de) throws IOException {
        nand2TVM2.reg1(de[1],de[2]);
        if (de[1].equals("constant")) {
            codeWriter.write.append("D=A+D\r\n");
        }else {
            codeWriter.write.append("D=M\r\n");
        }

        codeWriter.write.append("@R0\r\n");
        codeWriter.write.append("M=M+1\r\n");
        codeWriter.write.append("A=M-1\r\n");
        codeWriter.write.append("M=D\r\n");
    }
    public void popS(String[] de) throws IOException{
        nand2TVM2.reg1(de[1],de[2]);
        codeWriter.write.append("@R0\r\n");
        codeWriter.write.append("A=M\r\n");
        codeWriter.write.append("M=D\r\n");

        codeWriter.write.append("@R0\r\n");
        codeWriter.write.append("M=M-1\r\n");
        codeWriter.write.append("A=M\r\n");
        codeWriter.write.append("D=M\r\n");
        codeWriter.write.append("@R0\r\n");
        codeWriter.write.append("A=M+1\r\n");
        codeWriter.write.append("A=M\r\n");
        codeWriter.write.append("M=D\r\n");
    }
    public void label(String[] de)throws IOException{
        codeWriter.write.append("("+de[1]+")\r\n");
    }
    public void gotoS(String[] de)throws IOException{
        codeWriter.write.append("@"+de[1]+"\r\n");
        codeWriter.write.append("0;JMP\r\n");
    }
    public void ifGotoS(String[] de)throws IOException{
        codeWriter.write.append("@R0\r\n");
        codeWriter.write.append("M=M-1\r\n");
        codeWriter.write.append("A=M\r\n");
        codeWriter.write.append("D=M\r\n");
        codeWriter.write.append("@"+de[1]+"\r\n");
        codeWriter.write.append("D;JGT\r\n");
    }
    public void callS(String[] de) throws IOException {

        codeWriter.write.append("@R0\r\n");
        codeWriter.write.append("M=M+1\r\n");

        codeWriter.write.append("M=M+1\r\n");
        codeWriter.write.append("@R1\r\n");
        codeWriter.write.append("D=M\r\n");
        codeWriter.write.append("@R0\r\n");
        codeWriter.write.append("A=M-1\r\n");
        codeWriter.write.append("M=D\r\n");

        codeWriter.write.append("@R2\r\n");
        codeWriter.write.append("D=M\r\n");
        codeWriter.write.append("@R0\r\n");
        codeWriter.write.append("M=M+1\r\n");
        codeWriter.write.append("A=M-1\r\n");
        codeWriter.write.append("M=D\r\n");

        codeWriter.write.append("@R3\r\n");
        codeWriter.write.append("D=M\r\n");
        codeWriter.write.append("@R0\r\n");
        codeWriter.write.append("M=M+1\r\n");
        codeWriter.write.append("A=M-1\r\n");
        codeWriter.write.append("M=D\r\n");

        codeWriter.write.append("@R4\r\n");
        codeWriter.write.append("D=M\r\n");
        codeWriter.write.append("@R0\r\n");
        codeWriter.write.append("M=M+1\r\n");
        codeWriter.write.append("A=M-1\r\n");
        codeWriter.write.append("M=D\r\n");

        codeWriter.write.append("@0\r\n");
        codeWriter.write.append("D=M\r\n");
        codeWriter.write.append("D=D-1\r\n");
        codeWriter.write.append("D=D-1\r\n");
        codeWriter.write.append("D=D-1\r\n");
        codeWriter.write.append("D=D-1\r\n");
        codeWriter.write.append("D=D-1\r\n");

        codeWriter.write.append("@"+de[2]+"\r\n");
        codeWriter.write.append("D=D-A\r\n");
        codeWriter.write.append("@R2\r\n");
        codeWriter.write.append("M=D\r\n");

        codeWriter.write.append("@R0\r\n");
        codeWriter.write.append("D=M\r\n");
        codeWriter.write.append("@R1\r\n");
        codeWriter.write.append("M=D\r\n");


        sti++;
        h1.put(de[1], nowF+sti);


        codeWriter.write.append("@"+de[1]+"\r\n");
        codeWriter.write.append("0;JMP\r\n");
        codeWriter.write.append("("+nowF+sti+")\r\n");
    }
    public void returnS(String[] de) throws IOException {
        if (!h1.containsKey(nowF)){
            codeWriter.write.append("(END)\r\n");
            codeWriter.write.append("@END\r\n");
            codeWriter.write.append("0;JMP\r\n");
        }
        codeWriter.write.append("@R0\r\n");
        codeWriter.write.append("A=M-1\r\n");
        codeWriter.write.append("D=M\r\n");
        codeWriter.write.append("@R2\r\n");
        codeWriter.write.append("A=M\r\n");
        codeWriter.write.append("M=D\r\n");

        codeWriter.write.append("@R2\r\n");
        codeWriter.write.append("D=M\r\n");
        codeWriter.write.append("@R0\r\n");
        codeWriter.write.append("M=D+1\r\n");

        codeWriter.write.append("@R1\r\n");
        codeWriter.write.append("M=M-1\r\n");
        codeWriter.write.append("A=M\r\n");
        codeWriter.write.append("D=M\r\n");
        codeWriter.write.append("@R4\r\n");
        codeWriter.write.append("M=D\r\n");

        codeWriter.write.append("@R1\r\n");
        codeWriter.write.append("M=M-1\r\n");
        codeWriter.write.append("A=M\r\n");
        codeWriter.write.append("D=M\r\n");
        codeWriter.write.append("@R3\r\n");
        codeWriter.write.append("M=D\r\n");

        codeWriter.write.append("@R1\r\n");
        codeWriter.write.append("M=M-1\r\n");
        codeWriter.write.append("A=M\r\n");
        codeWriter.write.append("D=M\r\n");
        codeWriter.write.append("@R2\r\n");
        codeWriter.write.append("M=D\r\n");

        codeWriter.write.append("@R1\r\n");
        codeWriter.write.append("M=M-1\r\n");
        codeWriter.write.append("A=M\r\n");
        codeWriter.write.append("D=M\r\n");
        codeWriter.write.append("@R1\r\n");
        codeWriter.write.append("M=D\r\n");



        if (h1.containsKey(nowF)) {
            codeWriter.write.append("@" + h1.get(nowF) + "\r\n");
            codeWriter.write.append("0;JMP\r\n");
        }

    }
    public void functionS(String[] de) throws IOException {
        nowF=de[1];
        codeWriter.write.append("(" + de[1] + ")\r\n");
        int xxx=Integer.parseInt(de[2]);
        for (int i=0;i<xxx;i++){
            codeWriter.write.append("@0\r\n");
            codeWriter.write.append("M=M+1\r\n");
            codeWriter.write.append("A=M-1\r\n");
            codeWriter.write.append("M=0\r\n");
        }
    }



}

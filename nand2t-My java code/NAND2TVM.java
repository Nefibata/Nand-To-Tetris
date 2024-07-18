package nand2t;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class NAND2TVM {
    static Parser parser;
    HashMap<String,Integer> a=new HashMap<>();
int si=0;
    static {
        try {
            parser = new Parser("C:\\Users\\zxk\\Desktop\\nand2tetris\\projects\\07\\MemoryAccess\\StaticTest\\StaticTest.vm");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static CodeWriter codeWriter;

    static {
        try {
            codeWriter = new CodeWriter("C:\\Users\\zxk\\Desktop\\nand2tetris\\projects\\07\\MemoryAccess\\StaticTest\\StaticTest.asm");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static NAND2TVM nand2TVM;

    static {
        try {
            nand2TVM = new NAND2TVM();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public NAND2TVM() throws FileNotFoundException {
    }
    int ei=0;
    int gt=0;
int lt=0;
    public static void main(String[] args) throws IOException {
        while (true){
            String readLine=parser.in.readLine();
            if (readLine==null)break;
            if(readLine.equals(""))continue;
            readLine=readLine.trim();
            if (readLine.charAt(0)=='/')continue;
            String[] de=readLine.split(" ");
            if (de.length!=1){
                if (de[0].equals("push")){
                    nand2TVM.reg1(de[1],de[2]);
                    if (de[1].equals("constant")) {
                        codeWriter.write.append("D=A+D\r\n");
                    }else {
                        codeWriter.write.append("D=M\r\n");
                    }

                    codeWriter.write.append("@R0\r\n");
                    codeWriter.write.append("M=M+1\r\n");
                    codeWriter.write.append("A=M-1\r\n");
                    codeWriter.write.append("M=D\r\n");

                }else {
                    nand2TVM.reg1(de[1],de[2]);
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

            }else {
                nand2TVM.reg(de[0]);

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

}

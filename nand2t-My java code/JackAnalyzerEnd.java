package nand2t;

import java.io.IOException;
import java.util.HashMap;

public class JackAnalyzerEnd {
    public static void main(String[]  args) throws IOException {
        SymbolTabel st=new SymbolTabel();
        String s1="C:\\Users\\Administrator\\Desktop\\nand2tetris\\projects\\11\\Seven";
        String s2="\\Main";
        JackTokenizer jk=new JackTokenizer(
                s1+s2+".jack");
        CodeWriter cw=new CodeWriter(
                s1+s2+".temp");
        VMWriter VM=new VMWriter(cw);
        CompilationEngineEnd ce=new CompilationEngineEnd(jk,cw,VM,st);
        ce.compileClass();
        cw.write.close();
//处理函数操作数问题
        HashMap<String,Integer> hs2= ce.hs;
        Parser p1=new Parser(
                s1+s2+".temp");
        CodeWriter cw2=new CodeWriter(
                s1+s2+".vm");
        while (true){
            String nl=p1.in.readLine();
            if (nl==null)break;
            String[] nls=nl.split(" ");
            if (nls[0].equals("function")){
                String name=nls[1];
                int a=hs2.get(name);
                cw2.write.append("function ");
                cw2.write.append(name);
                cw2.write.append(" "+a+"\r\n");

            }else {
                cw2.write.append(nl);
                cw2.write.append("\r\n");

            }
        }
        cw2.write.close();

    }
}

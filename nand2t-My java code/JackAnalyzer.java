package nand2t;

import java.io.FileNotFoundException;
import java.io.IOException;

public class JackAnalyzer {
    public static void main(String[]  args) throws IOException {

        JackTokenizer jk=new JackTokenizer(
                "C:\\Users\\Administrator\\Desktop\\nand2tetris\\projects\\11\\Average\\Main1.jack");
        CodeWriter cw=new CodeWriter(
                "C:\\Users\\Administrator\\Desktop\\nand2tetris\\projects\\11\\Average\\Main.xml");
        CompilationEngine ce=new CompilationEngine(jk,cw);
        ce.compileClass();
        cw.write.close();

    }
}

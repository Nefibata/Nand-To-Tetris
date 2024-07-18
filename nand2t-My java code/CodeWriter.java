package nand2t;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class CodeWriter {
    File file;
    FileOutputStream fileOutputStream;
    OutputStreamWriter write;
    public CodeWriter(String s) throws FileNotFoundException {
        file=new File(s);
        fileOutputStream=new FileOutputStream(file);
        write=new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
    }

}

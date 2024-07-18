package nand2t;

import java.io.IOException;

public class VMWriter {
 static CodeWriter cw;
 public VMWriter(CodeWriter cw){
  VMWriter.cw =cw;
 }

   public void  writePush(String seg,int index) throws IOException {
       cw.write.append("push");
       cw.write.append(" ");
       cw.write.append(seg);
       cw.write.append(" ");
       cw.write.append(String.valueOf(index));
       cw.write.append("\r\n");

   }
    public void  writePop(String seg,int index) throws IOException {
     cw.write.append("pop");
     cw.write.append(" ");
     cw.write.append(seg);
     cw.write.append(" ");
     cw.write.append(String.valueOf(index));
     cw.write.append("\r\n");
    }
    public void  writeArithmetic(String com) throws IOException {
  cw.write.append(com);
     cw.write.append("\r\n");

    }
    public void  writeLabel(String label) throws IOException {
     cw.write.append("label");
     cw.write.append(" ");
     cw.write.append(label);
     cw.write.append("\r\n");
    }
    public void  writeGoto(String label) throws IOException {
     cw.write.append("goto");
     cw.write.append(" ");
     cw.write.append(label);
     cw.write.append("\r\n");
    }
    public void  writeIf(String label) throws IOException {
     cw.write.append("if-goto");
     cw.write.append(" ");
     cw.write.append(label);
     cw.write.append("\r\n");
    }
    public void  writeCall(String name,int nArgs) throws IOException {
     cw.write.append("call");
     cw.write.append(" ");
     cw.write.append(name);
     cw.write.append(" ");
     cw.write.append(String.valueOf(nArgs));
     cw.write.append("\r\n");
    }
    public void writeFunction(String name,int nArgs) throws IOException {
     cw.write.append("function");
     cw.write.append(" ");
     cw.write.append(name);
     cw.write.append(" ");
     cw.write.append(String.valueOf(nArgs));
     cw.write.append("\r\n");
    }
    public void writeReturn() throws IOException {
     cw.write.append("return\r\n");

    }
    public void add() throws IOException {
        cw.write.append("add\r\n");
    }
    public void sub() throws IOException {
        cw.write.append("sub\r\n");
    }
    public void mult() throws IOException {
        cw.write.append("call Math.multiply 2\r\n");
    }

    public void div() throws IOException {
        cw.write.append("call Math.divide 2\r\n");
    }
}

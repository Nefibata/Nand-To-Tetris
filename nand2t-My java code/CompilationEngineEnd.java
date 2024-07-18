package nand2t;

import java.io.IOException;
import java.util.*;

public class CompilationEngineEnd {
    static HashSet<String>  op=new HashSet<>();
    public HashMap<String,Integer> hs=new HashMap<>();
    static  int hsi=0;
   static int count=0;
   Stack<String> st=new Stack<>();
    public static final String[] ops = {"+","-","*","/","&","|","<",">","=","~"};
    static {
        Collections.addAll(op, ops);
    }
    String className;
    JackTokenizer jk;
    VMWriter vmw;
    SymbolTabel syt;
    static CodeWriter cw;
    public CompilationEngineEnd(JackTokenizer jk,CodeWriter cw,VMWriter vmw,SymbolTabel syt){
        this.jk=jk;
        CompilationEngineEnd.cw =cw;
        this.vmw=vmw;
        this.syt=syt;

    }
    public void compileClass() throws IOException {
        jk.advance();
        jk.advance();// 此时读到类名
        className=jk.keyword();
        jk.advance();

        jk.advance();
        while (!jk.symbol().equals("}")) {
            if (jk.keyword().equals("static") || jk.keyword().equals("field")) {
                //变量
                this.compileClassVarDec();
            } else {
                //读到函数
                syt.staticSubroutine();

                this.compileSubroutine();
            }
            jk.advance();
        }
    }
    public void compileClassVarDec() throws IOException {
        String s1=jk.keyword();
        jk.advance();
       String s2=jk.keyword();
        switch (s1) {
            case "static" -> {
                jk.advance();
                syt.Define(jk.nowWord,s2,"static");
                jk.advance();//符号
                while (jk.symbol().equals(",")){
                    jk.advance();
                    syt.Define(jk.nowWord,s2,"static");
                    jk.advance();
                }
            }
            case "field" -> {
                jk.advance();
                syt.Define(jk.nowWord,s2, "field");
                jk.advance();//符号
                while (jk.symbol().equals(",")){
                    jk.advance();
                    syt.Define(jk.nowWord,s2,"field");
                    jk.advance();
                }
            }
        }


    }
    public void compileSubroutine() throws IOException {
        hsi=0;
        String s;
        /*String cSN= jk.stringVal();
        if (cSN.equals("method")){
            jk.advance();
            jk.advance();
            s=jk.keyword();
            jk.advance();
            jk.advance();
            if (!jk.tokenType().equals("symbol")) {
                int a= this.compileParameterList();
                vmw.writeFunction(className+"."+s,a);
            }else {
                vmw.writeFunction(className+"."+s,0);
            }
        }else {
            jk.advance();
            jk.advance();
            s=jk.keyword();
            jk.advance();
            jk.advance();
            if (!jk.tokenType().equals("symbol")) {
                int a= this.compileParameterList();
                vmw.writeFunction(className+"."+s,a);
            }else {
                vmw.writeFunction(className+"."+s,0);

            }
        }

         */
        String cSN= jk.stringVal();
        jk.advance();
        jk.advance();
        s=jk.keyword();
        vmw.writeFunction(className+"."+s,0);
        jk.advance();
        jk.advance();
        switch (cSN){
            case "method"->{
                vmw.writePush("argument",0);
                vmw.writePop("pointer",0);
                syt.Define("mth","this","argument");
            }
            case  "constructor"->{
                vmw.writePush("constant",syt.VarCount("field"));
                vmw.writeCall("Memory.alloc",1);
                vmw.writePop("pointer",0);
            }
            case  "function"->{
            }
        }
        if (!jk.tokenType().equals("symbol")) {
            this.compileParameterList();
        }



        jk.advance();//到{
        jk.advance();

        while (!jk.symbol().equals("}")) {
            if (jk.keyword().equals("var") ) {
                this.compileVarDec();
                jk.advance();
            } else {
                this.compileStatements();
            }

        }
        hs.put(className+"."+s,hsi);

    }
    public void compileParameterList() throws IOException {
        int re=1;
        String s1=jk.keyword();
        jk.advance();
        syt.Define(jk.keyword(),s1,"argument");
        jk.advance();

        while (jk.symbol().equals(",")){
            jk.advance();
            re++;

            s1=jk.keyword();
            jk.advance();
            syt.Define(jk.keyword(),s1,"argument");
            jk.advance();
        }

    }
    public void compileVarDec() throws IOException {

        jk.advance();
        String s1=jk.keyword();
        jk.advance();
        syt.Define(jk.keyword(),s1,"var");
        hsi++;
        jk.advance();
        while (!jk.symbol().equals(";")){
            jk.advance();
            syt.Define(jk.keyword(),s1,"var");
            hsi++;
            jk.advance();
        }

    }
    public void compileStatements() throws IOException {
        while (!jk.tokenType().equals("symbol")) {
            switch (jk.keyword()) {
                case "let" -> this.compileLet();
                case "if" -> this.compileIf();
                case "while" -> this.compileWhile();
                case "do" -> this.compileDo();
                case "return" -> this.compileReturn();
            }
            jk.advance();
        }


    }
    public void compileDo() throws IOException {
        jk.advance();
        String Name1=jk.identifier();
        jk.advance();
        if (jk.symbol().equals("(")) {

            jk.advance();
            vmw.writePush("pointer",0);
            int a=this.compileExpressionList();
            vmw.writeCall(className+"."+Name1,a+1);

        }else {
            if (syt.segOf(Name1).equals("none")){
                jk.advance();
                String Name2=jk.identifier();
                jk.advance();
                jk.advance();
                int a=this.compileExpressionList();
                vmw.writeCall(Name1+"."+Name2,a);
            }else {
                String varName=syt.TypeOf(Name1);
                int varInt=syt.IndexOf(Name1);
                String xx=syt.segOf(Name1);
                jk.advance();
                String Name2=jk.identifier();
                jk.advance();
                jk.advance();
                vmw.writePush(xx,varInt);
                int a=this.compileExpressionList();
                vmw.writeCall(varName+"."+Name2,a+1);
            }


        }
        vmw.writePop("temp",0);

        jk.advance();

    }
    public void compileLet() throws IOException {
        jk.advance();//到var name
        String a=syt.segOf(jk.identifier());
        int b=syt.IndexOf(jk.identifier());
        jk.advance();
        if (jk.symbol().equals("=")){
            jk.advance();
            this.compileExpression();
            vmw.writePop(a,b);
        }else {

            jk.advance();
            this.compileExpression();
            vmw.writePush(a,b);
            vmw.add();
            jk.advance();
            jk.advance();
            this.compileExpression();
            vmw.writePop("temp",0);
            vmw.writePop("pointer",1);
            vmw.writePush("temp",0);
            vmw.writePop("that",0);
        }



    }
    public void compileWhile() throws IOException {
        int a=count;
        count++;
        int b=count;
        count++;

        jk.advance();
        jk.advance();
        vmw.writeLabel(className+"While"+String.valueOf(a));
        this.compileExpression();
        vmw.writeArithmetic("not");
        vmw.writeIf(className+"While"+String.valueOf(b));
        jk.advance();
        jk.advance();
        this.compileStatements();
        vmw.writeGoto(className+"While"+String.valueOf(a));
        vmw.writeLabel(className+"While"+String.valueOf(b));

    }
    public void compileReturn() throws IOException {
        jk.advance();
        if (!jk.symbol().equals(";")){
            this.compileExpression();

        }else {
            vmw.writePush("constant",0);
        }

        vmw.writeReturn();
    }
    public void compileIf() throws IOException {
        int a=count;
        count++;
        int b=count;
        count++;
        jk.advance();
        jk.advance();
        this.compileExpression();
        vmw.writeArithmetic("not");
        vmw.writeIf(className+"IF"+String.valueOf(a));
        jk.advance();
        jk.advance();
        this.compileStatements();
        vmw.writeGoto(className+"IF"+String.valueOf(b));

        jk.advanceB();
        if (jk.tokenType().equals("keyword")&&jk.keyword().equals("else")) {
            jk.advance();
            jk.advance();
            jk.advance();
            jk.advance();
            vmw.writeLabel(className+"IF"+String.valueOf(a));
            this.compileStatements();

        }else {
            vmw.writeLabel(className+"IF"+String.valueOf(a));
            jk.advance();
        }
        vmw.writeLabel(className+"IF"+String.valueOf(b));
    }
    public void compileExpression() throws IOException {
        this.compileTerm();
        //if (!(jk.symbol().equals(";")||op.contains(jk.symbol())))jk.advance();
        jk.advance();
        while (jk.tokenType().equals("symbol")&&op.contains(jk.symbol())){
            /*if (st.isEmpty()){
                st.add(jk.keyword());
            }else {
                String s1=st.pop();
                switch (s1){
                    case "+"->{
                        vmw.add();
                    }
                    case "-"->{
                        vmw.sub();
                    }
                    case "*"->{
                        vmw.mult();
                    }
                    case "/"->{
                        vmw.div();
                    }
                    case "<"->{
                        vmw.writeArithmetic("lt");
                    }
                    case ">"->{
                        vmw.writeArithmetic("gt");
                    }
                    case "="->{
                        vmw.writeArithmetic("eq");
                    }
                }
            }
            jk.advance();
            if(jk.keyword().equals("(")){
                while (!jk.keyword().equals(")")){
                    if (!st.peek().equals("(")&&!jk.keyword().equals("(")){
                        String s2=st.pop();
                        switch (s2){
                            case "+"->{
                                vmw.add();
                            }
                            case "-"->{
                                vmw.sub();
                            }
                            case "*"->{
                                vmw.mult();
                            }
                            case "/"->{
                                vmw.div();
                            }
                            case "<"->{
                                vmw.writeArithmetic("lt");
                            }
                            case ">"->{
                                vmw.writeArithmetic("gt");
                            }
                            case "="->{
                                vmw.writeArithmetic("eq");
                            }
                            case "&"->{
                             vmw.writeArithmetic("and");
                            }
                            case "|"->{
                             vmw.writeArithmetic("or");
                            }
                        }
                    }
                    st.add(jk.keyword());
                    jk.advance();
                    this.compileTerm();
                    jk.advance();
                }
            }else {
                this.compileTerm();
            }
            jk.advance();
            */
            String s2=jk.symbol();
            jk.advance();
            this.compileTerm();
            switch (s2){
                case "+"->{
                    vmw.add();
                }
                case "-"->{
                    vmw.sub();
                }
                case "*"->{
                    vmw.mult();
                }
                case "/"->{
                    vmw.div();
                }
                case "<"->{
                    vmw.writeArithmetic("lt");
                }
                case ">"->{
                    vmw.writeArithmetic("gt");
                }
                case "="->{
                    vmw.writeArithmetic("eq");
                }
                case "&"->{
                    vmw.writeArithmetic("and");
                }
                case "|"->{
                    vmw.writeArithmetic("or");
                }
            }
            jk.advance();

        }

    }
    public void compileTerm() throws IOException {
        switch (jk.tokenType()){
            case "stringConstant":
                String s=jk.stringVal();
                vmw.writePush("constant",s.length());
                vmw.writeCall("String.new" ,1);
                for (int i=0;i<s.length();i++){
                    int i2=s.charAt(i);
                    vmw.writePush("constant",i2);
                    vmw.writeCall("String.appendChar",2);
                }
                break;
            case "intConstant":
                vmw.writePush("constant",jk.intVal());
                break;
            case "keyword" :
                if(jk.keyword().equals("true")){
                    vmw.writePush("constant",1);
                    vmw.writeArithmetic("neg");
                }else if (jk.keyword().equals("this")){
                    vmw.writePush("pointer",0);
                }else {
                    vmw.writePush("constant",0);
                }

                break;
            case "symbol":
               /* if (jk.symbol().equals("(")){
                    jk.advance();
                    this.compileExpression();
                }else {

                    jk.advance();
                    this.compileTerm();
                }

                */
                switch (jk.symbol()){
                    case "("->{
                        jk.advance();
                        this.compileExpression();
                    }
                    case "-"->{
                        jk.advance();
                        this.compileTerm();
                        vmw.writeArithmetic("neg");
                    }
                    case "~"->{
                        jk.advance();
                        this.compileTerm();
                        vmw.writeArithmetic("not");
                    }


                }
                break;
            case "identifier":
                String x1=jk.identifier();
                jk.advanceB();
                switch (jk.symbol()) {
                    case "[" -> {
                        String a=syt.segOf(x1);
                        int b=syt.IndexOf(x1);
                        jk.advance();
                        jk.advance();
                        this.compileExpression();
                        vmw.writePush(a,b);
                        vmw.add();
                        vmw.writePop("pointer",1);
                        vmw.writePush("that",0);

                    }
                    case "(" -> {
                        jk.advance();
                        jk.advance();
                        vmw.writePush("pointer",0);
                        int x2=this.compileExpressionList();
                        vmw.writeCall(className+"."+x1,x2+1);
                    }
                    case "." -> {
                        if (syt.segOf(x1).equals("none")){
                            jk.advance();
                            jk.advance();
                            String x3=jk.identifier();
                            jk.advance();
                            jk.advance();
                            int x2=this.compileExpressionList();
                            vmw.writeCall(x1+"."+x3,x2);
                        }else {
                            String x4=syt.segOf(x1);
                            int  x5=syt.IndexOf(x1);
                            String x6=syt.TypeOf(x1);
                            jk.advance();
                            jk.advance();
                            String x3=jk.identifier();
                            jk.advance();
                            jk.advance();
                            vmw.writePush(x4,x5);
                            int x2=this.compileExpressionList();
                            vmw.writeCall(x6+"."+x3,x2+1);
                        }

                    }
                    default -> {

                        vmw.writePush(syt.segOf(x1),syt.IndexOf(x1));
                    }
                }
                break;

        }

    }
    public int compileExpressionList() throws IOException {
        int i=0;
        if (jk.tokenType().equals("symbol")&&jk.symbol().equals(")")){
            return i;
        }
        this.compileExpression();
        i++;
        while (jk.symbol().equals(",")){
            jk.advance();
            this.compileExpression();
            i++;
        }
        return i;

    }




}

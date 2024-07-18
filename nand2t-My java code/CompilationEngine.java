package nand2t;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;


public class CompilationEngine {
    static HashSet<String>  op=new HashSet<>();
    public static final String[] ops = {"+","-","*","/","&","|","<",">","=","~"};
    static {
        Collections.addAll(op, ops);
    }
    JackTokenizer jk;
    static CodeWriter cw;
    public CompilationEngine(JackTokenizer jk,CodeWriter cw){
        this.jk=jk;
        CompilationEngine.cw =cw;

    }
    public void compileClass() throws IOException {
        cw.write.append("<class>\r\n");
        jk.advance();
        this.keyWW();
        jk.advance();
        this.idWW();
        jk.advance();
        this.syWW();
        jk.advance();
        while (!jk.symbol().equals("}")) {
            if (jk.keyword().equals("static") || jk.keyword().equals("field")) {
                this.compileClassVarDec();
            } else {
                this.compileSubroutine();
            }
            jk.advance();
        }
        this.syWW();
        cw.write.append("</class>\r\n");
    }
    public void compileClassVarDec() throws IOException {
        cw.write.append("<classVarDec>\r\n");
        this.keyWW();
        jk.advance();
        if (jk.tokenType().equals("keyword")){
            this.keyWW();
        }else {
            this.idWW();
        }
        jk.advance();
        this.idWW();
        jk.advance();
        while (jk.symbol().equals(",")){
            this.syWW();
            jk.advance();
            this.idWW();
            jk.advance();
        }
        this.syWW();
        cw.write.append("</classVarDec>\r\n");

    }
    public void compileSubroutine() throws IOException {
        cw.write.append("<subroutineDec>\r\n");
        this.keyWW();
        jk.advance();
        if (jk.tokenType().equals("keyword")){
            this.keyWW();
        }else {
            this.idWW();
        }
        jk.advance();
        this.idWW();
        jk.advance();
        this.syWW();
        jk.advance();
        if (!jk.tokenType().equals("symbol")) {
            this.compileParameterList();
        }else {
            cw.write.append("<parameterList>\r\n");
            cw.write.append("</parameterList>\r\n");
        }
        this.syWW();
        jk.advance();
        cw.write.append("<subroutineBody>\r\n");
        this.syWW();

        jk.advance();
        while (!jk.symbol().equals("}")) {
            if (jk.keyword().equals("var") ) {
                this.compileVarDec();
                jk.advance();
            } else {
                this.compileStatements();
            }

        }

        this.syWW();
        cw.write.append("</subroutineBody>\r\n");
        cw.write.append("</subroutineDec>\r\n");
    }
    public void compileParameterList() throws IOException {
        cw.write.append("<parameterList>\r\n");
        if (jk.tokenType().equals("keyword")){
            this.keyWW();
        }else {
            this.idWW();
        }
        jk.advance();
        this.idWW();
        jk.advance();
        while (jk.symbol().equals(",")){
            this.syWW();
            jk.advance();
            if (jk.tokenType().equals("keyword")){
                this.keyWW();
            }else {
                this.idWW();
            }
            jk.advance();
            this.idWW();
            jk.advance();

        }
       // this.syWW();
        cw.write.append("</parameterList>\r\n");
    }
    public void compileVarDec() throws IOException {
        cw.write.append("<varDec>\r\n");
        this.keyWW();
        jk.advance();
        if (jk.tokenType().equals("keyword")){
            this.keyWW();
        }else {
            this.idWW();
        }
        jk.advance();
        this.idWW();
        jk.advance();
        while (!jk.symbol().equals(";")){
            this.syWW();
            jk.advance();
            this.idWW();
            jk.advance();
        }
        this.syWW();
        cw.write.append("</varDec>\r\n");
    }
    public void compileStatements() throws IOException {
        cw.write.append("<statements>\r\n");
        while (!jk.tokenType().equals("symbol")) {
            switch (jk.keyword()) {
                case "let":
                    this.compileLet();
                    break;
                case "if":
                    this.compileIf();
                    break;
                case "while":
                    this.compileWhile();
                    break;
                case "do":
                    this.compileDo();
                    break;
                case "return":
                    this.compileReturn();
                    break;

            }
            jk.advance();
        }

        cw.write.append("</statements>\r\n");
    }
    public void compileDo() throws IOException {
        cw.write.append("<doStatement>\r\n");
        this.keyWW();
        jk.advance();
        this.idWW();
        jk.advance();
        this.syWW();
        if (jk.symbol().equals("(")) {
            jk.advance();
            this.compileExpressionList();
        }else {
            jk.advance();
            this.idWW();
            jk.advance();
            this.syWW();
            jk.advance();
            this.compileExpressionList();
        }
        this.syWW();
        jk.advance();
        this.syWW();
        cw.write.append("</doStatement>\r\n");
    }
    public void compileLet() throws IOException {
        cw.write.append("<letStatement>\r\n");
        this.keyWW();
        jk.advance();
        this.idWW();
        jk.advance();
        if (jk.symbol().equals("=")){
            this.syWW();
            jk.advance();
            this.compileExpression();
        }else {
            this.syWW();
            jk.advance();
            this.compileExpression();
            this.syWW();
            jk.advance();
            this.syWW();
            jk.advance();
            this.compileExpression();
        }
        this.syWW();
        cw.write.append("</letStatement>\r\n");

    }
    public void compileWhile() throws IOException {
        cw.write.append("<whileStatement>\r\n");
        this.keyWW();
        jk.advance();
        this.syWW();
        jk.advance();
        this.compileExpression();
        this.syWW();
        jk.advance();
        this.syWW();
        jk.advance();
        this.compileStatements();
        this.syWW();
        cw.write.append("</whileStatement>\r\n");
    }
    public void compileReturn() throws IOException {
        cw.write.append("<returnStatement>\r\n");
        this.keyWW();
        jk.advance();
        if (!jk.symbol().equals(";"))this.compileExpression();
        this.syWW();
        cw.write.append("</returnStatement>\r\n");
    }
    public void compileIf() throws IOException {
        cw.write.append("<ifStatement>\r\n");
        this.keyWW();
        jk.advance();
        this.syWW();
        jk.advance();
        this.compileExpression();
        this.syWW();
        jk.advance();
        this.syWW();
        jk.advance();
        this.compileStatements();
        this.syWW();
        jk.advanceB();
        if (jk.tokenType().equals("keyword")&&jk.keyword().equals("else")) {
            jk.advance();
            jk.advance();
            this.keyWW();
            jk.advance();
            this.syWW();
            jk.advance();
            this.compileStatements();
            this.syWW();
        }else {
            jk.advance();
        }
        cw.write.append("</ifStatement>\r\n");

    }
    public void compileExpression() throws IOException {
        cw.write.append("<expression>\r\n");
        this.compileTerm();
        //if (!(jk.symbol().equals(";")||op.contains(jk.symbol())))jk.advance();
        jk.advance();
        while (jk.tokenType().equals("symbol")&&op.contains(jk.symbol())){
            this.syWW();
            jk.advance();
            this.compileTerm();
            jk.advance();
        }


        cw.write.append("</expression>\r\n");
    }
    public void compileTerm() throws IOException {
        cw.write.append("<term>\r\n");
        switch (jk.tokenType()){
            case "stringConstant":
                this.strWW();
                break;
            case "intConstant":
                this.intWW();
                break;
            case "keyword" :
                this.keyWWW();
                break;
            case "symbol":
                if (jk.symbol().equals("(")){
                    this.syWW();
                    jk.advance();
                    this.compileExpression();
                    this.syWW();
                }else {
                    this.syWW();
                    jk.advance();
                    this.compileTerm();
                }
                break;
            case "identifier":
                this.idWW();
                jk.advanceB();
                switch (jk.symbol()) {
                    case "[" -> {
                        jk.advance();
                        this.syWW();
                        jk.advance();
                        this.compileExpression();
                        this.syWW();
                    }
                    case "(" -> {
                        jk.advance();
                        this.syWW();
                        jk.advance();
                        this.compileExpressionList();
                        this.syWW();
                    }
                    case "." -> {
                        jk.advance();
                        this.syWW();
                        jk.advance();
                        this.idWW();
                        jk.advance();
                        this.syWW();
                        jk.advance();
                        this.compileExpressionList();
                        this.syWW();
                    }
                }




        }
        cw.write.append("</term>\r\n");
    }
    public void compileExpressionList() throws IOException {
        cw.write.append("<expressionList>\r\n");
        if (jk.tokenType().equals("symbol")&&jk.symbol().equals(")")){
            cw.write.append("</expressionList>\r\n");
            return;
        }
        this.compileExpression();
        while (jk.symbol().equals(",")){
            this.syWW();
            jk.advance();
            this.compileExpression();
        }
        cw.write.append("</expressionList>\r\n");

    }
    void keyWW() throws IOException {
        cw.write.append("<keyword>");
        cw.write.append(jk.keyword());
        cw.write.append("</keyword>\r\n");
    }
    void idWW() throws IOException {
        cw.write.append("<identifier>");
        cw.write.append(jk.identifier());
        cw.write.append("</identifier>\r\n");
    }
    void syWW() throws IOException {
        cw.write.append("<symbol>");
        switch (jk.symbol()) {
            case "<" -> cw.write.append("&lt;");
            case ">" -> cw.write.append("&gt;");
            case "&" -> cw.write.append("&amp;");
            default -> cw.write.append(jk.symbol());
        }


        cw.write.append("</symbol>\r\n");
    }
    void strWW() throws IOException {
        cw.write.append("<stringConstant>");
        cw.write.append(jk.stringVal().substring(1));
        cw.write.append("</stringConstant>\r\n");
    }
    void intWW() throws IOException {
        cw.write.append("<intConstant>");
        cw.write.append(jk.intVal()+"");
        cw.write.append("</intConstant>\r\n");
    }
    void keyWWW() throws IOException {
        cw.write.append("<keyword>");
        cw.write.append(jk.keyword());
        cw.write.append("</keyword>\r\n");
    }

}

package nand2t;

import java.util.HashMap;

public class SymbolTabel
{
     int stc;
     int fic;
    HashMap<String,sTable> st;
    HashMap<String,sTable> fi;

     int argc;
      int varC;
    HashMap<String,sTable> arg;
    HashMap<String,sTable> var;
    public int VarCount(String Kind){
        return switch (Kind) {
            case "static" -> stc;
            case "field" -> fic;
            case "argument" -> argc;
            case "var" -> varC;
            default -> -1;
        };
    }
    public String KindOf (String name){
        if (arg.containsKey(name))return "argument";
        if (var.containsKey(name))return "var";
        if (st.containsKey(name))return "static";
        if (fi.containsKey(name))return "field";
        return "none";


    }
    public String segOf (String name){
        if (arg.containsKey(name))return "argument";
        if (var.containsKey(name))return "local";
        if (st.containsKey(name))return "static";
        if (fi.containsKey(name))return "this";
        return "none";


    }
    public String TypeOf (String name){
        switch (this.KindOf(name)){
            case "static" -> {
                return st.get(name).type;
            }
            case "field" -> {
                return fi.get(name).type;
            }
            case "argument" ->{
                return arg.get(name).type;
            }
            case "var" ->{
                return var.get(name).type;
            }

        }
        return null;
    }
    public int IndexOf(String name){
        switch (this.KindOf(name)){
            case "argument" ->{
                return arg.get(name).count;
            }
            case "var" ->{
                return var.get(name).count;
            }
            case "static" -> {
                return st.get(name).count;
            }
            case "field" -> {
                return fi.get(name).count;
            }
        }
        return -1;
    }
    public void staticSubroutine(){
        arg.clear();
        var.clear();
        argc=0;
        varC=0;
    }
    public SymbolTabel(){
        this.st=new HashMap<>();
        this.fi=new HashMap<>();
        stc=0;
        fic=0;

        this.arg=new HashMap<>();
        this.var=new HashMap<>();
        argc=0;
        varC=0;
    }

    public void Define(String name,String type,String kind){
        switch (kind){
            case "static" -> {
                st.put(name,new sTable(type,this.stc++));
            }
            case "field" -> {
                fi.put(name,new sTable(type,this.fic++));
            }
            case "argument" ->{
                arg.put(name,new sTable(type,this.argc++));
            }
            case "var" ->{
                var.put(name,new sTable(type,this.varC++));
            }

        }
    }

}

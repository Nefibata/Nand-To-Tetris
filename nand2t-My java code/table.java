package nand2t;

import java.util.HashMap;

public class table {
    HashMap<String,Integer> table;
    HashMap<String,Integer> table1;

    HashMap<String,Integer> table2;
    public table(){
        table=new HashMap<>();
        table1=new HashMap<>();
        table2=new HashMap<>();
        table.put("SP",0);
        table.put("LCL",1);
        table.put("ARG",2);
        table.put("THIS",3);
        table.put("THAT",4);
        table.put("KBD",24576);
        table.put("SCREEN",16384);
        table.put("R0",0);
        table.put("R1",1);
        table.put("R2",2);
        table.put("R3",3);
        table.put("R4",4);
        table.put("R5",5);
        table.put("R6",6);
        table.put("R7",7);
        table.put("R8",8);
        table.put("R9",9);
        table.put("R10",10);
        table.put("R11",11);
        table.put("R12",12);
        table.put("R13",13);
        table.put("R14",14);
        table.put("R15",15);

        table1.put("M",1);
        table1.put("D",2);
        table1.put("MD",3);
        table1.put("A",4);
        table1.put("AM",5);
        table1.put("AD",6);
        table1.put("AMD",7);

        table2.put("JGT",1);
        table2.put("JEQ",2);
        table2.put("JGE",3);
        table2.put("JLT",4);
        table2.put("JNE",5);
        table2.put("JLE",6);
        table2.put("JMP",7);

        table2.put("0",42);
        table2.put("1",63);
        table2.put("-1",58);
        table2.put("D",12);
        table2.put("A",48);
        table2.put("!D",13);
        table2.put("!A",49);
        table2.put("-D",15);
        table2.put("-A",51);
        table2.put("D+1",31);
        table2.put("A+1",55);
        table2.put("D-1",14);
        table2.put("A-1",50);
        table2.put("D+A",2);
        table2.put("D-A",19);
        table2.put("A-D",7);
        table2.put("D&A",0);
        table2.put("D|A",21);
        table2.put("M",112);
        table2.put("!M",113);
        table2.put("-M",115);
        table2.put("M+1",119);
        table2.put("M-1",114);
        table2.put("D+M",66);
        table2.put("D-M",83);
        table2.put("M-D",71);
        table2.put("D&M",64);
        table2.put("D|M",85);


    }
}

package nand2t;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {
    File file;
    FileReader fileReader;
    BufferedReader in;
    public Parser(String f) throws FileNotFoundException {
        file=new File(f);
        fileReader=new FileReader(file);
        in=new BufferedReader(fileReader);
    }
}
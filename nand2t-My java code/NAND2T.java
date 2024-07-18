package nand2t;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;

public class NAND2T {
    static int c=16;
    static int i=0;
    static table t=new table();
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        //String fileName=sc.next();
        File file=new File("C:\\Users\\zxk\\Desktop\\nand2tetris\\projects\\06\\Max\\Max.asm");
        File file2=new File("C:\\Users\\zxk\\Desktop\\nand2tetris\\projects\\06\\Max\\Max.asm");
        try {
            FileReader fileReader=new FileReader(file);
            FileReader fileReader2=new FileReader(file2);
            BufferedReader in=new BufferedReader(fileReader);
            BufferedReader in2=new BufferedReader(fileReader2);
            StringBuilder b=new StringBuilder();
            File f = new File("C:\\Users\\zxk\\Desktop\\nand2tetris\\projects\\06\\a.hack");
            FileOutputStream fop = new FileOutputStream(f);
            // 构建FileOutputStream对象,文件不存在会自动新建
            OutputStreamWriter writer = new OutputStreamWriter(fop, StandardCharsets.UTF_8);
            // 构建OutputStreamWriter对象,参数可以指定编码,默认为操作系统默认编码,windows上是gbk

            int x;
            String re;
            String aaa;
            int ma2=0;
            while (true){
                try {
                    aaa=in2.readLine();
                    if (aaa== null) break;
                    if (aaa.equals("")){
                        continue;
                    }
                    aaa = aaa.replaceAll(" ","");
                    if (aaa.equals("")|aaa.charAt(0)=='/'|aaa.charAt(0)=='('){

                    }else {
                        ma2++;
                    }
                    if (aaa.charAt(0)=='('){
                        t.table.put(aaa.substring(1,aaa.length()-1),ma2);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



             while (true){
                try {
                    re= in.readLine();
                    if (re== null) break;
                    if (re.equals("")){
                        continue;
                    }
                    re = re.replaceAll(" ","");
                    re=tolChart(re);
                    if (re.equals("")){
                        continue;
                    }
                    if (re.charAt(0)=='/'){
                        continue;
                    }

                    if (re.charAt(0)=='@'){
                        b.append('0');
                        x=Integer.parseInt(re.substring(1));
                        b.append(toBinary(x,15));
                        writer.append(b.toString());
                        writer.append("\r\n");
                        i++;
                        b=new StringBuilder();
                    }else {
                        i++;
                        b.append("111");
                        String[] a=re.split("=");
                        if (a.length==1){
                            a=re.split(";");
                            if (a.length==1){
                                  int mm=t.table2.get(a[0]);
                                  b.append(toBinary(mm,7));
                                  b.append("000000");
                                  writer.write(b.toString());
                                  b=new StringBuilder();
                            }else {
                                int mm2=t.table2.get(a[0]);
                                b.append(toBinary(mm2,7));
                                mm2=t.table2.get(a[1]);
                                b.append("000");
                                b.append(toBinary(mm2,3));
                                writer.write(b.toString());
                                writer.append("\r\n");
                                b=new StringBuilder();
                            }


                        }else {
                            String aa= toBinary(t.table1.get(a[0]), 3);
                            String[] a2=a[1].split(";");
                            if (a2.length==1){

                                int mm=t.table2.get(a[1]);
                                b.append(toBinary(mm,7));
                                b.append(aa);
                                b.append("000");
                                writer.write(b.toString());
                                writer.append("\r\n");
                                b=new StringBuilder();
                            }else {

                                int mm2=t.table2.get(a[1]);
                                b.append(toBinary(mm2,7));
                                mm2=t.table2.get(a[0]);
                                b.append(aa);
                                b.append(toBinary(mm2,3));
                                writer.write(b.toString());
                                writer.append("\r\n");
                                b=new StringBuilder();
                            }


                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    public static String toBinary(int num, int digits) {
        int value = 1 << digits | num;
        String bs = Integer.toBinaryString(value);
        return  bs.substring(1);
    }
    public static  String tolChart(String a){
        StringBuilder b=new StringBuilder();
        if (a.charAt(0)=='@'){
            if (t.table.containsKey(a.substring(1))){
                b.append('@');
                b.append(t.table.get(a.substring(1)));
                return b.toString();
            }else {
                if (Character.isDigit(a.charAt(1))){
                    return a;
                }
                t.table.put(a.substring(1),c);
                c++;
                b.append('@');
                b.append(t.table.get(a.substring(1)));
                return b.toString();
            }
        }else if (a.charAt(0)=='('){
            t.table.put(a.substring(1,a.length()),i+1);
            return b.toString();

        }else {
            return a;
        }

    }


}

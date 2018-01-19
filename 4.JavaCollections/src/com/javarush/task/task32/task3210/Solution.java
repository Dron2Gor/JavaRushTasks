package com.javarush.task.task32.task3210;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/* 
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) throws IOException {
        String fileName=args[0];
        long number=Integer.parseInt(args[1]);
        String text=args[2];
        RandomAccessFile raf=new RandomAccessFile(fileName,"rw");
        raf.seek(number);
        byte[] b=new byte[text.length()];
        raf.read(b,0,text.length());
        String st=new String(b);
        st.trim();
        System.out.println(st);
        raf.seek(raf.length());
        if (st.equals(text)) raf.write("true".getBytes());
        else raf.write("false".getBytes());
        raf.close();

    }
}

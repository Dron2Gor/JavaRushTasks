package com.javarush.task.task31.task3102;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/* 
Находим все файлы
*/
public class Solution {
    public static List<String> getFileTree(String root) throws IOException {
        Stack<File> stack =new Stack<>();
        List<String> listAbsolutNameFile = new ArrayList<>();
        File rootDirectory=new File(root);
        stack.push(rootDirectory);
        while (!stack.empty()){
            File tempFile=stack.pop();
            for (File file:tempFile.listFiles()){
                if (file.isDirectory())
                    stack.push(file);
                else listAbsolutNameFile.add(file.getAbsolutePath());
            }
        }
        return listAbsolutNameFile;

    }

    public static void main(String[] args) throws IOException {
        System.out.println(getFileTree("d:/Temp"));
        
    }
}

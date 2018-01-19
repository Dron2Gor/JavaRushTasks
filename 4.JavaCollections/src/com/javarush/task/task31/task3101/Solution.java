package com.javarush.task.task31.task3101;

import java.io.*;
import java.util.*;

/*
Проход по дереву файлов
*/
public class Solution {
    static List<File> smallFiles = new ArrayList<>();
    static Stack<File> directoryStack = new Stack<>();

    static List<File> RecursionPassingFilesTree(File[] files) {

        for (File file : files) {
            if (file.isDirectory()) RecursionPassingFilesTree(file.listFiles());
            else addSmallFileAndDeleteBigFile(file);
        }
        return smallFiles;
    }

    static void addSmallFileAndDeleteBigFile(File file) {
        if (testSize(file))
            FileUtils.deleteFile(file);
        else smallFiles.add(file);
    }

    static boolean testSize(File fl) {
        return fl.length() > 50;
    }

    static List<File> stackPassingFilesTree(File fileDirectory) {
        directoryStack.push(fileDirectory);
        while (!directoryStack.empty()) {
            for (File file : directoryStack.pop().listFiles())
                if (file.isDirectory()) directoryStack.push(file);
                else addSmallFileAndDeleteBigFile(file);
        }
        return smallFiles;
    }

    public static void main(String[] args) {

        Map<String, File> map = new TreeMap();
        String path = args[0];
        String resultFileAbsolutePath = args[1];
        File workedDirectory = new File(path);
        File source = new File(resultFileAbsolutePath);
        File result = new File(source.getParent() + "\\allFilesContent.txt");
        if (FileUtils.isExist(source)) {
            FileUtils.renameFile(source, result);
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(result);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        for (File fl : stackPassingFilesTree(workedDirectory)) {
            map.put(fl.getName(), fl);
        }
        Collections.sort(smallFiles, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (File file : smallFiles) {
            FileInputStream reader = null;
            try {
                reader = new FileInputStream(file);
                while (reader.available() > 0) {
                    fileOutputStream.write(reader.read());
                }
                fileOutputStream.write(System.lineSeparator().getBytes());
                reader.close();
                fileOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }
}
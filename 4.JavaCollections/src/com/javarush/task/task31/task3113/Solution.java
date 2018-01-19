package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/* 
Что внутри папки?
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String nameDerectory = reader.readLine();
        reader.close();

        Path path = Paths.get(nameDerectory);
        if (!Files.isDirectory(path)) System.out.println(nameDerectory + " - не папка");
        else {
            class SearchFileVisitor extends SimpleFileVisitor<Path> {
                int numberOfDirectories = 0;
                int numberOfFiles = 0;
                long sizeAllFiles = 0;

                public int getNumberOfDirectories() {
                    return numberOfDirectories;
                }

                public int getNumberOfFiles() {
                    return numberOfFiles;
                }

                public long getSizeAllFiles() {
                    return sizeAllFiles;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path file, BasicFileAttributes attrs) throws IOException {
                    if (Files.isDirectory(file)) {
                        numberOfDirectories++;
                    }
                    if (Files.isRegularFile(file)) {
                        numberOfFiles++;
                        sizeAllFiles += Files.size(file);
                    }
                    return super.visitFile(file, attrs);
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (Files.isRegularFile(file)) {
                        numberOfFiles++;
                        sizeAllFiles += Files.size(file);
                    }
                    return super.visitFile(file, attrs);
                }
            }
            SearchFileVisitor searchFileVisitor = new SearchFileVisitor();
            Files.walkFileTree(path, searchFileVisitor);
            System.out.println("Всего папок - "+(searchFileVisitor.getNumberOfDirectories()-1));
            System.out.println("Всего файлов - "+searchFileVisitor.getNumberOfFiles());
            System.out.println("Общий размер - "+searchFileVisitor.getSizeAllFiles());
        }
    }
}
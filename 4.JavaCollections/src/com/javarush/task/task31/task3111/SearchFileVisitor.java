package com.javarush.task.task31.task3111;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {
    private String partOfName;
    private String partOfContent;
    private int minSize=-1;
    private int maxSize=-1;
    private List<Path> foundFiles = new ArrayList<>();

    public List<Path> getFoundFiles() {
        return foundFiles;
    }

    public void setPartOfName(String partOfName) {
        this.partOfName = partOfName;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        byte[] content = Files.readAllBytes(file); // размер файла: content.length
        String contentString = new String(content);
        if ((partOfName == null || file.getFileName().toString().contains(partOfName)) &&
                (minSize == -1 || content.length > minSize) &&
                (maxSize == -1 || content.length < maxSize) &&
                (partOfContent == null ||
                        new String(Files.readAllBytes(file)).contains(partOfContent))) {
            foundFiles.add(file);

        }

//        if (partOfName != null && file.getFileName().toString().contains(partOfName)) foundFiles.add(file);
//
//        if (partOfContent != null && contentString.contains(partOfContent)) foundFiles.add(file);
//
//        if (maxSize > 0)
//            if (Files.size(file) > minSize && Files.size(file) < maxSize) foundFiles.add(file);
//
//        if (minSize > 0)
//            if (Files.size(file) > minSize) foundFiles.add(file);
        return super.visitFile(file, attrs);
    }
}

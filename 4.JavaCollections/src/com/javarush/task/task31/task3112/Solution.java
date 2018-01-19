package com.javarush.task.task31.task3112;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/* 
Загрузчик файлов
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        Path passwords = downloadFile("https://javarush.ru/testdata/secretPasswords.txt", Paths.get("D:/MyDownloads"));

        for (String line : Files.readAllLines(passwords, Charset.defaultCharset())) {
            System.out.println(line);
        }
    }

    public static Path downloadFile(String urlString, Path downloadDirectory) throws IOException {
        // implement this method
        URL url = new URL(urlString);
        String fileName = urlString.substring(urlString.lastIndexOf("/") + 1, urlString.lastIndexOf("."));
        String fileExt = urlString.substring(urlString.lastIndexOf(".") , urlString.length());
        InputStream inputStream = url.openStream();
        Path tempfile = Files.createTempFile(fileName, fileExt);
        Files.copy(inputStream,tempfile, StandardCopyOption.REPLACE_EXISTING);
        Path result=Paths.get(downloadDirectory.toString()+"/"+fileName+fileExt);
        Files.move(tempfile,result);
        return result;
    }
}

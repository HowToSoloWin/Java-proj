package com.company;

import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<Long, String> stringMap = new HashMap<>();
        long line = 1;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "r");
            long fileLength = randomAccessFile.length();
            long filepos = fileLength - 1;
            for (line = 1; line < 10; filepos--){
                if (filepos >= 0){
                    randomAccessFile.seek(filepos);
                    if (randomAccessFile.readByte() == 10){
                        stringMap.put(line++, randomAccessFile.readLine());
                    } else {
                        stringMap.put(line++, randomAccessFile.readLine());
                        randomAccessFile.seek(filepos + 1);
                        System.out.println((char)randomAccessFile.read());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long startPos = line;
        while (startPos != 0){
            if (stringMap.containsKey(startPos)){
                System.out.println(stringMap.get(startPos));
            }
            startPos--;
        }
    }
}

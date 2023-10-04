package com.example.blog_system.Common;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;

public class FileTool {
    @SneakyThrows
    public static  boolean SaveFile(byte[] bytes, String path)
    {
        System.out.println("...");
        File file = new File(path);
        System.out.println("...");
        //不存在就创建
        if(!file.exists())
        {
            file.createNewFile();
        }
        System.out.println("...");
        try(FileOutputStream fileOutputStream = new FileOutputStream(file))
        {
            fileOutputStream.write(bytes);
        }
        return true;
    }
}

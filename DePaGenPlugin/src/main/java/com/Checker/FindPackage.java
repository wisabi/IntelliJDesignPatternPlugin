package com.Checker;
import java.io.*;
import java.util.*;
public class FindPackage{
//https://stackoverflow.com/questions/15624226/java-search-for-files-in-a-directory
    public static String findPackage(String name /*PACKAGE NAME*/ , File file /*PROJECT ROOT*/)
    {
        File[] list = file.listFiles();
        if(list==null){return null;}
        for (File fil : list) {
            System.out.println("Checking " + fil.getName());
            if (name.equals(fil.getName())) {
                System.out.println(fil.getPath());
                return (fil.getAbsolutePath());
            }
            if (fil.isDirectory()) {
                findPackage(name,fil);
            }
        }
        return null;
    }
}
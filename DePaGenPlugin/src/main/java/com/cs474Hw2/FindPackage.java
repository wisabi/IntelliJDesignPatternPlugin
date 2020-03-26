package com.cs474Hw2;
import java.io.*;
import java.util.*;
public class FindPackage{
//https://stackoverflow.com/questions/15624226/java-search-for-files-in-a-directory
    public static void findPackage(String name /*PACKAGE NAME*/ , File file /*PROJECT ROOT*/)
    {
        File[] list = file.listFiles();
        if(list==null){return;}
            for (File fil : list)
            {
                System.out.println("Checking " + fil.getName());
                if (name.equals(fil.getName()))
                {
                    System.out.println(fil.getPath());
                    return;
                }
                if (fil.isDirectory())
                {
                    findPackage(name,fil);
                }
            }
    }
    public static void main(String[] args)
    {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the file to be searched.. " );
        String name = scan.next();
        System.out.println("Enter the directory where to search ");
        String directory = scan.next(); ////PROJECT ROOT PATH
        findPackage("resources", new File("/home/wisam/CS342/Project 2/BaccaratProject/src/"));
    }
}
package com.Checker;
import java.io.*;

public class FindPackage{
    /**
     * Method to recursivly search the src directory for the matching package name.
     * @param name
     * @param file
     * @return
     */
    public static String findPackage(String name /*PACKAGE NAME*/ , File file /*PROJECT ROOT*/)
    {
        File[] list = file.listFiles();
        if(list==null){return null;}
        for (File fil : list) {
            System.out.println("checking = " + fil.getName());
            if (name.equals(fil.getName())) {
                return (fil.getAbsolutePath());
            }
            if (fil.isDirectory()) {

                String packageRet = findPackage(name,fil);
                if(packageRet != null){
                    return packageRet;
                }
                else {
                    continue;
                }
            }
        }
        return null;
    }
}
package com.DesignPatternFactory;

import sun.awt.X11.XSystemTrayPeer;

import java.util.*;

public class NameCheck {

    static private ArrayList<String> javaReservedWords = new ArrayList<String>(Arrays.asList(
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
            "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
            "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
            "interface", "long", "native", "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
            "throw", "throws", "transient", "try", "void", "volatile", "while", "var", "null",
            "true", "false"));

    //Method to check if identifiers are valid.
    public static boolean checkIdentifier(String identifier){
        if(javaReservedWords.contains(identifier) || !(identifier.matches("^[a-zA-Z_$][a-zA-Z_$0-9]*$"))){
            //System.out.println("Identifier Invalid! Try again!");
            return false;
        }
        return true;
    }

    //FOR POSIX ONLY
    public static boolean checkFolderName(String identifier){
        if(identifier.contains("/") || identifier.equals(".") || identifier.equals("..") || identifier.equals("")){
            return false;
        }
        return true;
    }

}

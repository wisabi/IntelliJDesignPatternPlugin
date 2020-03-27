package com.Checker;
import com.DesignPatternFactory.Log;
import org.slf4j.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class to parse directory (Packages) for java files,
 * and then parse those java files for class, interfaces, and enums.
 */
public class Checker {

    public HashMap<String ,HashSet<String>> set;
    Logger logger;

    public Checker(){
        //Get logger.
        logger = Log.getLogger();
        logger.trace("Creating Checker object.");
        set = new HashMap();
    }

    /**
     * Method to parse a directory for .java files.
     * The method will call parseFile that will detect
     * java classes, enums, and interfaces within the package.
     * @param root
     */
    public void parseDirectory(File root)  {
        logger.trace("Parsing directory: {}", root.toPath());
        File[] files = root.listFiles();
        for (File file : files){
            if(file.isDirectory()){
                /*If file is a directory, then save absolute address into map key,
                and explore that directory for .java files. (Package within a package).
                */
                set.putIfAbsent(file.getAbsolutePath(), new HashSet<String>());
                parseDirectory(file);
            }
            //Only checking java files.
            else if(file.getName().contains(".java")){
                try {
                    System.out.println(file.getName() + "  " + file.getPath());
                    parseFile(file);
                } catch (Exception e) {
                    logger.trace("EXCEPTION: {}", e.toString());
                }
            }
        }
        logger.trace("Completed Parsing directory: {}", root.toPath());
    }

    /**
     * Method to detect java classes, enums, and interfaces within a package.
     * Identifiers are stored for the package the .java file is in so
     * identifiers can be looked up to ensure there are no name clashes
     * when creating new classes/interfaces/enums in the package.
     * @param file
     * @throws FileNotFoundException
     */
    public void parseFile(File file) throws FileNotFoundException {
        logger.trace("Parsing File: {}", file.getPath());
        Stack<String> stack = new Stack<>();
        char[] content = (new Scanner(file).useDelimiter("\\Z").next()).toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        //For each char in the file
        for(char c : content){
            //If char is {, push to stack
            if(c == '{'){
                stack.push("{");
                continue;
            }
            //If char is }, pop from stack
            else if(c == '}'){
                stack.pop();
                continue;
            }
            //If stack is empty, add the characters
            if(!stack.empty()){
            }
            else{
                stringBuilder.append(c);
            }
        }/*This loop results in characters outside any scope that include
           class, enum and interface identifiers that has the potential
           to name clash with other classes/enums/interfaces.
         */

        //Cleaning string more, getting substring after class, enum, interface keyords.
        boolean getNext = false;
        String string = new String(stringBuilder.toString());
        string = string.replaceAll("\\s+", " ");
        String[] stringList = string.split(" ");

        //Saving the class/enum/interface identifiers.
        for(String q : stringList){
            if(q.equals("class") || q.equals("interface") || q.equals("enum")){
                getNext=true;
                continue;
            }
            if(getNext){
                getNext=false;
                System.out.println(file.getParent());
                System.out.println(set.toString());
                /*Saving names in a mapping where the key is the absolute path.
                  Absolute path is unique for each package, and only unique
                  class/enum/interface names can be within a package*/
                set.get(file.getParent()).add(q);
            }
        }
        logger.trace("Parsing {} complete", file.getPath());
    }
}
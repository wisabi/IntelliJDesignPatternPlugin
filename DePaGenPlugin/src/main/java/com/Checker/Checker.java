package com.Checker;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
//import org.eclipse.core.commands.AbstractHandler;
//import org.eclipse.core.commands.ExecutionEvent;
//import org.eclipse.core.commands.ExecutionException;
//import org.eclipse.core.resources.IProject;
//import org.eclipse.core.resources.IWorkspace;
//import org.eclipse.core.resources.IWorkspaceRoot;
//import org.eclipse.core.resources.ResourcesPlugin;
//import org.eclipse.core.runtime.CoreException;
//import org.eclipse.jdt.core.*;
//import org.eclipse.jdt.core.dom.*;
//import org.eclipse.jdt.core.dom.AST.*;
//import org.eclipse.jdt.core.dom.ASTParser;
//import org.eclipse.jdt.core.dom.CompilationUnit;
//import org.eclipse.jdt.core.dom.MethodDeclaration;

public class Checker {

    HashMap<String ,HashSet<String>> set;
        //set["ABSOULTE PATH OF FOLDER"] = { NAMES OF CLASSES Checker, FnidPackage, Test}
    Checker(){
        set = new HashMap();
    }

    public void parseDirectory(File root)  {
        File[] files = root.listFiles();
        for (File file : files){
            if(file.isDirectory()){
                set.putIfAbsent(file.getAbsolutePath(), new HashSet<String>());
                parseDirectory(file);
            }
            else if(file.getName().contains(".java")){
                try {
                    parseFile(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void parseFile(File file) throws FileNotFoundException {
        Stack<String> stack = new Stack<>();
        String fileString = file.toString();
        int doubleSpace = 0;
        char[] content = (new Scanner(file).useDelimiter("\\Z").next()).toCharArray();
        StringBuilder o = new StringBuilder();
        for(char c : content){
            if(c == '{'){
                stack.push("{");
                continue;
            }
            else if(c == '}'){
                stack.pop();
                continue;
            }

            if(!stack.empty()){
            }
            else{
               o.append(c);
            }
        }
        boolean getNext = false;
        String string = new String(o.toString());
        string = string.replaceAll("\\s+", " ");
        String[] stringList = string.split(" ");
        for(String q : stringList){
            if(q.equals("class") || q.equals("interface") || q.equals("enum")){
                getNext=true;
                continue;
            }
            if(getNext){
                getNext=false;
                set.get(file.getParent()).add(q);
                //System.out.println(set.get(file.getParent()).toString());
            }
        }
    }


    public static void main(String[] args) throws IOException {
        Checker c = new Checker();
        File file = new File("/home/wisam/DesignPatternPluginChecker/homework3/DePaGenPlugin/src/main/java/com");
        c.parseDirectory(file);
    }

}






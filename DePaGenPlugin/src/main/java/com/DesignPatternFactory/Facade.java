package com.DesignPatternFactory;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.slf4j.Logger;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Facade design pattern.
 */
public class Facade extends DesignPattern{
    Logger logger;
    private String abstractFacadeClassName;
    private LinkedHashSet<String> forwardedClassesNames;
    private LinkedHashSet<String> facadeClassNames;
    private String code;
    private ClassOrInterfaceDeclaration abstractClass;
    private ArrayList<ClassOrInterfaceDeclaration> forwardClassList;
    private ArrayList<ClassOrInterfaceDeclaration> classList;

    /**
     * Facade constructor
     */
    public Facade(){
        //Get logging instance.
        logger = Log.getLogger();
        logger.trace("Entering Facade");

        forwardedClassesNames = new LinkedHashSet<String>();
        facadeClassNames = new LinkedHashSet<String>();
    }

    /**
     * Method to prompt user and get design pattern attributes.
     */
    @Override
    public void getPatternAttributes() {
        logger.trace("Entering Facade.getPatternAttributes()");

        System.out.print("Facade Design Pattern\n");
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        //Prompting and getting facade pattern parameters.
        abstractFacadeClassName = UserPrompt.promptUserInput("Enter a name for the Facade Abstract Class:") + "Facade";
        facadeClassNames = UserPrompt.promptUserInputs("Enter the name(s) for the concrete facade classes. Enter # when complete.");
        forwardedClassesNames = UserPrompt.promptUserInputs("Enter the name(s) for the forwarded classes. Enter # when complete.");

        logger.trace("Exiting Facade.getPatternAttributes()");
    }

    /**
     * Method to use user's inputted parameters to build the design pattern.
     */
    @Override
    public void buildPattern() {
        logger.trace("Entering Facade.buildPattern()");

        //Creating a new compilation unit.
        CompilationUnit compilationUnit = new CompilationUnit();

        //Creating an abstract class.
        abstractClass = compilationUnit.addClass(abstractFacadeClassName)
                .setPublic(true)
                .setAbstract(true);
        abstractClass.addMethod("operation")
                .setPublic(true)
                .setAbstract(true)
                .removeBody();

        //Creating lists to hold classes.
        forwardClassList = new ArrayList<ClassOrInterfaceDeclaration>();
        classList = new ArrayList<ClassOrInterfaceDeclaration>();

        //Creating the facade classes
        for(String className : facadeClassNames){
            ClassOrInterfaceDeclaration temp = compilationUnit.addClass(className).addExtendedType(abstractFacadeClassName);
            temp.addMethod("operation").setPublic(true).addAnnotation("Override").setBlockComment("User must implement");
            MethodDeclaration methodDeclaration = temp.addMethod(className);
            for(String forwardedClassName : forwardedClassesNames){
                temp.addField(forwardedClassName, forwardedClassName+"Object", Modifier.Keyword.PRIVATE);
                methodDeclaration.addParameter(forwardedClassName, forwardedClassName+"Object");

            }
            forwardClassList.add(temp);
        }

        //Creating the forwarded class and its parameters.
        for(String forwardedClassName : forwardedClassesNames){
            ClassOrInterfaceDeclaration temp = compilationUnit
                    .addClass(forwardedClassName)
                    .setPublic(true);
            classList.add(temp);
        }
        logger.trace("Exiting Facade.buildPattern()");
    }

    /**
     * Method to build and print the string of generated design pattern classes and interfaces.
     * @return
     */
    @Override
    public String printCodeString() {
        logger.trace("Entering Facade.printCodeString()");

        //Building code from the abstract class.
        code = abstractClass.toString() + "\n";

        //Building code from each forwarded class group.
        for( ClassOrInterfaceDeclaration forwardedClass : forwardClassList){
            code = code + forwardedClass.toString() + "\n";
        }

        //Building code for each class.
        for( ClassOrInterfaceDeclaration classDeclaration : classList){
            code = code + classDeclaration.toString() + "\n";
        }

        System.out.print(code);
        logger.trace("Exiting Facade.printCodeString()");
        return code;
    }

    /**
     * Method to build and generate .java files of the generated design pattern classes and interfaces.
     */
    @Override
    public String createCodeFiles() {
        logger.trace("Entering Facade.createCodeFiles()");
        //Prompt and get a folder name from the user.


        //Creating folder.
        CreateFile files = new CreateFile();
        String folderPath = files.CreateFolder(path ,folderName);

        //Creating .java files.
        files.CreateFiles(abstractClass.getNameAsString(), abstractClass.toString());
        for(ClassOrInterfaceDeclaration forwardClass : forwardClassList){
            files.CreateFiles(forwardClass.getNameAsString(), forwardClass.toString());
        }
        for(ClassOrInterfaceDeclaration classDeclaration : classList){
            files.CreateFiles(classDeclaration.getNameAsString(), classDeclaration.toString());
        }

        System.out.print("Java file creation successful. You can find the files here: \n" + folderPath + "\n");
        logger.trace("Exiting Facade.createCodeFiles()");
        return  folderPath;
    }

    /**
     * Abstract facade class name string setter.
     * @param abstractFacadeClassName
     */
    public boolean setAbstractFacadeClassName(String abstractFacadeClassName){
        logger.trace("Attempting to set abstractFacadeClassName: {}", abstractFacadeClassName);
        if(NameCheck.checkIdentifier(abstractFacadeClassName)){
            this.abstractFacadeClassName = abstractFacadeClassName;
            logger.trace("Set abstractFacadeClassName: {}", abstractFacadeClassName);
            return true;
        }
        logger.trace("Failed to set abstractFacadeClassName: {}", abstractFacadeClassName);
        return false;
    }

    /**
     * Facade class name string setter.
     * @param facadeClassNames
     */
    public void setFacadeClassNames( LinkedHashSet<String> facadeClassNames){
        this.facadeClassNames = facadeClassNames;
    }

    /**
     * Forwarded class name string setter.
     * @param forwardedClassesNames
     */
    public void setForwardedClassesNames( LinkedHashSet<String> forwardedClassesNames){
        this.forwardedClassesNames = forwardedClassesNames;
    }


    /**
     * Facade class name string setter.
     * @param facadeClassName
     */
    public boolean setFacadeClassName( String facadeClassName){
        logger.trace("Attempting to set facadeClassName: {}", facadeClassName);
        if(NameCheck.checkIdentifier(facadeClassName)) {
            this.facadeClassNames.add(facadeClassName);
            logger.trace("Set facadeClassName: {}", facadeClassName);
            return true;
        }
        logger.trace("Failed to set facadeClassName: {}", facadeClassName);
        return false;
    }

    /**
     * Forwarded class name string setter.
     * @param forwardedClassesName
     */
    public boolean setForwardedClassesNames( String forwardedClassesName){
        logger.trace("Attempting to set forwardedClassesName: {}", forwardedClassesName);
        if(NameCheck.checkIdentifier(forwardedClassesName)){
            logger.trace("Set forwardedClassesName: {}", forwardedClassesName);
            this.forwardedClassesNames.add(forwardedClassesName);
            return true;
        }
        logger.trace("Failed to set forwardedClassesName: {}", forwardedClassesName);
        return false;

    }
}
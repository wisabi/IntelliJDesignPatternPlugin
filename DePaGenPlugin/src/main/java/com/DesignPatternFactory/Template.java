package com.DesignPatternFactory;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.slf4j.Logger;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Template design pattern.
 */
public class Template extends DesignPattern {
    Logger logger;
    private String abstractClassName;
    private LinkedHashSet<String> abstractMethods;
    private LinkedHashSet<String> finalMethods;
    private LinkedHashSet<String> concreteClasses;
    private String code;
    private ClassOrInterfaceDeclaration abstractClass;
    private ArrayList<ClassOrInterfaceDeclaration> classList;

    /**
     * Template constructor
     */
    public Template(){
        //Get logging instance.
        logger = Log.getLogger();
        logger.trace("Entering Template");

        abstractMethods = new LinkedHashSet<String>();
        concreteClasses = new LinkedHashSet<String>();
        finalMethods = new LinkedHashSet<String>();
    }

    /**
     * Method to prompt user and get design pattern attributes.
     */
    @Override
    public void getPatternAttributes() {
        logger.trace("Entering Template.getPatternAttributes()");

        System.out.print("Template Design Pattern\n");
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~\n");

        //Prompting and getting template design pattern attributes.
        abstractClassName = UserPrompt.promptUserInput("Enter a name for the Abstract Class:");
        abstractMethods = UserPrompt.promptUserInputs(String.format("Enter the name(s) for Abstract methods for the %s class. Enter # when complete.", abstractClassName));
        finalMethods = UserPrompt.promptUserInputs(String.format("Enter the name(s) for the final method for the %s class. Enter # when complete.", abstractClassName));
        concreteClasses = UserPrompt.promptUserInputs(String.format("Enter the concrete classes that extend %s. Enter # when complete.", abstractClassName));

        logger.trace("Exiting Template.getPatternAttributes()");
    }

    /**
     * Method to use user's inputted parameters to build the design pattern.
     */
    @Override
    public void buildPattern() {
        logger.trace("Entering Template.buildPattern()");

        //Creating a new compilation unit.
        CompilationUnit compilationUnit = new CompilationUnit();

        //Generating the template's abstract class.
        abstractClass = compilationUnit.addClass(abstractClassName).setPublic(true).setAbstract(true);

        //Creating a list of classes to store the child classes.
        classList = new ArrayList<ClassOrInterfaceDeclaration>();

        //Adding the abstract method to the abstract class.
        for(String methodName :  abstractMethods){
            abstractClass.addMethod(methodName).setPublic(true).setAbstract(true).removeBody();
        }

        //Adding the final methods to the abstract class.
        for(String methodName :  finalMethods){
            abstractClass.addMethod(methodName).setPublic(true).setBlockComment("User must implement.");
        }

        //Creating the concrete classes and their overridden methods.
        for(String classNames : concreteClasses){
            ClassOrInterfaceDeclaration tempClass = compilationUnit.addClass(classNames).addExtendedType(abstractClassName);
            for(String methodName : abstractMethods){
                tempClass.addMethod(methodName).setPublic(true).addAnnotation("Override").setBlockComment("User must implement");
            }
            classList.add(tempClass);
        }

        logger.trace("Exiting Template.buildPattern()");
    }

    /**
     * Method to build and print the string of generated design pattern classes and interfaces.
     * @return
     */
    @Override
    public String printCodeString() {
        logger.trace("Entering Template.printCodeString()");

        //Building abstract class code.
        code = abstractClass.toString() + "\n";

        //Building code for each inherited class.
        for(ClassOrInterfaceDeclaration classDeclaration : classList){
            code = code +  classDeclaration.toString() + "\n";
        }

        System.out.println(code);
        logger.trace("Exiting Template.printCodeString()");
        return code;
    }

    /**
     * Method to build and generate .java files of the generated design pattern classes and interfaces.
     */
    @Override
    public String createCodeFiles() {
        logger.trace("Entering Template.createCodeFiles()");

        CreateFile files = new CreateFile();
        String folderPath = files.CreateFolder(path, folderName);

        //Creating .java files.
        files.CreateFiles(abstractClass.getNameAsString(), abstractClass.toString());
        for(ClassOrInterfaceDeclaration classDeclaration : classList){
            files.CreateFiles(classDeclaration.getNameAsString(), classDeclaration.toString());
        }

        System.out.print("Java file creation successful. You can find the files here: \n" + folderPath + "\n");
        logger.trace("Exiting Template.createCodeFiles()");
        return folderPath;
    }

    /**
     * Abstract method string setter.
     * @param abstractMethods
     */
    public void setAbstractMethods(LinkedHashSet<String> abstractMethods) {
        this.abstractMethods = abstractMethods;
    }

    /**
     * Concrete class s string setter.
     * @param concreteClasses
     */
    public void setConcreteClasses(LinkedHashSet<String> concreteClasses) {
        this.concreteClasses = concreteClasses;
    }

    /**
     * Final methods string setter
     * @param finalMethods
     */
    public void setFinalMethods(LinkedHashSet<String> finalMethods) {
        this.finalMethods = finalMethods;
    }

    /**
     * Abstract class string setter.
     * @param abstractClassName
     */
    public boolean setAbstractClassName(String abstractClassName) {
        logger.trace("Attempting to set abstractClassName: {}", abstractClassName);
        if(NameCheck.checkIdentifier(abstractClassName)){
            this.abstractClassName = abstractClassName;
            logger.trace("Set abstractClassName: {}", abstractClassName);
            return true;
        }
        logger.trace("Failed to set abstractClassName: {}", abstractClassName);
        return false;
    }

    /**
     * Concrete class s string setter.
     * @param concreteClass
     */
    public boolean setConcreteClasses(String concreteClass) {
        logger.trace("Attempting to set concreteClass: {}", concreteClass);
        if(NameCheck.checkIdentifier(concreteClass)) {
            this.concreteClasses.add(concreteClass);
            logger.trace("Set concreteClass: {}", concreteClass);
            return true;
        }
        logger.trace("Failed to set concreteClass: {}", concreteClass);
        return false;
    }

    /**
     * Final methods string setter
     * @param finalMethod
     */
    public boolean setFinalMethods(String finalMethod) {
        logger.trace("Attempting to set finalMethod: {}", finalMethod);
        if(NameCheck.checkIdentifier(finalMethod)) {
            this.finalMethods.add(finalMethod);
            logger.trace("Set finalMethod: {}", finalMethod);
            return true;
        }
        logger.trace("Failed to set abstractMethods: {}", finalMethod);
        return false;
    }

    /**
     * Abstract method string setter.
     * @param abstractMethods
     */
    public boolean setAbstractMethods(String abstractMethods) {
        logger.trace("Attempting to set abstractMethods: {}", abstractMethods);
        if(NameCheck.checkIdentifier(abstractMethods)) {
            this.abstractMethods.add(abstractMethods);
            logger.trace("Set abstractMethods: {}", abstractMethods);
            return true;
        }
        logger.trace("Failed to set abstractMethods: {}", abstractMethods);
        return false;
    }
}
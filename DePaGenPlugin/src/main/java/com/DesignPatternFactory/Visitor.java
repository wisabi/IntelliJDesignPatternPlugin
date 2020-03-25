package com.DesignPatternFactory;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.slf4j.Logger;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Visitor design pattern.
 */
public class Visitor extends DesignPattern {
    Logger logger;
    private String code;
    private String elementInterfaceStr;
    private String visitorInterfaceStr;
    private LinkedHashSet<String> elementMethods;
    private LinkedHashSet<String> concreteElements;
    private LinkedHashSet<String> concreteVisitors;
    private ClassOrInterfaceDeclaration elementInterface;
    private ClassOrInterfaceDeclaration visitorInterface;
    private ArrayList<ClassOrInterfaceDeclaration> classList;
    private ArrayList<ClassOrInterfaceDeclaration> visitorClassList;

    /**
     * Visitor constructor.
     */
    public Visitor(){
        //Get logging instance
        logger = Log.getLogger();
        logger.trace("Entering Visitor");

        elementMethods = new LinkedHashSet<String>();
        concreteElements = new LinkedHashSet<String>();
        concreteVisitors = new LinkedHashSet<String>();
    }

    /**
     * Method to prompt user and get design pattern attributes.
     */
    @Override
    public void getPatternAttributes() {
        logger.trace("Entering Visitor.getPatternAttributes()");

        System.out.print("Visitor Design Pattern\n");
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~\n");

        //Prompting and getting Vistor design pattern attributes.
        elementInterfaceStr = UserPrompt.promptUserInput("Enter a name for the Element interface:");
        elementMethods = UserPrompt.promptUserInputs(String.format("Enter the abstract methods for the %s interface. Enter # when complete.", elementInterfaceStr));
        concreteElements = UserPrompt.promptUserInputs(String.format("Enter the names of the concrete Elements that implement the %s interface. Enter # when complete.", elementInterfaceStr));
        visitorInterfaceStr = UserPrompt.promptUserInput("Enter the name of the Vistor interface.");
        concreteVisitors = UserPrompt.promptUserInputs(String.format("Enter the names of the concrete Vistors that implement the %s interface. Enter # when compelte.", visitorInterfaceStr));

        logger.trace("Exiting Visitor.getPatternAttributes()");
    }

    /**
     * Method to use user's inputted parameters to build the design pattern.
     */
    @Override
    public void buildPattern() {
        logger.trace("Entering Visitor.buildPattern()");

        //Creating compilation unit.
        CompilationUnit compilationUnit = new CompilationUnit();

        //Creating lists to store class groups.
        classList = new ArrayList<ClassOrInterfaceDeclaration>();
        visitorClassList = new ArrayList<ClassOrInterfaceDeclaration>();

        //Creating the element interface
        elementInterface = compilationUnit.addInterface(elementInterfaceStr);

        //Creating the visitor interface.
        visitorInterface = compilationUnit.addInterface(visitorInterfaceStr);

        //Adding element methods to the element interface.
        for(String method : elementMethods){
            elementInterface.addMethod(method).setPublic(true).setAbstract(true).removeBody();
        }

        //Creating concrete element classes with overridden classes
        for(String str : concreteElements){
            ClassOrInterfaceDeclaration tempClass = compilationUnit.addClass(str).addImplementedType(elementInterfaceStr);
            for(String method : elementMethods){
                tempClass.addMethod(method).setPublic(true).addAnnotation("Override").setBlockComment("User must implement.");
            }
            classList.add(tempClass);
            visitorInterface.addMethod("visit" + str).setPublic(true).removeBody();
        }

        //Creating concrete visitors with the overidden classes.
        for(String str : concreteVisitors) {
            ClassOrInterfaceDeclaration tempClass = compilationUnit.addClass(str).addImplementedType(visitorInterfaceStr);//addExtendedType(visitorInterfaceStr);
            for(String str2 : concreteElements){
                tempClass.addMethod(str2).setPublic(true).addAnnotation("Override").setBlockComment("User must implement.");
            }
            visitorClassList.add(tempClass);
        }
        logger.trace("Exiting Visitor.buildPattern()");
    }

    /**
     * Method to build and print the string of generated design pattern classes and interfaces.
     * @return
     */
    @Override
    public String printCodeString() {
        logger.trace("Entering Visitor.printCodeString()");

        //Building element interface code.
        code = elementInterface.toString();

        //Building code for each class declaration.
        for ( ClassOrInterfaceDeclaration classDeclaration : classList){
            code = code + classDeclaration.toString() + "\n";
        }

        //Building code for each visitor interface.
        code = code + visitorInterface.toString() + "\n";
        for ( ClassOrInterfaceDeclaration classDeclaration : visitorClassList){
            code = code + classDeclaration.toString() + "\n";
        }

        System.out.println(code);
        logger.trace("Exiting Visitor.printCodeString()");
        return code;
    }

    @Override
    public String createCodeFiles() {
        logger.trace("Entering Visitor.createCodeFiles()");


        //Creating folder.
        CreateFile files = new CreateFile();
        String folderPath = files.CreateFolder(path, folderName);

        //Creating .java files.
        files.CreateFiles(elementInterface.getNameAsString(), elementInterface.toString());
        files.CreateFiles(visitorInterface.getNameAsString(), visitorInterface.toString());
        for ( ClassOrInterfaceDeclaration classDeclaration : classList){
            files.CreateFiles(classDeclaration.getNameAsString(), classDeclaration.toString());
        }
        for ( ClassOrInterfaceDeclaration classDeclaration : visitorClassList){
            files.CreateFiles(classDeclaration.getNameAsString(), classDeclaration.toString());
        }

        System.out.print("Java file creation successful. You can find the files here: \n" + folderPath + "\n");
        logger.trace("Exiting Visitor.createCodeFiles()");

        return folderPath;
    }

    /**
     * Element interface string setter.
     * @param elementInterfaceStr
     */
    public boolean setElementInterfaceStr(String elementInterfaceStr){
        logger.trace("Attempting to set elementInterface: {}", elementInterfaceStr);
        if(NameCheck.checkIdentifier(elementInterfaceStr)) {
            this.elementInterfaceStr = elementInterfaceStr;
            logger.trace("Set elementInterface: {}", elementInterfaceStr);
            return true;
        }
        logger.trace("Failed to set elementInterface: {}", elementInterfaceStr);
        return false;
    }

    /**
     * Visiter interface string setter.
     * @param visitorInterfaceStr
     */
    public boolean setVisitorInterfaceStr(String visitorInterfaceStr) {
        logger.trace("Attempting to set visitorInterfaceStr: {}", visitorInterfaceStr);
        if(NameCheck.checkIdentifier(visitorInterfaceStr)) {
            this.visitorInterfaceStr = visitorInterfaceStr;
            logger.trace("Set visitorInterfaceStr: {}", visitorInterfaceStr);
            return true;
        }
        logger.trace("Failed to set visitorInterfaceStr: {}", visitorInterfaceStr);
        return false;
    }

    /**
     * Element methods string setter.
     * @param elementMethods
     */
    public void setElementMethods(LinkedHashSet<String> elementMethods) {
        this.elementMethods = elementMethods;
    }

    /**
     * Concrete elements string setter.
     * @param concreteElements
     */
    public void setConcreteElements(LinkedHashSet<String> concreteElements) {
        this.concreteElements = concreteElements;
    }

    /**
     * Concrete visitor string setter.
     * @param concreteVisitors
     */
    public void setConcreteVisitors(LinkedHashSet<String> concreteVisitors) {
        this.concreteVisitors = concreteVisitors;
    }

    /**
     * Concrete elements string setter.
     * @param concreteElement
     */
    public boolean setConcreteElements(String concreteElement) {
        logger.trace("Attempting to set colleagueName: {}", concreteElement);
        if(NameCheck.checkIdentifier(concreteElement)) {
            this.concreteElements.add(concreteElement);
            logger.trace("Set colleagueName: {}", concreteElement);
            return true;
        }
        logger.trace("Failed to set colleagueName: {}", concreteElement);
        return false;
    }

    /**
     * Concrete visitor string setter.
     * @param concreteVisitor
     */
    public boolean setConcreteVisitors(String concreteVisitor) {
        logger.trace("Attempting to set concreteVisitor: {}", concreteVisitor);
        if(NameCheck.checkIdentifier(concreteVisitor)) {
            this.concreteVisitors.add(concreteVisitor);
            logger.trace("Set concreteVisitor: {}", concreteVisitor);
            return true;
        }
        logger.trace("Failed to set concreteVisitor: {}", concreteVisitor);
        return false;
    }

    /**
     * Element methods string setter.
     * @param elementMethod
     */
    public boolean setElementMethods(String elementMethod) {
        logger.trace("Attempting to set elementMethod: {}", elementMethod);
        if(NameCheck.checkIdentifier(elementMethod)){
            this.elementMethods.add(elementMethod);
            logger.trace("Set elementMethod: {}", elementMethod);
            return true;
        }
        logger.trace("Failed to set elementMethod: {}", elementMethod);
        return false;
    }
}
package com.DesignPatternFactory;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.slf4j.Logger;

import javax.naming.Name;
import java.util.*;

/**
 * Builder design pattern.
 */
public class Builder extends DesignPattern {
    Logger logger;
    private String code;
    private String directorName;
    private String builderInterfaceName;
    private LinkedHashSet<String> builderInterfaceMethodNames;
    private LinkedHashSet<String> concreteBuilderNames;
    private String complexObjectName;
    private String topProductInterfaceName;
    private LinkedHashSet<String> productInterfaceMethodNames;
    private LinkedHashSet<String> productInterfaceNames;
    private ClassOrInterfaceDeclaration director;
    private ClassOrInterfaceDeclaration builderInterface;
    private ArrayList<ClassOrInterfaceDeclaration> ConcreteBuilderClasses;
    private ClassOrInterfaceDeclaration complexObject;
    private ClassOrInterfaceDeclaration topProductInterface;
    private ArrayList<ClassOrInterfaceDeclaration> productInterfaceList;
    private ArrayList<ClassOrInterfaceDeclaration> productClassList;

    /**
     * Builder constructor
     */
    public Builder() {
        //Get logger instance.
        logger = Log.getLogger();
        logger.trace("Entering Builder");

        builderInterfaceMethodNames = new LinkedHashSet<String>();
        concreteBuilderNames = new LinkedHashSet<String>();
        productInterfaceMethodNames = new LinkedHashSet<String>();
        productInterfaceNames = new LinkedHashSet<String>();
    }

    /**
     * Method to prompt user and get design pattern attributes.
     */
    @Override
    public void getPatternAttributes() {
        logger.trace("Entering Builder.getPatternAttributes()");

        System.out.print("Builder Design Pattern\n");
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~\n");

        //Prompting user and getting Builder parameters and attributes.
        directorName = UserPrompt.promptUserInput("Enter the name of the Director class:");
        builderInterfaceName = UserPrompt.promptUserInput("Enter the name of Builder interface class:");
        builderInterfaceMethodNames = UserPrompt.promptUserInputs("Enter the " + builderInterfaceName + " interface methods. Enter # when complete.");
        concreteBuilderNames = UserPrompt.promptUserInputs("Enter the name of concrete builder interface class. Enter # when complete.");
        complexObjectName = UserPrompt.promptUserInput("Enter the name of the complex object.");
        topProductInterfaceName = UserPrompt.promptUserInput("Enter the name of the top product interface name.");
        productInterfaceMethodNames = UserPrompt.promptUserInputs("Enter the names of the top product " + topProductInterfaceName + " interface methods. Enter # when complete.");
        productInterfaceNames = UserPrompt.promptUserInputs("Enter the names of the products interfaces. Enter # when complete.");
        logger.trace("Exiting Builder.getPatternAttributes()");
    }

    /**
     * Method to use user's inputted parameters to build the design pattern.
     */
    @Override
    public void buildPattern() {
        logger.trace("Entering Builder.buildPattern()");

        //Creating a new compilation unit.
        CompilationUnit compilationUnit = new CompilationUnit();

        //Creating lists to store each class/interface group.
        ConcreteBuilderClasses = new ArrayList<ClassOrInterfaceDeclaration>();
        productInterfaceList = new ArrayList<ClassOrInterfaceDeclaration>();
        productClassList = new ArrayList<ClassOrInterfaceDeclaration>();
        productInterfaceList = new ArrayList<ClassOrInterfaceDeclaration>();
        productClassList = new ArrayList<ClassOrInterfaceDeclaration>();

        //Building a director class with fields and methods.
        director = compilationUnit.addClass(directorName);
        director.addField(complexObjectName, complexObjectName + "Object")
                .setPrivate(true);
        director.addConstructor(Modifier.Keyword.PUBLIC)
                .addParameter(builderInterfaceName, builderInterfaceName.toLowerCase() + "Object")
                .setBlockComment("User must implement.");
        director.addMethod("construct")
                .setPublic(true)
                .setBlockComment("User must implement.");

        //Building the builder interface.
        builderInterface = compilationUnit.addInterface(builderInterfaceName);
        for (String methodName : builderInterfaceMethodNames) {
            builderInterface.addMethod(methodName)
                    .removeBody();
        }
        builderInterface.addMethod("getResult")
                .setType(complexObjectName)
                .removeBody();

        //Building the concrete builder classes.
        for (String concreteBuilderName : concreteBuilderNames) {
            ClassOrInterfaceDeclaration concreteBuilder = compilationUnit.addClass(concreteBuilderName);

            concreteBuilder.addImplementedType(builderInterfaceName);
            concreteBuilder.addField(complexObjectName, complexObjectName
                    .toLowerCase())
                    .setPrivate(true);
            concreteBuilder.addMethod("getResult")
                    .setType(complexObjectName)
                    .setBlockComment("User must implement.");

            for (String methodName : builderInterfaceMethodNames) {
                if (methodName.equals("getResults")) {
                    continue;
                }
                concreteBuilder.addMethod(methodName)
                        .setPublic(true)
                        .setBlockComment("User must implement.");
            }
            ConcreteBuilderClasses.add(concreteBuilder);
        }

        //Building the complex object class
        complexObject = compilationUnit.addClass(complexObjectName);
        complexObject.setBlockComment("User implement");

        //Building the top product interface class.
        topProductInterface = compilationUnit.addInterface(topProductInterfaceName);
        for (String methodName : productInterfaceMethodNames) {
            topProductInterface.addMethod(methodName)
                    .removeBody();
        }

        //Building the product interface classes.
        for (String interfaceName : productInterfaceNames) {
            ClassOrInterfaceDeclaration temp = compilationUnit.addInterface(interfaceName);
            temp.addExtendedType(topProductInterfaceName)
                    .setPublic(true);
            for (String methodName : productInterfaceMethodNames) {
                temp.addMethod(methodName)
                        .setAbstract(true)
                        .removeBody();
            }
            productInterfaceList.add(temp);
        }
        logger.trace("Exiting Builder.buildPattern()");
    }

    /**
     * Method to build and print the string of generated design pattern classes and interfaces.
     * @return
     */
    @Override
    public String printCodeString() {
        logger.trace("Entering Builder.printCodeString()");

        //Building code from each interface and class.
        code = director.toString() + "\n" + builderInterface.toString() + "\n" + complexObject.toString() + "\n";

        //Building code for each concrete builder class.
        for (ClassOrInterfaceDeclaration classObj : ConcreteBuilderClasses) {
            code = code + classObj.toString() + "\n";
        }

        //Building code for each product class.
        for (ClassOrInterfaceDeclaration classObj : productInterfaceList) {
            code = code + classObj.toString() + "\n";
        }

        for (ClassOrInterfaceDeclaration classObj : productClassList) {
            code = code + classObj.toString() + "\n";
        }

        System.out.println(code);
        logger.trace("Exiting Builder.printCodeString()");
        return code;
    }

    /**
     * Method to build and generate .java files of the generated design pattern classes and interfaces.
     */
    @Override
    public String createCodeFiles() {
        logger.trace("Entering Builder.createCodeFiles()");

        //Creating folder.
        CreateFile files = new CreateFile();
        String folderPath = files.CreateFolder(path, folderName);

        //Creating .java files.
        files.CreateFiles(director.getNameAsString(), director.toString());
        files.CreateFiles(builderInterface.getNameAsString(), builderInterface.toString());
        files.CreateFiles(complexObject.getNameAsString(), complexObject.toString());
        files.CreateFiles(topProductInterface.getNameAsString(), topProductInterface.toString());
        for (ClassOrInterfaceDeclaration classObj : ConcreteBuilderClasses) {
            files.CreateFiles(classObj.getNameAsString(), classObj.toString());
        }

        for (ClassOrInterfaceDeclaration classObj : productInterfaceList) {
            files.CreateFiles(classObj.getNameAsString(), classObj.toString());
        }

        for (ClassOrInterfaceDeclaration classObj : productClassList) {
            files.CreateFiles(classObj.getNameAsString(), classObj.toString());
        }

        System.out.print("Java file creation successful. You can find the files here: \n" + folderPath + "\n");
        logger.trace("Exiting Builder.createCodeFiles()");
        return folderPath;
    }

    /**
     * @param directorName
     * @return boolean
     */
    public boolean setDirectorName(String directorName){
        logger.trace("Attempting to set directorName: {}", directorName);
        if(NameCheck.checkIdentifier(directorName)){
            this.directorName  = directorName;
            logger.trace("Set directorName: {}", directorName);
            return true;
        }
        logger.trace("Failed to set directorName: {}", directorName);
        return false;
    }

    /**
     * @param builderInterfaceName
     * @return boolean
     */
    public boolean setBuilderInterfaceName(String builderInterfaceName){
        logger.trace("Attempting to set builderInterfaceName: {}", builderInterfaceName);
        if(NameCheck.checkIdentifier(builderInterfaceName)){
            this.builderInterfaceName  = builderInterfaceName;
            logger.trace("Set builderInterfaceName: {}", builderInterfaceName);
            return true;
        }
        logger.trace("Failed to set builderInterfaceName: {}", builderInterfaceName);
        return false;
    }


    /**
     * @param builderInterfaceMethodName
     * @return boolean
     */
    public boolean setBuilderInterfaceMethodNames(String builderInterfaceMethodName) {
        logger.trace("Attempting to set builderInterfaceMethodName: {}", builderInterfaceMethodName);
        if(NameCheck.checkIdentifier(builderInterfaceMethodName)){
            this.builderInterfaceMethodNames.add(builderInterfaceMethodName);
            logger.trace("Set builderInterfaceMethodName: {}", builderInterfaceMethodName);
            return true;
        }
        logger.trace("Failed to set builderInterfaceMethodName: {}", builderInterfaceMethodName);
        return false;
    }

    /**
     * @param concreteBuilderName
     * @return boolean
     */
    public boolean setConcreteBuilderNames(String concreteBuilderName) {
        logger.trace("Attempting to set concreteBuilderName: {}", concreteBuilderName);
        if(NameCheck.checkIdentifier(concreteBuilderName)){
            logger.trace("Set concreteBuilderName: {}", concreteBuilderName);
            this.concreteBuilderNames.add(concreteBuilderName);
            logger.trace("Failed to set concreteBuilderName: {}", concreteBuilderName);
            return true;
        }
        return false;
    }

    /**
     * @param complexObjectName
     * @return boolean
     */
    public boolean setComplexObjectName(String complexObjectName){
        logger.trace("Attempting to set complexObjectName: {}", complexObjectName);
        if(NameCheck.checkIdentifier(complexObjectName)){
            this.complexObjectName = complexObjectName;
            logger.trace("Set complexObjectName: {}", complexObjectName);
            return true;
        }
        logger.trace("Failed to set complexObjectName: {}", complexObjectName);
        return false;
    }

    /**
     * @param topProductInterfaceName
     * @return boolean
     */
    public boolean setTopProductInterfaceName(String topProductInterfaceName){
        logger.trace("Attempting to set topProductInterfaceName: {}", topProductInterfaceName);
        if(NameCheck.checkIdentifier(topProductInterfaceName)){
            this.topProductInterfaceName = topProductInterfaceName;
            logger.trace("Set topProductInterfaceName: {}", topProductInterfaceName);
            return true;
        }
        logger.trace("Failed to set topProductInterfaceName: {}", topProductInterfaceName);
        return false;
    }

    /**
     * @param productInterfaceMethodNames
     * @return boolean
     */
    public boolean setProductInterfaceMethodNames(String productInterfaceMethodNames) {
        logger.trace("Attempting to set productInterfaceMethodNames: {}", productInterfaceMethodNames);
        if(NameCheck.checkIdentifier(productInterfaceMethodNames)){
            this.productInterfaceMethodNames.add(productInterfaceMethodNames);
            logger.trace("Set productInterfaceMethodNames: {}", productInterfaceMethodNames);
            return true;
        }
        logger.trace("Failed to set productInterfaceMethodNames: {}", productInterfaceMethodNames);
        return false;
    }

    /**
     * @param productInterfaceName
     * @return boolean
     */
    public boolean setProductInterfaceNames(String productInterfaceName) {
        logger.trace("Attempting to set productInterfaceName: {}", productInterfaceName);
        if(NameCheck.checkIdentifier(productInterfaceName)){
            this.productInterfaceNames.add(productInterfaceName);
            logger.trace("Set productInterfaceName: {}", productInterfaceName);
            return true;
        }
        logger.trace("Failed to set productInterfaceName: {}", productInterfaceName);
        return false;
    }
}
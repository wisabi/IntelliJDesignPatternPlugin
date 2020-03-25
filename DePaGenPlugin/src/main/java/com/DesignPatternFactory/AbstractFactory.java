package com.DesignPatternFactory;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.slf4j.Logger;



import javax.naming.Name;
import java.util.*;

/**
 * Abstract Factory deign pattern.
 */
public class AbstractFactory extends DesignPattern {
    Logger logger;
    private String code;
    private LinkedHashSet<String> methodNames;
    private LinkedHashSet<String> classNames;
    private String interfaceName;
    private ClassOrInterfaceDeclaration theInterface;
    private ClassOrInterfaceDeclaration abstractFactoryClass;
    private ClassOrInterfaceDeclaration interfaceFactoryClass;
    private ArrayList<ClassOrInterfaceDeclaration> listOfClasses;

    /**
     * Abstract factory constructor
     */
    public AbstractFactory(){
        //Get logging instance.
        logger = Log.getLogger();
        logger.trace("Entering AbstractFactory");

        methodNames = new LinkedHashSet<String>();
        classNames = new LinkedHashSet<String>();
    }

    /**
     * Method to prompt user and get design pattern attributes.
     */
    @Override
    public void getPatternAttributes(){
        logger.trace("Entering AbstractFactory.getPatternAttributes()");

        System.out.print("Abstract Factory Design Pattern\n");
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        //Prompting user for design pattern attributes.
        interfaceName = UserPrompt.promptUserInput("Enter the name of the Abstract product:");
        methodNames = UserPrompt.promptUserInputs("Enter the names of the concrete methods. Enter # when done.");
        classNames = UserPrompt.promptUserInputs("Enter the names of the concrete products. Enter # when done.");

        logger.trace("Exiting AbstractFactory.getPatternAttributes()");
    }

    /**
     * Method to use user's inputted parameters to build the design pattern.
     */
    @Override
    public void buildPattern(){
        logger.trace("Entering AbstractFactory.buildPattern()");

        //Begin building with a a new compilation unit.
        CompilationUnit compilationUnit = new CompilationUnit();

        //Add the abstract interface to the unit.
        theInterface = compilationUnit.addInterface(interfaceName);

        //Storing a list of classes.
        listOfClasses = new ArrayList<ClassOrInterfaceDeclaration>();

        //Adding the inherited classes to the compilation unit.
        for(String className : classNames){
            listOfClasses.add(compilationUnit
                    .addClass(className)
                    .setPublic(true)
                    .addImplementedType(interfaceName));
        }

        //Adding the methods to the interfaces and the each implemented class.
        for(String methodName : methodNames){
            theInterface.addMethod(methodName, Modifier.Keyword.PUBLIC).removeBody();
            for(ClassOrInterfaceDeclaration _class : listOfClasses){
                _class.addMethod(methodName, Modifier.Keyword.PUBLIC).addAnnotation("Override").setBlockComment("Implement");
            }
        }

        //Defining the abstract factory class.
        abstractFactoryClass = compilationUnit
                .addClass(interfaceName + "AbstractFactory")
                .setPublic(true)
                .setAbstract(true);

        //Defining get method of factory class.
        abstractFactoryClass
                .addMethod("get" + interfaceName, Modifier.Keyword.PUBLIC, Modifier.Keyword.ABSTRACT)
                .setType(interfaceName)
                .removeBody();

        //Defining the concrete factory class.
        interfaceFactoryClass = compilationUnit
                .addClass(interfaceName + "Factory")
                .setPublic(true)
                .addExtendedType(interfaceName + "AbstractFactory");

        //Defining the get object method class
        interfaceFactoryClass
                .addMethod("get" + interfaceName, Modifier.Keyword.PUBLIC)
                .setType(interfaceName)
                .addAnnotation("Override");

        logger.trace("Exiting AbstractFactory.buildPattern()");
    }

    /**
     * Method to build and print the string of generated design pattern classes and interfaces.
     * @return
     */
    @Override
    public String printCodeString() {
        logger.trace("Entering AbstractFactory.printCodeString()");

        //Building code from each interface and class.
        code = theInterface.toString() + "\n" + abstractFactoryClass.toString() + "\n" + interfaceFactoryClass.toString() + "\n";

        //Building code from each class group.
        for(ClassOrInterfaceDeclaration implementedClass : listOfClasses){
            code = code + implementedClass.toString() + "\n";
        }

        System.out.print(code);
        logger.trace("Exiting AbstractFactory.printCodeString()");
        return code;
    }

    /**
     * Method to build and generate .java files of the generated design pattern classes and interfaces.
     */
    @Override
    public String createCodeFiles() {
        logger.trace("Entering AbstractFactory.createCodeFiles()");

        //Creating folder.
        CreateFile files = new CreateFile();
        String folderPath = files.CreateFolder(path, folderName);

        //Creating .java files.
        files.CreateFiles(theInterface.getNameAsString(), theInterface.toString());
        files.CreateFiles(abstractFactoryClass.getNameAsString(), abstractFactoryClass.toString());
        files.CreateFiles(interfaceFactoryClass.getNameAsString(), interfaceFactoryClass.toString());
        for(ClassOrInterfaceDeclaration implementedClass : listOfClasses){
            files.CreateFiles(implementedClass.getNameAsString(), implementedClass.toString());
        }

        System.out.print("Java file creation successful. You can find the files here: \n" + folderPath + "\n");

        logger.trace("Exiting AbstractFactory.createCodeFiles()");
        return folderPath;
    }


    /**
     * Method names string setter.
     * @param methodNames
     */
    public void setMethodNames(LinkedHashSet<String> methodNames){
        this.methodNames = methodNames;
    }

    /**
     * Class names string setter.
     * @param classNames
     */
    public void setClassNames(LinkedHashSet<String> classNames){
        this.classNames = classNames;
    }






    /**
     * Method names string setter.
     * @param methodName
     */
    public boolean setMethodNames(String methodName){
        logger.trace("Attempting to set Method name {}", methodName);
        if(NameCheck.checkIdentifier(methodName)) {
            this.methodNames.add(methodName);
            logger.trace("Set Method name: {}", methodName);
            return true;
        }
        logger.trace("Failed to set Method name: {}", methodName);
        return false;
    }

    /**
     * Class names string setter.
     * @param className
     */
    public boolean setClassNames(String className){
        logger.trace("Attempting to set className: {}", className);
        if(NameCheck.checkIdentifier(className)){
            this.classNames.add(className);
            return true;
        }
        logger.trace("Failed to set class name name: {}", className);
        return false;

    }


    /**
     * Interface name string setter.
     * @param interfaceName
     */
    public boolean setInterfaceName(String interfaceName){
        logger.trace("Attempting to set interfaceName: {}", interfaceName);
        if(NameCheck.checkIdentifier(interfaceName)){
            this.interfaceName = interfaceName;
            logger.trace("Set interfaceName: {}", interfaceName);
            return true;
        }
        logger.trace("Failed to set interfaceName: {}", interfaceName);
        return false;
    }
}
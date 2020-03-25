package com.DesignPatternFactory;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.slf4j.Logger;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Factory design pattern.
 */
public class Factory extends DesignPattern {
    Logger logger;
    private String interfaceName;
    private LinkedHashSet<String> abstractMethods;
    private LinkedHashSet<String> concreteClassNames;
    private ArrayList<ClassOrInterfaceDeclaration> classList;
    private ClassOrInterfaceDeclaration interfaceUnit;
    private ClassOrInterfaceDeclaration factoryUnit;
    private String code;

    /**
     * Factory constructor
     */
    public Factory(){
        //Get logging instance.
        logger = Log.getLogger();
        logger.trace("Entering Factory");

        abstractMethods = new LinkedHashSet<String>();
        concreteClassNames = new LinkedHashSet<String>();;
    }

    /**
     * Method to prompt user and get design pattern attributes.
     */
    @Override
    public void getPatternAttributes() {
        logger.trace("Entering Factory.getPatternAttributes()");

        System.out.print("Factory Design Pattern\n");
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~\n");

        //Prompting and getting factory design parameters.
        interfaceName = UserPrompt.promptUserInput("Enter the name of the Factory:");
        abstractMethods = UserPrompt.promptUserInputs("Enter the names of the abstract methods. Enter # when complete.");
        concreteClassNames = UserPrompt.promptUserInputs("Enter the names of the concrete classes. Enter # when complete.");

        logger.trace("Exiting Factory.getPatternAttributes()");
    }

    /**
     * Method to use user's inputted parameters to build the design pattern.
     */
    @Override
    public void buildPattern() {
        logger.trace("Entering Factory.buildPattern()");

        //Creating a new compilation unit.
        CompilationUnit compilationUnit = new CompilationUnit();

        //Creating list to hold classes.
        classList = new ArrayList<ClassOrInterfaceDeclaration>();

        //Creating interface for concrete classes.
        interfaceUnit = compilationUnit.addInterface(interfaceName);

        //Creating concrete classes.
        for(String className : concreteClassNames){
            ClassOrInterfaceDeclaration temp = compilationUnit
                                                    .addClass(className)
                                                    .addImplementedType(interfaceName)
                                                    .setPublic(true);
            classList.add(temp);
        }

        //Adding abstract methods to concrete methods and the interface.
        for(String methodName : abstractMethods){
            interfaceUnit
                    .addMethod(methodName)
                    .setAbstract(true)
                    .removeBody()
                    .setPublic(true);
            for(ClassOrInterfaceDeclaration concreteClass : classList){
                concreteClass
                        .addMethod(methodName)
                        .setPublic(true)
                        .addAnnotation("Override")
                        .setBlockComment("User must implement.");

            }
        }

        //Creating the factory class.
        factoryUnit = compilationUnit.addClass(interfaceName+"Factory");
                factoryUnit.addMethod("get"+interfaceName)
                        .setPublic(true)
                        .setType(interfaceName)
                        .addParameter("String", interfaceName + "Type")
                        .setBlockComment("User must implement.");
        logger.trace("Exiting Factory.buildPattern()");
    }


    /**
     * Method to build and print the string of generated design pattern classes and interfaces.
     * @return
     */
    @Override
    public String printCodeString() {
        logger.trace("Entering Factory.printCodeString()");

        //Building code from each interface and class.
        code = interfaceUnit.toString() + "\n" + factoryUnit.toString() + "\n";

        //Building code for each concrete class.
        for(ClassOrInterfaceDeclaration concreteClass : classList){
            code = code + concreteClass + "\n";
        }

        System.out.println(code);
        logger.trace("Exiting Factory.printCodeString()");
        return code;
    }

    /**
     * Method to build and generate .java files of the generated design pattern classes and interfaces.
     */
    @Override
    public String createCodeFiles() {
        logger.trace("Entering Factory.createCodeFiles()");

        //Creating folder.
        CreateFile files = new CreateFile();
        String folderPath = files.CreateFolder(path ,folderName);

        //Creating .java files.
        files.CreateFiles(interfaceUnit.getNameAsString(), interfaceUnit.toString());
        files.CreateFiles(factoryUnit.getNameAsString(), factoryUnit.toString());
        for(ClassOrInterfaceDeclaration concreteClass : classList){
            files.CreateFiles(concreteClass.getNameAsString(), concreteClass.toString());
        }

        System.out.print("Java file creation successful. You can find the files here: \n" + folderPath + "\n");
        logger.trace("Exiting Factory.createCodeFiles()");

        return folderPath;
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

    /**
     * Abstract methods string setter.
     * @param abstractMethods
     */
    public void setAbstractMethods(LinkedHashSet<String> abstractMethods){
        this.abstractMethods = abstractMethods;
    }

    /**
     * Concrete class names string setter.
     * @param concreteClassNames
     */
    public void setConcreteClassNames(LinkedHashSet<String> concreteClassNames){
        this.concreteClassNames = concreteClassNames;
    }


    /**
     * Abstract methods string setter.
     * @param abstractMethod
     */
    public boolean setAbstractMethods(String abstractMethod){
        logger.trace("Attempting to set abstractMethod: {}", abstractMethod);
        if(NameCheck.checkIdentifier(abstractMethod)){
            this.abstractMethods.add(abstractMethod);
            logger.trace("Set abstractMethod: {}", abstractMethod);
            return true;
        }
        logger.trace("Failed to set abstractMethod: {}", abstractMethod);
        return false;
    }

    /**
     * Concrete class names string setter.
     * @param concreteClassName
     */
    public boolean setConcreteClassNames(String concreteClassName){
        logger.trace("Attempting to set concreteClassName: {}", concreteClassName);
        if(NameCheck.checkIdentifier(concreteClassName)) {
            this.concreteClassNames.add(concreteClassName);
            logger.trace("Set concreteClassName: {}", concreteClassName);
            return true;
        }
        logger.trace("Failed to set concreteClassName: {}", concreteClassName);
        return false;
    }
}
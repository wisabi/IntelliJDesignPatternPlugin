package com.DesignPatternFactory;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.slf4j.Logger;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Chain of Command design pattern.
 */
public class Chain extends DesignPattern {
    Logger logger;
    private String code;
    private String abstractHandlerClassName;
    private LinkedHashSet<String> receiverClassNames;
    private String finalReceiverClassName;
    private ClassOrInterfaceDeclaration abstractHandler;
    private ArrayList<ClassOrInterfaceDeclaration> receiverList;
    private ClassOrInterfaceDeclaration  finalReceiver;

    /**
     * Chain of command constructor
     */
   public Chain(){
        //Get logging instance.
        logger = Log.getLogger();
        logger.trace("Entering Chain");

        receiverClassNames = new LinkedHashSet<String>();
    }

    /**
     * Method to prompt user and get design pattern attributes.
     */
    @Override
    protected void getPatternAttributes() {
        logger.trace("Entering Chain.getPatternAttributes()");

        System.out.print("Builder Chain of Command Design Pattern\n");
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~\n");

        //Get pattern attributes from user.
        abstractHandlerClassName = UserPrompt.promptUserInput("Enter a name for the abstract Handler class:");
        finalReceiverClassName = UserPrompt.promptUserInput("Enter the Receiver class at the end of the chain of command.");
        receiverClassNames = UserPrompt.promptUserInputs("Enter the rest of the names of the Receiver classes. Enter # when complete.");

        logger.trace("Exiting Chain.getPatternAttributes()");
    }

    /**
     * Method to use user's inputted parameters to build the design pattern.
     */
    @Override
    public void buildPattern() {
        logger.trace("Entering Chain.buildPattern()");
        //Creating a new compilation unit.
        CompilationUnit compilationUnit = new CompilationUnit();

        //Creating list to store receiver classes.
        receiverList = new ArrayList<ClassOrInterfaceDeclaration>();

        //Creating the final receiver class.
        finalReceiver = compilationUnit.addClass(finalReceiverClassName);

        //Adding attributes, methods to the receiver classes.
        for(String receiverClassName : receiverClassNames){
            ClassOrInterfaceDeclaration temp = compilationUnit.addClass(receiverClassName)
                    .setPublic(true)
                    .addExtendedType(abstractHandlerClassName);
            temp.addConstructor(Modifier.Keyword.PUBLIC)
                    .addParameter(abstractHandlerClassName, abstractHandlerClassName.toLowerCase() + "Object")
                    .setBlockComment("User must implement");
            receiverList.add(temp);
        }

        for(ClassOrInterfaceDeclaration receiver : receiverList){
            receiver.addMethod("handleRequest")
                    .addAnnotation("override")
                    .setPublic(true)
                    .setBlockComment("User must implement");
        }

        //Creating an abstract handler class
        abstractHandler = compilationUnit.addClass(abstractHandlerClassName)
                .setPublic(true)
                .setAbstract(true);

        abstractHandler.addField(abstractHandlerClassName, "successor")
                .setPrivate(true);

        abstractHandler.addConstructor(Modifier.Keyword.PUBLIC)
                .addParameter(abstractHandlerClassName, "successor")
                .setBlockComment("User must implement");

        abstractHandler.addMethod("handleRequest")
                .setPublic(true)
                .setBlockComment("User must implement");

        abstractHandler.addMethod("canHandle Request")
                .setPublic(true)
                .setType("boolean")
                .setBlockComment("User must implement");


        //Creating the final receiver class.
        finalReceiver.addMethod("handleRequest")
                .addAnnotation("override")
                .setPublic(true)
                .setBlockComment("User must implement");

        logger.trace("Exiting Chain.buildPattern()");
    }

    /**
     * Method to build and print the string of generated design pattern classes and interfaces.
     * @return
     */
    @Override
    public String printCodeString() {
        logger.trace("Entering Chain.printCodeString()");

        //Building code from each interface and class.
        code = abstractHandler.toString() + "\n" + finalReceiver.toString() + "\n";

        //Building code for each receiver class.
        for(ClassOrInterfaceDeclaration receiverClass : receiverList){
            code = code + receiverClass.toString() + "\n";
        }

        System.out.println(code);
        logger.trace("Exiting Chain.printCodeString()");
        return code;
    }

    /**
     * Method to build and generate .java files of the generated design pattern classes and interfaces.
     */
    @Override
    public String createCodeFiles() {
        logger.trace("Entering Chain.createCodeFiles()");

        //Creating folder.
        CreateFile files = new CreateFile();
        String folderPath = files.CreateFolder(path ,folderName);;

        //Creating .java files.
        files.CreateFiles(abstractHandler.getNameAsString(), abstractHandler.toString());
        files.CreateFiles(finalReceiver.getNameAsString(), finalReceiver.toString());
        for(ClassOrInterfaceDeclaration receiverClass : receiverList){
            files.CreateFiles(receiverClass.getNameAsString(), receiverClass.toString());
        }

        System.out.print("Java file creation successful. You can find the files here: \n" + folderPath + "\n");

        logger.trace("Exiting Chain.createCodeFiles()");
        return folderPath;
    }

    /**
     * Abstract hadnler class name string setter.
     * @param abstractHandlerClassName
     */
    public boolean setAbstractHandlerClassName(String abstractHandlerClassName){
        logger.trace("Attempting to set abstractHandlerClassName: {}", abstractHandlerClassName);
        if(NameCheck.checkIdentifier(abstractHandlerClassName)) {
            logger.trace("Set abstractHandlerClassName: {}", abstractHandlerClassName);
            this.abstractHandlerClassName = abstractHandlerClassName;
            return true;
        }
        logger.trace("Failed to set abstractHandlerClassName: {}", abstractHandlerClassName);
        return false;
    }

    /**
     * Final receiver class name string setter.
     * @param finalReceiverClassName
     */
    public boolean setFinalReceiverClassName(String finalReceiverClassName){
        logger.trace("Attempting to set finalReceiverClassName: {}", finalReceiverClassName);
        if(NameCheck.checkIdentifier(finalReceiverClassName)){
            logger.trace("Set finalReceiverClassName: {}", finalReceiverClassName);
            this.finalReceiverClassName = finalReceiverClassName;
            return true;
        }
        logger.trace("Failed to set receiverClassName: {}", finalReceiverClassName);
        return false;

    }

    /**
     * Receiver class name string setter.
     * @param receiverClassNames
     */
    public void setReceiverClassNames(LinkedHashSet<String> receiverClassNames){
        this.receiverClassNames = receiverClassNames;
    }

    /**
     * Receiver class name string setter.
     * @param receiverClassName
     */
    public boolean setReceiverClassNames(String receiverClassName){
        logger.trace("Attempting to set receiverClassName: {}", receiverClassName);
        if(NameCheck.checkIdentifier(receiverClassName)) {
            logger.trace("Set receiverClassName: {}", receiverClassName);
            this.receiverClassNames.add(receiverClassName);
            return true;
        }
        logger.trace("Failed to set receiverClassName: {}", receiverClassName);
        return false;
    }
}
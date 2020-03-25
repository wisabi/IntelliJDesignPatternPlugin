package com.DesignPatternFactory;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.slf4j.Logger;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Mediator design pattern.
 */
public class Mediator extends DesignPattern{
    Logger logger;
    private String code;
    private String mediatorAbstractClassName;
    private LinkedHashSet<String> mediatorNames;
    private LinkedHashSet<String> colleagueNames;
    private ClassOrInterfaceDeclaration mediatorAbstractClass;
    private ArrayList<ClassOrInterfaceDeclaration> classList;
    private ClassOrInterfaceDeclaration abstractColleague;
    private ArrayList<ClassOrInterfaceDeclaration> colleagueList;

    /**
     * Mediator Constructor.
     */
    public Mediator(){
        //Get logging instance.
        logger = Log.getLogger();
        logger.trace("Entering Mediator");

         mediatorNames = new LinkedHashSet<String>();
         colleagueNames = new LinkedHashSet<String>();
    }

    /**
     * Method to prompt user and get design pattern attributes.
     */
    @Override
    public void getPatternAttributes() {
        logger.trace("Entering Mediator.getPatternAttributes()");

        System.out.print("Mediator Design Pattern\n");
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~\n");

        //Prompting and getting Mediator design pattern attributes.
        mediatorAbstractClassName = UserPrompt.promptUserInput("Enter the name of the abstract Mediator class:");
        mediatorNames = UserPrompt.promptUserInputs("Enter the names of the Mediator classes. Enter # when compete.");
        colleagueNames = UserPrompt.promptUserInputs("Enter the names of the Colleague classes. Enter # when compete.");

        logger.trace("Exiting Mediator.getPatternAttributes()");
    }

    /**
     * Method to use user's inputted parameters to build the design pattern.
     */
    @Override
    public void buildPattern() {
        logger.trace("Entering Mediator.buildPattern()");


        //Creating a new compilation unit object.
        CompilationUnit compilationUnit = new CompilationUnit();

        //Creating lists to store class groups.
        classList = new ArrayList<ClassOrInterfaceDeclaration>();
        colleagueList = new ArrayList<ClassOrInterfaceDeclaration>();

        //Creating the mediator abstract class.
        mediatorAbstractClass = compilationUnit.addClass(mediatorAbstractClassName)
                .setPublic(true)
                .setAbstract(true);
        mediatorAbstractClass.addMethod("mediate")
                .setPublic(true)
                .setAbstract(true)
                .removeBody()
                .addParameter("Colleague", "colleague");

        //Creating concret mediator classes
        for(String mediatorName: mediatorNames){
            ClassOrInterfaceDeclaration temp = compilationUnit.addClass(mediatorName)
                    .setPublic(true)
                    .addExtendedType(mediatorAbstractClassName);

            MethodDeclaration setColleaguesMethod = temp.addMethod("setColleagues");

            //Adding the colleague fields.
            for( String colleagueName : colleagueNames){
                temp.addField(colleagueName, colleagueName+"Object", Modifier.Keyword.PRIVATE);
                setColleaguesMethod.addParameter(colleagueName, colleagueName+"Object");
                setColleaguesMethod.setBlockComment("User must implement");
            }

            //Adding the mediate method.
            temp.addMethod("mediate").setPublic(true)
                    .addParameter("Colleague", "colleague")
                    .setBlockComment("User must implement");
            classList.add(temp);
        }

        //Creating the abstract colleague class
        abstractColleague = compilationUnit.addClass("Colleague")
                .setPublic(true)
                .setAbstract(true);
        abstractColleague.addField(mediatorAbstractClassName, "mediator");
        abstractColleague.addMethod("Colleague")
                .setPublic(true)
                .addParameter(mediatorAbstractClassName,mediatorAbstractClassName.toLowerCase())
                .setBlockComment("User must implement");

        //Building each colleague class.
        for(String colleagueName : colleagueNames){
            ClassOrInterfaceDeclaration temp = compilationUnit.addClass(colleagueName)
                    .addExtendedType("Colleague");
            temp.addField("String", "state")
                    .setPrivate(true);
            temp.addConstructor(Modifier.Keyword.PUBLIC)
                    .addParameter(mediatorAbstractClassName, "mediator")
                    .setBlockComment("User must implement");
            temp.addMethod("getState")
                    .setPublic(true)
                    .setBlockComment("User must implement.");
            temp.addMethod("setState")
                    .setPublic(true)
                    .setBlockComment("User must implement.");

            colleagueList.add(temp);
        }
        logger.trace("Exiting Mediator.buildPattern()");
    }

    /**
     * Method to build and print the string of generated design pattern classes and interfaces.
     * @return
     */
    @Override
    public String printCodeString() {
        logger.trace("Entering Mediator.printCodeString()");

        //Building code from each interface and class.
        code = abstractColleague.toString() + "\n" + abstractColleague.toString() + "\n";

        //Building code for each mediator class.
        for(ClassOrInterfaceDeclaration classObject : classList){
            code = code + classObject + "\n";
        }

        //Building code for each colleague class.
        for(ClassOrInterfaceDeclaration classObject : colleagueList) {
            code = code + classObject + "\n";
        }

        System.out.println(code);
        logger.trace("Exiting Mediator.printCodeString()");
        return code;
    }

    /**
     * Method to build and generate .java files of the generated design pattern classes and interfaces.
     */
    @Override
    public String createCodeFiles() {
        logger.trace("Entering Mediator.createCodeFiles()");
        //Prompt and get a folder name from the user.

        //Creating folder.
        CreateFile files = new CreateFile();
        String folderPath = files.CreateFolder(path ,folderName);


        files.CreateFiles(mediatorAbstractClass.getNameAsString(), mediatorAbstractClass.toString());
        files.CreateFiles(abstractColleague.getNameAsString(), abstractColleague.toString());
        for(ClassOrInterfaceDeclaration classObj : classList) {
            files.CreateFiles(classObj.getNameAsString(), classObj.toString());
        }
        for(ClassOrInterfaceDeclaration classObj : colleagueList) {
            files.CreateFiles(classObj.getNameAsString(), classObj.toString());
        }

        System.out.print("Java file creation successful. You can find the files here: \n" + folderPath + "\n");

        logger.trace("Exiting Mediator.createCodeFiles()");
        return folderPath;
    }

    /**
     * Mediator abstract class string setter
     * @param mediatorAbstractClassName
     */
    public boolean setMediatorAbstractClassName(String mediatorAbstractClassName){
        logger.trace("Attempting to set mediatorAbstractClassName: {}", mediatorAbstractClassName);
        if(NameCheck.checkIdentifier(mediatorAbstractClassName)){
            this.mediatorAbstractClassName = mediatorAbstractClassName;
            logger.trace("Set mediatorAbstractClassName: {}", mediatorAbstractClassName);
            return true;
        }
        logger.trace("Failed to set mediatorAbstractClassName: {}", mediatorAbstractClassName);
        return false;
    }

    /**
     * Mediator names string setter
     * @param mediatorNames
     */
    public void setMediatorNames(LinkedHashSet<String> mediatorNames){
        this.mediatorNames = mediatorNames;
    }

    /**
     * Colleague Names string setter
     * @param colleagueNames
     */
    public void setColleagueNames(LinkedHashSet<String> colleagueNames){
        this.colleagueNames = colleagueNames;
    }


    /**
     * Mediator names string setter
     * @param mediatorName
     */
    public boolean setMediatorNames(String mediatorName){
        logger.trace("Attempted to set mediatorName: {}", mediatorName);
        if(NameCheck.checkIdentifier(mediatorName)){
            this.mediatorNames.add(mediatorName);
            logger.trace("Set mediatorName: {}", mediatorName);
            return true;
        }
        logger.trace("Failed to set mediatorName: {}", mediatorName);
        return false;
    }

    /**
     * Colleague Names string setter
     * @param colleagueName
     */
    public boolean setColleagueNames(String colleagueName){
        logger.trace("Attempting to set colleagueName: {}", colleagueName);
        if(NameCheck.checkIdentifier(colleagueName)) {
            this.colleagueNames.add(colleagueName);
            logger.trace("Set colleagueName: {}", colleagueName);
            return true;
        }
        logger.trace("Failed to set colleagueName: {}", colleagueName);
        return false;
    }
}
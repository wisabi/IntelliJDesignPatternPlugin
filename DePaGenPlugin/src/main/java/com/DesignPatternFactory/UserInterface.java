package com.DesignPatternFactory;

import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class UserInterface {

    //Design pattern list string.
    private final static String designPatternList =
             "Select your desired Design Pattern:\n" +
             "1 - Abstract Factory\n" +
             "2 - Builder\n" +
             "3 - Factory\n" +
             "4 - Facade\n" +
             "5 - Chain of Responsibility\n" +
             "6 - Mediator\n" +
             "7 - Visitor \n" +
             "8 - Template\n"+
             "0 - Exit application\n";

    //Welcome message string.
    private final static  String welcomeMessage = "Welcome to Design Pattern Generator!\n" +
                                                   "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
    //Instructions string.
    private final static String instructions = "Instructions: You will be prompted to enter the\n" +
            "corresponding # of the Design Pattern you wish to implement.\n" +
            "You will then be prompted whether to output the generated code\n" +
            "to Java files or the standard out.";

    //Enter message string.
    private final static String continueMessage = "Press Enter to start a new Design Generation!";

    //Design pattern instruction string.
    private final static String designPatternInstruction = "Enter the corresponding # for which Design Pattern you wish to implement.";

    //File/stdout prompt string.
    private final static String fileOrStdOut = "Should design patterns be generated to a file(s) or printed to the console?\n" +
                                               "Enter 0 for file(s) or Enter 1 for the console.";

    /**
     * prompt method to output instruction and prompting messages to user. Gathers user input, and calls the design
     * pattern factory.
     */
    public static void prompt(){
        //getting logger instance.
        Logger logger = Log.getLogger();
        logger.trace("Entering UserInterface.prompt().");

        //Printing welcome message an instructions to user.
        System.out.println(welcomeMessage);
        System.out.println(instructions);

        //Instantiate the design pattern factory.
        PatternProducer factory = new PatternProducer();


        /*
        Loop to get user wanted design pattern, and output options(file or stdout).
        Loops so user can do multiple design pattern generation in a single application execution.
        */
        while(true) {
            System.out.println(continueMessage);

            try {
                new Scanner(System.in).nextLine();
            } catch (Exception e) {
                logger.error("Exception occurred: {} ", e.toString());
            }
            //Prints design pattern list.
            System.out.print(designPatternList);
            logger.trace("Prompting user for which design pattern");

            //Prompt and get design pattern user wants.
            int designPatternVal = UserPrompt.promptUserDesignPattern(designPatternInstruction);
            logger.trace("User chose design pattern {}", designPatternVal);

            //If user enters 0, then exit application.
            if(designPatternVal == 0){
                break;
            }

            logger.trace("Prompting user for output method");
            //Prompt and get user output option.
            boolean outputOption = UserPrompt.promptUserForOutputOption(fileOrStdOut);
            logger.trace("User chose output val {}", outputOption);

            logger.trace("Calling PatternProducer().getPattern(). Pattern: {}, output: {}", designPatternVal, outputOption);
            //Call pattern generation factory to build and generate the design pattern.
            factory.getPattern(designPatternVal, outputOption);
        }
        logger.trace("Returning from UserInterface.prompt().");
    }
 }
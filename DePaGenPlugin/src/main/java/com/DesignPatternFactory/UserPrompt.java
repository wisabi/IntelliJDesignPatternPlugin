package com.DesignPatternFactory;

import org.slf4j.Logger;

import java.util.LinkedHashSet;
import java.util.Scanner;

public class UserPrompt{


    public static String promptUserInput(String promptMessage){
        Logger logger = Log.getLogger();
        logger.trace("Entering UserPrompt.promptUserInput");
        String str = "_";
        Scanner reader = new Scanner(System.in);

        logger.trace("Prompting user: {}", promptMessage);
        System.out.println(promptMessage);

        try {
            str = reader.next();
            logger.trace("User entered: {}", str);
        }
        catch (Exception e){
            logger.error("Exception occurred: {}", e.toString());
        }

        while(!NameCheck.checkIdentifier(str)){
            logger.trace("User entered incorrect identifier name.");
            System.out.println(promptMessage);
            try {
                str = reader.next();
                logger.trace("User entered: {}", str);
            }
            catch (Exception e){
                logger.error("Exception occurred: {}",  e.toString());
            }
        }

        logger.trace("promptUserInput returning {}", str);
        return str;
    }

    public static String promptFolderNameInput(String promptMessage) {
        Logger logger = Log.getLogger();
        logger.trace("Entering UserPrompt.promptUserInput");
        String str = "_";
        Scanner reader = new Scanner(System.in);

        logger.trace("Prompting user: {}", promptMessage);
        System.out.println(promptMessage);

        try {
            str = reader.next();
            logger.trace("User entered: {}", str);
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.toString());
        }

        while (!NameCheck.checkIdentifier(str)) {
            logger.trace("User entered incorrect identifier name.");
            System.out.println(promptMessage);
            try {
                str = reader.next();
                logger.trace("User entered: {}", str);
            } catch (Exception e) {
                logger.error("Exception occurred: {}", e.toString());
            }
        }
        logger.trace("promptUserInput returning {}", str);
        return str;
    }

    public static LinkedHashSet<String> promptUserInputs(String promptMessage) {
        Logger logger = Log.getLogger();
        logger.trace("Entering UserPrompt.promptUserInputs()");
        String str;
        LinkedHashSet<String> strArray = new LinkedHashSet<String>();
        Scanner reader = new Scanner(System.in);  // Reading from System.in

        System.out.println(promptMessage);
        logger.trace("Prompting user: {}", promptMessage);

        try {
            while (!((str = reader.next()).equals("#"))) {

                if (NameCheck.checkIdentifier(str)) {
                    strArray.add(str);
                }
                else{
                    logger.trace("User entered incorrect identifier name.");
                }
            }
        }
        catch (Exception e){
            logger.error("Exception occurred: {}",  e.toString());
        }

        logger.trace("Returning from UserPrompt.promptUserInputs()");
        return strArray;
    }

    public static boolean promptUserForOutputOption(String promptMessage){
        Logger logger = Log.getLogger();
        logger.trace("Entering UserPrompt.promptUserForOutputOption()");
        Scanner reader = new Scanner(System.in);  // Reading from System.in

        int val = -1;
        System.out.println(promptMessage);
        logger.trace("Prompting user: {}", promptMessage);
        while(true){
            try{
                val = reader.nextInt();
                if(val == 0 || val == 1){
                    logger.trace("User entered {}", val);
                    logger.trace("Returning from UserPrompt.promptUserForOutputOption()");
                    return (val == 1);
                }
                logger.trace("User entered invalid option: {}", val);
                System.out.println("Invalid, Enter 0 for creating files or 1 for output to console.");

            }
            catch(Exception e){
                System.out.println("Invalid, Enter 0 for creating files or 1 for output to console.");
                logger.error("Exception occurred: {} ", e.toString());
                reader.next();
            }
        }

    }

    public static int promptUserDesignPattern(String promptMessage){
        Logger logger = Log.getLogger();
        logger.trace("Entering UserPrompt.promptUserForOutputOption");
        Scanner reader = new Scanner(System.in);  // Reading from System.in

        int val = -1;
        System.out.println(promptMessage);
        logger.trace("Prompting user: {}", promptMessage);
        while(true){
            try{
                val = reader.nextInt();
                if(val >= 0 && val <= 8){
                    logger.trace("User chose design pattern {}", val);
                    logger.trace("Returning from UserPrompt.promptUserForOutputOption");
                    return val;
                }
                logger.trace("User entered invalid design pattern value.");
                System.out.println("Invalid, Enter a value 0 to 9.");

            }
            catch(Exception e){
                logger.trace("Exception: {}", e.toString());
                System.out.println("Invalid, Enter a value 1 to 9.");
                reader.next();
            }
        }
    }
}
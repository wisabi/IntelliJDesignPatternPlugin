package com.DesignPatternFactory;

import org.slf4j.Logger;
import java.io.File;
import java.io.FileWriter;

public class CreateFile {

    static String folderPath;
    static Logger logger;

    public CreateFile(){
        logger = Log.getLogger();
    }

    public String CreateFolder(String folderName){
        logger.trace("Entering CreateFile.CreateFolder()");

        String currentDir = System.getProperty("user.dir");
        folderPath = currentDir + "/" + folderName;

        try{
            logger.trace("Attempting to created folder {}", folderPath);
            new File(folderPath).mkdir();
            logger.trace("created folder {}", folderPath);
        }
        catch (Exception e){
            logger.error("Failed to created folder {}", folderPath);
            logger.error("Exception occurred: {}", e.toString());
            System.out.println("Error creating a Folder. Please check user permissions. Exiting application.");
            logger.trace("Exiting due to folder creation failure");
            System.exit(1);
        }
        return folderPath;
    }


    public void CreateFiles(String fileName, String fileContents) {

        FileWriter fileWriter;
        String filePath = folderPath + "/" + fileName + ".java";
        File file = new File(filePath);

        try {
            logger.trace("Attempting to create file {}", filePath);
            file.createNewFile();
            logger.trace("created file {}", filePath);
        }
        catch (Exception e){
            logger.error("Failed to created file: {}", filePath);
            logger.error("Exception occurred: {}", e.toString());
            System.out.println("Error creating a file. Please check user permissions. Exiting application.");
            logger.trace("Exiting due to file creation failure");
            System.exit(1);
        }

        try {
            logger.trace("Attempting to write to file {}", filePath);
            fileWriter = new FileWriter(filePath);
            fileWriter.write(fileContents);
            fileWriter.close();
            logger.trace("Wrote to file {}", filePath);
        }
        catch(Exception e){
            logger.error("Failed to write to file: {}", filePath);
            logger.error("Exception occurred: {}", e.toString());
            System.out.println("Error writing to file. Please check user permissions. Exiting application.");
            logger.trace("Exiting due to file write failure");
            System.exit(1);
        }
    }

    public String CreateFolder(String currentDirectory ,String folderName){
        logger.trace("Entering CreateFile.CreateFolder()");

        //folderPath = currentDirectory + "/" + folderName;
        folderPath = currentDirectory + "/" + folderName;

        try{
            logger.trace("Attempting to created folder {}", folderPath);
            new File(folderPath).mkdir();
            logger.trace("created folder {}", folderPath);
        }
        catch (Exception e){
            logger.error("Failed to created folder {}", folderPath);
            logger.error("Exception occurred: {}", e.toString());
            System.out.println("Error creating a Folder. Please check user permissions. Exiting application.");
            logger.trace("Exiting due to folder creation failure");
            System.exit(1);
        }
        System.out.println("FOLDER PATH" + folderPath);
        return folderPath;
    }


}
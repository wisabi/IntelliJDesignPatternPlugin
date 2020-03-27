package com.DesignPatternFactory;

import org.slf4j.Logger;
import sun.awt.X11.XSystemTrayPeer;

public abstract class DesignPattern {

    protected abstract void buildPattern();
    protected abstract void getPatternAttributes();
    protected abstract String printCodeString();
    protected abstract String createCodeFiles();
    public String path;
    String folderName;
    final public void setPath(String path){
        this.path = path;
    }

    final public boolean setFolderName(String folderName){
        if(NameCheck.checkFolderName(folderName)){
            this.folderName = folderName;
            return true;
        }
        return false;
    }

    final DesignPattern executePatternGeneration(boolean stdout){
        Logger logger = Log.getLogger();
        logger.trace("Entering executePatternGeneration");
        if(stdout) {
            logger.trace("executePatternGeneration for stdout");
            getPatternAttributes();
            buildPattern();
            printCodeString();
        }
        else{
            logger.trace("executePatternGeneration for file creation");
            getPatternAttributes();
            buildPattern();
            createCodeFiles();
        }
        logger.trace("Exiting executePatternGeneration");
        return this;
    }

    final DesignPattern executePatternGeneration(){
        Logger logger = Log.getLogger();
        logger.trace("Entering executePatternGeneration");

        logger.trace("executePatternGeneration for file creation");
        getPatternAttributes();
        buildPattern();
        createCodeFiles();

        logger.trace("Exiting executePatternGeneration");
        return this;
    }
}

package com.cs474Hw2;
import com.DesignPatternFactory.*;
import org.slf4j.Logger;

/**
 * Factory for design pattern GUI pages.
 */
public class DesignPageFactory {
    /**
     * Method to select the design page GUI creator.
     * @param pattern
     */
    void getPage(String pattern){
        //Get logger instance
        Logger logger = Log.getLogger();
        logger.trace("Entering DesignFactory getPage with input: {}", pattern);

        //Selection control to choose the design pattern page to create.
        switch (pattern) {
            case "Abstract Factory":
                logger.trace("AbstractFactoryPage called.");
                new AbstractFactoryPage();
                return;
            case "Builder":
                logger.trace("BuilderPage called.");
                new BuilderPage();
                return;
            case "Factory":
                logger.trace("FactoryPage called.");
                new FactoryPage();
                return;
            case "Facade":
                logger.trace("FacadePage called.");
                new FacadePage();
                return;
            case "Chain of Responsibility":
                logger.trace("ChainPage called.");
                new ChainPage();
                return;
            case "Mediator":
                logger.trace("MediatorPage called.");
                new MediatorPage();
                return;
            case "Visitor":
                logger.trace("VisitorPage called.");
                new VisitorPage();
                return;
            case "Template":
                logger.trace("TemplatePage called.");
                new TemplatePage();
                return;
        }
        logger.error("Design pattern page factory is null");
    }
}
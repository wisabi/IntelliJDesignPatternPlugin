package com.DesignPatternFactory;

import org.slf4j.Logger;

/**
 * Design pattern factory class
 */
public class PatternProducer{
    /**
     * Calls on the design pattern subclass to create its object.
     * @param pattern The design pattern is to be created.
     * @param output The means of outputting the generated generated java code.
     * @return
     */
    DesignPattern  getPattern(int pattern, boolean output) {
        //Gets logger instance.
        Logger logger = Log.getLogger();
        logger.trace("Entering PatternProducer.getPattern(). pattern: {}, output: {}", pattern, output);

        //Selection control to choose the design pattern to generate.
        if (pattern == 1) {
            logger.trace("Calling to create Abstract Factory.");
            return new AbstractFactory().executePatternGeneration(output);
        }
        else if(pattern == 2){
            logger.trace("Calling to create Builder.");
            return new Builder().executePatternGeneration(output);
        }
        else if(pattern == 3){
            logger.trace("Calling to create Factory.");
            return new Factory().executePatternGeneration(output);
        }
        else if(pattern == 4) {
            logger.trace("Calling to create Facade.");
            return new Facade().executePatternGeneration(output);
        }
        else if(pattern == 5){
            logger.trace("Calling to create Chain.");
            return new Chain().executePatternGeneration(output);
        }
        else if(pattern == 6){
            logger.trace("Calling to create Mediator.");
            return new Mediator().executePatternGeneration(output);
        }
        else if(pattern == 7){
            logger.trace("Calling to create Visitor.");
            return new Visitor().executePatternGeneration(output);
        }
        else if(pattern == 8){
            logger.trace("Calling to create Template.");
            return new Template().executePatternGeneration(output);
        }
        return null;
    }

        //HAVE IT RETURN NEW DESIGNPATTERN().
        //CALL GETTERS FROM PLUGIN.
        //CALL EXECUTE PATTERN GEN FROM PLUGIN.
/////////////////////////////////////////////////////////////////////////////////
}
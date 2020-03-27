package com.cs474Hw2;

import com.Checker.Checker;
import com.DesignPatternFactory.DesignPattern;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashSet;

/**
 * Template for each Design pattern page method.
 */
public abstract class DesignPageTemplate {

    /**
     * Method each design pattern GUI must implement.
     */
    public abstract void eventHandlerSetup();


    final boolean checkClashes(boolean checkClashes, Checker checker, String packagePath, File packageFile, JFrame frame, String name, JTextField field, HashSet<String> identifiers) {
        if(checkClashes) {
            System.out.println("1 -> "+packageFile.getPath());
            checker.parseDirectory(packageFile.getParentFile());
            System.out.println(checker.set.toString());
            System.out.println(packagePath);
            if (checker.set.get(packagePath).contains(name)) {
                JOptionPane.showMessageDialog(WelcomePage.frame, "1ERROR: NAME CLASH!!!", "", JOptionPane.ERROR_MESSAGE);
                field.setText("");
                //logger.trace("interfaceField name clash");
                return false;
            }
            checker.set.get(packagePath).add(name);
        }
        else if(identifiers.contains(name)){
            JOptionPane.showMessageDialog(WelcomePage.frame, "2ERROR: NAME CLASH!!!", "", JOptionPane.ERROR_MESSAGE);
            field.setText("");
            //logger.trace("interfaceField name clash");
            return false;
        }
        return true;
    }
}

package com.cs474Hw2;
import com.Checker.Checker;
import javax.swing.*;
import java.io.File;
import java.util.HashSet;
import com.DesignPatternFactory.Log;
import org.slf4j.Logger;
/**
 * Template for each Design pattern page method.
 */
public abstract class DesignPageTemplate {

    /**
     * Method each design pattern GUI must implement.
     */
    public abstract void eventHandlerSetup();

    /**
     * Method to check name clashes within a package.
     * @param checkClashes
     * @param checker
     * @param packagePath
     * @param packageFile
     * @param frame
     * @param name
     * @param field
     * @param identifiers
     * @return
     */
    final boolean checkClashes(boolean checkClashes, Checker checker, String packagePath, File packageFile, JFrame frame, String name, JTextField field, HashSet<String> identifiers) {
        Logger logger = Log.getLogger();
        //logger.trace("checkClashes for package: {}", packageFile.getPath());
        if(checkClashes) {
            checker.parseDirectory(packageFile.getParentFile());
            if (checker.set.get(packagePath).contains(name)) {
                JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Name Clash!", "", JOptionPane.ERROR_MESSAGE);
                field.setText("");
                logger.trace("Name Clash found: {}", name);
                return false;
            }
            logger.trace("Name Clash not found: {}", name);
            checker.set.get(packagePath).add(name);
        }
        else if(identifiers.contains(name)){
            JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Name Clash!", "", JOptionPane.ERROR_MESSAGE);
            field.setText("");
            logger.trace("Name Clash found: {}", name);
            return false;
        }
        logger.trace("Name Clash not found: {}", name);
        return true;
    }
}

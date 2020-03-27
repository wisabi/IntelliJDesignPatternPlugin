package com.cs474Hw2;
import com.Checker.Checker;
import com.Checker.FindPackage;
import com.DesignPatternFactory.AbstractFactory;
import com.DesignPatternFactory.Log;
import org.slf4j.Logger;
import com.intellij.ui.content.ContentFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashSet;

/**
 * Class for Abstract Factory GUI.
 */
public class AbstractFactoryPage extends  DesignPageTemplate{

    JTextField interfaceField, classField, methodField, packageField;
    ActionListener interfaceAction, classAction, methodAction, buildAction, packageAction;
    AbstractFactory abstractFactoryBuilder;
    boolean enteredClass, enteredInterface, enteredMethods, enteredPackage;
    JButton buildButton;
    JPanel panel;
    Logger logger;
    Checker checker;
    String packagePath;
    boolean checkClashes;
    File packageFile;
    HashSet<String> identifiers;
    HashSet<String> methodIdentifiers;

    /**
     * Constructor and GUI maker.
     */
    AbstractFactoryPage(){
        //Get logger.
        logger = Log.getLogger();
        logger.trace("Entering AbstractFactoryPage.");

        checker = new Checker();
        identifiers = new HashSet<>();
        methodIdentifiers = new HashSet<>();

        //Get Abstract Factory code generator.
        logger.trace("Calling AbstractFactory generator.");
        abstractFactoryBuilder = new AbstractFactory();

        //Set the path of the user's project root.
        logger.trace("Setting user project path.");
        abstractFactoryBuilder.setPath(WelcomePage.path);

        //Set up the GUI event handler.
        logger.trace("Setting up Abstract Factory event handler.");
        eventHandlerSetup();

        //Creating panel with Y-axis box layout.
        logger.trace("Setting up AbstractFactoryPage GUI components.");
        panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        //Creating title
        JLabel textField00 = new JLabel
                ("<html>" +
                        "<font size = 10 color='black'><b>&nbsp; Abstract Factory </b></font>" +
                        "</html>"
                );
        textField00.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0


        //Configuring interface field.
        JLabel textField01 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Interface name:</b></font>" +
                        "</html>"
                );
        textField01.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        interfaceField = new JTextField("");
        interfaceField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        interfaceField.setMaximumSize(new Dimension(250, 40));
        interfaceField.addActionListener(interfaceAction);
        interfaceField.setEnabled(false);

        //Configuring class field.
        JLabel textField02 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Class name(s):</b></font>" +
                        "</html>"
                );
        textField02.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        classField = new JTextField("");
        classField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        classField.setMaximumSize(new Dimension(250, 40));
        classField.addActionListener(classAction);
        classField.setEnabled(false);

        //Configuring method field.
        JLabel textField03 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Method name(s):</b></font>" +
                        "</html>"
                );
        textField03.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        methodField = new JTextField("");
        methodField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        methodField.setMaximumSize(new Dimension(250, 40));
        methodField.addActionListener(methodAction);
        methodField.setEnabled(false);

        //Configuring package field.
        JLabel textField04 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Package name:</b></font>" +
                        "</html>"
                );
        textField04.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        packageField = new JTextField("");
        packageField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        packageField.setMaximumSize(new Dimension(250, 40));
        packageField.addActionListener(packageAction);

        //Configuring build button.
        buildButton = new JButton("Generate Code");
        buildButton.setMaximumSize(new Dimension(250, 40));
        buildButton.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        buildButton.addActionListener(buildAction);
        buildButton.setEnabled(false);

        //Adding components to panel.
        panel.add(textField00);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField04);
        panel.add(packageField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField01);
        panel.add(interfaceField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField02);
        panel.add(classField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField03);
        panel.add(methodField);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(buildButton);
        panel.setVisible(true);
        logger.trace("AbstractFactoryPage GUI panel created with components.");

        //Setting the created panel GUI as the tool window.
        logger.trace("Setting AbstractFactoryPage panel as the tool window.");
        TheToolWindow.content = TheToolWindow.contentFactory.createContent(panel, "", false);
        TheToolWindow.getToolWindow.getContentManager().removeAllContents(true);
        TheToolWindow.getToolWindow.getContentManager().addContent(TheToolWindow.content);
    }

    /**
     * Event handler lister.
     */
    @Override
    public void eventHandlerSetup() {
        logger.trace("Setting AbstractFactoryPage event handler.");
        /**
         * Interface field listener
         */
        interfaceAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String interfaceName = interfaceField.getText();
                logger.trace("interfaceField entered: {}", interfaceName);

                logger.trace("Checking name clashes.");
                if(!checkClashes(checkClashes, checker, packagePath, packageFile, WelcomePage.frame, interfaceName, interfaceField,  identifiers)){
                    //Name clashes found
                    logger.trace("Name clashes found.");
                    return;
                }

                //Check if identifier is valid
                if(!abstractFactoryBuilder.setInterfaceName(interfaceName)){
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    interfaceField.setText("");
                    logger.trace("interfaceField is invalid");
                    return;
                }

                //Identifier valid and locking field/
                identifiers.add(interfaceName);
                logger.trace("interfaceField is valid");
                enteredInterface = true;
                interfaceField.setEnabled(false);
                if(enteredMethods && enteredPackage && enteredClass){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated");
                }
            }
        };
        /**
         * Class field listener.
         */
        classAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String className = classField.getText();
                logger.trace("classField entered: {}", className);

                //Check clashes.
                if(!checkClashes(checkClashes, checker, packagePath, packageFile, WelcomePage.frame, className, classField,  identifiers)){
                    logger.trace("Name clashes found.");
                    return;
                }

                //Check if identifier is valid
                if(!abstractFactoryBuilder.setClassNames(className)){
                    logger.trace("classField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    classField.setText("");
                    return;
                }

                //Identifier valid and clearing field.
                identifiers.add(className);
                classField.setText("");
                enteredClass = true;
                logger.trace("classField is valid.");
                if(enteredMethods && enteredPackage && enteredInterface){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };
        /**
         * Method field listener.
         */
        methodAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String methodName = methodField.getText();
                logger.trace("methodField is entered: {}", methodName);

                //Check local name clashes.
                logger.trace("Checking method name clashes.");
                if(methodIdentifiers.contains(methodName)){
                    logger.trace("methodField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Name Clash!", "",  JOptionPane.ERROR_MESSAGE);
                    methodField.setText("");
                    return;
                }

                //Check if identifier is valid
                if(!abstractFactoryBuilder.setMethodNames(methodName)){
                    logger.trace("methodField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    methodField.setText("");
                    return;
                }

                //Identifier valid and clearing field.
                methodField.setText("");
                methodIdentifiers.add(methodName);
                enteredMethods = true;
                logger.trace("methodField is valid.");
                if(enteredPackage && enteredClass && enteredInterface){
                    //Activate build button
                    logger.trace("buildButton is activated.");
                    buildButton.setEnabled(true);
                }
            }
        };

        /**
         * Build button listener.
         */
        buildAction = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Calling builder code generator.
                logger.trace("Build button is clicked.");
                logger.trace("buildPattern is called.");
                abstractFactoryBuilder.buildPattern();
                String folderPath = abstractFactoryBuilder.createCodeFiles();

                //Notifying user of package creation.
                logger.trace("Code files created at: {}", folderPath);
                JOptionPane.showMessageDialog(WelcomePage.frame,"Package saved at:\n" + folderPath,"Package Created", 1);

                //Setting the GUI back to the welcome page.
                logger.trace("Setting the welcome page as the toolwindow.", folderPath);
                TheToolWindow.contentFactory = ContentFactory.SERVICE.getInstance();
                TheToolWindow.content = TheToolWindow.contentFactory.createContent(WelcomePage.panel, "", false);
                TheToolWindow.getToolWindow.getContentManager().removeAllContents(true);
                TheToolWindow.getToolWindow.getContentManager().addContent(TheToolWindow.content);
            }
        };

        //Package field listener.
        packageAction = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String packageName = packageField.getText();
                logger.trace("packageField is entered: {}", packageName);

                //Getting package path
                logger.trace("Looking if package exists.");
                packagePath = FindPackage.findPackage(packageName, new File(WelcomePage.path));

                //Check if package already exists. Parse package if exists.
                logger.trace("Checking if package exists in project.");
                if(packagePath != null){
                    logger.trace("Checking if package exists in project.");
                    packageFile = new File(packagePath);
                    abstractFactoryBuilder.setPath(packageFile.getParentFile().getAbsolutePath());
                    abstractFactoryBuilder.setFolderName(packageName);
                    checkClashes = true;
                }
                //Check if identifier is valid
                else if(!abstractFactoryBuilder.setFolderName(packageName)){
                    logger.trace("packageField is invalid");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    packageField.setText("");
                    return;
                }
                //Set checking clashes as false.
                else{
                    checkClashes = false;
                }

                //Identifier valid and locking field/
                packageField.setEnabled(false);
                enteredPackage = true;
                interfaceField.setEnabled(true);
                classField.setEnabled(true);
                methodField.setEnabled(true);
                logger.trace("packageField is valid");
                if(enteredMethods && enteredClass && enteredInterface){
                    //Activate build button
                    logger.trace("buildButton is activated.");
                    buildButton.setEnabled(true);
                }
            }
        };
    }
}
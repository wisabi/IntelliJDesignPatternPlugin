package com.cs474Hw2;
import com.Checker.Checker;
import com.Checker.FindPackage;
import com.DesignPatternFactory.Facade;
import com.DesignPatternFactory.Log;
import com.intellij.ui.content.ContentFactory;
import org.slf4j.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;

public class FacadePage extends DesignPageTemplate {
    JTextField abstractFacadeField, facadeClassField, forwardedClassesField, packageField;
    ActionListener abstractFacadeAction, facadeClassAction, forwardedClassesAction, buildAction, packageAction;
    Facade facadeBuilder;
    boolean enteredAbstractFacadeField, enteredFacadeClassField, enteredForwardedField, enteredPackage;
    JButton buildButton;
    JPanel panel;
    Logger logger;
    Checker checker;
    String packagePath;
    boolean checkClashes;
    File packageFile;
    HashSet<String> identifiers;

    public FacadePage() {
        //Getting logger instance.
        logger = Log.getLogger();
        logger.trace("Entering FacadePage.");

        checker = new Checker();
        identifiers = new HashSet<>();

        //Calling facade generator constructor.
        logger.trace("Calling Facade generator.");
        facadeBuilder = new Facade();

        //Set the path as the user's project root.
        logger.trace("Setting user project path.");
        facadeBuilder.setPath(WelcomePage.path);

        //Setting up the event handler.
        logger.trace("Setting up FacadePage event handler.");
        eventHandlerSetup();

        //Setting up JPanel with Y-Axis  box layout.
        panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        //Configuring page title.
        logger.trace("Setting up FacadePage GUI components.");
        JLabel textField00 = new JLabel
                ("<html>" +
                        "<font size = 10 color='black'><b>&nbsp; Facade </b></font>" +
                        "</html>"
                );
        textField00.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Configuring abstract facade field
        JLabel textField01 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Facade Abstract Class name:</b></font>" +
                        "</html>"
                );
        textField01.setAlignmentX( Component.CENTER_ALIGNMENT);
        abstractFacadeField = new JTextField("");
        abstractFacadeField.setAlignmentX( Component.CENTER_ALIGNMENT);
        abstractFacadeField.setMaximumSize(new Dimension(250, 40));
        abstractFacadeField.addActionListener(abstractFacadeAction);
        abstractFacadeField.setEnabled(false);
        //Configuring facade class field.
        JLabel textField02 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Concrete Facade class name(s):</b></font>" +
                        "</html>"
                );
        textField02.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        facadeClassField = new JTextField("");
        facadeClassField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        facadeClassField.setMaximumSize(new Dimension(250, 40));
        facadeClassField.addActionListener(facadeClassAction);
        facadeClassField.setEnabled(false);
        //Configuring forwarded class field.
        JLabel textField03 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Forwarded Class name(s):</b></font>" +
                        "</html>"
                );
        textField03.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        forwardedClassesField = new JTextField("");
        forwardedClassesField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        forwardedClassesField.setMaximumSize(new Dimension(250, 40));
        forwardedClassesField.addActionListener(forwardedClassesAction);
        forwardedClassesField.setEnabled(false);

        //Configuring package field.
        JLabel textField04 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Package name:</b></font>" +
                        "</html>"
                );
        textField04.setAlignmentX( Component.CENTER_ALIGNMENT);
        packageField = new JTextField("");
        packageField.setAlignmentX( Component.CENTER_ALIGNMENT);
        packageField.setMaximumSize(new Dimension(250, 40));
        packageField.addActionListener(packageAction);

        //Configuring build button
        buildButton = new JButton("Generate Code");
        buildButton.setMaximumSize(new Dimension(250, 40));
        buildButton.setAlignmentX( Component.CENTER_ALIGNMENT);
        buildButton.addActionListener(buildAction);
        buildButton.setEnabled(false);

        //Adding components to panel.
        panel.add(textField00);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField04);
        panel.add(packageField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField01);
        panel.add(abstractFacadeField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField02);
        panel.add(facadeClassField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField03);
        panel.add(forwardedClassesField);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(buildButton);
        panel.setVisible(true);
        logger.trace("FacadePage GUI panel created with components.");

        //Setting the created panel GUI as the tool window.
        logger.trace("Setting FacadePage panel as the tool window.");
        TheToolWindow.content = TheToolWindow.contentFactory.createContent(panel, "", false);
        TheToolWindow.getToolWindow.getContentManager().removeAllContents(true);
        TheToolWindow.getToolWindow.getContentManager().addContent(TheToolWindow.content);
    }

    /**
     * Event handler lister.
     */
    @Override
    public void eventHandlerSetup() {
        logger.trace("Setting up FacadePage event handler.");
        /**
         * abstract field listener
         */
        abstractFacadeAction = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String abstractFacade = abstractFacadeField.getText();
                logger.trace("abstractFacadeField entered: {}", abstractFacade);

                //Checking for name clashes
                logger.trace("Checking name clashes.");
                if(!checkClashes(checkClashes, checker, packagePath, packageFile, WelcomePage.frame, abstractFacade, abstractFacadeField,  identifiers)){
                    //Clash found
                    logger.trace("Name clashes found.");
                    return;
                }


                //Checking identifier validity.
                if(!facadeBuilder.setAbstractFacadeClassName(abstractFacade)){
                    logger.trace("abstractFacadeField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    abstractFacadeField.setText("");
                    return;
                }

                logger.trace("abstractFacadeField is valid.");
                identifiers.add(abstractFacade);
                //Locking field
                enteredAbstractFacadeField = true;
                abstractFacadeField.setEnabled(false);
                if(enteredAbstractFacadeField && enteredFacadeClassField && enteredForwardedField && enteredPackage){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated");
                }
            }
        };

        /**
         * facade class listener
         */
        facadeClassAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String facadeClassName = facadeClassField.getText();
                logger.trace("facadeClassField entered: {}", facadeClassName);

                //Checking for name clashes
                logger.trace("Checking name clashes.");
                if(!checkClashes(checkClashes, checker, packagePath, packageFile, WelcomePage.frame, facadeClassName, facadeClassField,  identifiers)){
                    //Clash found
                    logger.trace("Name clashes found.");
                    return;
                }

                //Checking identifier validity.
                if(!facadeBuilder.setFacadeClassName(facadeClassName)){
                    logger.trace("facadeClassField is invalid");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    facadeClassField.setText("");
                    return;
                }

                //Clearing field
                facadeClassField.setText("");
                identifiers.add(facadeClassName);
                enteredFacadeClassField = true;
                if(enteredAbstractFacadeField && enteredForwardedField && enteredPackage){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated");
                }
            }
        };
        /**
         * forwarded class listener.
         */
        forwardedClassesAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String forwardedClasses = forwardedClassesField.getText();
                logger.trace("forwardedClassesField entered: {}", forwardedClasses);

                //Checking for name clashes
                logger.trace("Checking name clashes.");
                if(!checkClashes(checkClashes, checker, packagePath, packageFile, WelcomePage.frame, forwardedClasses, forwardedClassesField,  identifiers)){
                    //Clash found
                    logger.trace("Name clashes found.");
                    return;
                }

                //Checking identifier validity.
                if(!facadeBuilder.setForwardedClassesNames(forwardedClasses)){
                    logger.trace("forwardedClassesField is invalid");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    forwardedClassesField.setText("");
                    return;
                }

                logger.trace("forwardedClassesField is valid.");
                identifiers.add(forwardedClasses);
                //Clearing field

                forwardedClassesField.setText("");
                enteredForwardedField = true;
                if(enteredAbstractFacadeField && enteredFacadeClassField && enteredPackage){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated");
                }
            }
        };

        /**
         * build button listener.
         */
        buildAction = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Calling chain Builder code generator.
                logger.trace("Build button is clicked.");
                facadeBuilder.buildPattern();
                String folderPath = facadeBuilder.createCodeFiles();
                JOptionPane.showMessageDialog(WelcomePage.frame,"Package saved at:\n" + folderPath,"Package Created", 1);
                logger.trace("Code files created at: {}", folderPath);
                //Setting the GUI back to the welcome page.
                logger.trace("Setting the welcome page as the toolwindow.");
                TheToolWindow.contentFactory = ContentFactory.SERVICE.getInstance();
                TheToolWindow.content = TheToolWindow.contentFactory.createContent(WelcomePage.panel, "", false);
                TheToolWindow.getToolWindow.getContentManager().removeAllContents(true);
                TheToolWindow.getToolWindow.getContentManager().addContent(TheToolWindow.content);
            }
        };

        /**
         * Package field listener.
         */
        packageAction = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String packageName = packageField.getText();
                logger.trace("packageField entered: {}", packageName);
                logger.trace("Looking if package exists.");
                packagePath = FindPackage.findPackage(packageName, new File(WelcomePage.path));

                //Check if package already exists. Parse package if exists.
                logger.trace("Checking if package exists in project.");
                if(packagePath != null){
                    logger.trace("Checking if package exists in project.");
                    packageFile = new File(packagePath);
                    facadeBuilder.setPath(packageFile.getParentFile().getAbsolutePath());
                    facadeBuilder.setFolderName(packageName);
                    checkClashes = true;
                }
                //Check if identifier is valid
                else if(!facadeBuilder.setFolderName(packageName)){
                    logger.trace("packageField is invalid");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    packageField.setText("");
                    return;
                }
                //Set checking clashes as false.
                else{
                    checkClashes = false;
                }

                logger.trace("packageField is valid");
                //locking field
                packageField.setEnabled(false);
                forwardedClassesField.setEnabled(true);
                facadeClassField.setEnabled(true);
                abstractFacadeField.setEnabled(true);
                enteredPackage = true;
                if(enteredAbstractFacadeField && enteredFacadeClassField && enteredForwardedField){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated");
                }
            }
        };
    }
}
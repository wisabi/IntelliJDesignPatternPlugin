package com.cs474Hw2;
import com.DesignPatternFactory.Log;
import com.DesignPatternFactory.Template;
import com.intellij.ui.content.ContentFactory;
import org.slf4j.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemplatePage extends DesignPageTemplate {

    JTextField     abstractClassField, abstractMethodField, finalMethodField, packageField, concreteClassField;
    ActionListener abstractClassAction, abstractMethodAction, finalMethodAction, packageAction, buildAction, concreteClassAction;
    boolean enteredAbstractClass, enteredAbstractMethod, enteredFinalMethod , enteredConcreteClass, enteredPackage;
    Template templateBuilder;
    JButton buildButton;
    JPanel panel;
    Logger logger;


    TemplatePage(){
        //Get logger.
        logger = Log.getLogger();
        logger.trace("Entering TemplatePage.");

        //Get Template code generator.
        logger.trace("Calling Template generator.");
        templateBuilder = new Template();

        //Set the path of the user's project root.
        logger.trace("Setting user project path.");
        templateBuilder.setPath(WelcomePage.path);

        //Set up the GUI event handler.
        logger.trace("Setting up TemplatePage event handler.");
        eventHandlerSetup();

        //Creating panel with Y-axis box layout.
        panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        //Configuring title
        logger.trace("Setting up TemplatePage GUI components.");
        JLabel textField00 = new JLabel
                ("<html>" +
                        "<font size = 10 color='black'><b>&nbsp; Template </b></font>" +
                        "</html>"
                );
        textField00.setAlignmentX( Component.CENTER_ALIGNMENT);

        //Configuring abstract class field
        JLabel textField01 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Abstract Class name:</b></font>" +
                        "</html>"
                );
        textField01.setAlignmentX( Component.CENTER_ALIGNMENT);
        abstractClassField = new JTextField("");
        abstractClassField.setAlignmentX( Component.CENTER_ALIGNMENT);
        abstractClassField.setMaximumSize(new Dimension(250, 40));
        abstractClassField.addActionListener(abstractClassAction);
        abstractClassField.setEnabled(false);

        //Configuring abstract method field
        JLabel textField02 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Abstract Method name(s):</b></font>" +
                        "</html>"
                );
        textField02.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        abstractMethodField = new JTextField("");
        abstractMethodField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        abstractMethodField.setMaximumSize(new Dimension(250, 40));
        abstractMethodField.addActionListener(abstractMethodAction);
        abstractMethodField.setEnabled(false);

        //Configuring final method field
        JLabel textField03 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Final Method name(s):</b></font>" +
                        "</html>"
                );
        textField03.setAlignmentX( Component.CENTER_ALIGNMENT);
        finalMethodField = new JTextField("");
        finalMethodField.setAlignmentX( Component.CENTER_ALIGNMENT);
        finalMethodField.setMaximumSize(new Dimension(250, 40));
        finalMethodField.addActionListener(finalMethodAction);
        finalMethodField.setEnabled(false);

        //Configuring concrete class field
        JLabel textField05 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Concrete class name(s):</b></font>" +
                        "</html>"
                );
        textField05.setAlignmentX( Component.CENTER_ALIGNMENT);
        concreteClassField = new JTextField("");
        concreteClassField.setAlignmentX( Component.CENTER_ALIGNMENT);
        concreteClassField.setMaximumSize(new Dimension(250, 40));
        concreteClassField.addActionListener(concreteClassAction);
        concreteClassField.setEnabled(false);

        //Configuring package field
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
        panel.add(abstractClassField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField02);
        panel.add(abstractMethodField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField03);
        panel.add(finalMethodField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField05);
        panel.add(concreteClassField);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(buildButton);
        panel.setVisible(true);
        logger.trace("TemplatePage GUI panel created with components.");

        //Setting the created panel GUI as the tool window.
        logger.trace("Setting TemplatePage panel as the tool window.");
        TheToolWindow.content = TheToolWindow.contentFactory.createContent(panel, "", false);
        TheToolWindow.getToolWindow.getContentManager().removeAllContents(true);
        TheToolWindow.getToolWindow.getContentManager().addContent(TheToolWindow.content);
    }

    /**
     * Event handler lister.
     * */
    @Override
    public void eventHandlerSetup() {
        logger.trace("Setting up TemplatePage event handler.");

        /**
         * Abstract class listener
         */
        abstractClassAction = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String abstractClassName = abstractClassField.getText();
                logger.trace("abstractClassField entered: {}", abstractClassName);
                //Check if identifier is valid.
                if(!templateBuilder.setAbstractClassName(abstractClassName)){
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    abstractClassField.setText("");
                    logger.trace("abstractClassField is invalid.");
                    return;
                }
                //Identifier valid and locking field
                enteredAbstractClass = true;
                abstractClassField.setEnabled(false);
                logger.trace("abstractClassField is valid.");
                if(enteredAbstractMethod && enteredFinalMethod && enteredConcreteClass && enteredPackage){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };
        /**
         * Abstract method listener
         */
        abstractMethodAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String abstractMethod = abstractMethodField.getText();
                logger.trace("abstractMethodField entered: {}", abstractMethod);
                //Check if identifier is valid.
                if(!templateBuilder.setAbstractMethods(abstractMethod)){
                    logger.trace("abstractMethodField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    abstractMethodField.setText("");
                    return;
                }
                logger.trace("abstractMethodField is valid.");
                //Identifier valid and clearing field
                abstractMethodField.setText("");
                enteredAbstractMethod = true;
                if(enteredAbstractClass && enteredFinalMethod && enteredConcreteClass && enteredPackage){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };
        /**
         * Final method listener
         */
        finalMethodAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String finalMethod = finalMethodField.getText();
                logger.trace("finalMethodField entered: {}", finalMethod);
                //Check if identifier is valid.
                if(!templateBuilder.setFinalMethods(finalMethod)){
                    logger.trace("finalMethodField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    finalMethodField.setText("");
                    return;
                }
                logger.trace("finalMethodField is invalid.");
                //Identifier valid and clearing field
                finalMethodField.setText("");
                enteredFinalMethod = true;
                if(enteredAbstractClass && enteredAbstractMethod && enteredConcreteClass && enteredPackage){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };
        /**
         * Concrete class listener
         */
        concreteClassAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String concreteClass = concreteClassField.getText();
                logger.trace("concreteClassField entered: {}", concreteClass);
                //Check if identifier is valid.
                if(!templateBuilder.setConcreteClasses(concreteClass)){
                    logger.trace("concreteClassField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    finalMethodField.setText("");
                    return;
                }
                logger.trace("concreteClassField is valid.");
                //Identifier valid and clearing field
                concreteClassField.setText("");
                enteredConcreteClass = true;
                if(enteredAbstractClass && enteredAbstractMethod && enteredFinalMethod && enteredPackage){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };

        /**
         * Build button listener
         */
        buildAction = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Notifying user of package creation.
                logger.trace("Build button is clicked.");
                templateBuilder.buildPattern();
                String folderPath = templateBuilder.createCodeFiles();
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
         * Package field listener
         */
        packageAction = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String packageName = packageField.getText();
                logger.trace("packageField entered: {}", packageName);
                //Check if identifier is valid.
                if(!templateBuilder.setFolderName(packageName)){
                    logger.trace("packageField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    packageField.setText("");
                    return;
                }
                //Identifier valid and locking field/
                logger.trace("packageField is valid.");
                packageField.setEnabled(false);
                enteredPackage = true;
                concreteClassField.setEnabled(true);
                finalMethodField.setEnabled(true);
                abstractMethodField.setEnabled(true);
                abstractClassField.setEnabled(true);
                if(enteredAbstractClass && enteredAbstractMethod && enteredFinalMethod && enteredConcreteClass) {
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };
    }
}
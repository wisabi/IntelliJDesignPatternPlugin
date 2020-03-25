package com.cs474Hw2;
import com.DesignPatternFactory.Log;
import com.DesignPatternFactory.Mediator;
import com.intellij.ui.content.ContentFactory;
import org.slf4j.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MediatorPage extends DesignPageTemplate {

    JTextField mediatorAbstractField, mediatorField, colleagueField, packageField;
    ActionListener mediatorAbstractAction, mediatorAction, colleagueAction, buildAction, packageAction;
    Mediator mediatorBuilder;
    boolean enteredMediatorAbstract, enteredMediator, enteredColleague, enteredPackage;
    JButton buildButton;
    JPanel panel;
    Logger logger;

    MediatorPage(){
        //Get logger.
        logger = Log.getLogger();
        logger.trace("Entering MediatorPage.");

        //Get Mediator code generator.
        logger.trace("Calling Mediator generator.");
        mediatorBuilder = new Mediator();

        //Set the path of the user's project root.
        logger.trace("Setting user project path.");
        mediatorBuilder.setPath(WelcomePage.path);

        //Set up the GUI event handler.
        logger.trace("Setting up MediatorPage event handler.");
        eventHandlerSetup();

        //Creating panel with Y-axis box layout.
        panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        //Configuring title
        logger.trace("Setting up MediatorPage GUI components.");
        JLabel textField00 = new JLabel
                ("<html>" +
                        "<font size = 10 color='black'><b>&nbsp; Mediator </b></font>" +
                        "</html>"
                );
        textField00.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0

        //Configuring mediator abstract field.
        JLabel textField01 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Abstract Mediator class name:</b></font>" +
                        "</html>"
                );
        textField01.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        mediatorAbstractField = new JTextField("");
        mediatorAbstractField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        mediatorAbstractField.setMaximumSize(new Dimension(250, 40));
        mediatorAbstractField.addActionListener(mediatorAbstractAction);

        //Configuring mediator field.
        JLabel textField02 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Mediator class name(s):</b></font>" +
                        "</html>"
                );
        textField02.setAlignmentX( Component.CENTER_ALIGNMENT);
        mediatorField = new JTextField("");
        mediatorField.setAlignmentX( Component.CENTER_ALIGNMENT);
        mediatorField.setMaximumSize(new Dimension(250, 40));
        mediatorField.addActionListener(mediatorAction);

        //Configuring colleague field.
        JLabel textField03 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Colleague class name(s):</b></font>" +
                        "</html>"
                );
        textField03.setAlignmentX( Component.CENTER_ALIGNMENT);
        colleagueField = new JTextField("");
        colleagueField.setAlignmentX( Component.CENTER_ALIGNMENT);
        colleagueField.setMaximumSize(new Dimension(250, 40));
        colleagueField.addActionListener(colleagueAction);

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

        //Configuring build button.
        buildButton = new JButton("Generate Code");
        buildButton.setMaximumSize(new Dimension(250, 40));
        buildButton.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        buildButton.addActionListener(buildAction);
        buildButton.setEnabled(false);

        //Adding components to panel.
        panel.add(textField00);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField01);
        panel.add(mediatorAbstractField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField02);
        panel.add(mediatorField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField03);
        panel.add(colleagueField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField04);
        panel.add(packageField);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(buildButton);
        panel.setVisible(true);
        logger.trace("MediatorPage GUI panel created with components.");

        //Setting the created panel GUI as the tool window.
        logger.trace("Setting MediatorPage panel as the tool window.");
        TheToolWindow.content = TheToolWindow.contentFactory.createContent(panel, "", false);
        TheToolWindow.getToolWindow.getContentManager().removeAllContents(true);
        TheToolWindow.getToolWindow.getContentManager().addContent(TheToolWindow.content);
    }

    /**
     * Event handler lister.
     */
    @Override
    public void eventHandlerSetup() {
        logger.trace("Setting up MediatorPage event handler.");
        /**
         * abstract mediator field listener.
         */
        mediatorAbstractAction = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String mediatorAbstract = mediatorAbstractField.getText();
                logger.trace("mediatorAbstractField entered: {}", mediatorAbstract);
                //Check if identifier is valid.
                if(!mediatorBuilder.setMediatorAbstractClassName(mediatorAbstract)){
                    logger.trace("mediatorAbstractField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    mediatorField.setText("");
                    return;
                }
                logger.trace("mediatorAbstractField is valid.");
                //Identifier valid and locking field/
                enteredMediatorAbstract = true;
                mediatorAbstractField.setEnabled(false);
                if(enteredMediator && enteredColleague && enteredPackage){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated");
                }
            }
        };
        /**
         * Mediator field listener.
         */
        mediatorAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String mediator = mediatorField.getText();
                logger.trace("mediatorField entered: {}", mediator);
                //Check if identifier is valid.
                if(!mediatorBuilder.setMediatorNames(mediator)){
                    logger.trace("mediatorField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    mediatorField.setText("");
                    return;
                }
                logger.trace("mediatorField is valid.");
                //Identifier valid and clearing field
                mediatorField.setText("");
                enteredMediator = true;
                if(enteredMediatorAbstract && enteredColleague && enteredPackage){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated");
                }
            }
        };
        /**
         * Colleague field listener.
         */
        colleagueAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String colleague = colleagueField.getText();
                logger.trace("colleagueField entered: {}", colleague);
                //Check if identifier is valid.
                if(!mediatorBuilder.setColleagueNames(colleague)){
                    logger.trace("colleagueField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    colleagueField.setText("");
                    return;
                }
                logger.trace("colleagueField is valid.");
                //Identifier valid and clearing field.
                colleagueField.setText("");
                enteredColleague = true;
                if(enteredMediatorAbstract && enteredMediator && enteredPackage){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated");
                }
            }
        };

        /**
         * Build button listener.
         */
        buildAction = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                logger.trace("Build button is clicked.");
                mediatorBuilder.buildPattern();
                String folderPath = mediatorBuilder.createCodeFiles();

                //Notifying user of package creation.
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
                //Check if identifier is valid.
                if(!mediatorBuilder.setFolderName(packageName)){
                    logger.trace("packageField is invalid");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    packageField.setText("");
                    return;
                }
                logger.trace("packageField is valid");
                //Identifier valid and locking field
                packageField.setEnabled(false);
                enteredPackage = true;

                if(enteredMediatorAbstract && enteredMediator && enteredColleague){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated");
                }
            }
        };
    }
}
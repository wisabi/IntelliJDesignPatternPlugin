package com.cs474Hw2;
import com.Checker.Checker;
import com.Checker.FindPackage;
import com.DesignPatternFactory.Chain;
import com.DesignPatternFactory.Log;
import com.intellij.ui.content.ContentFactory;
import org.slf4j.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;

public class ChainPage extends DesignPageTemplate {

    JTextField abstractHandlerField, finalReceiverField, receiverField, packageField;
    ActionListener abstractHandlerAction, finalReceiverAction, receiverAction, buildAction, packageAction;
    Chain chainBuilder;
    JButton buildButton;
    boolean enteredAbstractHandler, enteredFinalReceiver, enteredReceiver, enteredPackage;
    JPanel panel;
    Logger logger;
    Checker checker;
    String packagePath;
    boolean checkClashes;
    File packageFile;
    HashSet<String> identifiers;

    ChainPage() {
        //Get logger instance.
        logger = Log.getLogger();
        logger.trace("Entering ChainPage.");

        checker = new Checker();
        identifiers = new HashSet<>();

        //Calling Chain builder.
        logger.trace("Calling Chain generator.");
        chainBuilder = new Chain();

        //Set the path as the user's project root.
        logger.trace("Setting user project path.");
        chainBuilder.setPath(WelcomePage.path);

        //Setting up the event handler.
        logger.trace("Setting up ChainPage event handler.");
        eventHandlerSetup();

        //Setting up JPanel with Y-Axis  box layout.
        panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        //Configuring page title.
        logger.trace("Setting up ChainPage GUI components.");
        JLabel textField00 = new JLabel
                ("<html>" +
                        "<font size = 10 color='black'><b>&nbsp; Chain of Command</b></font>" +
                        "</html>"
                );
        textField00.setAlignmentX(Component.CENTER_ALIGNMENT);//0.0

        //Configuring abstract handler field.
        JLabel textField01 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Abstract Handler Class name:</b></font>" +
                        "</html>"
                );
        textField01.setAlignmentX(Component.CENTER_ALIGNMENT);
        abstractHandlerField = new JTextField("");
        abstractHandlerField.setAlignmentX(Component.CENTER_ALIGNMENT);
        abstractHandlerField.setMaximumSize(new Dimension(250, 40));
        abstractHandlerField.addActionListener(abstractHandlerAction);
        abstractHandlerField.setEnabled(false);
        //Configuring final receiver field.
        JLabel textField02 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Receiver class at the end chain of command:</b></font>" +
                        "</html>"
                );
        textField02.setAlignmentX(Component.CENTER_ALIGNMENT);//0.0
        finalReceiverField = new JTextField("");
        finalReceiverField.setAlignmentX(Component.CENTER_ALIGNMENT);//0.0
        finalReceiverField.setMaximumSize(new Dimension(250, 40));
        finalReceiverField.addActionListener(finalReceiverAction);
        finalReceiverField.setEnabled(false);

        //Configuring receiver field.
        JLabel textField03 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Receiver classes name(s):</b></font>" +
                        "</html>"
                );
        textField03.setAlignmentX(Component.CENTER_ALIGNMENT);
        receiverField = new JTextField("");
        receiverField.setAlignmentX(Component.CENTER_ALIGNMENT);
        receiverField.setMaximumSize(new Dimension(250, 40));
        receiverField.addActionListener(receiverAction);
        receiverField.setEnabled(false);

        //Configuring package field. 
        JLabel textField04 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Package name:</b></font>" +
                        "</html>"
                );
        textField04.setAlignmentX(Component.CENTER_ALIGNMENT);
        packageField = new JTextField("");
        packageField.setAlignmentX(Component.CENTER_ALIGNMENT);
        packageField.setMaximumSize(new Dimension(250, 40));
        packageField.addActionListener(packageAction);

        //Configuring package field.
        buildButton = new JButton("Generate Code");
        buildButton.setMaximumSize(new Dimension(250, 40));
        buildButton.setAlignmentX(Component.CENTER_ALIGNMENT);//0.0
        buildButton.addActionListener(buildAction);
        buildButton.setEnabled(false);

        //Adding components to panel.
        panel.add(textField00);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField04);
        panel.add(packageField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField01);
        panel.add(abstractHandlerField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField02);
        panel.add(finalReceiverField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField03);
        panel.add(receiverField);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(buildButton);
        panel.setVisible(true);
        logger.trace("ChainPage GUI panel created with components.");

        //Setting the created panel GUI as the tool window.
        logger.trace("Setting ChainPage panel as the tool window.");
        TheToolWindow.content = TheToolWindow.contentFactory.createContent(panel, "", false);
        TheToolWindow.getToolWindow.getContentManager().removeAllContents(true);
        TheToolWindow.getToolWindow.getContentManager().addContent(TheToolWindow.content);

    }

    /**
     * Event handler lister.
     */
    @Override
    public void eventHandlerSetup() {
        logger.trace("Setting up ChainPage event handler.");
        /**
         * Abstract field listener
         */
        abstractHandlerAction = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String abstractHandlerName = abstractHandlerField.getText();
                logger.trace("abstractHandlerField entered: {}", abstractHandlerName);


                //Checking for name clashes
                logger.trace("Checking name clashes.");
                if(!checkClashes(checkClashes, checker, packagePath, packageFile, WelcomePage.frame, abstractHandlerName, abstractHandlerField,  identifiers)){
                    //Clash found
                    logger.trace("Name clashes found.");
                    return;
                }

                //Checking identifier validity
                if (!chainBuilder.setAbstractHandlerClassName(abstractHandlerName)) {
                    logger.trace("abstractHandlerField invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "", JOptionPane.ERROR_MESSAGE);
                    abstractHandlerField.setText("");
                    return;
                }

                //Identifier valid and locking field/
                enteredAbstractHandler = true;
                identifiers.add(abstractHandlerName);
                abstractHandlerField.setEnabled(false);
                logger.trace("abstractHandlerField valid.");
                if (enteredReceiver && enteredFinalReceiver && enteredPackage) {
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated");
                }
            }
        };

        /**
         * Final receiver listener
         */
        finalReceiverAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String finalReceiverName = finalReceiverField.getText();
                logger.trace("finalReceiverField entered: {}", finalReceiverName);

                //Checking for name clashes
                logger.trace("Checking name clashes.");
                if(!checkClashes(checkClashes, checker, packagePath, packageFile, WelcomePage.frame, finalReceiverName, finalReceiverField,  identifiers)){
                    //Clash found
                    logger.trace("Name clashes found.");
                    return;
                }

                //Checking identifier validity
                if (!chainBuilder.setFinalReceiverClassName(finalReceiverName)) {
                    logger.trace("finalReceiverField is invalid");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "", JOptionPane.ERROR_MESSAGE);
                    finalReceiverField.setText("");
                    return;
                }

                //Identifier valid and locking field/
                logger.trace("finalReceiverField is valid");
                identifiers.add(finalReceiverName);
                enteredFinalReceiver = true;
                finalReceiverField.setEnabled(false);
                if (enteredAbstractHandler && enteredReceiver && enteredPackage) {
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated");
                }
            }
        };

        /**
         * Receiver field listener.
         */
        receiverAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String methodName = receiverField.getText();
                logger.trace("receiverField entered: {}", methodName);

                //Checking for name clashes
                logger.trace("Checking name clashes.");
                if(!checkClashes(checkClashes, checker, packagePath, packageFile, WelcomePage.frame, methodName, receiverField,  identifiers)){
                    //Clash found
                    logger.trace("Name clashes found.");
                    return;
                }

                //Checking identifier validity
                if (!chainBuilder.setReceiverClassNames(methodName)) {
                    //Activate build button
                    logger.trace("receiverField is invalid");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "", JOptionPane.ERROR_MESSAGE);
                    receiverField.setText("");
                    return;
                }

                //Identifier valid and clearing field.
                identifiers.add(methodName);
                logger.trace("receiverField is valid");
                receiverField.setText("");
                enteredReceiver = true;
                if (enteredAbstractHandler && enteredFinalReceiver && enteredPackage) {
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };

        /**
         * Build button listener.
         */
        buildAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Calling chain Builder code generator.
                logger.trace("Build button is clicked.");
                chainBuilder.buildPattern();
                String folderPath = chainBuilder.createCodeFiles();
                logger.trace("Code files created at: {}", folderPath);
                JOptionPane.showMessageDialog(WelcomePage.frame, "Package saved at:\n" + folderPath, "Package Created", 1);
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
        packageAction = new ActionListener() {
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
                    chainBuilder.setPath(packageFile.getParentFile().getAbsolutePath());
                    chainBuilder.setFolderName(packageName);
                    checkClashes = true;
                }
                //Check if identifier is valid
                else if(!chainBuilder.setFolderName(packageName)){
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
                logger.trace("packageField is valid.");
                packageField.setEnabled(false);
                enteredPackage = true;
                abstractHandlerField.setEnabled(true);
                finalReceiverField.setEnabled(true);
                receiverField.setEnabled(true);
                if (enteredAbstractHandler && enteredReceiver && enteredFinalReceiver) {
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };
    }
}
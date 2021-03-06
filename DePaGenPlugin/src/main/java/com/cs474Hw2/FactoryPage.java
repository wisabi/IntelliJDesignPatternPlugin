package com.cs474Hw2;
import com.Checker.Checker;
import com.Checker.FindPackage;
import com.DesignPatternFactory.Factory;
import com.DesignPatternFactory.Log;
import com.intellij.ui.content.ContentFactory;
import org.slf4j.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;

public class FactoryPage extends DesignPageTemplate {
    JTextField interfaceField, classField, methodField, packageField;
    ActionListener interfaceAction, classAction, methodAction, buildAction, packageAction;
    Factory factoryBuilder;
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

    FactoryPage() {
        //Get logger.
        logger = Log.getLogger();
        logger.trace("Entering FactoryPage.");

        //Get Factory code generator.
        logger.trace("Calling Factory generator.");
        factoryBuilder = new Factory();

        checker = new Checker();
        identifiers = new HashSet<>();
        methodIdentifiers = new HashSet<>();

        //Set the path of the user's project root.
        logger.trace("Setting user project path.");
        factoryBuilder.setPath(WelcomePage.path);

        //Set up the GUI event handler.
        logger.trace("Setting up FactoryPage event handler.");
        eventHandlerSetup();

        //Creating panel with Y-axis box layout.
        panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        //Creating title
        logger.trace("Setting up FactoryPage GUI components.");
        JLabel textField00 = new JLabel
                ("<html>" +
                        "<font size = 10 color='black'><b>&nbsp; Factory </b></font>" +
                        "</html>"
                );
        textField00.setAlignmentX( Component.CENTER_ALIGNMENT);

        //Configuring interface field.
        JLabel textField01 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Interface name:</b></font>" +
                        "</html>"
                );
        textField01.setAlignmentX( Component.CENTER_ALIGNMENT);
        interfaceField = new JTextField("");
        interfaceField.setAlignmentX( Component.CENTER_ALIGNMENT);
        interfaceField.setMaximumSize(new Dimension(250, 40));
        interfaceField.addActionListener(interfaceAction);
        interfaceField.setEnabled(false);

        //Configuring class field.
        JLabel textField02 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Class name(s):</b></font>" +
                        "</html>"
                );
        textField02.setAlignmentX( Component.CENTER_ALIGNMENT);
        classField = new JTextField("");
        classField.setAlignmentX( Component.CENTER_ALIGNMENT);
        classField.setMaximumSize(new Dimension(250, 40));
        classField.addActionListener(classAction);
        classField.setEnabled(false);

        //Configuring method field.
        JLabel textField03 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Method name(s):</b></font>" +
                        "</html>"
                );
        textField03.setAlignmentX( Component.CENTER_ALIGNMENT);
        methodField = new JTextField("");
        methodField.setAlignmentX( Component.CENTER_ALIGNMENT);
        methodField.setMaximumSize(new Dimension(250, 40));
        methodField.addActionListener(methodAction);
        methodField.setEnabled(false);

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
        logger.trace("FactoryPage GUI panel created with components.");

        //Setting the created panel GUI as the tool window.
        logger.trace("Setting FactoryPage panel as the tool window.");
        TheToolWindow.content = TheToolWindow.contentFactory.createContent(panel, "", false);
        TheToolWindow.getToolWindow.getContentManager().removeAllContents(true);
        TheToolWindow.getToolWindow.getContentManager().addContent(TheToolWindow.content);
    }
    /**
     * Event handler lister.
     */
    @Override
    public void eventHandlerSetup() {
        logger.trace("Setting up FactoryPage event handler.");
        interfaceAction = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String interfaceName = interfaceField.getText();
                logger.trace("interfaceField entered: {}", interfaceName);

                //Checking name clashes
                logger.trace("Checking name clashes.");
                if(!checkClashes(checkClashes, checker, packagePath, packageFile, WelcomePage.frame, interfaceName, interfaceField,  identifiers)){
                    //Name clashes found
                    logger.trace("Name clashes found.");
                    return;
                }

                //Check if identifier is valid
                if(!factoryBuilder.setInterfaceName(interfaceName)){
                    logger.trace("interfaceField is invalid");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    interfaceField.setText("");
                    return;
                }

                logger.trace("interfaceField is valid");
                //Identifier valid and locking field/
                identifiers.add(interfaceName);
                enteredInterface = true;
                interfaceField.setEnabled(false);
                if(enteredMethods && enteredPackage && enteredClass){
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated");
                }
            }
        };

        classAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String className = classField.getText();
                logger.trace("classField entered: {}", className);

                //Checking name clashes
                logger.trace("Checking name clashes.");
                if(!checkClashes(checkClashes, checker, packagePath, packageFile, WelcomePage.frame, className, classField,  identifiers)){
                    //Name clashes found
                    logger.trace("Name clashes found.");
                    return;
                }

                //Check if identifier is valid
                if(!factoryBuilder.setConcreteClassNames(className)){
                    logger.trace("classField is invalid");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    classField.setText("");
                    return;
                }
                //Identifier valid and clearing field/
                logger.trace("classField is valid");
                identifiers.add(className);
                classField.setText("");
                enteredClass = true;
                if(enteredMethods && enteredPackage && enteredInterface){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated");
                }
            }
        };

        methodAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String methodName = methodField.getText();
                logger.trace("methodField entered: {}", methodName);

                //Checking method name clashes;
                logger.trace("Checking method name clashes.");
                if(methodIdentifiers.contains(methodName)){
                    logger.trace("methodField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Name Clash!", "",  JOptionPane.ERROR_MESSAGE);
                    methodField.setText("");
                    return;
                }

                //Check if identifier is valid
                if(!factoryBuilder.setAbstractMethods(methodName)){
                    logger.trace("methodField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    methodField.setText("");
                    return;
                }
                //Identifier valid and clearing field/
                logger.trace("methodField is valid.");
                methodIdentifiers.add(methodName);
                methodField.setText("");
                enteredMethods = true;
                if(enteredPackage && enteredClass && enteredInterface){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated");
                }
            }
        };

        buildAction = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                logger.trace("Build button is clicked.");
                factoryBuilder.buildPattern();
                String folderPath = factoryBuilder.createCodeFiles();

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
                    factoryBuilder.setPath(packageFile.getParentFile().getAbsolutePath());
                    factoryBuilder.setFolderName(packageName);
                    checkClashes = true;
                }
                //Check if identifier is valid
                else if(!factoryBuilder.setFolderName(packageName)){
                    logger.trace("packageField is invalid");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    packageField.setText("");
                    return;
                }
                //Set checking clashes as false.
                else{
                    checkClashes = false;
                }

                //Identifier valid and clearing field/
                logger.trace("packageField is valid");
                packageField.setEnabled(false);
                methodField.setEnabled(true);
                classField.setEnabled(true);
                interfaceField.setEnabled(true);
                enteredPackage = true;
                if(enteredMethods && enteredClass && enteredInterface){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated");
                }
            }
        };
    }
}
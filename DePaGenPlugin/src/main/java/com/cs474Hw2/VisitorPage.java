package com.cs474Hw2;
import com.Checker.Checker;
import com.Checker.FindPackage;
import com.DesignPatternFactory.Log;
import com.DesignPatternFactory.Visitor;
import com.intellij.ui.content.ContentFactory;
import org.slf4j.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;

public class VisitorPage extends DesignPageTemplate {

    JTextField elementInterfaceField, elementMethodsField, concreteElementsField, visitorInterfaceField,
            concreteVisitorsField, packageField;
    ActionListener elementInterfaceAction, elementMethodsAction, concreteElementsAction, visitorInterfaceAction,
            concreteVisitorsAction, buildAction, packageAction;
    Visitor visitorBuilder;
    boolean enteredElementInterface, enteredElementMethods, enteredConcreteElements, enteredConcreteVisitors,
            enteredVisitorInterface , enteredPackage;
    JButton buildButton;
    JPanel panel;
    Logger logger;
    Checker checker;
    String packagePath;
    boolean checkClashes;
    File packageFile;
    HashSet<String> identifiers, elementMethods;

    VisitorPage(){
        //Get logger.
        logger = Log.getLogger();
        logger.trace("Entering VisitorPage.");

        checker = new Checker();
        identifiers = new HashSet<>();
        elementMethods = new HashSet<>();

        //Get Template code generator.
        logger.trace("Calling Visitor generator.");
        visitorBuilder = new Visitor();

        //Set the path of the user's project root.
        logger.trace("Setting user project path.");
        visitorBuilder.setPath(WelcomePage.path);

        //Set up the GUI event handler.
        logger.trace("Setting up VisitorPage event handler.");
        eventHandlerSetup();

        //Creating panel with Y-axis box layout.
        panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        //Configuring title
        logger.trace("Setting up VisitorPage GUI components.");
        JLabel textField00 = new JLabel
                ("<html>" +
                        "<font size = 10 color='black'><b>&nbsp; Visitor </b></font>" +
                        "</html>"
                );
        textField00.setAlignmentX( Component.CENTER_ALIGNMENT);

        //Configuring element interface field.
        JLabel textField01 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Element Interface name:</b></font>" +
                        "</html>"
                );
        textField01.setAlignmentX( Component.CENTER_ALIGNMENT);
        elementInterfaceField = new JTextField("");
        elementInterfaceField.setAlignmentX( Component.CENTER_ALIGNMENT);
        elementInterfaceField.setMaximumSize(new Dimension(250, 40));
        elementInterfaceField.addActionListener(elementInterfaceAction);
        elementInterfaceField.setEnabled(false);

        //Configuring element method field
        JLabel textField02 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Element Abstract Methods name(s):</b></font>" +
                        "</html>"
                );
        textField02.setAlignmentX( Component.CENTER_ALIGNMENT);
        elementMethodsField = new JTextField("");
        elementMethodsField.setAlignmentX( Component.CENTER_ALIGNMENT);
        elementMethodsField.setMaximumSize(new Dimension(250, 40));
        elementMethodsField.addActionListener(elementMethodsAction);
        elementMethodsField.setEnabled(false);

        //Configuring concrete element field.
        JLabel textField03 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Concrete Element class name(s):</b></font>" +
                        "</html>"
                );
        textField03.setAlignmentX( Component.CENTER_ALIGNMENT);
        concreteElementsField = new JTextField("");
        concreteElementsField.setAlignmentX( Component.CENTER_ALIGNMENT);
        concreteElementsField.setMaximumSize(new Dimension(250, 40));
        concreteElementsField.addActionListener(concreteElementsAction);
        concreteElementsField.setEnabled(false);

        //Configuring visitor interface field.
        JLabel textField05 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Visitor Interface name:</b></font>" +
                        "</html>"
                );
        textField05.setAlignmentX( Component.CENTER_ALIGNMENT);
        visitorInterfaceField = new JTextField("");
        visitorInterfaceField.setAlignmentX( Component.CENTER_ALIGNMENT);
        visitorInterfaceField.setMaximumSize(new Dimension(250, 40));
        visitorInterfaceField.addActionListener(visitorInterfaceAction);
        visitorInterfaceField.setEnabled(false);

        //Configuring concrete visitor field.
        JLabel textField06 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter Concrete Visitor class name(s):</b></font>" +
                        "</html>"
                );
        textField06.setAlignmentX( Component.CENTER_ALIGNMENT);
        concreteVisitorsField = new JTextField("");
        concreteVisitorsField.setAlignmentX( Component.CENTER_ALIGNMENT);
        concreteVisitorsField.setMaximumSize(new Dimension(250, 40));
        concreteVisitorsField.addActionListener(concreteVisitorsAction);
        concreteVisitorsField.setEnabled(false);

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
        panel.add(elementInterfaceField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField02);
        panel.add(elementMethodsField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField03);
        panel.add(concreteElementsField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField05);
        panel.add(visitorInterfaceField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField06);
        panel.add(concreteVisitorsField);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(buildButton);
        panel.setVisible(true);
        logger.trace("Visitor GUI panel created with components.");

        //Setting the created panel GUI as the tool window.
        logger.trace("Setting Visitor panel as the tool window.");
        TheToolWindow.content = TheToolWindow.contentFactory.createContent(panel, "", false);
        TheToolWindow.getToolWindow.getContentManager().removeAllContents(true);
        TheToolWindow.getToolWindow.getContentManager().addContent(TheToolWindow.content);
    }

    /**
     *
     */
    @Override
    public void eventHandlerSetup() {
        logger.trace("Setting up Visitor event handler.");
        elementInterfaceAction = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String elementInterface = elementInterfaceField.getText();
                logger.trace("elementInterfaceField entered: {}", elementInterface);

                //Checking for name clashes
                logger.trace("Checking name clashes.");
                if(!checkClashes(checkClashes, checker, packagePath, packageFile, WelcomePage.frame, elementInterface, elementInterfaceField,  identifiers)){
                    //Clash found
                    logger.trace("Name clashes found.");
                    return;
                }
                //Check if identifier is valid.
                if(!visitorBuilder.setElementInterfaceStr(elementInterface)){
                    logger.trace("elementInterfaceField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    elementInterfaceField.setText("");
                    return;
                }
                //Identifier valid and locking field
                identifiers.add(elementInterface);
                logger.trace("elementInterfaceField is valid.");
                enteredElementInterface = true;
                elementInterfaceField.setEnabled(false);
                if(enteredElementMethods && enteredConcreteElements && enteredConcreteVisitors && enteredVisitorInterface && enteredPackage){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };

        elementMethodsAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String elementMethod = elementMethodsField.getText();
                logger.trace("elementMethod entered: {}", elementMethodsField);

                //Checking method name clashes;
                logger.trace("Checking method name clashes.");
                if(elementMethods.contains(elementMethod)){
                    logger.trace("methodField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Name Clash!", "",  JOptionPane.ERROR_MESSAGE);
                    elementMethodsField.setText("");
                    return;
                }

                //Check if identifier is valid.
                if(!visitorBuilder.setElementMethods(elementMethod)){
                    logger.trace("elementMethodsField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    elementMethodsField.setText("");
                    return;
                }
                logger.trace("elementMethodsField is valid");
                elementMethods.add(elementMethod);
                //Identifier valid and clearing field
                elementMethodsField.setText("");
                enteredElementMethods = true;
                if(enteredElementInterface && enteredConcreteElements && enteredConcreteVisitors && enteredVisitorInterface && enteredPackage){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };

        concreteElementsAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String concreteElements = concreteElementsField.getText();
                logger.trace("concreteElements entered: {}", concreteElements);

                //Checking for name clashes
                logger.trace("Checking name clashes.");
                if(!checkClashes(checkClashes, checker, packagePath, packageFile, WelcomePage.frame, concreteElements, concreteElementsField,  identifiers)){
                    //Clash found
                    logger.trace("Name clashes found.");
                    return;
                }

                //Check if identifier is valid.
                if(!visitorBuilder.setConcreteElements(concreteElements)){
                    logger.trace("concreteElements is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    concreteElementsField.setText("");
                    return;
                }

                logger.trace("concreteElements is valid.");
                identifiers.add(concreteElements);
                //Identifier valid and clearing field
                concreteElementsField.setText("");
                enteredConcreteElements = true;
                if(enteredElementInterface && enteredElementMethods && enteredConcreteVisitors && enteredVisitorInterface && enteredPackage){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };

        visitorInterfaceAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String  visitorInterface = visitorInterfaceField.getText();
                logger.trace("visitorInterfaceField entered: {}", visitorInterface);

                //Checking for name clashes
                logger.trace("Checking name clashes.");
                if(!checkClashes(checkClashes, checker, packagePath, packageFile, WelcomePage.frame, visitorInterface, visitorInterfaceField,  identifiers)){
                    //Clash found
                    logger.trace("Name clashes found.");
                    return;
                }

                //Check if identifier is valid.
                if(!visitorBuilder.setVisitorInterfaceStr( visitorInterface)){
                    logger.trace("visitorInterfaceField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    visitorInterfaceField.setText("");
                    return;
                }
                //Identifier valid and locking field
                identifiers.add(visitorInterface);
                logger.trace("visitorInterfaceField is valid.");
                visitorInterfaceField.setEnabled(false);
                enteredVisitorInterface = true;
                if(enteredElementInterface && enteredElementMethods && enteredConcreteVisitors && enteredConcreteElements && enteredPackage){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };

        concreteVisitorsAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String concreteVisitor = concreteVisitorsField.getText();
                logger.trace("concreteVisitorsField entered: {}", concreteVisitor);

                //Checking for name clashes
                logger.trace("Checking name clashes.");
                if(!checkClashes(checkClashes, checker, packagePath, packageFile, WelcomePage.frame, concreteVisitor, concreteVisitorsField,  identifiers)){
                    //Clash found
                    logger.trace("Name clashes found.");
                    return;
                }

                //Check if identifier is valid.
                if(!visitorBuilder.setConcreteVisitors(concreteVisitor)){
                    logger.trace("concreteVisitorsField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    concreteVisitorsField.setText("");
                    return;
                }
                logger.trace("concreteVisitorsField is valid.");
                identifiers.add(concreteVisitor);
                //Identifier valid and clearing field
                concreteVisitorsField.setText("");
                enteredConcreteVisitors = true;
                if(enteredElementInterface && enteredElementMethods && enteredVisitorInterface && enteredConcreteElements && enteredPackage){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };

        buildAction = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                logger.trace("Build button is clicked.");
                visitorBuilder.buildPattern();
                String folderPath = visitorBuilder.createCodeFiles();
                logger.trace("Code files created at: {}", folderPath);

                logger.trace("Setting the welcome page as the toolwindow.");
                JOptionPane.showMessageDialog(WelcomePage.frame,"Package saved at:\n" + folderPath,"Package Created", 1);
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
                    visitorBuilder.setPath(packageFile.getParentFile().getAbsolutePath());
                    visitorBuilder.setFolderName(packageName);
                    checkClashes = true;
                }
                //Check if identifier is valid
                else if(!visitorBuilder.setFolderName(packageName)){
                    logger.trace("packageField is invalid");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    packageField.setText("");
                    return;
                }
                //Set checking clashes as false.
                else{
                    checkClashes = false;
                }

                //Identifier valid and locking field
                logger.trace("packageField is valid.");
                packageField.setEnabled(false);
                enteredPackage = true;
                concreteVisitorsField.setEnabled(true);
                visitorInterfaceField.setEnabled(true);
                concreteElementsField.setEnabled(true);
                elementMethodsField.setEnabled(true);
                elementInterfaceField.setEnabled(true);
                if(enteredElementInterface && enteredElementMethods && enteredVisitorInterface && enteredConcreteElements && enteredConcreteVisitors){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };
    }
}
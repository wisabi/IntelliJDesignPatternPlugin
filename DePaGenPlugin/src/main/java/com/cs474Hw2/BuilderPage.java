package com.cs474Hw2;
import com.DesignPatternFactory.Builder;
import com.DesignPatternFactory.Log;
import com.intellij.ui.content.ContentFactory;
import org.slf4j.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuilderPage extends  DesignPageTemplate{

    JTextField directorField, builderInterfaceField, builderInterfaceMethodsField,
            concreteBuilderField, complexObjectField, topProductInterfaceField,
            topProductInterfaceMethodField, productInterfaceField, packageField;

    ActionListener directorFieldAction, builderInterfaceFieldAction, builderInterfaceMethodsFieldAction,
            concreteBuilderFieldAction, complexObjectFieldAction, topProductInterfaceFieldAction,
            topProductInterfaceMethodFieldAction, productInterfaceAction, packageAction, buildAction;

    Builder builderBuilder;

    boolean enteredDirector, enteredBuilderInterface, enteredBuilderMethod, enteredConcreteBuilder,
           enteredComplexObject, enteredTopProductInterface, enteredTopProductInterfaceMethod, enteredPackage,
           enteredProductInterface;

    JButton buildButton;
    JPanel panel;
    Logger logger;

    BuilderPage(){
        //Get logger.
        logger = Log.getLogger();
        logger.trace("Entering BuilderPage.");

        //Get Builder code generator.
        logger.trace("Setting up Builder generator.");
        builderBuilder = new Builder();
        builderBuilder.setPath(WelcomePage.path);

        //Set the path of the user's project root.
        logger.trace("Calling event handler setup.");
        eventHandlerSetup();

        //Set up the GUI event handler.
        panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        //Configuring title
        logger.trace("Builder GUI components.");
        JLabel textField00 = new JLabel
                ("<html>" +
                        "<font size = 10 color='black'><b>&nbsp; Builder </b></font>" +
                        "</html>"
                );
        textField00.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0

        //Configuring director field
        JLabel textField01 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Director Class name:</b></font>" +
                        "</html>"
                );
        textField01.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        directorField = new JTextField("");
        directorField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        directorField.setMaximumSize(new Dimension(250, 40));
        directorField.addActionListener(directorFieldAction);
        directorField.setEnabled(false);

        //Configuring builder interface field
        JLabel textField02 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Builder Interface name:</b></font>" +
                        "</html>"
                );
        textField02.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        builderInterfaceField = new JTextField("");
        builderInterfaceField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        builderInterfaceField.setMaximumSize(new Dimension(250, 40));
        builderInterfaceField.addActionListener(builderInterfaceFieldAction);
        builderInterfaceField.setEnabled(false);

        //Configuring builder interface method field
        JLabel textField03 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Builder Interface Method name(s):</b></font>" +
                        "</html>"
                );
        textField03.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        builderInterfaceMethodsField = new JTextField("");
        builderInterfaceMethodsField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        builderInterfaceMethodsField.setMaximumSize(new Dimension(250, 40));
        builderInterfaceMethodsField.addActionListener(builderInterfaceMethodsFieldAction);
        builderInterfaceMethodsField.setEnabled(false);

        //Configuring concrete builder field.
        JLabel textField05 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Concrete Builder class name(s):</b></font>" +
                        "</html>"
                );
        textField05.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        concreteBuilderField = new JTextField("");
        concreteBuilderField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        concreteBuilderField.setMaximumSize(new Dimension(250, 40));
        concreteBuilderField.addActionListener(concreteBuilderFieldAction);
        concreteBuilderField.setEnabled(false);

        //Configuring complex object field.
        JLabel textField06 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Complex Object class name:</b></font>" +
                        "</html>"
                );
        textField06.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        complexObjectField = new JTextField("");
        complexObjectField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        complexObjectField.setMaximumSize(new Dimension(250, 40));
        complexObjectField.addActionListener(complexObjectFieldAction);
        complexObjectField.setEnabled(false);

        //Configuring top product interface field.
        JLabel textField07 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Top Product Interface name:</b></font>" +
                        "</html>"
                );
        textField07.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        topProductInterfaceField = new JTextField("");
        topProductInterfaceField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        topProductInterfaceField.setMaximumSize(new Dimension(250, 40));
        topProductInterfaceField.addActionListener(topProductInterfaceFieldAction);
        topProductInterfaceField.setEnabled(false);

        //Configuring top product interface method field.
        JLabel textField08 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Top Product Interface Method name(s):</b></font>" +
                        "</html>"
                );
        textField08.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        topProductInterfaceMethodField = new JTextField("");
        topProductInterfaceMethodField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        topProductInterfaceMethodField.setMaximumSize(new Dimension(250, 40));
        topProductInterfaceMethodField.addActionListener(topProductInterfaceMethodFieldAction);
        topProductInterfaceMethodField.setEnabled(false);

        //Configuring product interface method field.
        JLabel textField09= new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Product Interface name(s):</b></font>" +
                        "</html>"
                );
        textField09.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        productInterfaceField = new JTextField("");
        productInterfaceField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        productInterfaceField.setMaximumSize(new Dimension(250, 40));
        productInterfaceField.addActionListener(productInterfaceAction);
        productInterfaceField.setEnabled(false);

        //Configuring package field.
        JLabel textField04 = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Enter the Package name:</b></font></b>" +
                        "</html>"
                );
        textField04.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        textField04.setVisible(true);
        packageField = new JTextField("");
        packageField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        packageField.setMaximumSize(new Dimension(250, 40));
        packageField.setVisible(true);
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
        panel.add(directorField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField02);
        panel.add(builderInterfaceField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField03);
        panel.add(builderInterfaceMethodsField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField05);
        panel.add(concreteBuilderField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField06);
        panel.add(complexObjectField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField07);
        panel.add(topProductInterfaceField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField08);
        panel.add(topProductInterfaceMethodField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textField09);
        panel.add(productInterfaceField);
        panel.add(Box.createRigidArea(new Dimension(0, 80)));

        panel.add(buildButton);
        panel.setVisible(true);
        logger.trace("Builder GUI panel created with components.");

        //Setting the created panel GUI as the tool window.
        logger.trace("Setting BuilderPage panel as the tool window.");
        TheToolWindow.content = TheToolWindow.contentFactory.createContent(panel, "", false);
        TheToolWindow.getToolWindow.getContentManager().removeAllContents(true);
        TheToolWindow.getToolWindow.getContentManager().addContent(TheToolWindow.content);
    }

    /**
     * Event handler lister.
     * */
    @Override
    public void eventHandlerSetup() {
        logger.trace("Setting up BuilderPage event handler.");
        //director field listener.
        directorFieldAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String director = directorField.getText();
                logger.trace("directorField entered: {}", director);
                //Check if identifier is valid.
                if(!builderBuilder.setDirectorName(director)){
                    logger.trace("directorField is invalid");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    directorField.setText("");
                    return;
                }
                //Identifier valid and locking field
                directorField.setEnabled(false);
                enteredDirector = true;
                logger.trace("directorField is valid");
                if(enteredDirector && enteredBuilderInterface && enteredBuilderMethod && enteredConcreteBuilder&&
                        enteredComplexObject && enteredTopProductInterface && enteredTopProductInterfaceMethod &&
                        enteredPackage && enteredProductInterface){
                        //Activate build button
                        buildButton.setEnabled(true);
                        logger.trace("buildButton is activated");
                }
            }
        };
        //Build interface field listener.
        builderInterfaceFieldAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String builderInterface = builderInterfaceField.getText();
                logger.trace("builderInterfaceField is entered: {}", builderInterface);
                //Check if identifier is valid.
                if(!builderBuilder.setBuilderInterfaceName(builderInterface)){
                    logger.trace("builderInterfaceField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    builderInterfaceField.setText("");
                    return;
                }
                //Identifier valid and locking field
                builderInterfaceField.setEnabled(false);
                enteredBuilderInterface = true;
                logger.trace("builderInterfaceField is valid.");
                if(enteredDirector && enteredBuilderInterface && enteredBuilderMethod && enteredConcreteBuilder&&
                        enteredComplexObject && enteredTopProductInterface && enteredTopProductInterfaceMethod &&
                        enteredPackage && enteredProductInterface){
                        //Activate build button
                        buildButton.setEnabled(true);
                        logger.trace("buildButton is activated");
                }
            }
        };
        //build interface methods field action
        builderInterfaceMethodsFieldAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String builderInterfaceMethod = builderInterfaceMethodsField.getText();
                logger.trace("builderInterfaceMethodsField is entered: {}.", builderInterfaceMethod);
                //Check if identifier is valid.
                if(!builderBuilder.setDirectorName(builderInterfaceMethod)){
                    logger.trace("builderInterfaceMethodsField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    builderInterfaceMethodsField.setText("");
                    return;
                }
                //Identifier valid and clearing field
                builderInterfaceMethodsField.setText("");
                enteredBuilderMethod = true;
                logger.trace("builderInterfaceMethodsField is valid.");
                if(enteredDirector && enteredBuilderInterface && enteredBuilderMethod && enteredConcreteBuilder&&
                        enteredComplexObject && enteredTopProductInterface && enteredTopProductInterfaceMethod &&
                        enteredPackage && enteredProductInterface){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };
        //Concrete builder field action listener.
        concreteBuilderFieldAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String concreteBuilder = concreteBuilderField.getText();
                logger.trace("concreteBuilderField is entered: {}", concreteBuilder);
                //Check if identifier is valid.
                if(!builderBuilder.setConcreteBuilderNames(concreteBuilder)){
                    logger.trace("concreteBuilderField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    concreteBuilderField.setText("");
                    return;
                }
                //Identifier valid and clearing field
                concreteBuilderField.setText("");
                enteredConcreteBuilder = true;
                logger.trace("concreteBuilderField is valid.");
                if(enteredDirector && enteredBuilderInterface && enteredBuilderMethod && enteredConcreteBuilder&&
                        enteredComplexObject && enteredTopProductInterface && enteredTopProductInterfaceMethod &&
                        enteredPackage && enteredProductInterface){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };

        //Complex object field listener.
        complexObjectFieldAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String complexObject = complexObjectField.getText();
                //Check if identifier is valid.
                logger.trace("complexObjectField is entered: {}", complexObject);
                if(!builderBuilder.setComplexObjectName(complexObject)){
                    logger.trace("complexObjectField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    complexObjectField.setText("");
                    return;
                }
                //Identifier valid and locking field
                complexObjectField.setEnabled(false);
                enteredComplexObject = true;
                logger.trace("complexObjectField is valid.");
                if(enteredDirector && enteredBuilderInterface && enteredBuilderMethod && enteredConcreteBuilder&&
                        enteredComplexObject && enteredTopProductInterface && enteredTopProductInterfaceMethod &&
                        enteredPackage && enteredProductInterface){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };

        //Top product interface field action listener.
        topProductInterfaceFieldAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String topProductInterface = topProductInterfaceField.getText();
                logger.trace("topProductInterfaceField is entered: {}", topProductInterface);
                //Check if identifier is valid.
                if(!builderBuilder.setTopProductInterfaceName(topProductInterface)){
                    logger.trace("topProductInterfaceField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    topProductInterfaceField.setText("");
                    return;
                }
                //Identifier valid and locking field
                topProductInterfaceField.setEnabled(false);
                enteredTopProductInterface = true;
                logger.trace("topProductInterfaceField is valid.");
                if(enteredDirector && enteredBuilderInterface && enteredBuilderMethod && enteredConcreteBuilder&&
                        enteredComplexObject && enteredTopProductInterface && enteredTopProductInterfaceMethod &&
                        enteredPackage && enteredProductInterface){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };
        //Top product interface method field listener.
        topProductInterfaceMethodFieldAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String topProductInterface = topProductInterfaceMethodField.getText();
                logger.trace("topProductInterfaceMethodField is entered: {}", topProductInterface);
                //Check if identifier is valid.
                if(!builderBuilder.setTopProductInterfaceName(topProductInterface)){
                    logger.trace("topProductInterfaceMethodField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    topProductInterfaceMethodField.setText("");
                    return;
                }
                //Identifier valid and clearing field
                topProductInterfaceMethodField.setText("");
                enteredTopProductInterfaceMethod = true;
                logger.trace("topProductInterfaceMethodField is valid.");
                if(enteredDirector && enteredBuilderInterface && enteredBuilderMethod && enteredConcreteBuilder&&
                        enteredComplexObject && enteredTopProductInterface && enteredTopProductInterfaceMethod &&
                        enteredPackage && enteredProductInterface){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };

        //Product interface field listener.
        productInterfaceAction = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String productInterface = productInterfaceField.getText();
                logger.trace("productInterfaceField is entered: {}", productInterface);
                //Check if identifier is valid.
                if(!builderBuilder.setProductInterfaceNames(productInterface)){
                    logger.trace("productInterfaceField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    productInterfaceField.setText("");
                    return;
                }
                //Identifier valid and clearing field
                enteredProductInterface = true;
                productInterfaceField.setText("");
                logger.trace("productInterfaceField is valid.");
                if(enteredDirector && enteredBuilderInterface && enteredBuilderMethod && enteredConcreteBuilder&&
                        enteredComplexObject && enteredTopProductInterface && enteredTopProductInterfaceMethod &&
                        enteredPackage && enteredProductInterface){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };

        //Build button listener.
        buildAction = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Notifying user of package creation.
                logger.trace("Build button is clicked.");
                logger.trace("buildPattern is called.");
                builderBuilder.buildPattern();
                String folderPath = builderBuilder.createCodeFiles();
                logger.trace("Code files created at: {}", folderPath);
                JOptionPane.showMessageDialog(WelcomePage.frame,"Package saved at:\n" + folderPath,"Package Created", 1);

                //Setting the GUI back to the welcome page.
                logger.trace("Setting the welcome page as the toolwindow.");
                TheToolWindow.contentFactory = ContentFactory.SERVICE.getInstance();
                TheToolWindow.content = TheToolWindow.contentFactory.createContent(WelcomePage.panel, "", false);
                TheToolWindow.getToolWindow.getContentManager().removeAllContents(true);
                TheToolWindow.getToolWindow.getContentManager().addContent(TheToolWindow.content);
            }
        };

        //package field listener.
        packageAction = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String packageName = packageField.getText();
                logger.trace("packageField is entered: {}", packageName);
                //Check if identifier is valid.
                if(!builderBuilder.setFolderName(packageName)){
                    logger.trace("packageField is invalid.");
                    JOptionPane.showMessageDialog(WelcomePage.frame, "ERROR: Bad Identifier!", "",  JOptionPane.ERROR_MESSAGE);
                    packageField.setText("");
                    return;
                }
                //Identifier valid and locking field
                packageField.setEnabled(false);
                enteredPackage = true;
                directorField.setEnabled(true);
                builderInterfaceField.setEnabled(true);
                builderInterfaceMethodsField.setEnabled(true);
                concreteBuilderField.setEnabled(true);
                complexObjectField.setEnabled(true);
                topProductInterfaceField.setEnabled(true);
                topProductInterfaceMethodField.setEnabled(true);
                productInterfaceField.setEnabled(true);
                logger.trace("packageField is valid.");
                if(enteredDirector && enteredBuilderInterface && enteredBuilderMethod && enteredConcreteBuilder&&
                        enteredComplexObject && enteredTopProductInterface && enteredTopProductInterfaceMethod &&
                        enteredPackage && enteredProductInterface){
                    //Activate build button
                    buildButton.setEnabled(true);
                    logger.trace("buildButton is activated.");
                }
            }
        };
    }
}
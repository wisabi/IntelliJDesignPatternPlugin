package com.cs474Hw2;
import com.DesignPatternFactory.Log;
import com.intellij.openapi.ui.ComboBox;
import org.slf4j.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Welcome Page GUI.
 * A page for user to select a design pattern.
 */
public class WelcomePage {

    static JPanel panel;
    public static JFrame frame;
    static ComboBox<String> selectBox;
    ActionListener patternSelectionAction, nextButtonAction;
    static JButton nextButton;
    static String selectedPattern;
    static DesignPageFactory designPageFactory;
    static String path;
    Logger logger;
    static final  String[] designPatternNames = {
            "Abstract Factory",
            "Builder",
            "Factory",
            "Facade",
            "Chain of Responsibility",
            "Mediator",
            "Visitor",
            "Template"
    };

    /**
     * WelcomePage constructor
     * */
    WelcomePage(){
        //Get logger instance.
        logger = Log.getLogger();
        logger.trace("Starting the design pattern page factory");

        //Creating the design page factory.
        designPageFactory = new DesignPageFactory();



        //Setting up the event handler for the welcome page.
        logger.trace("Starting the WeclomePage eventHandler");
        eventHandlerSetup();

    }

    /**
     * Method to create GUI elements in a panel.
     * @return JPanel panel
     */
    JPanel makeGUI(){

        //Saving the path of the user's root folder
        path = TheToolWindow.projectPath;

        //Creating a new JPanel.
        logger.trace("Creating GUI components for WelcomePage");
        panel = new JPanel();

        //Using Box Layout in the Y-axis for the GUI.
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxlayout);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        //Configuring the selectBox (dropdown menu).
        JLabel textField = new JLabel
                ("<html>" +
                        "<font color='black'><b>&nbsp; Select a Design pattern:</b></font>" +
                        "</html>"
                );
        textField.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        panel.add(textField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        selectBox = new ComboBox(designPatternNames);
        selectBox.setAlignmentX( Component.CENTER_ALIGNMENT );//0.0
        selectBox.setMaximumSize(new Dimension(250, 40));
        selectBox.setMaximumSize(new Dimension(250, 40));
        selectBox.addActionListener(patternSelectionAction);
        panel.add(selectBox);

        //Configuring the Next button.
        panel.add(Box.createRigidArea(new Dimension(0, 100)));
        nextButton = new JButton("Next");
        nextButton.setMinimumSize(new Dimension(200, 40));
        nextButton.setMaximumSize(new Dimension(250, 40));
        nextButton.setAlignmentX( Component.CENTER_ALIGNMENT);//0.0
        nextButton.addActionListener(nextButtonAction);
        nextButton.setEnabled(false);
        panel.add(nextButton);
        panel.setVisible(true);

        //Returning this panel.
        logger.trace("Returning WelcomePage panel");
        return panel;
    }

    /**
     * Event handler for this GUI panel.
     */
    public void eventHandlerSetup() {
        logger.trace("WelcomePage Event Handler set up.");

        /**
         * Listening for the SelectBox (dropdown menu).
         */
        patternSelectionAction = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                selectedPattern = (String) selectBox.getSelectedItem();
                nextButton.setEnabled(true);
                logger.trace("Pattern selected: {}", selectedPattern);
            }
        };

        /**
         * Listener for the Next button.
         */
        nextButtonAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                logger.trace("Next button clicked.");
                //Call to the design page factory to create the selected design pattern page.
                designPageFactory.getPage(selectedPattern);
            }
        };
    }
}
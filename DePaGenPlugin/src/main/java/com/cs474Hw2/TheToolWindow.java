package com.cs474Hw2;
import com.DesignPatternFactory.Log;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * Implementing ToolWindowFactory as an application starting point.
 */
public class TheToolWindow implements ToolWindowFactory {

    public static com.intellij.openapi.wm.ToolWindow getToolWindow;
    public static String projectPath;
    public static ContentFactory contentFactory;
    public static Content content;
    Logger logger;

    /**
     * Implementation of abstract method to create a tool window.
     * @param project
     * @param toolWindow
     */
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull com.intellij.openapi.wm.ToolWindow toolWindow) {
        //Getting logger instance.
        logger = Log.getLogger();
        logger.trace("Application has began execution.");

        //Creating new WelcomePage instance.
        logger.trace("Creating welcome page.");
        WelcomePage welcomePage = new WelcomePage();

        //Getting user's project root path.
        logger.trace("Getting path to user's project in Intellij.");
        VirtualFile virtualFile = ModuleRootManager.getInstance(ModuleManager.getInstance(project).getModules()[0]).getContentRoots()[0];
        projectPath = virtualFile.getPath();
        logger.trace("User's project path: {}", projectPath);

        //Saving the the toolWindow.
        getToolWindow = toolWindow;

        //Creating the WelcomePage GUI, and setting it as the tool window.
        logger.trace("Invoking the content factory");
        contentFactory = ContentFactory.SERVICE.getInstance();
        content = contentFactory.createContent(welcomePage.makeGUI(), "", false);
        getToolWindow.getContentManager().addContent(content);
        logger.trace("Setting tool window as the WelcomePage");
    }
}
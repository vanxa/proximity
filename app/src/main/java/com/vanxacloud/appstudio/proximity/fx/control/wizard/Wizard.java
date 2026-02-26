package com.vanxacloud.appstudio.proximity.fx.control.wizard;

import com.vanxacloud.appstudio.proximity.ProximityApp;
import com.vanxacloud.appstudio.proximity.fx.control.wizard.page.AbstractWizardDialogPage;
import com.vanxacloud.appstudio.proximity.fx.control.wizard.page.ProjectPage;
import com.vanxacloud.appstudio.proximity.fx.control.wizard.page.SettingsPage;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import org.controlsfx.dialog.WizardPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Wizard {

    private org.controlsfx.dialog.Wizard wizard;
    private static final Logger log = LoggerFactory.getLogger(Wizard.class);

    public Wizard() {
        try {
            initializeWizard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void initializeWizard() throws IOException {
        List<WizardPane> pages = new ArrayList<>();
        this.wizard = new org.controlsfx.dialog.Wizard();
        this.wizard.getDialog().setResizable(true);

        WizardPane projectPane = createProjectPage();
        pages.add(projectPane);
        pages.add(createSettingsPage(projectPane));
        wizard.setTitle("Wizard");
        wizard.setFlow(new org.controlsfx.dialog.Wizard.LinearFlow(pages));
    }

    private WizardPane createSettingsPage(WizardPane projectPane) throws IOException {
        WizardPane pane = createWizardPane();
        SettingsPage settingsPage = new SettingsPage(pane, projectPane);
        return loadPane(pane, "wizard/settings.fxml", settingsPage);

    }

    private WizardPane createProjectPage() throws IOException {
        WizardPane pane = createWizardPane();
        ProjectPage projectPage = new ProjectPage(pane);
        return loadPane(pane, "wizard/projects.fxml", projectPage);
    }

    private WizardPane loadPane(WizardPane pane, String resourcePath, AbstractWizardDialogPage page) throws IOException {
        URL res = ProximityApp.class.getResource(resourcePath);
        if (res == null) {
            throw new RuntimeException("Unable to locate wizard page");
        }
        FXMLLoader loader = new FXMLLoader(res);
        loader.setControllerFactory(clazz -> page);
        pane.setContent(loader.load());
        return pane;
    }

    private WizardPane createWizardPane() {
        WizardPane pane = new WizardPane();
        pane.getStylesheets().add("/style.css");
        pane.getStyleClass().remove("wizard-pane"); // Remove annoying png in default wizard-pane
        return pane;
    }

    public Settings show() {
        Optional<ButtonType> buttonTypeOptional = wizard.showAndWait();
        if (buttonTypeOptional.isEmpty()) {
            System.out.println("Wizard not completed");
            return null;
        }
        ButtonType buttonType = buttonTypeOptional.get();
        if (buttonType.equals(ButtonType.CANCEL)) {
            System.out.println("Wizard cancelled");
        } else if (buttonType.equals(ButtonType.OK)) {
            System.out.println("Wizard completed");
        } else if (buttonType.equals(ButtonType.FINISH)) {
            return new Settings(wizard.getSettings());
        }
        return null;
    }

    public record Settings(ObservableMap<String, Object> settings) {

    }
}

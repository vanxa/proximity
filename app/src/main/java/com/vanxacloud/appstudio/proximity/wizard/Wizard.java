package com.vanxacloud.appstudio.proximity.wizard;

import com.vanxacloud.appstudio.proximity.ProximityApp;
import com.vanxacloud.appstudio.proximity.wizard.page.controller.ProjectController;
import com.vanxacloud.appstudio.proximity.wizard.page.controller.SettingsController;
import com.vanxacloud.appstudio.proximity.wizard.page.state.WizardPageState;
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
        WizardPageState state = new WizardPageState();
        for (String page : new String[]{"projects.fxml", "settings.fxml"}) {
            WizardPane pane = new WizardPane();
            pane.getStylesheets().add("/style.css");
            pane.getStyleClass().remove("wizard-pane"); // Remove annoying png in default wizard-pane
            URL res = ProximityApp.class.getResource(String.format("wizard/%s", page));
            if (res == null) {
                throw new RuntimeException("Unable to locate wizard page");
            }
            FXMLLoader loader = new FXMLLoader(res);
            loader.setControllerFactory(clazz -> {
                try {
                    if (clazz.isAssignableFrom(ProjectController.class)) {
                        return new ProjectController(state);
                    }
                    if (clazz.isAssignableFrom(SettingsController.class)) {
                        return new SettingsController(state);
                    }
                    return clazz.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            });
            pane.setContent(loader.load());
            pages.add(pane);
        }
        // Show wizard and handle result

        wizard.setTitle("Wizard");
        wizard.setFlow(new org.controlsfx.dialog.Wizard.LinearFlow(pages));
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

package com.vanxacloud.appstudio.proximity.wizard;

import com.vanxacloud.appstudio.proximity.ProximityApp;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import org.controlsfx.dialog.WizardPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Wizard {


    private final int numPages;
    private org.controlsfx.dialog.Wizard wizard;

    public Wizard() {
        this(1);
    }

    public Wizard(int numPages) {
        this.numPages = numPages;
        try {
            initializeWizard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void initializeWizard() throws IOException {
        List<WizardPane> pages = new ArrayList<>();
        for (int page = 1; page <= numPages; page++) {
            WizardPane pane = new WizardPane();
            pane.getStylesheets().add("/style.css");
            pane.getStyleClass().remove("wizard-pane"); // Remove annoying png in default wizard-pane
            URL res = ProximityApp.class.getResource(String.format("wizard/page%d/page.fxml", page));
            if (res == null) {
                throw new RuntimeException("Unable to locate wizard page");
            }
            pane.setContent(FXMLLoader.load(res));
            pages.add(pane);
        }
        // Show wizard and handle result
        this.wizard = new org.controlsfx.dialog.Wizard();
        this.wizard.getDialog().setResizable(true);
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

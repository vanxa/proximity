package com.vanxacloud.appstudio.proximity.fx.control.wizard.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.dialog.WizardPane;

public class SettingsPage extends AbstractWizardDialogPage {
    
    private final WizardPane projectWizardPane;
    @FXML
    public TextField existingSettingsFilePath;
    @FXML
    public RadioButton settingsFromProjectRadioButton;

    public SettingsPage(WizardPane settingsWizardPane, WizardPane projectWizardPane) {
        super(settingsWizardPane);
        this.projectWizardPane = projectWizardPane;

    }

    @FXML
    private void initialize() {
        Node existingProjectNode = projectWizardPane.lookup("#existingProjectRadioButton");
        if (existingProjectNode instanceof RadioButton existingProjectNodeRadioButton) {
            this.settingsFromProjectRadioButton.disableProperty().bind(existingProjectNodeRadioButton.selectedProperty().not());
            this.settingsFromProjectRadioButton.selectedProperty().bind(existingProjectNodeRadioButton.selectedProperty());
        }
    }

    @FXML
    public void handleOpenSettingsFile(ActionEvent event) {
        String title = "Load from configuration file";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        java.io.File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            existingSettingsFilePath.setText(file.getAbsolutePath());
        }

    }


}

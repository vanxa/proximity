package com.vanxacloud.appstudio.proximity.wizard.page.controller;

import com.vanxacloud.appstudio.proximity.wizard.page.state.WizardPageState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SettingsController {

    private final WizardPageState state;
    @FXML
    public TextField existingSettingsFilePath;
    @FXML
    public RadioButton settingsFromProjectRadioButton;

    public SettingsController(WizardPageState state) {
        this.state = state;

    }

    @FXML
    private void initialize() {
        if (this.state.getExistingProjectRadioButton() != null) {
            this.settingsFromProjectRadioButton.disableProperty().bind(this.state.getExistingProjectRadioButton().selectedProperty().not());
            this.settingsFromProjectRadioButton.selectedProperty().bind(this.state.getExistingProjectRadioButton().selectedProperty());
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

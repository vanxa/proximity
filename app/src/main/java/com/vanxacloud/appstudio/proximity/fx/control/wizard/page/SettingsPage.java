package com.vanxacloud.appstudio.proximity.fx.control.wizard.page;

import com.vanxacloud.appstudio.proximity.fx.validation.decoration.LabelDecorator;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Component
public class SettingsPage extends WizardDialogPage {

    private final SimpleBooleanProperty valid = new SimpleBooleanProperty(true);
    /*
           ------------------------------------------------------------------------
           ------------------Load from configuration file ------------------------------------------
           ------------------File: --------------------------------------------
           ------------------Error label ------------------------------------------
            */
    @FXML
    public RadioButton existingSettingsFilePathRadioButton;
    @FXML
    public TextField existingSettingsFilePath;
    @FXML
    public Label existingSettingsFileErrorLabel;

    @FXML
    private void initialize() {
        Validator validator = new Validator();
        validator.createCheck()
                .dependsOn("existingProject", existingSettingsFilePathRadioButton.selectedProperty())
                .dependsOn("existingProjectFilePath", existingSettingsFilePath.textProperty())
                .withMethod(context -> {
                    boolean isValid = true;
                    if (existingSettingsFilePathRadioButton.isSelected()) {
                        if (existingSettingsFilePath.getText().isEmpty()) {
                            isValid = false;
                        } else {
                            isValid = Paths.get(existingSettingsFilePath.getText()).toFile().exists();
                        }
                    }
                    if (!isValid) {
                        context.error("Choose a valid file");
                    }
                    valid.set(isValid);
                })
                .decoratingWith(LabelDecorator::new)
                .decorates(existingSettingsFileErrorLabel)
                .immediate();
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

    @Override
    public BooleanExpression isValid() {
        return valid;
    }

    @Override
    void stateChanged(MapChangeListener.Change<? extends String, ?> change) {
        change.getMap().get("existingProjectRadioButton");
    }

}

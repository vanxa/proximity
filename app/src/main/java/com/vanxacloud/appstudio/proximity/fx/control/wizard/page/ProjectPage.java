package com.vanxacloud.appstudio.proximity.fx.control.wizard.page;

import com.vanxacloud.appstudio.proximity.config.Constants;
import com.vanxacloud.appstudio.proximity.config.LanguageConfig;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.synedra.validatorfx.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ProjectPage implements WizardDialogPage {

    /*
        ------------------------------------------------------------------------
        ------------------New Project ------------------------------------------
        ------------------Project Name: --------------------------------------------
        ------------------Project File: --------------------------------------------
        ------------------Error label ------------------------------------------
         */
    @Autowired
    private LanguageConfig languageConfig;

    @FXML
    public RadioButton newProjectRadioButton;

    @FXML
    public TextField newProjectName;


    @FXML
    public TextField newProjectFilePath;

    @FXML
    public Label newProjectErrorLabel;


    /*
    ------------------------------------------------------------------------
    ------------------Open Project ------------------------------------------
    ------------------Filename: --------------------------------------------
    ------------------Error label ------------------------------------------
     */

    @FXML
    public RadioButton existingProjectRadioButton;

    @FXML
    public TextField existingProjectFilePath;

    @FXML
    public Label existingProjectErrorLabel;
    private final SimpleBooleanProperty valid = new SimpleBooleanProperty(true);

    @FXML
    private void initialize() {
        Validator validator = new Validator();
        validator.createCheck()
                .dependsOn("newProject", newProjectRadioButton.selectedProperty())
                .withMethod(c -> {
                    if (newProjectRadioButton.isSelected()) {
                        if (newProjectFilePath.getText().isEmpty() || newProjectName.getText().isEmpty()) {
                            c.error("Please fill out all the fields");
                            valid.set(false);
                        } else {
                            valid.set(true);
                        }
                    } else {
                        valid.set(true);
                    }
                })
                .decorates(newProjectErrorLabel)
                .immediate();

//        getWizard().invalidProperty().bind(validator.containsErrorsProperty());
    }

    @FXML
    public void handleOpenFile(ActionEvent event) {
        EventTarget target = event.getTarget();
        if (target instanceof Button button) {
            TextField targetField;
            String title;
            if ("newProjectButton".equals(button.getId())) {
                title = "New Project File";
                targetField = newProjectFilePath;
            } else if ("openProjectButton".equals(button.getId())) {
                title = "Open Project File";
                targetField = existingProjectFilePath;
            } else {
                throw new IllegalArgumentException(String.format("Invalid event for unknown button %s", button.getId()));
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(title);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            java.io.File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                targetField.setText(file.getAbsolutePath());
            }
        }

    }

    public void setDefaultNewProject(MouseEvent mouseEvent) {
        if (newProjectFilePath.getText().isEmpty()) {
            String date = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.getDefault()).format(LocalDateTime.now());
            newProjectFilePath.setText(String.format("%s.%s", date, Constants.PROJECT_FILE_EXTENSION));
            newProjectName.setText(date);
        }

    }

    @Override
    public BooleanExpression isValid() {
        return valid;
    }
}

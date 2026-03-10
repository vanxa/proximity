package com.vanxacloud.appstudio.proximity.fx.control.wizard.page;

import com.vanxacloud.appstudio.proximity.config.Constants;
import com.vanxacloud.appstudio.proximity.fx.validation.decoration.LabelDecorator;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.MapChangeListener;
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
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class ProjectPage extends WizardDialogPage {


    BooleanProperty initializedProjectName = new SimpleBooleanProperty(false);
    /*
        ------------------------------------------------------------------------
        ------------------New Project ------------------------------------------
        ------------------Project Name: --------------------------------------------
        ------------------Project File: --------------------------------------------
        ------------------Error label ------------------------------------------
         */

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
                .dependsOn("initializedNewProjectName", initializedProjectName)
                .dependsOn("newProjectText", newProjectName.textProperty())
                .dependsOn("newProjectFilePath", newProjectFilePath.textProperty())
                .withMethod(c -> {
                    if (newProjectRadioButton.isSelected()) {
                        if (newProjectFilePath.getText().isEmpty() || newProjectName.getText().isEmpty()) {
                            c.error("Invalid project name");
                            valid.set(false);
                        } else {
                            valid.set(true);
                        }
                    } else {
                        valid.set(true);
                    }
                })
                .decoratingWith(LabelDecorator::new)
                .decorates(newProjectErrorLabel)
                .immediate();

        validator.createCheck()
                .dependsOn("existingProject", existingProjectRadioButton.selectedProperty())
                .dependsOn("existingProjectFilePath", existingProjectFilePath.textProperty())
                .withMethod(context -> {
                    boolean isValid = true;
                    if (existingProjectRadioButton.isSelected()) {
                        if (existingProjectFilePath.getText().isEmpty()) {
                            isValid = false;
                        } else {
                            isValid = Paths.get(existingProjectFilePath.getText()).toFile().exists();
                        }
                    }
                    if (!isValid) {
                        context.error("Choose a valid file");
                    }
                    valid.set(isValid);
                })
                .decoratingWith(LabelDecorator::new)
                .decorates(existingProjectErrorLabel)
                .immediate();
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
            initializedProjectName.set(true);
        }

    }


    @Override
    public BooleanExpression isValid() {
        return valid;
    }

    @Override
    void stateChanged(MapChangeListener.Change<? extends String, ?> change) {

    }
}

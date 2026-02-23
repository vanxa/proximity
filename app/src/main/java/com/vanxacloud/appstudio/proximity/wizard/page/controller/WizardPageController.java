package com.vanxacloud.appstudio.proximity.wizard.page.controller;

import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class WizardPageController {


    public TextField projectFilePath;


    @FXML
    public void handleOpenFile(ActionEvent event) {
        EventTarget target = event.getTarget();
        if (target instanceof Button button) {
            String title = switch (button.getId()) {
                case "newProjectButton" -> "New Project File";
                case "openProjectButton" -> "Open Project File";
                default -> null;
            };
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(title);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            java.io.File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                projectFilePath.setText(file.getAbsolutePath());
            }
        }

    }

    public void setDefaultNewProject(ActionEvent actionEvent) {
        
    }
}

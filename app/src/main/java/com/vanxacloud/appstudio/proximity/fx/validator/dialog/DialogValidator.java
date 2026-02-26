package com.vanxacloud.appstudio.proximity.fx.validator.dialog;

import com.vanxacloud.appstudio.proximity.fx.validator.NodeValidator;
import javafx.scene.control.DialogPane;
import net.synedra.validatorfx.Validator;

import java.lang.reflect.Field;

public abstract class DialogValidator implements NodeValidator {
    private final Validator validator;

    public DialogValidator(DialogPane dialogPane) {
        this.validator = new Validator();

        for (Field declaredField : getClass().getDeclaredFields()) {
            declaredField.getAnnotation()
        }


        validator.createCheck()
                .dependsOn("newProject", newProjectRadioButton.selectedProperty())
                .withMethod(c -> {
                    if (newProjectRadioButton.isSelected()) {
                        if (newProjectFilePath.getText().isEmpty() || newProjectName.getText().isEmpty()) {
                            c.error("Please fill out all the fields");
                        }
                    }
                })
                .decorates(newProjectErrorLabel)
                .decoratingWith(this::sumDecorator)
                .immediate();
    }
}

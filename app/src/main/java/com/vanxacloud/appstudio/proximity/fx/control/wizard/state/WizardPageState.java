package com.vanxacloud.appstudio.proximity.fx.control.wizard.state;

import javafx.scene.control.RadioButton;
import org.springframework.stereotype.Component;

@Component
public class WizardPageState {

    private RadioButton existingProjectRadioButton;

    public void setExistingProjectRadioButton(RadioButton existingProjectRadioButton) {
        this.existingProjectRadioButton = existingProjectRadioButton;
    }

    public RadioButton getExistingProjectRadioButton() {
        return existingProjectRadioButton;
    }
}

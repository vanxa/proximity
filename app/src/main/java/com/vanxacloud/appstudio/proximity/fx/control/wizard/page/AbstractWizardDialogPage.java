package com.vanxacloud.appstudio.proximity.fx.control.wizard.page;

import org.controlsfx.dialog.WizardPane;

public abstract class AbstractWizardDialogPage {

    private final WizardPane pane;

    protected AbstractWizardDialogPage(WizardPane pane) {
        this.pane = pane;
    }

    public WizardPane getPane() {
        return pane;
    }
}

package com.vanxacloud.appstudio.proximity.wizard;

import org.controlsfx.dialog.Wizard;

public class WizardPane extends org.controlsfx.dialog.WizardPane {

    @Override
    public void onEnteringPage(Wizard wizard) {

        super.onExitingPage(wizard);
    }
}

package com.vanxacloud.appstudio.proximity.event;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public interface Events {

    class ReadyForWizardEvent extends ApplicationEvent {

        public ReadyForWizardEvent(Object source) {
            super(source);
        }

        public Stage getStage() {
            return Stage.class.cast(getSource());
        }
    }
}

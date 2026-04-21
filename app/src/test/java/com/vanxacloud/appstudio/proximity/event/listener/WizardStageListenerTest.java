package com.vanxacloud.appstudio.proximity.event.listener;

import com.vanxacloud.appstudio.proximity.app.event.Events;
import com.vanxacloud.appstudio.proximity.app.event.listener.WizardStageListener;
import com.vanxacloud.appstudio.proximity.fx.control.wizard.Wizard;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.Collections;

import static org.mockito.Mockito.doReturn;

@ExtendWith({ApplicationExtension.class, SpringExtension.class})
@SpringBootTest
class WizardStageListenerTest {

    @Autowired
    private WizardStageListener listener;

    @Autowired
    @MockitoSpyBean
    private Wizard wizard;

    private Stage stage;

    @Start
    public void start(Stage stage) {
        this.stage = stage;

    }

    @Test
    void test_openWizard_cancel_return(FxRobot robot) {
        doReturn(null).when(wizard).show();
        Events.ReadyForWizardEvent event = new Events.ReadyForWizardEvent(stage);
        robot.interact(() -> {
            listener.onApplicationEvent(event);
        });

    }

    @Test
    void test_openWizard_complete_showMainWindow(FxRobot robot) {
        doReturn(new Wizard.Settings(FXCollections.observableMap(Collections.emptyMap()))).when(wizard).show();
        Events.ReadyForWizardEvent event = new Events.ReadyForWizardEvent(stage);
        robot.interact(() -> {
            listener.onApplicationEvent(event);
            robot.targetWindow("Hello!");
        });

    }
}
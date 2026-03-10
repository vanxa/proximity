package com.vanxacloud.appstudio.proximity.event.listener;

import com.vanxacloud.appstudio.proximity.JavaFxApplication;
import com.vanxacloud.appstudio.proximity.event.Events;
import com.vanxacloud.appstudio.proximity.fx.control.wizard.Wizard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WizardStageListener implements ApplicationListener<Events.ReadyForWizardEvent> {

    private final ApplicationContext ac;
    private static final Logger log = LoggerFactory.getLogger(WizardStageListener.class);

    WizardStageListener(ApplicationContext ac) {
        this.ac = ac;
    }

    @Override
    public void onApplicationEvent(Events.ReadyForWizardEvent event) {
        Stage stage = event.getStage();
        Wizard wizard = ac.getBean(Wizard.class);
        Wizard.Settings settings = wizard.show();
        if (settings == null) {
            // Cancelled, closed
            return;
        }
        log.info("{}", settings);


        FXMLLoader fxmlLoader = new FXMLLoader(JavaFxApplication.class.getResource("boot-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            scene.getStylesheets().add("/style.css");
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

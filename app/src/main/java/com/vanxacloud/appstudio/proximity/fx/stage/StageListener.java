package com.vanxacloud.appstudio.proximity.fx.stage;

import com.vanxacloud.appstudio.proximity.JavaFxApplication;
import com.vanxacloud.appstudio.proximity.fx.control.wizard.Wizard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageListener implements ApplicationListener<JavaFxApplication.StageReadyEvent> {


    @Override
    public void onApplicationEvent(JavaFxApplication.StageReadyEvent event) {
        Stage stage = event.getStage();
        Wizard wizard = new Wizard();
        Wizard.Settings settings = wizard.show();
        if (settings == null) {
            // Cancelled, closed
            return;
        }
        System.out.println(settings);
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

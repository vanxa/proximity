package com.vanxacloud.appstudio.proximity;

import com.vanxacloud.appstudio.proximity.fx.control.wizard.Wizard;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProximityApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        CSSFX.start();
//        stage.setResizable(true);
        Wizard wizard = new Wizard();
        Wizard.Settings settings = wizard.show();
        if (settings == null) {
            // Cancelled, closed
            return;
        }

        System.out.println(settings);


        FXMLLoader fxmlLoader = new FXMLLoader(ProximityApp.class.getResource("boot-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        scene.getStylesheets().add("/style.css");
        stage.show();
    }

}

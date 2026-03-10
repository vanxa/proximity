package com.vanxacloud.appstudio.proximity;

import com.vanxacloud.appstudio.proximity.config.ConfigurationManager;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ProximityApplication {
    public static void main(String[] args) {
        ConfigurationManager.getInstance().initialize();
        Application.launch(JavaFxApplication.class, args);
    }
}

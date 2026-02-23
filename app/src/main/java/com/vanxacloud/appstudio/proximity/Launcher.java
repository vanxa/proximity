package com.vanxacloud.appstudio.proximity;

import com.vanxacloud.appstudio.proximity.config.ConfigurationManager;
import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        ConfigurationManager.getInstance().initialize();
        Application.launch(ProximityApp.class, args);
    }
}

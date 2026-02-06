package com.vanxacloud.appstudio.proximity;

import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        CSSFX.start();
        Application.launch(ProximityApp.class, args);
    }
}

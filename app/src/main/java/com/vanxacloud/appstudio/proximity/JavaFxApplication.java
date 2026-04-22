package com.vanxacloud.appstudio.proximity;

import com.vanxacloud.appstudio.proximity.app.event.Events;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;

@GeneratedSkipCoverage
public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext context;
    private Stage mainStage;


    @Override
    public void init() throws Exception {
        ApplicationContextInitializer<GenericApplicationContext> initializer =
                ac -> {
                    ac.registerBean(Application.class, () -> JavaFxApplication.this);
                    ac.registerBean(Parameters.class, this::getParameters);
                    ac.registerBean(HostServices.class, this::getHostServices);
                };
        this.context = new SpringApplicationBuilder()
                .sources(ProximityApplication.class)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage stage) throws IOException {
        CSSFX.start();
        this.mainStage = stage;
        this.context.publishEvent(new Events.ReadyForWizardEvent(stage));
    }

    @Override
    public void stop() throws Exception {
        this.context.publishEvent(new Events.ApplicationStoppingEvent(this.mainStage));
        this.context.close();
        Platform.exit();
    }
}



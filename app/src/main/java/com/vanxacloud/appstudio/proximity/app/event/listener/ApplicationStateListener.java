package com.vanxacloud.appstudio.proximity.app.event.listener;

import com.vanxacloud.appstudio.proximity.app.MainApp;
import com.vanxacloud.appstudio.proximity.app.event.Events;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStateListener implements ApplicationListener<Events.ApplicationStateEvent> {

    private final ApplicationContext ac;
    private final MainApp app;

    public ApplicationStateListener(GenericApplicationContext ac, MainApp app) {
        this.ac = ac;
        this.app = app;
    }

    @Override
    public void onApplicationEvent(Events.ApplicationStateEvent event) {
        switch (event) {
            case Events.ApplicationStartingEvent ev -> startApplication(ev);
            case Events.ApplicationStoppingEvent ev -> stopApplication(ev);
            case Events.ApplicationStoppedEvent ev -> handleClose(ev);
            case Events.ApplicationRunningEvent ev -> handleRunning(ev);
            case Events.ReadyForWizardEvent ev -> {
            }
            default -> throw new RuntimeException(String.format("Unsupported event %s", event.getClass().getSimpleName()));
        }

    }

    private void handleRunning(Events.ApplicationRunningEvent ev) {

    }

    private void handleClose(Events.ApplicationStoppedEvent ev) {

    }

    private void stopApplication(Events.ApplicationStoppingEvent ev) {

    }

    private void startApplication(Events.ApplicationStartingEvent ev) {
        app.start(ev.getStage(), ev.getWizardSettings());
    }
}

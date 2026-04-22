package com.vanxacloud.appstudio.proximity.app;

import com.vanxacloud.appstudio.proximity.JavaFxApplication;
import com.vanxacloud.appstudio.proximity.app.proxy.ProximityProxy;
import com.vanxacloud.appstudio.proximity.fx.control.wizard.Wizard;
import com.vanxacloud.appstudio.proximity.fx.splash.SplashLoader;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The main app class responsible for managing the {@link ProximityProxy} lifecycle (start, stop) and displaying the main application GUI
 * This class does not manage the GUI properties and controls
 */
@Component
public class MainApp {
    private static final Logger log = LoggerFactory.getLogger(MainApp.class);
    private final GenericApplicationContext ac;
    private final ProximityProxy proxy;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    MainApp(GenericApplicationContext ac) {
        this.ac = ac;
        this.proxy = new ProximityProxy();
        this.ac.registerBean(ProximityProxy.class, () -> proxy);
    }


    /**
     * Starts the proxy thread and opens the main app window.
     *
     * @param stage    - the stage for the main app
     * @param settings - the wizard settings
     */
    public void start(Stage stage, Wizard.Settings settings) {
        SplashLoader splashLoader = new SplashLoader();
        final Task<Boolean> proxyStartupTask = new Task<>() {
            @Override
            protected Boolean call() throws InterruptedException {
                updateMessage("Starting proxy . . .");
                executorService.submit(proxy);
                Thread.sleep(1000);
                boolean initialized = false;
                boolean ready = false;
                while (!(initialized && ready)) {
                    if (proxy.isInitialized()) {
                        updateMessage("Proxy initialized");
                        updateProgress(1, 2);
                        initialized = true;
                    }
                    if (proxy.isReady()) {
                        updateMessage("Proxy running");
                        updateProgress(2, 2);
                        ready = true;
                    }
                    log.info("Sleeping");
                    Thread.sleep(1000);
                }
                log.info("Done");
                return true;
            }
        };

        splashLoader.showSplash(
                proxyStartupTask,
                () -> showMainStage(stage)
        );
        new Thread(proxyStartupTask).start();
    }

    private void showMainStage(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFxApplication.class.getResource("/boot-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.setMaximized(true);
            scene.getStylesheets().add("/style.css");
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        log.info("Closing application");
        try {
            log.debug("Shutting down thread pool");
            executorService.shutdown();
            log.info("Stopping proxy");
            proxy.stop();
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            log.warn("Interrupted while closing. Ignoring inerruption and proceeding with close");
        }
        log.info("Terminating");
        executorService.shutdownNow();
    }


    public interface InitCompletionHandler {
        void complete();
    }
}

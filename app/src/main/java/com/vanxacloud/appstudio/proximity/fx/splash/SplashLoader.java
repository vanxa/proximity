package com.vanxacloud.appstudio.proximity.fx.splash;

import com.vanxacloud.appstudio.proximity.GeneratedSkipCoverage;
import com.vanxacloud.appstudio.proximity.app.MainApp;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@GeneratedSkipCoverage
public class SplashLoader {


    private static final int SPLASH_WIDTH = 676;
    private static final int SPLASH_HEIGHT = 227;


    public void showSplash(final Stage initStage,
                           Task<?> task,
                           MainApp.InitCompletionHandler initCompletionHandler) {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/splash/splash.fxml"));
        try {
//            loader.setController(this);
//            Pane splashLayout = loader.load();
            ImageView splash = new ImageView(new Image("/images/logo.png"));
            ProgressBar loadProgress = new ProgressBar();
            loadProgress.setPrefWidth(SPLASH_WIDTH - 20);
            Label progressText = new Label("Will find friends for peanuts . . .");
            VBox splashLayout = new VBox();
            splashLayout.getChildren().addAll(splash, loadProgress, progressText);
            progressText.setAlignment(Pos.CENTER);
            splashLayout.setStyle(
                    "-fx-padding: 5; " +
                            "-fx-background-color: cornsilk; " +
                            "-fx-border-width:5; " +
                            "-fx-border-color: " +
                            "linear-gradient(" +
                            "to bottom, " +
                            "chocolate, " +
                            "derive(chocolate, 50%)" +
                            ");"
            );
            splashLayout.setEffect(new DropShadow());
            progressText.textProperty().bind(task.messageProperty());
            loadProgress.progressProperty().bind(task.progressProperty());
            task.stateProperty().addListener((observableValue, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    loadProgress.progressProperty().unbind();
                    loadProgress.setProgress(1);
//                    initStage.toFront();
//                    FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
//                    fadeSplash.setFromValue(1.0);
//                    fadeSplash.setToValue(0.0);
//                    fadeSplash.setOnFinished(actionEvent -> initStage.close());
//                    fadeSplash.play();
                    initStage.close();
                    initCompletionHandler.complete();
                }
            });

            Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
            final Rectangle2D bounds = Screen.getPrimary().getBounds();
            initStage.setScene(splashScene);
            initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
            initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
            initStage.initStyle(StageStyle.TRANSPARENT);
            initStage.setAlwaysOnTop(true);
            initStage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

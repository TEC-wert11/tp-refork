import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Main JavaFX application for the XMOKE chatbot GUI.
 */
public class MainApp extends Application {
    private Stage primaryStage;

    /** Entry point when run as main class (e.g. from IDE or gradlew run). */
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/view/HomePage.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            primaryStage.setScene(scene);
            primaryStage.setTitle("XMOKE");
            primaryStage.setMinHeight(300.0);
            primaryStage.setMinWidth(400.0);

            HomePageController homeController = fxmlLoader.getController();
            homeController.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Switches to the chat window for the given user and Xmoke instance (user-specific data). */
    public void showChatScene(String userName, xmoke.Xmoke xmoke) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            primaryStage.setScene(scene);

            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setMainApp(this);
            mainWindow.setXmoke(xmoke);
            String userImagePath = "/images/" + userName.trim().replace(" ", "_") + ".jpg";
            mainWindow.setUserImage(loadImage(userImagePath));
            mainWindow.setDukeImage(loadImage("/images/DaDuke.jpg"));
            mainWindow.showWelcomeMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Switches back to the home page (user selection). */
    public void showHomeScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/view/HomePage.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            primaryStage.setScene(scene);

            HomePageController homeController = fxmlLoader.getController();
            homeController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Image loadImage(String path) {
        try (var stream = getClass().getResourceAsStream(path)) {
            if (stream != null) {
                Image img = new Image(stream);
                if (img.getWidth() > 1 && img.getHeight() > 1) {
                    return img;
                }
            }
        } catch (Exception ignored) {
            // Use fallback image below
        }
        WritableImage fallback = new WritableImage(100, 100);
        var pw = fallback.getPixelWriter();
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                pw.setColor(x, y, Color.LIGHTGRAY);
            }
        }
        return fallback;
    }
}

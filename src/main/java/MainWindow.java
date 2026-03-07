import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private MainApp mainApp;
    private xmoke.Xmoke xmoke;
    private Image userImage;
    private Image dukeImage;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /** Initializes the scroll pane and binds scroll position to bottom. */
    @FXML
    public void initialize() {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToWidth(true);
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    public void setXmoke(xmoke.Xmoke x) {
        xmoke = x;
    }

    public void setUserImage(Image img) {
        userImage = img;
    }

    public void setDukeImage(Image img) {
        dukeImage = img;
    }

    /** Shows the initial greeting from the bot (with profile photo) when the chat page opens. */
    public void showWelcomeMessage() {
        Image img = dukeImage != null ? dukeImage : new javafx.scene.image.WritableImage(1, 1);
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog("How may I help you, my young padawan?", img));
    }

    @FXML
    private void handleUserInput() {
        if (xmoke == null) {
            showErrorInChat("Chat is not ready. Please go back and try again.");
            return;
        }
        String input = userInput.getText();
        String response;
        try {
            response = xmoke.getResponse(input);
        } catch (Exception e) {
            String msg = e.getMessage() != null ? e.getMessage() : "Something went wrong.";
            showErrorInChat(msg);
            return;
        }
        Image userImg = userImage != null ? userImage : new javafx.scene.image.WritableImage(1, 1);
        Image dukeImg = dukeImage != null ? dukeImage : new javafx.scene.image.WritableImage(1, 1);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImg),
                DialogBox.getDukeDialog(response, dukeImg)
        );
        userInput.clear();
        if (input.trim().equalsIgnoreCase("bye")) {
            Stage stage = (Stage) userInput.getScene().getWindow();
            stage.close();
        }
    }

    private void showErrorInChat(String message) {
        Image img = dukeImage != null ? dukeImage : new javafx.scene.image.WritableImage(1, 1);
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(message, img));
    }

    @FXML
    private void handleBack() {
        if (mainApp != null) {
            mainApp.showHomeScene();
        }
    }
}

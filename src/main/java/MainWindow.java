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
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog("How may I help you, my young padawan?", dukeImage));
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = xmoke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();
        if (input.trim().equalsIgnoreCase("bye")) {
            Stage stage = (Stage) userInput.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void handleBack() {
        mainApp.showHomeScene();
    }
}

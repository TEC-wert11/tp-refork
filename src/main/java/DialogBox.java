import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * A dialog box with text and an avatar image.
 */
public class DialogBox extends HBox {
    private Label dialog;
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        dialog = new Label(text);
        dialog.getStyleClass().add("chat-dialog");
        displayPicture = new ImageView(img);
        dialog.setWrapText(true);
        dialog.setMinHeight(Region.USE_PREF_SIZE);
        dialog.setMaxHeight(Double.MAX_VALUE);
        dialog.setMaxWidth(280);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);
        setAlignment(Pos.TOP_RIGHT);
        dialog.setAlignment(Pos.TOP_RIGHT);
        HBox.setHgrow(dialog, Priority.ALWAYS);
        getChildren().addAll(dialog, displayPicture);
    }

    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    public static DialogBox getDukeDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}

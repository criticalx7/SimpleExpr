package client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Animation;

import java.io.IOException;

/**
 * Author: Mr.Chatchapol Rasameluangon
 * ID: 5810404901
 */
public class SimpleUIController {
    @FXML
    protected Label exprLabel;

    @FXML
    protected TextField exprField;

    @FXML
    protected ToggleButton prefixButton;

    @FXML
    protected ToggleButton postfixButton;

    @FXML
    protected TextField resultField;

    @FXML
    protected VBox root;

    private Connector connector;
    private final ToggleGroup toggleGroup = new ToggleGroup();


    public void initialize() {
        connector = new Connector("localhost", 31463);
        setupRoot();
        setupFieldListener();
        prefixButton.setToggleGroup(toggleGroup);
        postfixButton.setToggleGroup(toggleGroup);
        postfixButton.setSelected(true);
    }

    private void setupRoot() {
        Platform.runLater(root::requestFocus);
        root.setOnMouseClicked(e -> {
            root.requestFocus();
            resetField();
        });
        root.setOnKeyPressed(e -> {
            if (e.getCode() != KeyCode.ESCAPE) exprField.requestFocus();
        });
    }

    private void setupFieldListener() {
        exprField.setOnMouseClicked(e -> Animation.animateTextField(exprField, Duration.millis(500), 40.00));

        exprField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                exprField.clear();
                Platform.runLater(root::requestFocus);
            }
        });
    }

    private void resetField() {
        Animation.animateTextField(exprField, Duration.millis(500), 200.0);
        exprField.clear();
        resultField.clear();
    }

    @FXML
    public void onEnter() {
        if (!exprField.getText().isEmpty()) {
            String mode = ((ToggleButton) toggleGroup.getSelectedToggle()).getText().toUpperCase();
            String result = connector.requestResult(String.format("%s %s", mode, exprField.getText()));
            resultField.setText(result);
        }
    }

    @FXML
    public void onAuthorPressed() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/authorUI.fxml"));
        try {
            Parent dialog = loader.load();
            Scene scene = new Scene(dialog);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.initOwner(root.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);

            ColorAdjust effect = new ColorAdjust();
            effect.setBrightness(-0.5);
            stage.setOnShowing(e -> root.setEffect(effect));
            stage.setOnCloseRequest(e -> root.setEffect(null));
            stage.setResizable(false);

            Platform.runLater(dialog::requestFocus);
            stage.showAndWait();
        } catch (IOException e) {
            resultField.setText("Resources not found");
        }
    }

    @FXML
    public void onPrefixSelected() {
        prefixButton.setSelected(true);
    }

    @FXML
    public void onPostfixSelected() {
        postfixButton.setSelected(true);
    }

}

package client;

import common.StatusCode;
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
 * <p>
 * A simple action map controller of UI
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
    private final ToggleGroup toggleGroup = new ToggleGroup();

    private Connector connector;
    private StatusCode phrase;


    /**
     * Initialize connector and setup various UI elements
     */
    public void initialize() {
        connector = new Connector("localhost", 31463);
        setupRoot();
        setupFieldListener();
        prefixButton.setToggleGroup(toggleGroup);
        postfixButton.setToggleGroup(toggleGroup);
        onPostfixSelected();
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

    /**
     * On enter request a result from server
     */
    @FXML
    public void onEnter() {
        if (!exprField.getText().isEmpty()) {
            String mode = phrase.getCode();
            String result = connector.requestResult(String.format("%s:%s", mode, exprField.getText()));
            resultField.setText(result);
        }
    }

    /**
     * Handle author button pressed to show Author dialog
     */
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

    /**
     * Change mode to prefix
     */
    @FXML
    public void onPrefixSelected() {
        prefixButton.setSelected(true);
        phrase = StatusCode.PREFIX;
    }

    /**
     * Change mode to postfix
     */
    @FXML
    public void onPostfixSelected() {
        postfixButton.setSelected(true);
        phrase = StatusCode.POSTFIX;
    }

}

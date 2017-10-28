package utils;

import javafx.animation.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Author: Mr.Chatchapol Rasameluangon
 * ID: 5810404901
 */
public class Animation {

    private Animation() {
    }

    @SuppressWarnings("unused")
    public static void fadeNode(Node node, Duration duration) {
        FadeTransition in = new FadeTransition(duration);
        in.setNode(node);
        in.setFromValue(0);
        in.setToValue(100);
        in.setCycleCount(1);
        in.setAutoReverse(false);
        in.setInterpolator(Interpolator.EASE_BOTH);
        in.play();
    }

    public static void animateTextField(TextField textField, Duration duration, Double target) {
        SimpleDoubleProperty margin = new SimpleDoubleProperty(VBox.getMargin(textField).getLeft());
        margin.addListener((ObservableValue<? extends Number> ob, Number o, Number n) ->
                VBox.setMargin(textField, new Insets(
                        VBox.getMargin(textField).getTop(),
                        n.doubleValue(),
                        VBox.getMargin(textField).getBottom(),
                        n.doubleValue())));


        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        timeline.getKeyFrames().add(
                new KeyFrame(duration, new KeyValue(margin, target, Interpolator.EASE_BOTH)));
        timeline.play();
    }
}

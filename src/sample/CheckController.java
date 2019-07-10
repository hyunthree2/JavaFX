package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckController implements Initializable {
    @FXML private WebView webView;
    @FXML private Label textLabel;

    public void normAction(ActionEvent event) {
        WebEngine webEngine = webView.getEngine();
        webEngine.load("http://164.125.7.61/speller/");
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<>() {
            boolean check = false;
            @Override
            public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                if (newState == Worker.State.SUCCEEDED && !check) {
                    webEngine.executeScript("document.getElementById('text1').value='" + textLabel.getText().replaceAll("\n", "\\n") + "'");
                    webEngine.executeScript("fFirstSpell();");
                    check = true;
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setText(String text) {
        textLabel.setVisible(false);
        textLabel.setText(text);
    }

    public void backAction(ActionEvent event) {
        Stage stage = (Stage) textLabel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}

package com.example.coursework;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class HomePage {

    @FXML
    private Button buttonAuthorization;
    @FXML
    public void close() {
        Stage stage = (Stage) buttonAuthorization.getScene().getWindow();
        stage.close();
    }


    @FXML
    void initialize() {

        buttonAuthorization.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("authorization.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 409, 494);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    stage.getIcons().add(new Image("C:/Users/Anna/IdeaProjects/coursework/logo.png"));
                    close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
package com.example.coursework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(com.example.coursework.Main.class.getResource("homePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 764, 573);
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image("C:/Users/Anna/IdeaProjects/coursework/logo.png"));
    }

    public static void main(String[] args) {
        launch();
    }
}
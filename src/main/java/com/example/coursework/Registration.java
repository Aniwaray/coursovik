package com.example.coursework;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Registration {
    @FXML
    private Button buttonRegistration;

    @FXML
    private TextField regFio, regLogin, regPassword, regTel;

    Database database = new Database();

    @FXML
    public void close() {
        Stage stage = (Stage) buttonRegistration.getScene().getWindow();
        stage.close();
    }


    @FXML
    void initialize() {

        buttonRegistration.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (!regFio.getText().isEmpty() && !regLogin.getText().isEmpty() && !regPassword.getText().isEmpty() && !regTel.getText().isEmpty()) {
                        database.clientAdd(regFio.getText(), regLogin.getText(), regPassword.getText(), regTel.getText());
                        System.out.println("Вы зарегистрированны.");
                        close();

                    }else {
                        System.out.println("Заполните все поля.");
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
package com.example.coursework;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Registration {
    @FXML
    private Button buttonRegistration;

    @FXML
    private TextField regFio, regLogin, regPassword, regTel;
    @FXML
    private Label labelInfo;

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
                        labelInfo.setText(database.clientAdd(regFio.getText(), regLogin.getText(), regPassword.getText(), regTel.getText()));
                        if (labelInfo.getText().equals("Новый клиент успешно добавлен")) {
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Platform.runLater(() -> close());
                                }
                            }, 2000);
                        }
                    } else {
                        Authorization.showAlertError("Ошибка", "Заполните все поля.");
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
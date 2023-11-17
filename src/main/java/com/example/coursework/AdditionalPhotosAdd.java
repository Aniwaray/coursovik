package com.example.coursework;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class AdditionalPhotosAdd {

    @FXML
    private Button buttonAdd;

    @FXML
    private TextField nameImage1, nameImage2;

    Database database = new Database();

    @FXML
    void initialize() {
        buttonAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

            if (nameImage1.getText().trim().isEmpty() && nameImage2.getText().trim().isEmpty()) {
                Authorization.showAlertError("Ошибка", "Заполните хотя бы одно поле.");
            }
            if (!nameImage1.getText().trim().isEmpty() && nameImage2.getText().trim().isEmpty()) {
                try {
                    database.insertAdditionalPhotos(nameImage1.getText(), Integer.valueOf(MainAccount.id_product));
                    Authorization.showAlert("", "Фото добавлено.");
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if (!nameImage2.getText().trim().isEmpty() && nameImage1.getText().trim().isEmpty()) {
                try {
                    database.insertAdditionalPhotos(nameImage2.getText(), Integer.valueOf(MainAccount.id_product));
                    Authorization.showAlert("", "Фото добавлено.");
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            if (!nameImage2.getText().trim().isEmpty() && !nameImage1.getText().trim().isEmpty()) {
                try {
                    database.insertAdditionalPhotos(nameImage1.getText(), Integer.valueOf(MainAccount.id_product));
                    database.insertAdditionalPhotos(nameImage2.getText(), Integer.valueOf(MainAccount.id_product));
                    Authorization.showAlert("", "Фото добавлены.");
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
}
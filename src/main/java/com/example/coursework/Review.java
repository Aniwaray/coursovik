package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Review {
    @FXML
    private Button buttonSend;

    @FXML
    private ComboBox<String> comboProduct;

    @FXML
    private TextArea textComment;

    @FXML
    private TextField textRating;
    Database database = new Database();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        List<String> prod = database.getProductMain();
        comboProduct.setItems(FXCollections.observableArrayList(prod));

        buttonSend.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if (comboProduct.getValue() == null || textRating.getText().isEmpty()) {
                        Authorization.showAlertError("Ошибка", "Заполните все данные.");
                    } else {
                        if(Integer.parseInt(textRating.getText()) > 0 && Integer.parseInt(textRating.getText()) <= 5) {
                            database.insertReviews(textRating.getText(), textComment.getText(), Authorization.id_client,
                                    database.getProductForInsert(comboProduct.getValue()));
                            Authorization.showAlert("", "Спасибо за Ваш отзыв!");
                        }else Authorization.showAlertError("Ошибка", "Оцените нас от 1 до 5.");
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

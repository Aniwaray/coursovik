package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.List;

public class ProductAdd {
    @FXML
    private Button buttonAdd;

    @FXML
    private ComboBox<String> comboCategory, comboManufacture, comboModel;

    @FXML
    private TextField textPrice, textImage, textName, textStock;

    @FXML
    private TextArea textDescription;

    String getStatusForAdd, getImageForAdd;
    Database database = new Database();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        List<String> cat = database.getCategoryMain();
        comboCategory.setItems(FXCollections.observableArrayList(cat));

        List<String> man = database.getManufactureMain();
        comboManufacture.setItems(FXCollections.observableArrayList(man));

        List<String> mod = database.getModelMain();
        comboModel.setItems(FXCollections.observableArrayList(mod));

        loadInfo();
    }

    void loadInfo() {
        try {
            buttonAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        if (!textName.getText().isEmpty() && !textDescription.getText().isEmpty() && !textPrice.getText().isEmpty()
                                && !textStock.getText().isEmpty() && comboCategory.getValue() != null
                                && comboManufacture.getValue() != null && comboModel.getValue() != null
                                && (Integer.parseInt(textPrice.getText()) > 0) && Integer.parseInt(textStock.getText()) >= 0) {
                            if (textImage.getText().isEmpty()) {
                                getImageForAdd = "no.jpg";
                            } else getImageForAdd = textImage.getText();
                            if (Integer.parseInt(textStock.getText()) == 0) {
                                getStatusForAdd = "Отсутствует";
                            } else getStatusForAdd = "Присутствует";

                            database.insertProduct(textName.getText(), textDescription.getText(),
                                    Integer.parseInt(textPrice.getText()), Integer.parseInt(textStock.getText()),
                                    getStatusForAdd, getImageForAdd, database.getCategoryForInsert(comboCategory.getValue()),
                                    database.getManufactureForInsert(comboManufacture.getValue()),
                                    database.getModelForInsert(comboModel.getValue()));
                            Authorization.showAlert("", "Данные добавлены. Обновите.");
                        } else {
                            Authorization.showAlertError("Ошибка", "Заполните все поля или проверьте корректность данных.");
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


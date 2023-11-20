package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;

public class ProductEdit extends ListCell<ProductData> {

    @FXML
    private Button buttonEdit;

    @FXML
    private ComboBox<String> comboCategory, comboManufacture, comboModel;

    @FXML
    private TextField textPrice, textImage, textName, textStock;

    @FXML
    private TextArea textDescription;

    String getStatusForEdit, getImageForEdit;
    Database database = new Database();
    @FXML
    public void close() {
        Stage stage = (Stage) buttonEdit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        textName.setText(database.getOneProductName(MainAccount.id_product));
        textPrice.setText(String.valueOf(database.getOneProductPrice(MainAccount.id_product)));
        textStock.setText(String.valueOf(database.getOneProductQuantityInStock(MainAccount.id_product)));
        textDescription.setText(database.getOneProductDescription(MainAccount.id_product));
        textImage.setText(database.getOneProductPhoto(MainAccount.id_product));

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
            buttonEdit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        if (!textName.getText().isEmpty() && !textDescription.getText().isEmpty() && !textPrice.getText().isEmpty()
                                && !textStock.getText().isEmpty() && comboCategory.getValue() != null
                                && comboManufacture.getValue() != null && comboModel.getValue() != null
                                && (Integer.parseInt(textPrice.getText()) > 0) && Integer.parseInt(textStock.getText()) >= 0) {
                            if (textImage.getText().isEmpty()) {
                                getImageForEdit = "no.jpg";
                            } else getImageForEdit = textImage.getText();
                            if (Integer.parseInt(textStock.getText()) == 0) {
                                getStatusForEdit = "Отсутствует";
                            } else getStatusForEdit = "Присутствует";

                            database.updateProduct(textName.getText(),  textDescription.getText(),Integer.parseInt(textPrice.getText()),
                                    Integer.parseInt(textStock.getText()), getStatusForEdit, getImageForEdit, database.getCategoryForInsert(comboCategory.getValue()),
                                    database.getManufactureForInsert(comboManufacture.getValue()), database.getModelForInsert(comboModel.getValue()),
                                    Integer.parseInt(MainAccount.id_product));

                            Authorization.showAlert("", "Данные отредактированны. Обновите.");
                            close();
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
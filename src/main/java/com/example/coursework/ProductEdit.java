package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
    private TextField textCost, textImage, textName, textStock;

    @FXML
    private TextArea textDescription;

    Database database = new Database();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        textName.setText(database.getOneProductName(MainAccount.id_product));
        textCost.setText(String.valueOf(database.getOneProductPrice(MainAccount.id_product)));
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
                    MainAccount mainAccount = new MainAccount();
//                    try {
//                        database.updateProduct(Integer.parseInt(mainAccount.id_product), Integer.parseInt(textCost.getText()),
//                                textDescription.getText(), textManufacture.getText(), textName.getText(),
//                                Integer.parseInt(textStock.getText()), textCategory.getText(), nameImage.getText());
//                        if (Integer.parseInt(textStock.getText()) == 0) {
//                            database.updateProductStatus(Integer.parseInt(mainAccount.id_product), "Отсутствует");
//                        }
//                        System.out.println("Данные обновлены.");
//                    } catch (SQLException | ClassNotFoundException e) {
//                        throw new RuntimeException(e);
//                    }
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

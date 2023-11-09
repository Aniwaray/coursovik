package com.example.coursework;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

public class Data extends ListCell<ProductData> {
    @FXML
    AnchorPane anchorPane;

    @FXML
    ImageView imageView1, imageView2, imageView3;

    @FXML
    Label labelCategory, labelDescription, labelManufacturer, labelName, labelPrice, labelStock;

    private FXMLLoader mLLoader;
    Database database = new Database();

    @Override
    protected void updateItem(ProductData productData, boolean empty) {
        super.updateItem(productData, empty);

        if (empty || productData == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("data.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Установка серого фона ячеек для товаров с отсутствующим статусом
            String status = productData.getStatus();
            if (status != null && status.equals("Отсутствует")) {
                anchorPane.setStyle("-fx-background-color: gray;");
            } else {
                anchorPane.setStyle(""); // Сброс стиля ячейки
            }

            try {
                File file = new File(String.valueOf(database.getPhoto(Account.model)));
                String urlImage = file.toURI().toURL().toString();
                Image image = new Image(urlImage);
                System.out.println(image);
                imageView1.setImage(image);
                imageView2.setImage(image);
                imageView3.setImage(image);
            } catch (SQLException | ClassNotFoundException | MalformedURLException e) {
                throw new RuntimeException(e);
            }
            setText(null);
            setGraphic(anchorPane);

            labelName.setText(productData.getName());
            labelDescription.setText(productData.getDescription());
            labelPrice.setText(String.valueOf(productData.getPrice()));
            labelStock.setText(String.valueOf(productData.getStock()));
            labelCategory.setText(String.valueOf(productData.getCategory()));
            labelManufacturer.setText(String.valueOf(productData.getManufacture()));

        }
    }
}
package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class Order {
    @FXML
    private Button buttonCreateOrder, buttonAddOrder;

    @FXML
    private ComboBox<String> comboClient, comboPoint, comboProduct;

    @FXML
    private TextField countProduct;

    @FXML
    private Label labelPrice;

    @FXML
    private ListView<String> listCount, listPrice, listProduct;

    int id_product, num, price = 0;

    Database database = new Database();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        loadInfo();
    }

    void loadInfo() throws SQLException, ClassNotFoundException {

        labelPrice.setText(String.valueOf(price));

        List<String> point = database.getPoint();
        comboPoint.setItems(FXCollections.observableArrayList(point));

        List<String> client = database.getClient();
        comboClient.setItems(FXCollections.observableArrayList(client));

        List<String> product = database.getProductMain();
        comboProduct.setItems(FXCollections.observableArrayList(product));

        buttonCreateOrder.setDisable(true);

        buttonAddOrder.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

            if (comboClient.getValue() == null || comboPoint.getValue() == null || comboProduct.getValue() == null
                    || countProduct.getText() == null) {
                System.out.println("Заполните все данные.");
            } else {
                try {
                    buttonCreateOrder.setDisable(false);
                    List<String> l = Collections.singletonList(comboProduct.getValue());
                    listProduct.getItems().addAll(l);

                    List<String> ls = database.getPrice(comboProduct.getValue());
                    listPrice.getItems().addAll(ls);

                    List<String> lss = Collections.singletonList(countProduct.getText());
                    listCount.getItems().addAll(lss);
                }catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
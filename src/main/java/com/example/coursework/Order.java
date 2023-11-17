package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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

    double finalPrice, newPrice, price = 0;

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
                    || countProduct.getText() == null || Integer.parseInt(countProduct.getText()) <= 0) {
                Authorization.showAlertError("Ошибка", "Заполните данные корректно.");
            } else {

                buttonCreateOrder.setDisable(false);

                try {
                    price = database.getPriceInt(comboProduct.getValue());
                    labelPrice.setText(String.valueOf(price));
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 0; i < listProduct.getItems().size(); i++) {
                    if (listProduct.getItems().get(i).equals(comboProduct.getValue())) {
                        int newCount = Integer.parseInt(listCount.getItems().get(i)) + Integer.parseInt(countProduct.getText());
                        listCount.getItems().set(i, String.valueOf(newCount));
                        newPrice = Double.parseDouble(String.valueOf(price * newCount));
                        listPrice.getItems().set(i, String.valueOf(newPrice));
                        labelPrice.setText(String.valueOf(newPrice));

                        finalPrice = 0;
                        for (int j = 0; j < listPrice.getItems().size(); j++) {
                            finalPrice += Double.parseDouble(listPrice.getItems().get(j));
                            labelPrice.setText(String.valueOf(finalPrice));
                        }
                        return;
                        // выходим из цикла, так как товар найден и обновлен
                    }
                }
                // Если товар не был найден в списке, добавляем новый элемент
                listProduct.getItems().add(comboProduct.getValue());
                listCount.getItems().add(countProduct.getText());
                try {
                    List<String> ls = database.getPrice(comboProduct.getValue(), countProduct.getText());
                    listPrice.getItems().addAll(ls);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                finalPrice = 0;
                for (int i = 0; i < listPrice.getItems().size(); i++) {
                    finalPrice += Double.parseDouble(listPrice.getItems().get(i));
                    labelPrice.setText(String.valueOf(finalPrice));
                }
            }
        });

        buttonCreateOrder.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            try {
                for (int i = 0; i < listCount.getItems().size(); i++) {
                    int count = Integer.parseInt(listCount.getItems().get(i));
                    int finalPrice = (int) Double.parseDouble(listPrice.getItems().get(i));
                    int getPoint = database.getPointForInsert(comboPoint.getValue());
                    int getProduct = database.getProductForInsert(String.valueOf(listProduct.getItems().get(i)));
                    int getClient = database.getClientForInsert(String.valueOf(comboClient.getValue()));
                    database.insertOrder(count, finalPrice, getPoint, getProduct, getClient);
                }
                Authorization.showAlert("", "Заказ оформлен.");
            } catch (SQLException | ClassNotFoundException e) {
                // Обрабатываем исключение
                if (e.getMessage().equals("45000")) {
                    Authorization.showAlert("Ошибка", "Недостаточно количества на складе");
                } else {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
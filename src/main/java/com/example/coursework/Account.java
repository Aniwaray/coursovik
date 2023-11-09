package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Account {

    @FXML
    private ImageView imageHome, imageAudi, imageKia, imageMitsubishi, imageSearch;

    @FXML
    private TextField textSearch;

    @FXML
    Label labelFio, labelRole;

    @FXML
    private ComboBox<String> comboCategory, comboManufacture, comboStatus;

    @FXML
    private ListView<ProductData> listView;

    public static int model;
    ObservableList<ProductData> productDataList;

    Database database = new Database();

    @FXML
    public void close() {
        Stage stage = (Stage) imageHome.getScene().getWindow();
        stage.close();
    }

    private void search() {
        textSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            String filter = newValue.toLowerCase();

            while (!filter.isEmpty()) {
                try {
                    listView.getItems().clear();
                    List<ProductData> ls = database.getProductSearch(textSearch.getText());
                    listView.getItems().addAll(ls);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        });
    }


    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        loadInfo();
        search();

        imageHome.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            close();
        });

        imageSearch.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            try {

                if (!comboCategory.getValue().isEmpty() && !comboStatus.getValue().isEmpty()) {
                    listView.getItems().clear();
                    ArrayList<Integer> c = database.getCategory(String.valueOf(comboCategory.getValue()));
                    System.out.println(c);
                    if (comboStatus.getValue()=="Отсутствует") {
                        ArrayList<Integer> se = database.getProductStatNo();
                        System.out.println(se);
                        for (int i = 0; i < se.size(); i++) {
                            for (int j = 0; j < c.size(); j++) {
                                if (se.get(i) == c.get(j)) {
                                    List<ProductData> l = database.getProduct2(se.get(i));
                                    listView.getItems().addAll(l);
                                }
                            }
                        }
                    } else {
                        ArrayList<Integer> se = database.getProductStat();
                        for (int i = 0; i < se.size(); i++) {
                            for (int j = 0; j < c.size(); j++) {
                                if (se.get(i) == c.get(j)) {
                                    List<ProductData> l = database.getProduct2(se.get(i));
                                    listView.getItems().addAll(l);
                                }
                            }
                        }
                    }
                }
            }catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        imageAudi.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            model = 1;

            listView.getItems().clear();
            try {
                List<ProductData> ls = database.getProductModel(model);
                listView.getItems().addAll(ls);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        imageKia.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            model = 2;

            listView.getItems().clear();
            try {
                List<ProductData> ls = database.getProductModel(model);
                listView.getItems().addAll(ls);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        imageMitsubishi.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            model = 3;

            listView.getItems().clear();
            try {
                List<ProductData> ls = database.getProductModel(model);
                listView.getItems().addAll(ls);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void loadInfo() throws SQLException, ClassNotFoundException {
        try {
            comboCategory.getItems().addAll("Без сортировки", "Детали тормозной системы", "Запчасти для двигателя", "Детали трансмиссии");

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        try {
            comboManufacture.getItems().addAll("Без сортировки", "AutoPro", "AutoTop");
            comboManufacture.setOnAction(event -> {

                if (comboManufacture.getValue().equals("AutoPro")) {
                    listView.getItems().clear();

                    try {
                        List<ProductData> ls = database.getProductSortMan(1);
                        listView.getItems().addAll(ls);
                        listView.setCellFactory(stringListView -> {
                            ListCell<ProductData> cell = new Data();
                            cell.setContextMenu(null);
                            return cell;
                        });
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (comboManufacture.getValue().equals("AutoTop")) {
                    listView.getItems().clear();
                    try {
                        List<ProductData> ls = database.getProductSortMan(2);
                        listView.getItems().addAll(ls);
                        listView.setCellFactory(stringListView -> {
                            ListCell<ProductData> cell = new Data();
                            cell.setContextMenu(null);
                            return cell;
                        });
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (comboManufacture.getValue().equals("Без сортировки")) {
                    listView.getItems().clear();
                    try {
                        List<ProductData> ls = database.getProduct();
                        listView.getItems().addAll(ls);
                        listView.setCellFactory(stringListView -> {
                            ListCell<ProductData> cell = new Data();
                            cell.setContextMenu(null);
                            return cell;
                        });
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        comboStatus.getItems().addAll("Без сортировки", "Присутствует", "Отсутствует");

        List<ProductData> ls = database.getProduct();
        listView.getItems().addAll(ls);
        listView.setCellFactory(stringListView -> {
            ListCell<ProductData> cell = new Data();
            cell.setContextMenu(null);
            return cell;
        });
    }
}
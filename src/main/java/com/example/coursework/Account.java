package com.example.coursework;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Account {

    @FXML
    private ImageView imageHome, imageAudi, imageKia, imageMitsubishi, imageClear;

    @FXML
    private TextField textSearch;

    @FXML
    Label labelFio, labelRole;

    @FXML
    private ComboBox<String> comboCategory, comboManufacture, comboStatus;

    @FXML
    private ListView<ProductData> listView;

    public static int model;

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
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("authorization.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 409, 494);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            close();
        });
        imageClear.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            listView.getItems().clear();
            try {
                List<ProductData> ls = database.getProduct();
                listView.getItems().addAll(ls);
            } catch (SQLException | ClassNotFoundException e) {
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

        List<String> cat = database.getCategoryMain();
        comboCategory.setItems(FXCollections.observableArrayList(cat));

        List<String> stat = database.getStatusMain();
        comboCategory.setItems(FXCollections.observableArrayList(stat));

        List<String> man = database.getManufactureMain();
        comboCategory.setItems(FXCollections.observableArrayList(man));

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
            });

        List<ProductData> ls = database.getProduct();
        listView.getItems().addAll(ls);
        listView.setCellFactory(stringListView -> {
            ListCell<ProductData> cell = new Data();
            cell.setContextMenu(null);
            return cell;
        });
    }

    public void Home(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {

        //вывели по категории
        ArrayList<Integer> co = database.getCategory(String.valueOf(comboCategory.getValue()));
        listView.getItems().clear();
        try {
            for (int i = 0; i < co.size(); i++) {
                List<ProductData> ls = database.getProduct2(co.get(i));
                listView.getItems().addAll(ls);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //вывели по статусу
        if (comboStatus.getValue() == "Присутствует") {
            ArrayList<Integer> se = database.getProductStat();
            listView.getItems().clear();
            try {
                for (int i = 0; i < se.size(); i++) {
                    List<ProductData> ls = database.getProduct2(se.get(i));
                    listView.getItems().addAll(ls);
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            ArrayList<Integer> se = database.getProductStatNo();
            listView.getItems().clear();
            try {
                for (int i = 0; i < se.size(); i++) {
                    List<ProductData> ls = database.getProduct2(se.get(i));
                    listView.getItems().addAll(ls);
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        //если выбраны двое
        if (comboCategory.getValue() != null && comboStatus.getValue() != null) {
            listView.getItems().clear();
            ArrayList<Integer> c = database.getCategory(String.valueOf(comboCategory.getValue()));
            if (comboStatus.getValue() == "Отсутствует") {
                ArrayList<Integer> se = database.getProductStatNo();
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
    }
}
package com.example.coursework;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainAccount {

    @FXML
    ImageView imageHome, imageAudi, imageKia, imageMitsubishi, imageClear, imageSearch;

    @FXML
    TextField textSearch;

    @FXML
    Label labelFio, labelRole;

    @FXML
    ComboBox<String> comboCategory, comboManufacture, comboStatus;

    @FXML
    ListView<ProductData> listView;

    @FXML
    private Button createOrder, buttonAddProduct, buttonUpdate;

    public static int model;
    static String id_product;

    Database database = new Database();

    @FXML
    public void close() {
        Stage stage = (Stage) imageHome.getScene().getWindow();
        stage.close();
    }

    private void search() {
        textSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            String filter = newValue.toLowerCase();

            if (newValue.isEmpty()) {  // Если строка поиска пустая, то отображаем все данные
                try {
                    listView.getItems().clear();
                    List<ProductData> allData = database.getProduct();
                    listView.getItems().addAll(allData);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

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

        createOrder.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("order.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 833, 538);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                stage.getIcons().add(new Image("C:/Users/Anna/IdeaProjects/coursework/logo.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        buttonAddProduct.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("productAdd.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 524, 597);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                stage.getIcons().add(new Image("C:/Users/Anna/IdeaProjects/coursework/logo.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        imageHome.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("authorization.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 409, 494);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                stage.getIcons().add(new Image("C:/Users/Anna/IdeaProjects/coursework/logo.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            close();
        });
        imageAudi.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            model = 1;

            listView.getItems().clear();
            try {
                List<ProductData> ls = database.getProductModel(model);
                listView.getItems().addAll(ls);
                openContextMenu();
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
                openContextMenu();
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
                openContextMenu();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        imageClear.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            listView.getItems().clear();
            try {
                List<ProductData> ls = database.getProduct();
                listView.getItems().addAll(ls);
                openContextMenu();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            comboCategory.getSelectionModel().select("Категория");
            comboStatus.getSelectionModel().select("Статус");
            comboManufacture.getSelectionModel().select("Поставщики");
        });

        buttonUpdate.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            listView.getItems().clear();
            try {
                List<ProductData> ls = database.getProduct();
                listView.getItems().addAll(ls);
                openContextMenu();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        imageSearch.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if ((!(comboCategory.getValue() == null) || !(comboCategory.getValue() == "Категория"))
                    && (comboStatus.getValue() == null || comboStatus.getValue() == "Статус")) {
                try {
                    //сортируем по категории
                    ArrayList<Integer> category = database.getCategory(String.valueOf(comboCategory.getValue()));
                    listView.getItems().clear();
                    // Создаем отсортированный список категорий для вывода
                    List<ProductData> sortedItems = new ArrayList<>();
                    for (int i = 0; i < category.size(); i++) {
                        List<ProductData> ls = database.getCategoryForSorting(category.get(i));
                        sortedItems.addAll(ls);
                    }
                    Collections.sort(sortedItems, Comparator.comparing(ProductData::getCategory));
                    listView.getItems().addAll(sortedItems);

                } catch (Exception e) {
                    // Обработка ошибок
                }
            } else if (((comboCategory.getValue() == null) || (comboCategory.getValue() == "Категория"))
                    && (!(comboStatus.getValue() == null) || !(comboStatus.getValue() == "Статус"))) {
                try {
                    //сортируем по статусу
                    listView.getItems().clear();
                    List<ProductData> sortedItemsStat = new ArrayList<>();
                    List<ProductData> l = database.getStatusForSorting(comboStatus.getValue());
                    sortedItemsStat.addAll(l);
                    Collections.sort(sortedItemsStat, Comparator.comparing(ProductData::getStatus));
                    listView.getItems().addAll(sortedItemsStat);
                } catch (Exception e) {
                    // Обработка ошибок
                }
            } else if ((!(comboCategory.getValue() == null) || !(comboCategory.getValue() == "Категория"))
                    && (!(comboStatus.getValue() == null) || !(comboStatus.getValue() == "Статус"))) {
                try {
                    if (comboCategory.getValue() != null && comboStatus.getValue() != null) {
                    listView.getItems().clear();
                    ArrayList<Integer> cat = database.getCategory(String.valueOf(comboCategory.getValue()));
                    if (comboStatus.getValue() == "Отсутствует") {
                        ArrayList<Integer> stat = database.getProductStatNo();
                        for (int i = 0; i < stat.size(); i++) {
                            for (int j = 0; j < cat.size(); j++) {
                                if (stat.get(i) == cat.get(j)) {
                                    List<ProductData> l = database.getProductWithId(stat.get(i));
                                    listView.getItems().addAll(l);
                                }
                            }
                        }
                    } else {
                        ArrayList<Integer> stat = database.getProductStat();
                        for (int i = 0; i < stat.size(); i++) {
                            for (int j = 0; j < cat.size(); j++) {
                                if (stat.get(i) == cat.get(j)) {
                                    List<ProductData> l = database.getProductWithId(stat.get(i));
                                    listView.getItems().addAll(l);
                                }
                            }
                        }
                    }
                }
                } catch (Exception e) {
                    // Обработка ошибок
                }
            }
        });

    }

    void loadInfo() throws SQLException, ClassNotFoundException {

        List<String> cat = database.getCategoryMain();
        comboCategory.setItems(FXCollections.observableArrayList(cat));
        comboCategory.getItems().addAll("Категория");

        comboStatus.getItems().addAll("Присутствует", "Отсутствует", "Статус");

        List<String> man = database.getManufactureMain();
        comboManufacture.setItems(FXCollections.observableArrayList(man));
        comboManufacture.getItems().addAll("Поставщики");

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
            openContextMenu();
        });

        List<ProductData> ls = database.getProduct();
        listView.getItems().addAll(ls);
        listView.setCellFactory(stringListView -> {
            ListCell<ProductData> cell = new Data();
            cell.setContextMenu(null);
            openContextMenu();
            return cell;
        });
    }

    public void openContextMenu() {

        listView.setCellFactory(stringListView -> {
            ListCell<ProductData> cell = new Data();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem editItemEdit = new MenuItem("Редактировать");
            editItemEdit.setOnAction(event -> {
                ProductData item = cell.getItem();
                try {
                    id_product = String.valueOf(database.getIdProduct(item.getName()));
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("productEdit.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(fxmlLoader.load(), 524, 597);
                    stage.setScene(scene);
                    stage.show();
                    stage.getIcons().add(new Image("C:/Users/Anna/IdeaProjects/coursework/logo.png"));
                } catch (SQLException | ClassNotFoundException | IOException e) {
                    throw new RuntimeException(e);
                }
            });
            MenuItem editItemDelete = new MenuItem("Удалить");
            editItemDelete.setOnAction(event -> {
                ProductData item = cell.getItem();
                try {
                    id_product = String.valueOf(database.getIdProduct(item.getName()));
                    database.deleteAdditionalPhotot(Integer.valueOf(id_product));
                    database.deleteProduct(Integer.valueOf(id_product));
                    Authorization.showAlert("", "Данные удалены. Обновите.");
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            MenuItem editItemPhoto = new MenuItem("Просмотреть доп. фото");
            editItemPhoto.setOnAction(event -> {
                ProductData item = cell.getItem();
                try {
                    id_product = String.valueOf(database.getIdProduct(item.getName()));
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("additionalPhotos.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 518, 303);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    stage.getIcons().add(new Image("C:/Users/Anna/IdeaProjects/coursework/logo.png"));
                } catch (IOException | SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            MenuItem editItemPhotoAdd = new MenuItem("Добавить доп. фото");
            editItemPhotoAdd.setOnAction(event -> {
                ProductData item = cell.getItem();
                try {
                    id_product = String.valueOf(database.getIdProduct(item.getName()));
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("additionalPhotosAdd.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 388, 346);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                    stage.getIcons().add(new Image("C:/Users/Anna/IdeaProjects/coursework/logo.png"));
                } catch (IOException | SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            contextMenu.getItems().addAll(editItemEdit, editItemDelete, editItemPhotoAdd, editItemPhoto);
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell;
        });
    }
}
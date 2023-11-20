package com.example.coursework;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdditionalPhotos {

    @FXML
    Button button1, button2;
    @FXML
    private ImageView image;
    @FXML
    Label labelName;
    Database database = new Database();
    private int currentPhotoIndex = 0;
    ArrayList<String> listPhoto; // Создаем список для хранения имен файлов или URL-адресов

    @FXML
    void initialize() {
        try {
            labelName.setText(database.getProductName(MainAccount.id_product));
            listPhoto = database.getPhoto(Integer.valueOf(MainAccount.id_product)); // Заполняем список фотографиями из базы данных
            displayImage(0); // Отображаем первую фотографию при инициализации
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        button1.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            showPreviousImage(); // Показываем предыдущую фотографию при нажатии на кнопку
        });

        button2.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

            showNextImage(); // Показываем следующую фотографию при нажатии на кнопку
        });
    }
    private void displayImage(int index) {
        try {
            String urlImage = new File(listPhoto.get(index)).toURI().toURL().toString(); // Преобразуем путь к файлу в URL
            Image image1 = new Image(urlImage);
            image.setImage(image1); // Отображаем изображение в ImageView
        } catch (Exception e) {
            Authorization.showAlertError("Ошибка", "Нет дополнительных фото к выбранному товару.");
            // Выводим информацию об исключении
        }
    }

        private void showNextImage() {
            if (currentPhotoIndex < listPhoto.size() - 1) {
                currentPhotoIndex++; // Увеличиваем индекс для отображения следующей фотографии
                displayImage(currentPhotoIndex); // Отображаем следующую фотографию
            }
        }

        // Метод для отображения предыдущей фотографии
        private void showPreviousImage() {
            if (currentPhotoIndex > 0) {
                currentPhotoIndex--; // Уменьшаем индекс для отображения предыдущей фотографии
                displayImage(currentPhotoIndex); // Отображаем предыдущую фотографию
            }
        }

    }
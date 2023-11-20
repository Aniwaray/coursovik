package com.example.coursework;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Captcha {
    private static final int captchaLength = 5;

    @FXML
    private Canvas canvas;
    @FXML
    private TextField textInputField;
    @FXML
    private Button buttonExam;

    private CaptchaGenerator captchaGenerator;
    private String captchaText;

    @FXML
    public boolean validate() {
        if (!Objects.equals(this.captchaText, this.textInputField.getText())) {
            regenerateCaptcha();
            this.textInputField.setText("");
            return false;
        }
        return true;
    }

    @FXML
    private void initialize() {
        this.captchaGenerator = new CaptchaGenerator(this.canvas);

        regenerateCaptcha();

        buttonExam.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    if(CaptchaGenerator.text.equals(textInputField.getText())){
                        Authorization.showAlert("", "Проверка пройдена.");
                        Stage stage = (Stage) textInputField.getScene().getWindow();
                        stage.close();
                    }else {
                        Authorization.showAlertError("Ошибка", "Проверка не пройдена. Попробуйте ещё раз.");
                        Stage stage = (Stage) textInputField.getScene().getWindow();
                        stage.close();
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("captcha.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 300, 140);
                        Stage stage2 = new Stage();
                        stage2.initStyle(StageStyle.UNDECORATED);
                        stage2.setScene(scene);
                        stage2.initModality(Modality.APPLICATION_MODAL);
                        stage2.show();
                        stage2.getIcons().add(new Image("C:/Users/Anna/IdeaProjects/coursework/logo.png"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void regenerateCaptcha() {
        this.captchaText = this.captchaGenerator.generate(captchaLength);
    }
}
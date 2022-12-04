package com.example.myapp;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import Interfaces.ImageController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.io.FileUtils;

public class HomeController implements ImageController {
    File pathImagesDirectory = new File("C:\\Users\\Маksim\\My-app\\My-App\\src\\main\\java\\Images");
    File resultPathDirectory = new File("C:\\Users\\Маksim\\DownLoadIamges");
    DataBaseHandler handler = new DataBaseHandler();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private ImageView ImageView;

    @FXML
    private Button SearchButton;

    @FXML
    private TextField SearchField;

    @FXML
    private Button createButton;

    @FXML
    private TextField createField;

    @FXML
    private Button removeButton;


    @FXML
    void initialize() {

        SearchButton.setOnAction(event ->{
            String searchText = SearchField.getText();
            String resultImage = null;
            try {
                resultImage = handler.getImageFromTable(searchText);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            ImageView.setImage(getImage(resultImage));
            ImageView.setId(searchText);


        });
        createButton.setOnAction(event ->{
          String newNameImage = createField.getText();
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
          alert.setTitle("Добавить файл");
          alert.setHeaderText("Вы действительно хотите добавить этот файл?");
          Optional<ButtonType> optional = alert.showAndWait();
            if(optional.get() == ButtonType.OK){
          handler.CreateImageName(newNameImage);
          System.out.println("Добавлено успешно");
            }
        });
        removeButton.setOnAction(event ->{
            handler.removeNameImage();
        });
        ImageView.setOnMouseClicked(event ->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Скачать изображение");
            alert.setHeaderText("Вы действительно хотите скачать это изображение?");
            Optional<ButtonType> optional = alert.showAndWait();
            if(optional.get() == ButtonType.OK){
                File f = new File(pathImagesDirectory + "\\" + ImageView.getId() + ".jpg" );
                try {
                    FileUtils.copyFileToDirectory(f,resultPathDirectory);
                    System.out.println("Копирование успешно");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}


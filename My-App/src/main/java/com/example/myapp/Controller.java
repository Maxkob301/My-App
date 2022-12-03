package com.example.myapp;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import Animation.Shake;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Login;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button authSignInButton;

    @FXML
    private Button loginSignButton;

    @FXML
    void initialize() {

        authSignInButton.setOnAction(event ->{
            String loginText = Login.getText().trim();
            String Password = PasswordField.getText().trim();
            if(!loginText.equals("") && !Password.equals("")){
                try {
                    loginUser(loginText,Password);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                System.out.println("Некоректные данные");
            }
        });

       loginSignButton.setOnAction(event -> {
         openNewScene("SignUp.fxml");
       });

    }
    //Ищем пользователя в базе данных,если его нет,то выводим сообщение об этом и предлагаем зарегестрироваться
    private void loginUser(String loginText, String password) throws SQLException {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        User user = new User();
        user.setLoginName(loginText);
        user.setPasswordName(password);
        dataBaseHandler.getUser(user);
        ResultSet resultSet = dataBaseHandler.getUser(user);
        int counter = 0;
        while(true){
                if (resultSet.next())
            counter++;
            if( counter >= 1){
               openNewScene("app.fxml");
               break;
            }else {
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("Connection result");
               alert.setHeaderText("SignUp");
               alert.setContentText("Такой пользователь не найден,хотите зарегестрироваться?");
               Optional<ButtonType> optional = alert.showAndWait();
               if(optional.get() == ButtonType.OK){
                   openNewScene("SignUp.fxml");
               } else if (optional.get() == ButtonType.CANCEL) {
                   break;
               }
               break;
            }
        }

    }
    //открытие нового окна приложения
    public void openNewScene(String window){
        loginSignButton.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Controller.class.getResource(window));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }


}

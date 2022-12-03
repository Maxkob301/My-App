
package com.example.myapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox CheckBoxFemale;

    @FXML
    private CheckBox CheckBoxMale;

    @FXML
    private TextField CountryField;

    @FXML
    private TextField LastNameField2;

    @FXML
    private TextField LoginField;

    @FXML
    private TextField NameField;

    @FXML
    private Button authSignInButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    void initialize() {
       authSignInButton.setOnAction(event ->{
          signUpNewUser();
       });
    }

    private void signUpNewUser() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        String firsName = NameField.getText();
        String LastName = LastNameField2.getText();
        String Country = CountryField.getText();
        String Login = LoginField.getText();
        String password = passwordField.getText();
        String gender = "";
        if(CheckBoxMale.isSelected())
            gender = "Male";
        else gender = "Female";
        User user = new User(firsName,LastName,Login,password,Country,gender);

        dataBaseHandler.signUpUser(user);
    }

}


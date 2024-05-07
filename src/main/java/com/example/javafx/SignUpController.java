package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

public class SignUpController {

    @FXML
    private RadioButton signUpMale;

    @FXML
    private TextField signUpLogin;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUpFirstName;

    @FXML
    private RadioButton signUpFemale;

    @FXML
    private TextField signUpLastName;

    @FXML
    private TextField signUpCountry;

    @FXML
    void initialize() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        signUpButton.setOnAction(event -> {
            try {
                signUpNewUser();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void signUpNewUser() throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        String firstName = signUpFirstName.getText();
        String lastName = signUpLastName.getText();
        String userName = signUpLogin.getText();
        String password = signUpPassword.getText();
        String location = signUpCountry.getText();
        String gender = "";

        if (signUpMale.isSelected()) gender = "M";
        else gender = "F";

        User user = new User(firstName, lastName, userName, password, location, gender);

        dbHandler.signUpUser(user);
    }
}

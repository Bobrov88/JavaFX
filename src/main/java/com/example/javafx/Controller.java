package com.example.javafx;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.javafx.animations.Shake;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button authSignInButton;

    @FXML
    private Button loginSignUpButton;

    @FXML
    void initialize() {

        authSignInButton.setOnAction(event -> {
            String loginText = loginField.getText().trim();
            String loginPassword = passField.getText().trim();
            if (!loginText.isEmpty() && !loginPassword.isEmpty()) {
                try {
                    loginUser(loginText, loginPassword);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            else System.out.println("Login or password is empty");
        });

        loginSignUpButton.setOnAction(event -> {
            openNewScene("signUp.fxml");
        });
    }

    private void loginUser(String loginText, String loginPassword) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);
        int counter = 0;
        while(result.next()) {
            counter++;
        }
        if (counter > 0) {
           openNewScene("app.fxml");
        } else {
            Shake userloginAnim  = new Shake(loginField);
            Shake userPassAnim  = new Shake(passField);
            userloginAnim.playAnim();
            userPassAnim.playAnim();
        }
    }

    public void openNewScene(String window) {
        loginSignUpButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}

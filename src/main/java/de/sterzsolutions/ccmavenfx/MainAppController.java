/*
 * Copyright (c) Gabriel Sterz 2022
 */

package de.sterzsolutions.ccmavenfx;

import de.sterzsolutions.ccmavenfx.util.DatabaseUtil;
import de.sterzsolutions.ccmavenfx.util.UserSession;
import de.sterzsolutions.ccmavenfx.util.ValidationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainAppController implements Initializable {

    @FXML
    private Button loginLoginButton;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private Button loginRegisterButton;

    @FXML
    private TextField loginUsernameTextfield;

    @FXML
    private TextArea registerApiKey;

    @FXML
    private Button registerBackButton;

    @FXML
    private AnchorPane registerPane;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private Button registerRegisterButton;

    @FXML
    private PasswordField registerRepeatPasswordField;

    @FXML
    private TextArea registerSecret;

    @FXML
    private TextField registerUsernameTextfield;

    @FXML
    private Pane userCreatedPane;

    @FXML
    private Text userCreatedText;

    @FXML
    private Pane userDoesntExist;

    @FXML
    private Pane passwordWrong;

    @FXML
    private Pane registerWarningBinance;

    @FXML
    private Pane registerWarningPassword;

    @FXML
    private Pane registerWarningUser;

    private ValidationUtil validator = new ValidationUtil();
    private DatabaseUtil dbUtil = new DatabaseUtil();
    public static UserSession uS = UserSession.getInstance();

    /**
     * Switch to main Window
     * @param event
     * @throws IOException
     */
    public void switchFXML(ActionEvent event) throws IOException {
        Parent root;
        Stage stage;

        stage = (Stage) loginLoginButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("main-view.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * switch to Register-Pane and empty the fields
     * @param event
     */
    @FXML
    void enterRegisterPane(ActionEvent event){
        clearFields();
        registerPane.setVisible(true);
        registerPane.setDisable(false);
    }

    /**
     * switch to Login-Pane and empty the fields
     * @param event
     */
    @FXML
    void exitRegisterPane(ActionEvent event){
        clearFields();
        registerPane.setVisible(false);
        registerPane.setDisable(true);
    }

    @FXML
    void userLogin(ActionEvent event) throws SQLException, IOException {

        String loginUser = loginUsernameTextfield.getText().toLowerCase(Locale.ROOT);
        if (dbUtil.userExists(loginUser)){
            if(!validator.validateUserLogin(loginUser, loginPasswordField.getText())){

            } else {
                uS.setUser(loginUser);
                String api = dbUtil.getUserInfo(loginUser, "apikey");
                uS.setApiKey(api);
                String secret = dbUtil.getUserInfo(loginUser, "secret");
                uS.setSecret(secret);
                switchFXML(event);
            }
        } else {

        }


    }

    @FXML
    void registerUser(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

        registerWarningUser.setVisible(false);
        registerWarningPassword.setVisible(false);
        registerWarningBinance.setVisible(false);

        String user = registerUsernameTextfield.getText().toLowerCase(Locale.ROOT);
        String password = registerPasswordField.getText();
        String repeatPassword = registerRepeatPasswordField.getText();
        String api = registerApiKey.getText();
        String secret = registerSecret.getText();


        if (dbUtil.userExists(user)){
            clearFields();
            registerWarningUser.setVisible(true);
        }
        else if(!password.equals(repeatPassword)){
            clearFields();
            registerWarningPassword.setVisible(true);
        }
        else if(!validator.validateBinanceApi(api, secret)){
            clearFields();
            registerWarningBinance.setVisible(true);
        }
        else{
            dbUtil.addUser(user, password, api, secret);
            registerPane.setVisible(false);
            clearFields();
            userCreatedPane.setVisible(true);
            userCreatedText.setText("User " + user + " successfully registered. Please Login now.");
        }
    }

    /**
     * empties all fields, errors or success panes
     */
    private void clearFields(){
        registerUsernameTextfield.setText("");
        registerPasswordField.setText("");
        registerRepeatPasswordField.setText("");
        registerApiKey.setText("");
        registerSecret.setText("");
        loginPasswordField.setText("");
        loginUsernameTextfield.setText("");
        userCreatedPane.setVisible(false);
        passwordWrong.setVisible(false);
        userDoesntExist.setVisible(false);
        registerWarningBinance.setVisible(false);
        registerWarningUser.setVisible(false);
        registerWarningPassword.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}

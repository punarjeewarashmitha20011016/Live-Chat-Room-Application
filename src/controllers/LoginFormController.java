package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import util.PrimaryStageCommon;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane loginFormPaneId;
    public JFXTextField txtUserNameId;
    public JFXTextField txtPasswordId;

    public void LoginOnAction(ActionEvent actionEvent) throws IOException {
        PrimaryStageCommon<LoginFormController> s = new PrimaryStageCommon().setStage(loginFormPaneId, new LoginFormController(), "Chat Room", "../views/ChatRoomForm.fxml");
    }

    public void CreateNewAccountOnAction(ActionEvent actionEvent) throws IOException {
        PrimaryStageCommon<LoginFormController> s = new PrimaryStageCommon().setStage(loginFormPaneId, new LoginFormController(), "Create New Customer Account Form", "../views/CreateCustomerAccountForm.fxml");
    }
}

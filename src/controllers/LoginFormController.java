package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import util.PrimaryStageCommon;

import java.io.IOException;

public class LoginFormController {
    public static String userName;
    public AnchorPane loginFormPaneId;
    public JFXTextField txtUserNameId;
    public JFXTextField txtPasswordId;

    public void initialize() throws IOException {

    }

    public void LoginOnAction(ActionEvent actionEvent) throws IOException {
        userName = txtUserNameId.getText();
        PrimaryStageCommon<LoginFormController> s = new PrimaryStageCommon().setStage(loginFormPaneId, new LoginFormController(), "Chat Room", "../views/ChatRoomForm.fxml");
    }
}

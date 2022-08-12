package controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import util.PrimaryStageCommon;

import java.io.IOException;

public class ChatRoomFormController {
    public AnchorPane leftChatVBoxId;
    public AnchorPane rightChatVBoxId;
    public JFXTextField txtSendMessageId;
    public ImageView imgBackToLoginBtn;
    public AnchorPane chatRoomFormId;
    public ScrollPane scrollPaneId;

    public void initialize(){
        scrollPaneId.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void sendMessageBtnOnClick(MouseEvent mouseEvent) {
    }

    public void imgBackToLoginBtnOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        PrimaryStageCommon<LoginFormController> s = new PrimaryStageCommon().setStage(chatRoomFormId, new LoginFormController(), "Login Form", "../views/LoginForm.fxml");
    }
}

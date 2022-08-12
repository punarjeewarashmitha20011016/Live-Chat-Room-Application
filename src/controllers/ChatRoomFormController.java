package controllers;

import bo.BOFactory;
import bo.custom.MessageBO;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dto.MessageDTO;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import util.PrimaryStageCommon;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class ChatRoomFormController {
    public JFXTextField txtSendMessageId;
    public ImageView imgBackToLoginBtn;
    public AnchorPane chatRoomFormId;
    public ScrollPane scrollPaneId;
    public JFXTextArea txtAreaId;
    Socket socket = null;
    private MessageBO messageBO = (MessageBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.MESSAGE);

    public void initialize() throws IOException {
        scrollPaneId.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Random r = new Random();
        int i = r.nextInt(3001) + 2000;
        System.out.println("Num - " + i);
        socket = new Socket("localhost", 3000);
        setMessagesToTxtArea();
    }

    public void sendMessageBtnOnClick(MouseEvent mouseEvent) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(new MessageDTO(LoginFormController.userName, txtSendMessageId.getText()));
        objectOutputStream.flush();
        setMessagesToTxtArea();
    }

    public void imgBackToLoginBtnOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        PrimaryStageCommon<LoginFormController> s = new PrimaryStageCommon().setStage(chatRoomFormId, new LoginFormController(), "Login Form", "../views/LoginForm.fxml");
    }

    public void setMessagesToTxtArea() {
        List<MessageDTO> all = messageBO.getAll();
        for (int i = 0; i < all.size(); i++) {
            txtAreaId.appendText(all.get(i).getName() + " : " + all.get(i).getMessage() + "\n");
        }
    }
}

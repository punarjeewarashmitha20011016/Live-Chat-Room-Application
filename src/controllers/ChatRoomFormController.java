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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ChatRoomFormController {
    public static JFXTextArea txtAreaIdDup;
    public JFXTextField txtSendMessageId;
    public ImageView imgBackToLoginBtn;
    public AnchorPane chatRoomFormId;
    public ScrollPane scrollPaneId;
    public JFXTextArea txtAreaId;
    Socket socket = null;
    private MessageBO messageBO = (MessageBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.MESSAGE);
    private boolean check = false;
    private boolean ifClicked = false;

    public synchronized void setClient() {
        new Thread(() -> {
            try {
                System.out.println("running");
                while (check != true) {
                    if (ifClicked == true) {
                        socket = new Socket("localhost", 3000);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(new MessageDTO(LoginFormController.userName, txtSendMessageId.getText()));
                        objectOutputStream.flush();
                        System.out.println("Check");
                        /*Thread.sleep(1000);*/
                        ifClicked = false;
                    }
                    if (txtSendMessageId.getText().equalsIgnoreCase("Exist")) {
                        check = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void initialize() throws IOException, ClassNotFoundException {
        scrollPaneId.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        txtAreaIdDup = txtAreaId;
        setMsgToTxtAreaFromDb();
        setClient();


    }

    public void sendMessageBtnOnClick(MouseEvent mouseEvent) {
        ifClicked = true;
        txtAreaId.appendText(LoginFormController.userName + " : " + txtSendMessageId.getText() + "\n");
    }

    public void imgBackToLoginBtnOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        PrimaryStageCommon<LoginFormController> s = new PrimaryStageCommon().setStage(chatRoomFormId, new LoginFormController(), "Login Form", "../views/LoginForm.fxml");
    }

    public void setMessagesToTxtArea() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        List<MessageDTO> all = (List<MessageDTO>) objectInputStream.readObject();
        System.out.println(all.toString());
        if (all != null) {
            txtAreaId.clear();
            for (int i = 0; i < all.size(); i++) {
                System.out.println(all.get(i).getMessage());
                txtAreaId.appendText(all.get(i).getName() + " : " + all.get(i).getMessage() + "\n");
            }
        }
    }

    public void setMsgToTxtAreaFromDb() {
        List<MessageDTO> all = messageBO.getAll();
        txtAreaId.clear();
        for (int i = 0; i < all.size(); i++) {
            txtAreaId.appendText(all.get(i).getName() + " : " + all.get(i).getMessage() + "\n");
        }
    }
}

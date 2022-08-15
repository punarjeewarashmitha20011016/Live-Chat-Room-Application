package Client_Side.controller;

import Client_Side.model.Message;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessageFormController extends Thread {

    public Label lblContactName;
    public TextField txtMessage;

    public Socket socket;

    public ObjectInputStream objectInputStream;
    public ObjectOutputStream objectOutputStream;
    public VBox messageVBox;
    ObservableList<Label> observableList = FXCollections.observableArrayList();

    public void initialize() {
        System.out.println("Initialized method" + ClientLoginFormController.userName);
        lblContactName.setText(ClientLoginFormController.userName);
        try {
            socket = new Socket("localhost", 5000);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            this.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*txtArea.setEditable(false);*/
    }

    public void messageSend() {
        String msg = txtMessage.getText().trim();
        try {
            objectOutputStream.writeObject(new Message(ClientLoginFormController.userName, msg));
            objectOutputStream.flush();

            System.out.println("flushed");
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*txtArea.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);*/
        txtMessage.setText("");
        if (msg.equalsIgnoreCase("Bye") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
    }

    public void txtMsgOnAction(ActionEvent actionEvent) {
        messageSend();
    }


    public void sendBtnOnAction(MouseEvent mouseEvent) {
        messageSend();
    }

    @Override
    public void run() {

        try {
            System.out.println("returned");

            while (true) {
                Message msg = (Message) objectInputStream.readObject();
                System.out.println("Msg In Client Thread : " + msg);
                if (msg.getName().equalsIgnoreCase(ClientLoginFormController.userName + ": ")) {
                    continue;
                } else if (msg.getMessage().equalsIgnoreCase("bye")) {
                    break;
                }
                Thread.sleep(500);
                Label label = new Label();
                Platform.runLater(() -> {
                            label.setText(msg.getName() + " : " + msg.getMessage() + "\n\n");
                            label.setStyle("-fx-background-color: green;-fx-text-fill: white;-fx-background-radius: 10 10 10 10;-fx-border-radius: 10 10 10 10;-fx-padding: 0 10 0 10");
                            observableList.addAll(label);
                            messageVBox.getChildren().clear();
                            messageVBox.setSpacing(10);
                            for (int i = 0; i < observableList.size(); i++) {
                                messageVBox.getChildren().addAll(observableList.get(i));
                            }
                        }
                );
                /*txtArea.appendText(msg.getName() + " : " + msg.getMessage() + "\n\n");*/

            }

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

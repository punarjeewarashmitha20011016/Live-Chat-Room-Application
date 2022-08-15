package Client_Side.controller;

import Client_Side.model.Message;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
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
    public AnchorPane messageControllerPane;
    ObservableList<Label> observableList = FXCollections.observableArrayList();
    String imageFilePath;

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
    }

    public void messageSend() {
        String msg = txtMessage.getText().trim();
        try {
            if (imageFilePath != null) {
                System.out.println("File path : " + imageFilePath);
                objectOutputStream.writeObject(new Message(ClientLoginFormController.userName, msg, imageFilePath));
                objectOutputStream.flush();
            } else {
                objectOutputStream.writeObject(new Message(ClientLoginFormController.userName, msg, null));
                objectOutputStream.flush();
            }

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
                            if (msg.getImage() != null) {
                                Image image = new Image(msg.getImage());
                                ImageView imageView = new ImageView();
                                imageView.setImage(image);
                                imageView.setFitWidth(100);
                                imageView.setFitHeight(100);

                                if (msg.getMessage() != null) {
                                    label.setText(msg.getName() + " : " + msg.getMessage() + "\n\n");
                                    label.setGraphic(imageView);
                                    label.setContentDisplay(ContentDisplay.BOTTOM);
                                } else {
                                    label.setText(msg.getName() + " : " + "\n\n");
                                    label.setGraphic(imageView);
                                    label.setContentDisplay(ContentDisplay.BOTTOM);
                                }
                            } else {
                                label.setText(msg.getName() + " : " + msg.getMessage() + "\n\n");
                            }
                            label.setStyle("-fx-background-color:  #4CDF79;-fx-text-fill: white;-fx-background-radius: 10 10 10 10;-fx-border-radius: 10 10 10 10;-fx-padding: 0 10 0 10");
                            observableList.addAll(label);
                            messageVBox.getChildren().clear();
                            messageVBox.setSpacing(10);
                            for (int i = 0; i < observableList.size(); i++) {
                                messageVBox.getChildren().addAll(observableList.get(i));
                            }
                        }
                );
            }

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void fileChooserOnClick(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        File file = fileChooser.showOpenDialog(messageControllerPane.getScene().getWindow());

        imageFilePath = file.toURI().toString();
    }
}

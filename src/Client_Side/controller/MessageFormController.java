package Client_Side.controller;

import Client_Side.model.Message;
import javafx.event.ActionEvent;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.Socket;

public class MessageFormController extends Thread {

    public Label lblContactName;
    public TextArea txtArea;
    public TextField txtMessage;

    public BufferedReader reader;
    public PrintWriter writer;
    public Socket socket;

    public ObjectInputStream objectInputStream;
    public ObjectOutputStream objectOutputStream;

    public void initialize() {
        System.out.println("Initialized method" + ClientLoginFormController.userName);
        lblContactName.setText(ClientLoginFormController.userName);
        try {
            socket = new Socket("localhost", 5000);
            System.out.println("Socket is connecting with server");
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Socket is connecting with server2");
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            this.start();
            System.out.println("Socket is connecting with server3");
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtArea.setEditable(false);
    }

    public void messageSend() {
        String msg = txtMessage.getText().trim();
        /*writer.println(ClientLoginFormController.userName + ": " + msg);*/
        try {
            objectOutputStream.writeObject(new Message(ClientLoginFormController.userName, msg));
            objectOutputStream.flush();

            System.out.println("flushed");
        } catch (IOException e) {
            e.printStackTrace();
        }
        txtArea.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
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
                    System.out.println("Socket is connecting with server3");
                    continue;
                } else if (msg.getMessage().equalsIgnoreCase("bye")) {
                    break;
                }
                txtArea.appendText(msg.getName() + " : " + msg.getMessage() + "\n\n");

            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

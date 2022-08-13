package controllers;

import bo.BOFactory;
import bo.custom.MessageBO;
import com.jfoenix.controls.JFXTextArea;
import dto.MessageDTO;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
    private static ServerSocket serverSocket;
    private static Socket accept;
    private static boolean check = false;
    private static MessageBO messageBO = (MessageBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.MESSAGE);

    public static void main(String[] args) throws IOException {

        serverSocket = new ServerSocket(3000);
        System.out.println("Server Started");

        new Thread(() -> {
            while (check != true) {
                try {
                    System.out.println("Running");
                    accept = serverSocket.accept();
                    System.out.println("Connection Established");
                    ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
                    MessageDTO dto = (MessageDTO) objectInputStream.readObject();
                    System.out.println("Message - " + dto);
                    messageBO.save(dto);

                    JFXTextArea txtArea = null;
                    passListOfDataToInterface(dto, txtArea);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void passListOfDataToInterface(MessageDTO dto, JFXTextArea txtArea) throws IOException {
        FXMLLoader loader = new FXMLLoader(ServerController.class.getResource("../views/ChatRoomForm.fxml"));
        ChatRoomFormController dac = (ChatRoomFormController) loader.getController();
        txtArea = dac.txtAreaId;
        txtArea.appendText(dto.getName() + " : " + dto.getMessage() + "\n");

        /*List<MessageDTO> all = messageBO.getAll();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(accept.getOutputStream());
        objectOutputStream.writeObject(all);
        objectOutputStream.flush();*/
    }
}

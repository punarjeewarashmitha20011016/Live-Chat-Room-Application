package controllers;

import bo.BOFactory;
import bo.custom.MessageBO;
import dto.MessageDTO;

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
        new Thread(() -> {
            System.out.println("Server Started");
            try {
                serverSocket = new ServerSocket(3000);
                System.out.println("Connection Established");
                accept = serverSocket.accept();
                while (check != true) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
                    MessageDTO dto = (MessageDTO) objectInputStream.readObject();
                    System.out.println("Message - " + dto);
                    messageBO.save(dto);
                    /*setMessagesToTxtArea();*/
                    /*serverSocket.close();*/
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

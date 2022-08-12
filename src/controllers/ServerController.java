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
                accept = serverSocket.accept();
                System.out.println("Connection Established");

                while (check != true) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
                    MessageDTO dto = (MessageDTO) objectInputStream.readObject();
                    System.out.println("Message - " + dto);
                    messageBO.save(dto);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

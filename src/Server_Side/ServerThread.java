package Server_Side;

import Client_Side.model.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {

    private final Socket socket;
    private final ArrayList<ServerThread> threadArrayList;
    private ObjectOutputStream objectOutputStream;

    public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
        this.socket = socket;
        this.threadArrayList = threads;
    }

    @Override
    public void run() {
        try {
            //Reading the input from Client


            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());


            /*returning the output to the client : true statement is to flush the buffer otherwise
            we have to do it manually*/
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            //infinite loop for server
            while (true) {
                /*String outputString = input.readLine();*/
                Message msg = (Message) objectInputStream.readObject();

                //if user type exit command then program will terminate
                if (msg.getMessage().equals("exit")) {
                    break;
                }
                System.out.println("Server received " + msg.getMessage());
                printToAllClients(msg.getName(), msg.getMessage());


            }
        } catch (Exception e) {
            System.out.println("Error occurred " + e.getStackTrace());
        }
    }

    private void printToAllClients(String name, String outputString) throws IOException {
        for (ServerThread sT : threadArrayList) {
            try {
                System.out.println("Server side name = " + name);
                System.out.println("Server side msg = " + outputString);
                sT.objectOutputStream.writeObject(new Message(name, outputString));
                sT.objectOutputStream.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

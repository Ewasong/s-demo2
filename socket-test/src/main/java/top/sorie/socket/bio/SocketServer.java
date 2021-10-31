package top.sorie.socket.bio;


import top.sorie.socket.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public void start(int port) {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                System.out.println("clinet connected");
                try (ObjectInputStream objectInputStream =
                             new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream objectOutputStream =
                             new ObjectOutputStream(socket.getOutputStream());) {
                    Message message = (Message) objectInputStream.readObject();
                    System.out.println("recevied: " + message.getContent());
                    message.setContent("resp to " + message.getContent());
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer();
        socketServer.start(8090);
    }
}

package top.sorie.socket.bio;

import top.sorie.socket.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8090)){
            System.out.println("connected");
            try (ObjectOutputStream objectOutputStream =
                         new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream inputStream =
                         new ObjectInputStream(socket.getInputStream());){
                Message hello = new Message("hello, sorie");
                objectOutputStream.writeObject(hello);
                objectOutputStream.flush();
                Message resp = (Message) inputStream.readObject();
                System.out.println(resp.getContent());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

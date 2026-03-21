package labQuestions.NetworkProgram;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        System.out.println("Trying to connect with server .......");
        Socket socket = new Socket("localhost", 7777);
        System.out.println("Server connected........" + socket.getInetAddress());
        System.out.println("Start chatting");

        DataInputStream din = new DataInputStream(socket.getInputStream());
        DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

        Scanner scanner = new Scanner(System.in);

        Thread sendMessageThread = new Thread(() -> {
            while(true){
                try{
                    String message = scanner.nextLine();
                    dout.writeUTF(message);
                    dout.flush();

                    if(message.equalsIgnoreCase("exit")){
                        System.out.println("The server has stopped the chat.");
                        break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    break;
                }
            }
        });

        Thread readMessageThread = new Thread(() -> {
            while(true){
                try{
                    String clientMessage = din.readUTF();
                    System.out.println("Server: " + clientMessage);

                    if(clientMessage.equalsIgnoreCase("exit")){
                        System.out.println("The client has stopped the chat.");
                        break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    break;
                }
            }
        });

        sendMessageThread.start();
        readMessageThread.start();
    }
}

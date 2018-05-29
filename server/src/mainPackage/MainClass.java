package mainPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainClass {
    static final int PORT = 4545;

    public static void main(String[] args) throws IOException {
       /* ServerSocket s = new ServerSocket(PORT);
        System.out.println("Server Started");
        try {
            while (true) {
                Socket socket = s.accept();
                try {
                    new Server();
                }
                catch (Exception e) {
                    socket.close();
                }
            }
        }
        finally {
            s.close();
        }*/
       new Server();
    }
}

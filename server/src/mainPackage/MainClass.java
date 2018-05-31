package mainPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainClass {
    static final int PORT = 4545;

    public static void main(String[] args) throws IOException {
       new Server();
    }
}

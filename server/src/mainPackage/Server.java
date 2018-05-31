package mainPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class Server extends Thread{
    JTextArea area;
    JButton buttonExit;

    public Server(){
        JFrame f = new JFrame("Server");
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setSize(240, 250);
        f.setLayout(null);

        area = new JTextArea();
        buttonExit = new JButton("Выход");
        f.add(area).setBounds(10,10,200,100);
        f.add(buttonExit).setBounds(10,140,200,30);

        f.setAlwaysOnTop(true);
        f.setVisible(true);

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        connect();

    }

    public void connect(){
        int port = 2155;
        String encryptionFileName;

        try {
            ServerSocket ss = new ServerSocket(port);
            area.append("Ждем подключения...");

            while(true){
                Socket soket = ss.accept();

                InputStream in = soket.getInputStream();
                DataInputStream din = new DataInputStream(in);

                int filesCount = din.readInt();//получаем количество файлов
                area.setText("Передается " + filesCount + " файлов\n");

                for(int i = 0; i<filesCount; i++){
                    area.append("Прием " + (i+1) + "вого файла: \n");

                    long fileSize = din.readLong(); // получаем размер файла

                    String fileName = din.readUTF(); //прием имени файла
                    fileName = ConvertingString.addDirName(fileName); //добавляем имя папки
                    encryptionFileName = ConvertingString.addEncryptionPart(fileName);

                    area.append("Имя файла: " + fileName+"\n");

                    area.append("Размер файла: " + fileSize + " байт\n");

                    byte[] buffer = new byte[64*1024];
                    FileOutputStream outF = new FileOutputStream(fileName);
                    FileOutputStream outF2 = new FileOutputStream(encryptionFileName);
                    int count, total = 0;

                    while ((count = din.read(buffer)) != -1){
                        total += count;
                        outF2.write(buffer, 0, count); //записываем в файл с щифротекстом
                        Decryption.makeDecryption(buffer); //расшифровываем
                        outF.write(buffer,0,count); //записываем в файл с обычным текстом
                        if(total == fileSize){
                            break;
                        }
                    }
                    outF2.flush();
                    outF2.close();
                    outF.flush();
                    outF.close();
                    area.append("Файл принят\n---------------------------------\n");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}

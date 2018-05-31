package mainPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkWithNetwork {
    JTextArea area;
    JTextField field;
    Socket socket;
    ArrayList<String> selectFiles;
    WorkWithNetwork(){

        JFrame f = new JFrame("Client");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(240, 250);
       // f.setLayout(new BorderLayout());
        f.setVisible(true);

        area = new JTextArea();
        field = new JTextField(20);
        final JButton selectBut = new JButton("Выбрать");

        final JButton but = new JButton("Отправить");
        but.setEnabled(false);
        f.setLayout(null);
        f.add(selectBut).setBounds(10,10,200,30);
        f.add(area).setBounds(10,50,200,100);
        f.add(but).setBounds(10,160,200,30);

        but.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    sendFiles(selectFiles);
                } catch (IOException ex) {
                    Logger.getLogger(WorkWithNetwork.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        selectBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setMultiSelectionEnabled(true);
                selectFiles = new ArrayList<String>();
                area.setText("");
                int returnVal = chooser.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION){
                    area.append("Выбранны файлы для передачи:\n" );
                    File[] file = chooser.getSelectedFiles();
                    for (File d : file){
                        selectFiles.add(d+"");
                        area.append(d+"\n");
                    }
                    but.setEnabled(true);
                }
            }
        });
    }

    private void sendFiles(ArrayList<String> list) throws IOException{
        //----------------------------------------------
        int port = 2155;
        String addres = "127.0.0.1";
        InetAddress ipAddress = null;
        try {
            ipAddress = InetAddress.getByName(addres);
            socket = new Socket(ipAddress, port);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //----------------------------------------------
        int countFiles = list.size();

        DataOutputStream outD;
        try{
            outD = new DataOutputStream(socket.getOutputStream());

            outD.writeInt(countFiles);//отсылаем количество файлов

            for(int i = 0; i<countFiles; i++){
                File f = new File(list.get(i));

                outD.writeLong(f.length());//отсылаем размер файла
                outD.writeUTF(f.getName());//отсылаем имя файла

                FileInputStream in = new FileInputStream(f);
                byte [] buffer = new byte[64*1024];
                int count;

                while((count = in.read(buffer)) != -1){
                    Encryption.makeEncryption(buffer);
                    outD.write(buffer, 0, count);
                }
                outD.flush();
                in.close();
            }
            socket.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}

package mainPackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Form extends JFrame {
    private JButton buttonChooseFile = new JButton("Выбрать файл");
    private JLabel labelFileInServer = new JLabel("Файлы на сервере");
    private JComboBox comboBoxFileInSeerver = new JComboBox();
    private JTextField fieldFileName = new JTextField(20);
    private JButton buttonSendFile = new JButton("Отправить");

    public Form(){
        setLayout(null);
        add(buttonChooseFile).setBounds(20,20,200,30);
        add(fieldFileName).setBounds(20,60,200,20);
        add(buttonSendFile).setBounds(20,90,200,30);

        componentAction();
    }

    private void componentAction(){
        buttonChooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileOpen = new JFileChooser();
                String dirPath;
                String fileName;
                int ret = fileOpen.showDialog(null, "Load file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    fileName = fileOpen.getSelectedFile().getName();
                    dirPath = fileOpen.getCurrentDirectory().toString();
                    fieldFileName.setText(dirPath+"\\"+fileName);
                }
            }
        });

        buttonSendFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WorkWithNetwork();
            }
        });
    }




}

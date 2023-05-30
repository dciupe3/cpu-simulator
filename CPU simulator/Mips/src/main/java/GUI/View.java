package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {

    JPanel mainPanel = new JPanel();
    JPanel panelPoza;
    JPanel panelJos = new JPanel();

    JTextArea instructionTextArea;
    JTextArea instructionTextArea2;
    JTextArea regTextArea;
    JTextArea ramTextArea;
    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;

    JLabel pcLabel = new JLabel("");

    JButton pipe1 = new JButton();
    JButton pipe2 = new JButton();
    JButton pipe3 = new JButton();
    JButton pipe4 = new JButton();

    public View(){
        setSize(1920,1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);



        panelPoza = new JPanel() {
            public void paintComponent(Graphics g){
                ImageIcon im = new ImageIcon("C:\\Users\\david\\OneDrive\\Desktop\\mips2.png");
                Image i = im.getImage();

                g.drawImage(i, 0, 0, this.getSize().width, this.getSize().height, this);
            }
        };

        panelPoza.setSize(1920, 700);

        mainPanel.setLayout(new GridLayout(2, 1));
        //setContentPane(new JLabel(new ImageIcon("C:\\Users\\david\\OneDrive\\Desktop\\mips.png")));
        //setLayout(new FlowLayout());

        JButton button = new JButton("bvvsad");
        button.setBounds(40, 4, 200, 50);

        //add pe poza

        pcLabel.setBounds(45, 300, 44, 45);
        pcLabel.setBackground(Color.pink);
        pcLabel.setHorizontalAlignment(SwingConstants.CENTER);

        pcLabel.setOpaque(true);
        pcLabel.setFont(new Font("Arial", Font.BOLD, 26));

        pipe1.setBounds(300, 465, 130, 50);
        pipe1.setBackground(Color.green);
        pipe1.setHorizontalAlignment(SwingConstants.CENTER);
        pipe1.setText("IF/ID");
        pipe1.setFont(new Font("Arial", Font.BOLD, 18));

        pipe2.setBounds(780, 465, 130, 50);
        pipe2.setBackground(Color.green);
        pipe2.setHorizontalAlignment(SwingConstants.CENTER);
        pipe2.setText("ID/EX");
        pipe2.setFont(new Font("Arial", Font.BOLD, 18));

        pipe3.setBounds(1190, 465, 130, 50);
        pipe3.setBackground(Color.green);
        pipe3.setHorizontalAlignment(SwingConstants.CENTER);
        pipe3.setText("EX/MEM");
        pipe3.setFont(new Font("Arial", Font.BOLD, 18));

        pipe4.setBounds(1603, 465, 134, 50);
        pipe4.setBackground(Color.green);
        pipe4.setHorizontalAlignment(SwingConstants.CENTER);
        pipe4.setText("MEM/WB");
        pipe4.setFont(new Font("Arial", Font.BOLD, 18));

        panelJos.setBackground(Color.BLACK);

        panelJos.setLayout(new GridLayout(1, 5));

        //PANEL 1
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.DARK_GRAY);
        instructionTextArea = new JTextArea("addi $2 $4 127\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "sw $2 1($0)\n" +
                "noop\n" +
                "noop\n" +
                "noop\n" +
                "lw $3 1($0)");
        JScrollPane scrollPane1 = new JScrollPane(instructionTextArea);
        //instructionTextArea.setPreferredSize(new Dimension(350, 400));
        scrollPane1.setPreferredSize(new Dimension(350, 400));
        panel1.add(scrollPane1);

        //PANEL 2
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.GRAY);
        instructionTextArea2 = new JTextArea();
        JScrollPane scrollPane2 = new JScrollPane(instructionTextArea2);
        //instructionTextArea2.setPreferredSize(new Dimension(350, 400));
        instructionTextArea2.setEditable(false);
        scrollPane2.setPreferredSize(new Dimension(350, 400));
        panel2.add(scrollPane2);


        //PANEL 3
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.DARK_GRAY);

        regTextArea = new JTextArea();
        JScrollPane scrollPane3 = new JScrollPane(regTextArea);
        //regTextArea.setPreferredSize(new Dimension(350, 400));
        regTextArea.setEditable(false);
        scrollPane3.setPreferredSize(new Dimension(350, 400));
        panel3.add(scrollPane3);

        //PANEL4
        JPanel panel4 = new JPanel();
        panel4.setBackground(Color.GRAY);

        ramTextArea = new JTextArea();
        JScrollPane scrollPane4 = new JScrollPane(ramTextArea);
        //ramTextArea.setPreferredSize(new Dimension(350, 400));
        scrollPane4.setPreferredSize(new Dimension(350, 400));
        panel4.add(scrollPane4);

        //PANEL5
        JPanel panel5 = new JPanel();
        panel4.setBackground(Color.lightGray);

        panel5.setLayout(new GridLayout(2, 1));
        button1 = new JButton("Upload code");
        button1.setFont(new Font("Arial", Font.BOLD, 24));

        button2 = new JButton("");
        button3 = new JButton("");
        button4 = new JButton("CLOCK");
        button4.setFont(new Font("Arial", Font.BOLD, 24));

        panel5.add(button1);
        //panel5.add(button2);
        //panel5.add(button3);
        panel5.add(button4);


        panelJos.add(panel1);
        panelJos.add(panel2);
        panelJos.add(panel3);
        panelJos.add(panel4);
        panelJos.add(panel5);

        mainPanel.add(panelPoza);
        mainPanel.add(panelJos);

        this.add(pcLabel);
        this.add(pipe1);
        this.add(pipe2);
        this.add(pipe3);
        this.add(pipe4);

        this.add(mainPanel);
        setSize(1919,1079);
        setSize(1920,1080);

    }



    private void writeTextArea(JTextArea textArea){
        textArea.append("AAAAAAAA");
        textArea.append("BBBBBBB");
    }

    public String getText1(){
        return instructionTextArea.getText();

    }
    public void updatePC(String str){
        pcLabel.setText(str);
        System.out.println("hai");
    }


    public void writeTextArea2(String s){
        instructionTextArea2.setText(s);
    }

    public void writeTextArea3(String s){
        regTextArea.setText(s);
    }

    public void writeTextArea4(String s){
        ramTextArea.setText(s);
    }

    public void addButton1Listener(ActionListener listener){
        button1.addActionListener(listener);
    }

    public void addButton2Listener(ActionListener listener){
        button2.addActionListener(listener);
    }

    public void addButton3Listener(ActionListener listener){
        button3.addActionListener(listener);
    }

    public void addButton4Listener(ActionListener listener){
        button4.addActionListener(listener);
    }

    public void addPipe1Listener(ActionListener listener){
        pipe1.addActionListener(listener);
    }
    public void addPipe2Listener(ActionListener listener){
        pipe2.addActionListener(listener);
    }

    public void addPipe3Listener(ActionListener listener){
        pipe3.addActionListener(listener);
    }
    public void addPipe4Listener(ActionListener listener){
        pipe4.addActionListener(listener);
    }



}

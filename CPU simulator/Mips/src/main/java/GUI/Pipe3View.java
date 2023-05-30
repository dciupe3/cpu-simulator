package GUI;

import javax.swing.*;
import java.awt.*;

public class Pipe3View extends JFrame {

    JTextArea textArea;
    JPanel panel = new JPanel();

    public Pipe3View(){
        setSize(500,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        panel.setBackground(Color.DARK_GRAY);
        textArea = new JTextArea();
        JScrollPane scrollPane1 = new JScrollPane(textArea);
        scrollPane1.setPreferredSize(new Dimension(400, 500));
        panel.add(scrollPane1);

        this.add(panel);
    }

    public void writeTextArea(String s){
        textArea.setText(s);
    }
}

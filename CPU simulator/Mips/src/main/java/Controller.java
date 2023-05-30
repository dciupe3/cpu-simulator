import GUI.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Controller {

    Mips mips;
    View view;
    TransformInstruction transformInstruction;

    Pipe1View pipe1View;
    Pipe2View pipe2View;
    Pipe3View pipe3View;
    Pipe4View pipe4View;


    int count = 0;

    public Controller(Mips m, View v){
        this.mips = m;
        this.view = v;
        this.transformInstruction = new TransformInstruction("C:\\Users\\david\\OneDrive\\Desktop\\facultate\\SSC\\Mips\\src\\main\\java\\instruction2.txt");

        v.addButton1Listener(new Button1Listener());
        v.addButton2Listener(new Button2Listener());
        v.addButton3Listener(new Button3Listener());
        v.addButton4Listener(new Button4Listener());
        v.addPipe1Listener(new Pipe1Listener());
        v.addPipe2Listener(new Pipe2Listener());
        v.addPipe3Listener(new Pipe3Listener());
        v.addPipe4Listener(new Pipe4Listener());
    }

    class Button1Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                transformInstruction.clearList();

                String text = view.getText1();
                System.out.println("-");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\david\\OneDrive\\Desktop\\facultate\\SSC\\Mips\\src\\main\\java\\instruction2.txt"))) {
                    writer.write(text);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                transformInstruction.writeFile();

                String fileName = "C:\\Users\\david\\OneDrive\\Desktop\\facultate\\SSC\\Mips\\src\\main\\java\\instruction3.txt";
                String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
                view.writeTextArea2(fileContent);

                mips.updateMemory();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    class Button2Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    class Button3Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {


            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    class Button4Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                System.out.println("--------------------------" + (++count));
                mips.run();

                String str = mips.showAllReg();
                view.writeTextArea3(str);

                String str2 = mips.showMem();
                //System.out.println(str2);
                view.writeTextArea4(str2);

                String s = mips.getPC();
                view.updatePC(s);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    class Pipe1Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                pipe1View = new Pipe1View();
                String str = mips.showRegIFID();
                pipe1View.writeTextArea(str);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
    class Pipe2Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                pipe2View = new Pipe2View();
                String str = mips.showRegIDEX();
                pipe2View.writeTextArea(str);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
    class Pipe3Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                pipe3View = new Pipe3View();
                String str = mips.showRegEXMEM();
                pipe3View.writeTextArea(str);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
    class Pipe4Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                pipe4View = new Pipe4View();
                String str = mips.showRegMEMWB();
                pipe4View.writeTextArea(str);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }


}

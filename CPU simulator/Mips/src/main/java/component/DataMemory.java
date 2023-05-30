package component;

import java.io.*;
import java.util.ArrayList;
public class DataMemory {
    private ArrayList<Long> memory;

    public DataMemory(){
        memory = new ArrayList<Long>(1024);
        for(int i = 0; i < 1024; i++){
            memory.add(0l);
        }
    }

    public DataMemory(String path) {
        memory = new ArrayList<Long>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();

            while (line != null) {
                Long nr = convertStringToLong(line);
                memory.add(nr);
                //System.out.println(nr);
                //System.out.println(line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateInstructions(String path){
        memory.clear();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();

            while (line != null) {
                Long nr = convertStringToLong(line);
                memory.add(nr);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long convertStringToLong(String string){
        Long nr = 0l;

        if(string.equals(""))
            return 0l;
        else
            nr = Long.parseLong(string, 2);

        return nr;
    }


    public ArrayList<Long> getMemory(){
        return memory;
    }

    public long getValue(long address){

        //TO DO : exeptie
        if(address >= memory.size()){
            System.out.println("Adresa prea mare");
            return -1;
        }

        return memory.get((int) address);
    }

    public void storeValue(long address, long value){

        memory.add(Math.toIntExact(address), value);
        if(address >= memory.size()){
            System.out.println("Adresa prea mare");
        }
    }

}

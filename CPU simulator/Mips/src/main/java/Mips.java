import component.*;
import stage.*;

public class Mips {

    private ProgramCounter pc = new ProgramCounter();
    private DataMemory instructionMemory = new DataMemory("C:\\Users\\david\\OneDrive\\Desktop\\facultate\\SSC\\Mips\\src\\main\\java\\instruction3.txt");
    private DataMemory dataMemory = new DataMemory();
    private RegisterPipeline if_id = new RegisterPipeline();
    private RegisterPipeline id_ex = new RegisterPipeline();
    private RegisterPipeline ex_mem = new RegisterPipeline();
    private RegisterPipeline mem_wb = new RegisterPipeline();
    private RegisterFile registerFile = new RegisterFile();
    private InstructionFetch instructionFetch;
    private InstructionDecode instructionDecode;
    private InstructionExecute instructionExecute;
    private Memory memory;
    private WriteBack writeBack;

    public Mips(){
        instructionFetch = new InstructionFetch(if_id, pc, instructionMemory);
        instructionDecode = new InstructionDecode(if_id, id_ex, registerFile, pc);
        instructionExecute = new InstructionExecute(ex_mem, id_ex, mem_wb);
        memory = new Memory(ex_mem, mem_wb, dataMemory);
        writeBack = new WriteBack(mem_wb, registerFile);
    }

    public void run(){
        instructionFetch.run();
        instructionDecode.run();
        instructionExecute.run();
        memory.run();
        writeBack.run();
        clock();

        //System.out.println(if_id.getValueFromReg("INSTRUCTION"));

        //if_id.showAllReg();
        //id_ex.showAllReg();
        //ex_mem.showAllReg();
        //mem_wb.showAllReg();
/*        System.out.println("REGISTRIIIIIIIIIIII");
        registerFile.showAllReg();*/
    }

    public String showRegIFID(){
        return if_id.getAllRegString();
    }
    public String showRegIDEX(){
        return id_ex.getAllRegString();
    }
    public String showRegEXMEM(){
        return ex_mem.getAllRegString();
    }
    public String showRegMEMWB(){
        return mem_wb.getAllRegString();
    }

    public String showAllReg(){
        String s = new String();

        int i = 0;
        for (Register r : registerFile.getArrayOfReg()){
            s = s + i + ": " + r.getValue() + " = " + (Long.toBinaryString(r.getValue() | 4294967296l).substring(1,33)) + "\n";
            i++;
        }
        return  s;
    }

    public String showMem(){
        String s = new String();

        int i = 0;
        for(Long l : dataMemory.getMemory()){
            s = s + i + ": " + l + " =  " + (Long.toBinaryString(l | 4294967296l).substring(1,33)) +  "\n";
            i++;
        }
        return  s;
    }

    public void updateMemory(){
        instructionMemory.updateInstructions("C:\\Users\\david\\OneDrive\\Desktop\\facultate\\SSC\\Mips\\src\\main\\java\\instruction3.txt");
    }

    public String getPC(){
        //return Long.toString(pc.getValue());
        return Long.toString(if_id.getValueFromReg("PC"));
    }

    private void clock() {
        pc.clock();
        if_id.clock();
        id_ex.clock();
        ex_mem.clock();
        mem_wb.clock();
    }
}

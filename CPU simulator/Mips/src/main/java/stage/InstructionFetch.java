package stage;

import component.DataMemory;
import component.ProgramCounter;
import component.RegisterPipeline;

import static java.lang.Integer.toBinaryString;

public class InstructionFetch {

    private RegisterPipeline if_id;
    private ProgramCounter pc;
    DataMemory rom;

    public InstructionFetch(RegisterPipeline if_id, ProgramCounter pc, DataMemory rom){
        this.if_id = if_id;
        this.pc = pc;
        this.rom = rom;
    }

    public void run(){
        long instruction = rom.getValue(pc.getValue() / 4); // /4?
        System.out.println("instruction: " + toBinaryString((int) instruction));
        if_id.setValueFromReg("PC", pc.getValue());
        pc.increment();
        System.out.println(pc.getValue());
        if_id.setValueFromReg("PC_INC", (pc.getValue() + 4));
        if_id.setValueFromReg("INSTRUCTION", instruction);
    }

    public RegisterPipeline getIf_id() {
        //System.out.println("1 " + if_id.getValueFromReg("INSTRUCTION"));
        return if_id;
    }
}

package stage;

import component.DataMemory;
import component.RegisterPipeline;

public class Memory {
    private RegisterPipeline ex_mem;
    private RegisterPipeline mem_wb;
    private DataMemory ram;

    public Memory(RegisterPipeline ex_mem, RegisterPipeline mem_wb, DataMemory ram){
        this.ex_mem = ex_mem;
        this.mem_wb = mem_wb;
        this.ram = ram;
    }

    public void run(){
        if(ex_mem.getValueFromReg("MEM_WRITE") == 1){
            System.out.println("MERGE MAAAAA");
            System.out.println(ex_mem.getValueFromReg("ALURES") + " " + ex_mem.getValueFromReg("WRITEDATA"));
            ram.storeValue(ex_mem.getValueFromReg("ALURES"), ex_mem.getValueFromReg("WRITEDATA"));
        }

        long memRes = ram.getValue(ex_mem.getValueFromReg("ALURES")); //ar trebui o exceptie

        mem_wb.setValueFromReg("MEM_RESULT", memRes);
        ex_mem.forwardValues(mem_wb);
    }

}

package stage;

import component.RegisterFile;
import component.RegisterPipeline;

public class WriteBack {

    private RegisterPipeline mem_wb;
    private RegisterFile registerFile;

    public WriteBack(RegisterPipeline mem_wb, RegisterFile registerFile) {
        this.mem_wb = mem_wb;
        this.registerFile = registerFile;
    }

    public void run(){
        long addr = 0l;
        long value = 0l;
        if(mem_wb.getValueFromReg("REGWRITE") == 1){

            if(mem_wb.getValueFromReg("REGDST") == 1)
                addr = mem_wb.getValueFromReg("RD");
            else
                addr = mem_wb.getValueFromReg("RT");

            if(mem_wb.getValueFromReg("MEMTOREG") == 1){
                value = mem_wb.getValueFromReg("MEM_RESULT");
            }
            else
                value = mem_wb.getValueFromReg("ALURES");
        }

        registerFile.setValueFromReg(Long.toString(addr), value);
        registerFile.clock();
    }

}

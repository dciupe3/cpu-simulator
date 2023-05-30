package stage;

import component.RegisterPipeline;

public class InstructionExecute {

    private RegisterPipeline ex_mem;
    private RegisterPipeline id_ex;
    private RegisterPipeline mem_wb;

    public InstructionExecute(RegisterPipeline ex_mem, RegisterPipeline id_ex, RegisterPipeline mem_wb){
        this.ex_mem = ex_mem;
        this.id_ex = id_ex;
        this.mem_wb = mem_wb;
    }

    public void run(){

        long dest = 0l;
        if(id_ex.getValueFromReg("REGDST") == 1){
            dest = ex_mem.getValueFromReg("RD");
        }
        else {
            dest = ex_mem.getValueFromReg("RT");
        }

        long dest2 = 0l;
        if(id_ex.getValueFromReg("REGDST") == 1){
            dest2 = mem_wb.getValueFromReg("RD");
        }
        else {
            dest2 = mem_wb.getValueFromReg("RT");
        }

        long aluArg1 = 0l;
        long aluArg2 = 0l;

        if(ex_mem.getValueFromReg("REGWRITE") == 1 && dest == id_ex.getValueFromReg("RS")){
            aluArg1 = ex_mem.getValueFromReg("ALURES");
        }
        else if (mem_wb.getValueFromReg("REGWRITE") == 1 && dest2 == id_ex.getValueFromReg("RS")){  // pentru hazarduri
            if(mem_wb.getValueFromReg("MEMTOREG") == 1){
                aluArg1 = mem_wb.getValueFromReg("MEM_RESULT");
            }
            else {
                aluArg1 = mem_wb.getValueFromReg("ALU_RESULT");
            }
        }
        else {
            aluArg1 = id_ex.getValueFromReg("READ_DATA_1");  //asta e de obicei
        }

        //aluArg1 = id_ex.getValueFromReg("READ_DATA_1");

        long writeData = id_ex.getValueFromReg("READ_DATA_2");

        if(ex_mem.getValueFromReg("REGWRITE") == 1 && dest == id_ex.getValueFromReg("RT")){
            writeData = ex_mem.getValueFromReg("ALU_RESULT");
        }
        else if(mem_wb.getValueFromReg("REGWRITE") == 1 && dest2 == id_ex.getValueFromReg("RT")){
            if(mem_wb.getValueFromReg("MEM_TO_REG") == 1){
                writeData = mem_wb.getValueFromReg("MEM_RESULT");
            }
            else {
                writeData = mem_wb.getValueFromReg("ALU_RESULT");
            }
        }
        else {
            writeData = id_ex.getValueFromReg("READ_DATA_2");
        }

        if(id_ex.getValueFromReg("ALUSRC") == 0){
            aluArg2 = writeData;
        }
        else {
            aluArg2 = id_ex.getValueFromReg("IMMEDIATE");
        }

        id_ex.forwardValues(ex_mem);

        long aluRes = 0l;

        switch((int) id_ex.getValueFromReg("ALUOP")) {
            case (0) :   //add
                aluRes = aluArg1 + aluArg2;
                break;
            case (1) :   //dif
                aluRes = aluArg1 - aluArg2;
                break;
            case (2) :   //and
                aluRes = aluArg1 & aluArg2;
                break;
            case (3) :   //or
                aluRes = aluArg1 | aluArg2;
                break;
            case (4) :   //XOR
                aluRes = aluArg1 ^ aluArg2;
                break;
            case (5) :   //SLT
                if(aluArg1 < aluArg2){
                    aluRes = 1;
                }
                else{
                    aluRes = 0;
                }
                break;
        }

        ex_mem.setValueFromReg("ALURES", aluRes);
        ex_mem.setValueFromReg("WRITEDATA", writeData);
    }



}

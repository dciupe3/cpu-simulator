package stage;

import component.ProgramCounter;
import component.RegisterFile;
import component.RegisterPipeline;

import java.util.HashMap;

public class InstructionDecode {

    private RegisterPipeline if_id;
    private RegisterPipeline id_ex;
    private RegisterFile regFile;
    private ProgramCounter pc;

    private static final int NOP = 63;

    public InstructionDecode(RegisterPipeline if_id, RegisterPipeline id_ex, RegisterFile regFile, ProgramCounter pc) {
        this.if_id = if_id;
        this.id_ex = id_ex;
        this.regFile = regFile;
        this.pc = pc;
        this.regFile.add32Reg();   //cream cei 32 de registrii din blocul de registrii
    }

    public void run(){
        long instruction = if_id.getValueFromReg("INSTRUCTION");
        long opCode = (instruction & 0xFC000000 ) >> 26;    //1111 1100 0000 0000 0000 0000 0000 0000
        long rs = (instruction & 0x3E00000) >> 21;          //0000 0011 1110 0000 0000 0000 0000 0000
        long rt = (instruction & 0x1F0000) >> 16;           //0000 0000 0001 1111 0000 0000 0000 0000
        long rd = (instruction & 0xF800) >> 11;             //0000 0000 0000 0000 1111 1000 0000 0000
        long sa = (instruction & 0x7C0) >> 6;               //0000 0000 0000 0000 0000 0111 1100 0000
        long func = (instruction & 0x3F);                   //0000 0000 0000 0000 0000 0000 0011 1111
        long immediate = (instruction & 0xFFFF);            //0000 0000 0000 0000 1111 1111 1111 1111
        long address = (instruction & 0x3FFFFFF);           //0000 0011 1111 1111 1111 1111 1111 1111

        id_ex.setValueFromReg("RS", rs);
        id_ex.setValueFromReg("OPCODE", opCode);
        id_ex.setValueFromReg("RT", rt);
        id_ex.setValueFromReg("RD", rd);
        id_ex.setValueFromReg("SA", sa);
        id_ex.setValueFromReg("FUNC", func);
        id_ex.setValueFromReg("IMMEDIATE", immediate);
        id_ex.setValueFromReg("ADDRESS", address);

        HashMap<String, Long> controlSignals = setControl(func, opCode);

        long readData1 = regFile.getValueFromReg(Long.toString(rs));
        long readData2 = regFile.getValueFromReg(Long.toString(rt));

        id_ex.setValueFromReg("READ_DATA_1", readData1);
        id_ex.setValueFromReg("READ_DATA_2", readData2);

        if(controlSignals.get("BRANCH") == 1 && ((controlSignals.get("BRANCH_NE") == 1 && readData1 != readData2) || (controlSignals.get("BRANCH_NE") == 0 && readData1 == readData2) )){
            System.out.println("Face branch");
            takeBranch(immediate);
        }
        else if(controlSignals.get("JUMP") == 1){
            long jumpAddress = address;

            if(controlSignals.get("JUMP_SRC") == 1){
                jumpAddress = readData1;

                jumpAddress = jumpAddress / 4;
            }

            takeJump(jumpAddress);
        }


    }

    private HashMap<String, Long> setControl(long func, long opCode){
        HashMap<String, Long> c = new HashMap<String, Long>();

        c.put("JUMP", 0l);
        c.put("MEMTOREG", 0l);
        c.put("REGWRITE", 0l);
        c.put("MEM_WRITE", 0l);
        c.put("BRANCH", 0l);
        c.put("ALUOP", 0l);
        c.put("ALUSRC", 0l);
        c.put("REGDST", 0l);
        c.put("EXTOP", 0l);
        c.put("JUMP_SRC", 0l);
        c.put("BRANCH_NE", 0l);


        id_ex.setValueFromReg("JUMP", 0l);
        id_ex.setValueFromReg("MEMTOREG", 0l);
        id_ex.setValueFromReg("REGWRITE", 0l);
        id_ex.setValueFromReg("MEM_WRITE", 0l);
        id_ex.setValueFromReg("BRANCH", 0l);
        id_ex.setValueFromReg("ALUOP", 0l);
        id_ex.setValueFromReg("ALUSRC", 0l);
        id_ex.setValueFromReg("REGDST", 0l);
        id_ex.setValueFromReg("EXTOP", 0l);
        id_ex.setValueFromReg("BRANCH_NE", 0l);


        if(opCode == NOP){
            System.out.println("NOOP");
            return c;
        }


        switch ((int)opCode) {
            case 0 :                    //Operatie aritmetica
                c.put("REGDST", 1l);
                id_ex.setValueFromReg("REGDST", 1l);

                c.put("REGWRITE", 1l);
                id_ex.setValueFromReg("REGWRITE", 1l);

                switch ((int) func) {
                    case 0 :            //ADD
                        c.put("ALUOP", 0l);
                        id_ex.setValueFromReg("ALUOP", 0l);
                        break;
                    case 1 :            //SUB
                        c.put("ALUOP", 1l);
                        id_ex.setValueFromReg("ALUOP", 1l);
                        break;
                    case 2 :            //AND
                        c.put("ALUOP", 2l);
                        id_ex.setValueFromReg("ALUOP", 2l);
                        break;
                    case 3 :            //OR
                        c.put("ALUOP", 3l);
                        id_ex.setValueFromReg("ALUOP", 3l);
                        break;
                    case 4 :            //XOR
                        c.put("ALUOP", 4l);
                        id_ex.setValueFromReg("ALUOP", 4l);
                        break;
                    case 5 :            //SLT
                        c.put("ALUOP", 5l);
                        id_ex.setValueFromReg("ALUOP", 5l);
                        break;
                    case 6 :            //JR - jump register
                        c.put("JUMP", 1l);
                        id_ex.setValueFromReg("JUMP", 1l);

                        c.put("JUMP_SRC", 1l);
                        id_ex.setValueFromReg("JUMP_SRC", 1l);
                        break;
                }
                break;

            case 0x8 :  //ADDI
                c.put("ALUSRC", 1l);
                id_ex.setValueFromReg("ALUSRC", 1l);
                c.put("REGWRITE", 1l);
                id_ex.setValueFromReg("REGWRITE", 1l);
                break;

            case 0xC :  //ANDI  001100
                c.put("ALUSRC", 1l);
                id_ex.setValueFromReg("ALUSRC", 1l);
                c.put("REGWRITE", 1l);
                id_ex.setValueFromReg("REGWRITE", 1l);
                c.put("ALUOP", 2l);
                id_ex.setValueFromReg("ALUOP", 2l);
                break;

            case 0xD :  //ORI  001101
                c.put("ALUSRC", 1l);
                id_ex.setValueFromReg("ALUSRC", 1l);
                c.put("REGWRITE", 1l);
                id_ex.setValueFromReg("REGWRITE", 1l);
                c.put("ALUOP", 3l);
                id_ex.setValueFromReg("ALUOP", 3l);
                break;

            case 0xE :  //XORI  001110
                c.put("ALUSRC", 1l);
                id_ex.setValueFromReg("ALUSRC", 1l);
                c.put("REGWRITE", 1l);
                id_ex.setValueFromReg("REGWRITE", 1l);
                c.put("ALUOP", 4l);
                id_ex.setValueFromReg("ALUOP", 4l);
                break;

            case 0xA :  //SLTI 001010
                c.put("ALUSRC", 1l);
                id_ex.setValueFromReg("ALUSRC", 1l);
                c.put("REGWRITE", 1l);
                id_ex.setValueFromReg("REGWRITE", 1l);
                c.put("ALUOP", 5l);
                id_ex.setValueFromReg("ALUOP", 5l);
                break;

            case 0x4 :  //BEQ 000100
                c.put("BRANCH", 1l);
                id_ex.setValueFromReg("BRANCH", 1l);
                c.put("ALUOP", 1l);
                id_ex.setValueFromReg("ALUOP", 1l);
                break;

            case 0x5 :  //BNE 000101
                c.put("BRANCH", 1l);
                id_ex.setValueFromReg("BRANCH", 1l);
                c.put("BRANCH_NE", 1l);
                id_ex.setValueFromReg("BRANCH_NE", 1l);
                c.put("ALUOP", 1l);
                id_ex.setValueFromReg("ALUOP", 1l);
                break;

            case 0x2 :  //JUMP 000010
                c.put("JUMP", 1l);
                id_ex.setValueFromReg("JUMP", 1l);

                break;

            case 0x23 :  //LW 100011
                c.put("ALUSRC", 1l);
                id_ex.setValueFromReg("ALUSRC", 1l);
                c.put("REGWRITE", 1l);
                id_ex.setValueFromReg("REGWRITE", 1l);
                c.put("MEMTOREG", 1l);
                id_ex.setValueFromReg("MEMTOREG", 1l);
                //MEMREAD
                break;

            case 0x2B :  //SW 101011
                c.put("ALUSRC", 1l);
                id_ex.setValueFromReg("ALUSRC", 1l);
                c.put("MEM_WRITE", 1l);
                id_ex.setValueFromReg("MEM_WRITE", 1l);
                break;

        }



        return c;
    }

    private void takeBranch(long address){
        zeroOutRegister(if_id);

        pc.setValue(if_id.getValueFromReg("PC") + 4 + 4 * address);
        pc.enableWrite();
        System.out.println("New PC" + if_id.getValueFromReg("PC"));
    }

    private void takeJump(long address){
        zeroOutRegister(if_id);

        //System.out.println("Address : " + address + " PC: " + pc.getValue());
        pc.setValue(address * 4);
        pc.enableWrite();
       // System.out.println("PC SCHIMBAT" + pc.getValue());
    }


    private void zeroOutRegister(RegisterPipeline r) {
        r.setValueFromReg("JUMP", 0l);
        r.setValueFromReg("JUMP_SRC", 0l);
        r.setValueFromReg("MEMTOREG", 0l);
        r.setValueFromReg("REGWRITE", 0l);
        r.setValueFromReg("MEM_WRITE", 0l);
        r.setValueFromReg("BRANCH", 0l);
        r.setValueFromReg("BRANCH_NE", 0l);
        r.setValueFromReg("ALUOP", 0l);
        r.setValueFromReg("ALUSRC", 0l);
        r.setValueFromReg("REGDST", 0l);
        r.setValueFromReg("EXTOP", 0l);
        r.setValueFromReg("OPCODE", 0xFF);
    }


}

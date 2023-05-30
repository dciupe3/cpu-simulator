import GUI.View;

import static java.lang.Integer.toBinaryString;

public class Main {
    public static void main(String[] args) {


        Mips mips = new Mips();
/*        mips.run();
        System.out.println("--------------------------");
        mips.run();
        System.out.println("--------------------------");
        mips.run();
        System.out.println("--------------------------");
        mips.run();
        System.out.println("--------------------------");
        mips.run();
        System.out.println("--------------------------");
        mips.run();*/

        View view = new View();
        Controller controller = new Controller(mips, view);

/*        component.DataMemory instructionMemory = new component.DataMemory();
        component.RegisterPipeline if_id = new component.RegisterPipeline();
        component.ProgramCounter pc = new component.ProgramCounter();

        instructionMemory.storeValue(0, 0x00111000);
        stage.InstructionFetch instructionFetch = new stage.InstructionFetch(if_id, pc, instructionMemory);
        instructionFetch.run();
        if_id.clock();
        System.out.println("rom : " + toBinaryString((int) instructionFetch.rom.getValue(0)));
        System.out.println("instruction: " + toBinaryString((int) instructionFetch.getIf_id().getValueFromReg("INSTRUCTION")));*/
    }
}

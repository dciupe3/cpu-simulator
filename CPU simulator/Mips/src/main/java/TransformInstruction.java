import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransformInstruction {

    private String path;
    private List<String> mipsInstructions = new ArrayList<>();

    public TransformInstruction(String path){
        this.path = path;
    }

    public void convert(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();

            while (line != null) {

                String mipsInstruction = generateMipsInstruction(line);
                mipsInstructions.add(mipsInstruction);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile() throws IOException {
        convert();
        BufferedWriter br = new BufferedWriter(new FileWriter("C:\\Users\\david\\OneDrive\\Desktop\\facultate\\SSC\\Mips\\src\\main\\java\\instruction3.txt"));
        for (String str : mipsInstructions) {
            System.out.println(str);
            br.write(str + System.lineSeparator());
        }
        br.close();
    }

    private String generateMipsInstruction(String assambly){
        String instr = new String();

        String finalOpcode = "";
        String finalrd = "";
        String finalrt = "";
        String finalrs = "";


        String[] fields = assambly.split(" ");
        String[] fields2 = assambly.split(" ");
        String opcode = fields[0];
        String rd = "";
        String rs = "";
        String rt = "";

        //System.out.println(opcode + rd + rs + rt);

        if(opcode.equals("noop") || opcode.equals("noOp")){
            return "11111111111111111111111111111111";
        }
        else if(isTypeRInstruction(opcode)){
            rd = fields[1];
            rs = fields[2];
            rt = fields[3];
            System.out.println(opcode + rd + rs + rt);

            String func = getFunc(opcode);
            finalOpcode = "000000";
            finalrd = rd.substring(1);
            finalrs = rs.substring(1);
            finalrt = rt.substring(1);

            int rdI = Integer.valueOf(finalrd) | 32;
            int rsI = Integer.valueOf(finalrs) | 32;
            int rtI = Integer.valueOf(finalrt) | 32;

            finalrd = Integer.toBinaryString(rdI).substring(1);
            finalrs = Integer.toBinaryString(rsI).substring(1);
            finalrt = Integer.toBinaryString(rtI).substring(1);
            String sa = "00000";


            instr = String.format("%s%s%s%s%s%s", finalOpcode, finalrs, finalrt, finalrd, sa, func);
        }
        else if(isTypeIInstruction(opcode)){

            rt = fields2[1];
            rs = fields2[2];

            String immediate = fields2[3];
            int imdInt = Integer.valueOf(immediate) | 65536;
            immediate = Integer.toBinaryString(imdInt).substring(1);

            finalrs = rs.substring(1);
            finalrt = rt.substring(1);
            int rsI = Integer.valueOf(finalrs) | 32;
            int rtI = Integer.valueOf(finalrt) | 32;
            finalrs = Integer.toBinaryString(rsI).substring(1);
            finalrt = Integer.toBinaryString(rtI).substring(1);

            finalOpcode = getOpcode(opcode);

            instr = String.format("%s%s%s%s", finalOpcode, finalrs, finalrt, immediate);
        }
        else if(opcode.equals("lw") || opcode.equals("sw")){

            rt = fields[1];
            String pattern = "(-?\\d+)\\((\\$\\w+)\\)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(fields[2]);
            String imm = "";
            if (m.find()) {
                imm = m.group(1);
                rs = m.group(2);
                System.out.println("Immediate value: " + imm);
                System.out.println("$rs register: " + rs);
            }
            finalrs = rs.substring(1);
            int rsI = Integer.valueOf(finalrs) | 32;
            finalrs = Integer.toBinaryString(rsI).substring(1);
            finalrt = rt.substring(1);
            int rtI = Integer.valueOf(finalrt) | 32;
            finalrt = Integer.toBinaryString(rtI).substring(1);


            finalOpcode = getOpcode(opcode);

            int imdInt = Integer.valueOf(imm) | 65536;
            imm = Integer.toBinaryString(Integer.valueOf(imdInt)).substring(1);

            instr = String.format("%s%s%s%s", finalOpcode, finalrs, finalrt, imm);
        }
        else if(opcode.equals("j")){
            String address = fields[1];

            finalOpcode = "000010";
            long addr = Long.valueOf(address) | 67108864l;
            address = Long.toBinaryString(addr).substring(1);

            instr = String.format("%s%s", finalOpcode, address);
        }
        else if(opcode.equals("jr")){
            String func = "000110";
            finalOpcode = "000000";
            rs = fields[1];
            finalrs = rs.substring(1);
            int rsI = Integer.valueOf(finalrs) | 32;
            finalrs = Integer.toBinaryString(rsI).substring(1);

            finalrt = "00000";
            finalrd = "00000";
            String sa = "00000";

            instr = String.format("%s%s%s%s%s%s", finalOpcode, finalrs, finalrt, finalrd, sa, func);

        }

        return instr;
    }

    public void clearList(){
        mipsInstructions.clear();
    }

    private boolean isTypeRInstruction(String s){
        if(s.equals("add") || s.equals("sub") || s.equals("and") || s.equals("or") || s.equals("xor") || s.equals("slt")){
            return true;
        }
        return false;
    }

    private boolean isTypeIInstruction(String s){
        if(s.equals("addi") || s.equals("subi") || s.equals("andi") || s.equals("ori") || s.equals("xori") || s.equals("slti") || s.equals("beq") || s.equals("bne")){
            return true;
        }
        return false;
    }

    private String getOpcode(String s){
        String op = "";

        switch (s) {
            case "addi" :
                op = "001000";
                break;
            case "subi" :
                op = "001001";
                break;
            case "andi" :
                op = "001100";
                break;
            case "ori" :
                op = "001101";
                break;
            case "xori" :
                op = "001110";
                break;
            case "slti" :
                op = "001010";
                break;
            case "beq" :
                op = "000100";
                break;
            case "bne" :
                op = "000101";
                break;
            case "lw" :
                op = "100011";
                break;
            case "sw" :
                op = "101011";
                break;
        }
        return op;
    }


    private String getFunc(String s){
        String func = "";

        switch (s) {
            case "add" :
                func = "000000";
                break;
            case "sub" :
                func = "000001";
                break;
            case "and" :
                func = "000010";
                break;
            case "or" :
                func = "000011";
                break;
            case "xor" :
                func = "000100";
                break;
            case "slt" :
                func = "000101";
                break;
            case "jr" :
                func = "000110";
                break;
        }

        return func;
    }

}

package component;

import component.Register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
public class RegisterFile {

    protected HashMap<String, Register> registers = new HashMap<String, Register>();

    private boolean write = true;

    public void setValueFromReg(String name, long value){
        Register register = getRegisterFromMap(name);
        register.setValue(value);
    }

    public long getValueFromReg(String name){
        return getRegisterFromMap(name).getValue();
    }

    private Register getRegisterFromMap(String name){
        //TO DO : sa mai verific daca numele ii valid

        Register register = registers.get(name);

        if(register == null){
            register = new Register();
            registers.put(name, register);
        }

        return register;
    }

    public void showAllReg(){
        for (Entry<String, Register> r : registers.entrySet()) {
            System.out.println(r.getKey() + ": " + r.getValue().getValue());
        }
    }

    public String getAllRegString(){
        String str = new String();
        for (Entry<String, Register> r : registers.entrySet()) {
            str = str + r.getKey() + ": " + r.getValue().getValue() + " = " + (Long.toBinaryString(r.getValue().getValue() | 4294967296l).substring(1,33)) + "\n";
        }
        return str;
    }

    public ArrayList<Register> getArrayOfReg(){
        ArrayList<Register> reg = new ArrayList<Register>();


        for(int i = 0; i < 32; i++){
            reg.add(registers.get(Integer.toString(i)));
        }

        return reg;
    }

    public void add32Reg(){
        for(long i = 0; i < 32; i++){
            registers.put(Long.toString(i), new Register());
        }
    }

    public void enableWrite(){
        this.write = true;
    }
    public void disableWtrite(){
        this.write = false;
    }

    public void clock(){
        if(write){
            for(Register r : registers.values()){
                r.clock();
            }
        }
    }

}

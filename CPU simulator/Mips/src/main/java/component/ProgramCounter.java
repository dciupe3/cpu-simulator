package component;

public class ProgramCounter extends Register {

    public ProgramCounter (){
        //disableWrite();
    }

    public void increment(){
        newValue = getValue() + 4;
        //setInstantValue(getValue() + 4);
    }
}

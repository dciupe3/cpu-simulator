package component;

import java.util.Map.Entry;

public class RegisterPipeline extends RegisterFile {

    public RegisterPipeline(){
        super();
    }

    public void forwardValues(RegisterPipeline newPipeReg){
        for (Entry<String, Register> entry : registers.entrySet()) {
            newPipeReg.setValueFromReg(entry.getKey(), entry.getValue().getValue());
        }
    }

}

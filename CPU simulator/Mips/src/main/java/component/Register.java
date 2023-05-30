package component;

public class Register {

    //valoarea
    private long value = 0;

    private boolean write = true;

    protected long newValue = -1;

    public Register(){
        this.write = true;
    }

    public Register(long value){
        this.value = value;
    }


    public long getValue(){
        return this.value;
    }

    public void setValue(long v){
        this.newValue = v;
    }

    public void setInstantValue(long value){
        this.value = value;
    }

    public void enableWrite(){
        this.write = true;
    }

    public void disableWrite(){
        this.write = false;
    }

    public void reset(){
        value = 0;
        newValue = 0;
        write = false;
    }

    public void clock() {
        if(write) {
            if(newValue != -1){
                value = newValue;
                newValue = -1;
            }
        }
    }
}

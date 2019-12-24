public class ALU {

    private boolean isNegative;
    private boolean isOverflow;
    private boolean isZero;
    private boolean isCarry;
    private byte leftOperand;
    private byte rightOperand;
    private byte output;
    private byte controlSignal;

    public ALU(){

    }

    public boolean getNegative(){

        return isNegative;
    }

    public boolean getOverflow(){

        return isOverflow;
    }

    public boolean getZero(){

        return isZero;
    }

    public boolean getCarry(){

        return isCarry;
    }

    public byte getOutput() {

        return output;
    }

    public void setLeftOperand(byte leftOperand){

        this.leftOperand = leftOperand;
        execute();
    }

    public void setRightOperand(byte rightOperand){

        this.rightOperand = rightOperand;
        execute();
    }

    public void setControlSignal(byte controlSignal){

        this.controlSignal = controlSignal;
        execute();
    }

    private void execute(){
        this.isCarry = false;
        this.isOverflow = false;
        this.isNegative = false;
        this.isZero = false;

        if(controlSignal == 0){
            passThrough();
        } else if(controlSignal == 1){
            add();
        } else if(controlSignal == 2){
            compare();
        }
    }

    private void add(){
        this.isCarry = false;
        this.isOverflow = false;
        this.isNegative = false;
        this.isZero = false;

        output = (byte) (leftOperand + rightOperand);
        if(((leftOperand < 0) && (rightOperand < 0) && output > 0)) {
            isOverflow = true;
            isCarry = true;
        } else if((leftOperand > 0) && (rightOperand > 0) && (output < 0)){
            isOverflow = true;
            isCarry = true;
        }

    }

    private void compare(){
        this.isCarry = false;
        this.isOverflow = false;
        this.isNegative = false;
        this.isZero = false;

        int difference = rightOperand;
        difference = -difference;
        rightOperand = (byte)difference;
        add();

    }

    private void passThrough(){
        this.isNegative = false;
        this.isZero = false;

        output = leftOperand;

        if(output < 0){
            isNegative = true;
        }else if(output == 0){
            isZero = true;
        }
    }

    public String toString(){
        String retVal = ("N flag: " + getNegative() + "   |   " + "V flag: " + getOverflow() + "   |   " + "Z flag: " + getZero() + "   |   " + "C flag: " + getCarry() + "   |   " + "Left: " + leftOperand + "   |   " + "Right:"+ rightOperand + "   |   " + "Control: " + controlSignal + "\n");
        return retVal;
    }
}
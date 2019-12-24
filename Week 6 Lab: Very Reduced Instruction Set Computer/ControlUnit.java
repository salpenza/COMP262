public class ControlUnit {

    private boolean isStopped;
    private byte instructionRegister;
    private byte programCounter;
    private byte memoryAddressRegister;
    private byte memoryBufferRegister;
    private byte accumulator;
    private byte operandRegister;
    private boolean isDirect;
    private RAM ram = null;
    private ALU alu = null;
    private byte dataRegister;

    public ControlUnit(){
        ram = new RAM();
        alu = new ALU();
    }

    private void fetchInstruction() throws RAM.ModeMismatchException {
        memoryAddressRegister = programCounter;
        ram.setMode(false);
        memoryBufferRegister = ram.readByte(memoryBufferRegister);
        programCounter++;
        instructionRegister = memoryBufferRegister;
    }

    private void decodeInstruction() throws RAM.ModeMismatchException {

        if(instructionRegister > 0 && instructionRegister < 12) {
            memoryAddressRegister = programCounter;
            ram.setMode(false);
            memoryBufferRegister = ram.readByte(memoryAddressRegister);
            programCounter++;
            dataRegister = memoryBufferRegister;
            operandRegister = dataRegister;
            fetchOperand();
        }

    }

    private void fetchOperand() throws RAM.ModeMismatchException {
        memoryAddressRegister = dataRegister;
        ram.setMode(false);
        memoryBufferRegister = ram.readByte(memoryAddressRegister);
        dataRegister = memoryBufferRegister;
    }

    private void execute() throws RAM.ModeMismatchException {

        switch(instructionRegister){
            case 0:
                stop();
                break;
            case 2:
            case 3:
                load();
                break;
            case 4:
            case 5:
                store();
                break;
            case 6:
            case 7:
                compare();
                break;
            case 8:
                jump();
                break;
            case 9:
                jumpIfEqual();
                break;
            case 10:
                jumpIfLessThan();
                break;
            case 11:
                jumpIfGreaterThan();
                break;
        }
    }

    private void load(){
        alu.setControlSignal((byte) 0);
        alu.setLeftOperand(operandRegister);
        accumulator = operandRegister;
    }

    private void store() throws RAM.ModeMismatchException {
        ram.setMode(true);
        ram.writeByte(operandRegister, accumulator);
    }

    private void compare(){
        alu.setControlSignal((byte) 2);
        alu.setRightOperand(operandRegister);
    }

    private void jump(){
        programCounter = operandRegister;
    }

    private void jumpIfEqual(){
        if(alu.getZero()){
            programCounter = operandRegister;
        }
    }

    private void jumpIfGreaterThan(){
        if(!alu.getOverflow() && alu.getNegative()){
            programCounter = operandRegister;
        } else if(alu.getOverflow() && !alu.getNegative()){
            programCounter = operandRegister;
        }
    }

    private void jumpIfLessThan(){
        if(alu.getOverflow() && alu.getNegative()){
            programCounter = operandRegister;
        } else if(!(alu.getOverflow() && alu.getNegative())){
            programCounter = operandRegister;
        }
    }

    private void stop(){
        isStopped = true;
    }

    public String toString(){
        String retval = ("IR: " + Byte.toString(instructionRegister) + "   |   " + "OPR: " + Byte.toString(operandRegister) + "   |   " + "PC: " + Byte.toString(programCounter) + "   |   " + "MAR: " + Byte.toString(memoryAddressRegister) + "   |   " + "MBR: " + Byte.toString(memoryBufferRegister) + "   |   " + "Accumulator:"+ Byte.toString(accumulator) + "\n");
        System.out.println(alu);
        return retval;
    }

    public static void main(String[] args) throws RAM.ModeMismatchException {
        ControlUnit cu = new ControlUnit();
        while(!(cu.isStopped)){
            cu.fetchInstruction();
            cu.decodeInstruction();
            cu.execute();

            System.out.println(cu);
        }
        cu.ram.toString();
    }

}


public class RAM {

    private boolean isWriteMode;
    public byte[] memory = {2,7,7,64,9,20,10,25,2,3,4,70,0,0,0,0,0,0,0,0,2,1,4,70,0,2,2,4,70,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,85,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,};

    class ModeMismatchException extends Exception{

        public ModeMismatchException(String s){

            super(s);
        }
    }

    public RAM(){

    }
    public void setMode(boolean mode){

        isWriteMode = mode;
    }

    public byte readByte(byte address) throws ModeMismatchException{
        if(!isWriteMode) {
            return memory[address];
        }else{
            throw new ModeMismatchException("error");
        }
    }

    public void writeByte(byte address, byte value) throws ModeMismatchException{
        if(isWriteMode){
            memory[address] = value;
        }else{
            throw new ModeMismatchException("error");
        }

    }

}
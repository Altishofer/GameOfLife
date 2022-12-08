package Utils;

import java.util.concurrent.TimeUnit;

public class InputUtils {

    private final int aSecond;
    public InputUtils(int sleep){
        aSecond = sleep;
    }

    public void doSleep(){
        try{
            TimeUnit.SECONDS.sleep(this.aSecond);
        }catch (InterruptedException e){
            System.out.print("Please do not interrupt!\n");
        }
    }

    public static boolean cleanString(String string){
        if (string == null || string.isEmpty() || string.contains(" ")){return false;}
        return true;
    }
}

package manager;

public class Manager {
    private int status;
    public static void main(String[] args) {

        GuiThread guiThread = new GuiThread();
        guiThread.start();

//        SerialThread st = new SerialThread("COM19");
//        try {
//            Thread.sleep(10000);
//            st.stop();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


    }
}

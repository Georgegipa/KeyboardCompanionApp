package manager;

public class Manager {
    private int status;
    public static void main(String[] args) {

        GuiThread guiThread = new GuiThread();
        guiThread.start();

//        String portName  = new SerialScanner(false).detectPort("Arduino");
//        if(portName!=null) {
//            try {
//                SerialPortConnector serialport = new SerialPortConnector(portName);
//                while (true) {
//                    serialport.nbreadPort();
//                }
//            } catch (Exception e) {
//                System.out.println("Port not found!");
//            }
//        }

    }
}

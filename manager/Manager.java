package manager;

public class Manager {
    public static void main(String[] args) {
//        Tray tray= new Tray();
//        tray.PrepareUI();
        SerialScanner ser = new SerialScanner();
        String[] ports = ser.getPortNames();
        for (String key : ports) {
            System.out.println( key);
        }

    }
}

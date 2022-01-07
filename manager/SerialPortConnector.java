package manager;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;

public class SerialPortConnector extends SerialScanner {
    private SerialPortConnector(){
        super();

    }
    public void writePort(String port) throws IOException, InterruptedException {
        if(!super.portExists(port))return;

        SerialPort p = map.get(port);
        if (p.openPort()) {
            System.out.println("Port is open :)");

        } else {
            System.out.println("Failed to open port :(");
            return;
        }

        for (int i = 0; i < 3; ++i) {
            p.getOutputStream().write((byte) i);
            p.getOutputStream().flush();
            System.out.println("Sent number: " + i);
            Thread.sleep(1000);
        }
    }

    public void readPort(String port) {
        SerialPort comPort = SerialPort.getCommPort("COM19");

        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        InputStream in = comPort.getInputStream();
        try {
            for (int j = 0; j < 1000; ++j)
                System.out.println((char) in.read());
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        comPort.closePort();
    }
}

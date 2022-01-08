package manager;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SerialPortConnector extends SerialScanner {
    private final SerialPort comPort;

    public SerialPortConnector(String port) throws Exception {
        super(true);
        comPort = getPort(port);
        if (comPort != null)
            comPort.openPort();
        else  throw new Exception("ComPortNotFound");
    }

    public void closeConnection() {
        comPort.closePort();
    }

    public void writePort() throws IOException, InterruptedException {

        if (comPort.openPort()) {
            System.out.println("Port is open :)");

        } else {
            System.out.println("Failed to open port :(");
            return;
        }

        for (int i = 0; i < 3; ++i) {
            comPort.getOutputStream().write((byte) i);
            comPort.getOutputStream().flush();
            System.out.println("Sent number: " + i);
            Thread.sleep(1000);
        }
    }

    public void readPort() {
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        InputStream in = comPort.getInputStream();
        try {
            for (int j = 0; j < 1000; ++j)
                System.out.println((char) in.read());
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nbreadPort() {
        try {
            while (comPort.bytesAvailable() == 0)
                Thread.sleep(20);

            byte[] readBuffer = new byte[comPort.bytesAvailable()];
            comPort.readBytes(readBuffer, readBuffer.length);
            String s = new String(readBuffer, StandardCharsets.UTF_8);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



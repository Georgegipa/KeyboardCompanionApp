package manager;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SerialPortConnector extends SerialScanner {
    private final SerialPort comPort;
    private boolean connectionEstablished;
    private String boardName;

    public SerialPortConnector(String port) throws Exception {
        super(false);
        comPort = getPort(port);
        if (comPort != null)
            comPort.openPort();
        else throw new Exception("ComPortNotFound");
    }

    public void closeConnection() {

        comPort.closePort();

    }

    private void writebyte(int b) throws IOException, InterruptedException {
        comPort.getOutputStream().write((byte) b);
        comPort.getOutputStream().flush();
        Thread.sleep(100);
    }

    private boolean waitforbyte(int b, int timeout)//timeout in sec
    {
        long startTime = System.currentTimeMillis();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        InputStream in = comPort.getInputStream();
        while ((System.currentTimeMillis() - startTime) < timeout * 1000) {
            startTime = System.currentTimeMillis();
            try {
                if (in.read() == (byte) b)
                    return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean initConnection() throws IOException, InterruptedException {
        if (!comPort.isOpen()) {
            writebyte(5);
            connectionEstablished = true;
            waitforbyte(1, 5);
            comPort.getOutputStream().write((byte) 6);
            Thread.sleep(50);
            boardName = nbreadPort();
            System.out.println("Connection Established with:" + boardName);
        } else
            connectionEstablished = false;
        return connectionEstablished;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String nbreadPort() {
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
        try {
            while (comPort.bytesAvailable() == 0)
                Thread.sleep(50);

            byte[] readBuffer = new byte[comPort.bytesAvailable()];
            comPort.readBytes(readBuffer, readBuffer.length);
            String s = new String(readBuffer, StandardCharsets.UTF_8);
            System.out.println(s);
            return s;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void end()
    {
        comPort.removeDataListener();
        comPort.closePort();
    }

    public void eventBus(){

        comPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
            @Override
            public void serialEvent(SerialPortEvent event)
            {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;
                byte[] newData = new byte[comPort.bytesAvailable()];
                int numRead = comPort.readBytes(newData, newData.length);
                String s = new String(newData, StandardCharsets.UTF_8);
                System.out.println("Read " + numRead + " bytes:"+s);
            }
        });
    }
}



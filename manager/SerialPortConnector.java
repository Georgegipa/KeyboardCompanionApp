package manager;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SerialPortConnector extends SerialScanner {
    private final SerialPort comPort;
    private String boardName, buttons, protocol, protocolVersion;

    public enum Request {name, buttons, protocol, protocol_version}

    public SerialPortConnector(String port) throws Exception {
        super(false);
        comPort = getPort(port);
        if (comPort != null)
            comPort.openPort();
        else throw new Exception("ComPortNotFound");
        if (!checkProtocol())
            throw new Exception("PortNotSupported");
    }

    public void openConnection() {
        comPort.openPort();
        System.out.println("Port " + comPort.getSystemPortName() + " opened!");
    }

    public void closeConnection() {
        comPort.closePort();
        System.out.println("Port " + comPort.getSystemPortName() + " closed!");
    }

    public void writebyte(int b) throws IOException, InterruptedException {
        comPort.getOutputStream().write((byte) b);
        comPort.getOutputStream().flush();
        Thread.sleep(300);
    }


    public boolean checkProtocol() throws IOException, InterruptedException {
        int progressFlag = 1;
        long startTime = System.currentTimeMillis();
        boolean done = false;
        while (!done) {
            switch (progressFlag) {
                case 1:
                    makeRequest(Request.protocol);
                    break;
                case 2:
                    makeRequest(Request.protocol_version);
                    break;
                case 3:
                    makeRequest(Request.name);
                    break;
                case 4:
                    makeRequest(Request.buttons);
                    break;
                case 5:
                    done = true;
                    break;
            }

            if (comPort.bytesAvailable() > 0) {
                String s = readPort();
                if (s.contains("Protocol")) {
                    protocol = s.split(":")[1];
                    progressFlag++;
                } else if (s.contains("Version")) {
                    protocolVersion = s.split(":")[1];
                    progressFlag++;
                } else if (s.contains("Board")) {
                    boardName = s.split(":")[1];
                    progressFlag++;
                } else if (s.contains(("Buttons"))) {
                    buttons = s.split(":")[1];
                    progressFlag++;
                }
                System.out.println(s);
                Thread.sleep(250);
            }
            //timeout in seconds
            long timeout = 5;
            if ((System.currentTimeMillis() - startTime) > timeout * 1000)
                return false;
        }
        System.out.println(protocol + ":" + protocolVersion + " running on board:" + boardName + " with:" + buttons + " buttons");
        return !boardName.isEmpty();//if name is filled then connection is established
    }

    private void makeRequest(Request r) throws IOException, InterruptedException {
        switch (r) {
            case protocol:
                writebyte(1);
                break;
            case protocol_version:
                writebyte(2);
                break;
            case name:
                writebyte(3);
                break;
            case buttons:
                writebyte(4);
                break;
        }
    }

    public String readPort() {
        byte[] readBuffer = new byte[comPort.bytesAvailable()];
        comPort.readBytes(readBuffer, readBuffer.length);
        return new String(readBuffer, StandardCharsets.UTF_8);
    }

    public String readPort2() {
        if (comPort.bytesAvailable() > 0) {
            byte[] readBuffer = new byte[comPort.bytesAvailable()];
            comPort.readBytes(readBuffer, readBuffer.length);
            return new String(readBuffer, StandardCharsets.UTF_8);
        }
        return null;
    }




}



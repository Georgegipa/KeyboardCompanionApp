package manager;

import com.fazecast.jSerialComm.*;

import java.util.HashMap;

public class SerialScanner {
    protected static HashMap<String, SerialPort> map = new HashMap<>();
    public SerialScanner (boolean Verbose){
        refreshPorts();
        if(Verbose) {
            String[] ports = getPortNames();
            System.out.println("Ports found:");
            for (String p : ports) {
                System.out.println("*" + p + "-" + getPortInfo(p));
            }
        }
    }

    protected void refreshPorts() {
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            String portName = port.getSystemPortName();
            map.put(portName, port);
        }
    }

    protected boolean portExists(String port) {
        refreshPorts();
        return map.containsKey(port);
    }

    protected SerialPort getPort(String port) {
        if (portExists(port)) return map.get(port);
        else return null;
    }

    public String[] getPortNames() {
        String[] portNames = new String[map.size()];
        int i = 0;
        for (String key : map.keySet()) {
            portNames[i] = key;
            i++;
        }
        return portNames;
    }

    public String getPortInfo(String port) {
        if (portExists(port)) return map.get(port).getPortDescription();
        else return null;
    }

    public String detectPort(String Keyword) {
        String keyfound = null;
        for (String key : map.keySet()) {
            if (getPortInfo(key).contains(Keyword)) {
                keyfound = key;
                break;
            }
        }
        System.out.println("Detected "+Keyword+":"+keyfound);
        return keyfound;
    }
}

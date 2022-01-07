package manager;

import com.fazecast.jSerialComm.*;

import java.util.HashMap;

public class SerialScanner {
    protected static HashMap<String, SerialPort> map = new HashMap<>();

    public SerialScanner()
    {
        refreshPorts();
    }

    protected void refreshPorts(){
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            String portName = port.getSystemPortName();
            map.put(portName, port);
        }
    }

    public String[] getPortNames(){
        String[] portNames = new String[map.size()];
        int i=0;
        for (String key : map.keySet()) {
            portNames[i] = key;
            i++;
        }
        return portNames;
    }

    protected boolean portExists(String port){
        boolean exists = false;
        refreshPorts();
        return map.containsKey(port);
    }

    protected SerialPort getPort(String port)
    {
        if(portExists(port))return  map.get(port);
        else return null;
    }
}

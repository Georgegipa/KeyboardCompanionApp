package manager;

import java.io.IOException;

public class SerialThread implements Runnable {
    private SerialPortConnector comPort;
    private boolean running;
    Thread t;

    public SerialThread(String port) {
        try {
            comPort = new SerialPortConnector(port);
        } catch (Exception e) {
            System.err.println("Port doesn't exist");
        }
        String threadName = "ThreadPort" + port;
        t = new Thread(this, threadName);
        running = true;
        t.start();
    }

    public void run() {
        comPort.eventBus();
        if(Thread.interrupted())
        {
            comPort.closeConnection();
        }
//        while (!Thread.interrupted()) {
//            //if(comPort.nbreadPort()==null)running=false;
//            comPort.nbreadPort();
//        }
//        try {
//            comPort.initConnection();
//        } catch (Exception e) {
//           System.err.println("Connection error!");
//        }
    }

    public void stop(){
        comPort.closeConnection();
        running = false;

    }
}

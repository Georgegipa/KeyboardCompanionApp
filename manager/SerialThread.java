package manager;

public class SerialThread implements Runnable {
    private SerialPortConnector comPort;
    private boolean running;
    private final String port;
    private Exception e;
    Thread t;

    public SerialThread(String port) {
        this.port = port;
        reEstablishConnection();
    }

    public void reEstablishConnection(){
        try {
            comPort = new SerialPortConnector(port);
        } catch (Exception e){
            this.e = e;
        }
        if(comPort!=null)
            comPort.closeConnection();
        String threadName = "ThreadPort" + port;
        t = new Thread(this, threadName);
        running = true;
        t.start();
    }

    public void run() {

        if(comPort!=null)comPort.openConnection();
        else System.err.println("Error:"+e);
        while (running && comPort!=null)
        {
            String incoming = "";
            if(comPort.bytesAvailable())
                incoming = comPort.readPort2();
            if(!incoming.isEmpty() )
                System.out.println(incoming);
        }
    }

    public void stop(){
        running = false;
        if(comPort!=null)
        comPort.closeConnection();
    }
}

package manager;

public class Manager {
    public static void main(String[] args) {
        GuiThread guiThread = new GuiThread();
        guiThread.start();
    }
}

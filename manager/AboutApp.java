package manager;

public class AboutApp {
    private static final String IconPath = "/assets/keypad.png" ;
    private static final String version = "0.0.1";
    private static final String build = "pre-alpha";
    private static final String developer = "Georgegipa";
    private static final String developer_link = "https://github.com/Georgegipa";

    public static String getDeveloper_link() {
        return developer_link;
    }

    public String getIconPath() {
        return IconPath;
    }

    public static String getName() {
        return "Keyboard Manager";
    }

    public static String getVersion() {
        return version;
    }

    public static String getBuild() {
        return build;
    }

    public static String getDeveloper() {
        return developer;
    }
}

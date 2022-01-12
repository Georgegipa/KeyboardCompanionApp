package manager;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Macros {
    private static final HashMap<String, Integer> keys;
    static {
        keys = new HashMap<>();
        keys.put("CTRL", KeyEvent.VK_CONTROL);
        keys.put("SHIFT", KeyEvent.VK_SHIFT);
        keys.put("ALT", KeyEvent.VK_ALT);
        keys.put("GUI", KeyEvent.VK_WINDOWS);
        keys.put("UP_ARROW", KeyEvent.VK_KP_UP);
        keys.put("DOWN_ARROW", KeyEvent.VK_KP_DOWN);
        keys.put("LEFT_ARROW", KeyEvent.VK_KP_LEFT);
        keys.put("RIGHT_ARROW", KeyEvent.VK_KP_RIGHT);
        keys.put("BACKSPACE", KeyEvent.VK_BACK_SPACE);
        keys.put("TAB", KeyEvent.VK_TAB);
        keys.put("RETURN", KeyEvent.VK_ENTER);
        keys.put("ESC", KeyEvent.VK_ESCAPE);
        keys.put("INSERT", KeyEvent.VK_INSERT);
        keys.put("DELETE", KeyEvent.VK_DELETE);
        keys.put("PAGE_UP", KeyEvent.VK_PAGE_UP);
        keys.put("PAGE_DOWN", KeyEvent.VK_PAGE_DOWN);
        keys.put("HOME", KeyEvent.VK_HOME);
        keys.put("END", KeyEvent.VK_END);
        keys.put("CAPS_LOCK", KeyEvent.VK_CAPS_LOCK);
        keys.put("F1", KeyEvent.VK_F1);
        keys.put("F2", KeyEvent.VK_F2);
        keys.put("F3", KeyEvent.VK_F3);
        keys.put("F4", KeyEvent.VK_F4);
        keys.put("F5", KeyEvent.VK_F5);
        keys.put("F6", KeyEvent.VK_F6);
        keys.put("F7", KeyEvent.VK_F7);
        keys.put("F8", KeyEvent.VK_F8);
        keys.put("F9", KeyEvent.VK_F9);
        keys.put("F10", KeyEvent.VK_F10);
        keys.put("F11", KeyEvent.VK_F11);
        keys.put("F12", KeyEvent.VK_F12);
        keys.put("F13", KeyEvent.VK_F13);
        keys.put("F14", KeyEvent.VK_F14);
        keys.put("F15", KeyEvent.VK_F15);
        keys.put("F16", KeyEvent.VK_F16);
        keys.put("F17", KeyEvent.VK_F17);
        keys.put("F18", KeyEvent.VK_F18);
        keys.put("F19", KeyEvent.VK_F19);
        keys.put("F20", KeyEvent.VK_F20);
        keys.put("F21", KeyEvent.VK_F21);
        keys.put("F22", KeyEvent.VK_F22);
        keys.put("F23", KeyEvent.VK_F23);
        keys.put("F24", KeyEvent.VK_F24);
    }

    public static int findKey(String key)
    {
        return keys.get(key);
    }
}

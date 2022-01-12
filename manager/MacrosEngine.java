package manager;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class MacrosEngine {
    private Robot MK;
    private final char macroCommandRegex=',';
    private final char macroKeysRegex='+';
    private final boolean PROFILES=true;
    private int currentProfile;//currently, selected profile
    private int profilesSum;//number of profiles
    private final int buttonSum;
    private List<Integer> keysPressed = new ArrayList<>();
    private final String[] defaultBindings={
            "CTRL+C",
            "CTRL+V",
            "CTRL+Z",
            "CTRL+Y",
            "ALT+SHIFT+A",
            "CTRL+SHIFT+P",
            "W,calc",
            "W,msedge"
    };

    public MacrosEngine(int buttons)
    {
        try {
            MK = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        buttonSum = buttons;

        profilesSum = defaultBindings.length/buttons;
        int remainingBindings = 0;
        if(defaultBindings.length%buttons!=0) {
            remainingBindings = defaultBindings.length - profilesSum;
            profilesSum++;
        }

        System.out.println("Created new MacrosEngine instance!"+"With "+ buttonSum +" buttons and "+ profilesSum +" profiles");
    }

    //setters and getters for profiles and number of profiles
    public int getDefaultProfilesSum() {
        return profilesSum;
    }

    public int getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(int currentProfile) {
        this.currentProfile = currentProfile;
    }

    private void executeMacro(String macro){
        String[] keys = macro.split(Character.toString(macroKeysRegex));
        for(String key:keys)
        {
            press(Macros.findKey(key));
        }
        releaseAll();
    }

    //finds the corresponding String id value
    private int findMacroID(int profile_id,int button_id){
        return (PROFILES ? buttonSum - 1 : buttonSum) * profile_id + button_id;
    }

    //release all the keys that were just pressed
    private void releaseAll()
    {
        for(int key:keysPressed)
        {
            MK.keyRelease(key);
        }
        keysPressed.clear();
    }

    private void press(int keycode)
    {
        keysPressed.add(keycode);
        MK.keyPress(keycode);
    }

    private void press(KeyEvent key)
    {
        press(key);
    }

    private void pasteText(String text)
    {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String clipboardData= null;
        try {
            clipboardData = (String)clipboard.getData(DataFlavor.stringFlavor);
            StringSelection stringSelection = new StringSelection(text);
            clipboard.setContents(stringSelection, null);
        } catch (Exception e) {
            System.err.println("Clipboard error!");
        }
        press(KeyEvent.VK_CONTROL);
        press('V');
        releaseAll();
        StringSelection stringSelection = new StringSelection(clipboardData);
        clipboard.setContents(stringSelection, null);
    }

    public void parseMacro(int buttonId){
        //check if profiles button is pressed and profiles are enabled
        if(PROFILES && buttonId==0)
        {
            if (currentProfile < profilesSum - 1)
                currentProfile++;
            else
                currentProfile = 0;
        }
        else{
            String macro = defaultBindings[findMacroID(currentProfile,buttonId)];
            char[] macroCommand = macro.substring(0,2).toCharArray();
            if(macroCommand[1]==macroCommandRegex)
            {
                macro = macro.substring(2);
                switch (macroCommand[0]){
                    case 'W':
                        press(524);//windows key
                        press('R');
                        releaseAll();
                        pasteText(macro);
                        press(KeyEvent.VK_ENTER);
                        releaseAll();
                        break;
                    case 'P':
                        pasteText(macro);
                        press(KeyEvent.VK_ENTER);
                        releaseAll();
                        break;
                }
            }
            else executeMacro(macro);
        }
    }

}

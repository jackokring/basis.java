/*
 * (C) 2016 K Ring Technologies Ltd.
 * All right reserved.
 * A request to use this code can be sent to <jacko@kring.co.uk>.
 */
package uk.co.kring.system;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import java.io.File;
import java.io.IOException;
import uk.co.kring.basis.*;

/**
 *
 * @author user
 */
public class ConsoleDevice extends ViewDevice {
    Screen screen;
    MultiWindowTextGUI gui;
    
    public ConsoleDevice(Screen s) {
        screen = s;
        try {
            screen.startScreen();
            gui = new MultiWindowTextGUI(screen, TextColor.ANSI.BLACK);
        } catch(IOException e) {
            Util.log(this, e);
        }
    }

    @Override
    public GeneralString modelSelect(GeneralString named) {
        DialogWindow fd = new FileDialog("File Select", "Select the active file", "Change",
            screen.getTerminalSize(), true, new File(named.toString()));
        return new GeneralString().fromString(fd.showDialog(gui).toString());
    }
    
    @Override
    public void destroy() {
        try {
            screen.stopScreen();
        } catch(IOException e) {
            Util.log(this, e);
        }
        gui = null;
        screen = null;
    }
}

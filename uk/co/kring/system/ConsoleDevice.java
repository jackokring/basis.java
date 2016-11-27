/*
 * (C) 2016 K Ring Technologies Ltd.
 * All right reserved.
 * A request to use this code can be sent to <jacko@kring.co.uk>.
 */
package uk.co.kring.system;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.terminal.*;
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
    Terminal term;
    Screen screen;
    MultiWindowTextGUI gui;
    
    @Override
    public ConsoleDevice init(SystemZome z) {
        setZome(z);
        try {
            term = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(term);
            screen.startScreen();
            gui = new MultiWindowTextGUI(screen, TextColor.ANSI.BLACK);
        } catch(IOException e) {
            Util.log(this, e);
        }
        return this;
    }

    @Override
    public GeneralString modelSelect(GeneralString named) {
        DialogWindow fd = new FileDialog("File Select", "Select the active file", "Change",
            screen.getTerminalSize(), true, new File(named.toString()));
        return new GeneralString().fromString(fd.showDialog(gui).toString());
    }
    
    @Override
    public boolean destroy() {
        boolean ok = true;
        try {
            screen.stopScreen();
        } catch(IOException e) {
            Util.log(this, e);
            ok = false;
        }
        gui = null;
        screen = null;
        term = null;
        return ok;
    }
}

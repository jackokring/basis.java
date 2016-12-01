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
import com.googlecode.lanterna.terminal.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import uk.co.kring.basis.*;

/**
 *
 * @author user
 */
public class ConsoleDevice extends ViewDevice {
    Terminal term;
    Screen screen;
    MultiWindowTextGUI gui;
    Label stat = new Label("Status Messages Here");
    
    @Override
    public ConsoleDevice init(SystemZome z) {
        setZome(z);
        try {
            term = new DefaultTerminalFactory()
                    .setTerminalEmulatorFrameAutoCloseTrigger(
                            TerminalEmulatorAutoCloseTrigger.CloseOnExitPrivateMode)
                    .createTerminal();
            term.enterPrivateMode();
            screen = new TerminalScreen(term);
            screen.startScreen();
            gui = new MultiWindowTextGUI(screen, TextColor.ANSI.BLACK);
            gui.getBackgroundPane().setComponent(stat);
            gui.addWindowAndWait(new BasicWindow("Hello"));//TODO: Test
        } catch(IOException e) {
            Util.log(this, e);
        }
        return this;
    }

    @Override
    public GeneralString modelSelect(GeneralString named) {
        DialogWindow fd = new FileDialog("File Select", "Select the active file", "Change",
            screen.getTerminalSize(), true, new File(named == null ? "" : named.toString()));
        return new GeneralString().fromString(fd.showDialog(gui).toString());
    }

    @Override
    public void statusShow(GeneralString string) {
        stat.setText(string.toString());
    }
    
    @Override
    public boolean destroy() {
        boolean ok = true;
        try {
            Iterator<Window> wc = gui.getWindows().iterator();
            while(wc.hasNext()) {
                wc.next().close();
            }
            screen.stopScreen();
            term.resetColorAndSGR();
            term.exitPrivateMode();
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

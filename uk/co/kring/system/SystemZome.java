/*
 * (C) 2016 K Ring Technologies Ltd.
 * All right reserved.
 * A request to use this code can be sent to <jacko@kring.co.uk>.
 */
package uk.co.kring.system;
import uk.co.kring.basis.*;
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.input.*;
import java.io.IOException;

/**
 *
 * @author user
 */
public class SystemZome extends Zome {
    static Terminal term;
    Screen screen;
    static SystemZome dis;
    ConsoleDevice display;
    KeyboardDevice keyboard;
    FileDevice disk;
    
    public static void main(String[] args) {
        dis = new SystemZome();
        dis.enter(args);
    }
    
    public void enter(String[] args) {
        try {
            if(term == null) term = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(term);
            screen.startScreen();
        } catch(IOException e) {
            Util.log(this, e);
        }
    }
    
    public void exit(int code) {
        try {
            screen.stopScreen();
            term.flush();
        } catch(IOException e) {
            Util.log(this, e);
        }
        if(dis == this) System.exit(code);
    } 
}

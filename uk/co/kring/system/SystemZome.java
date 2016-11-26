/*
 * (C) 2016 K Ring Technologies Ltd.
 * All right reserved.
 * A request to use this code can be sent to <jacko@kring.co.uk>.
 */
package uk.co.kring.system;
import uk.co.kring.basis.*;
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.screen.*;
import java.io.IOException;

/**
 *
 * @author user
 */
public class SystemZome extends Zome {
    static Terminal term;
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
            display = new ConsoleDevice(new TerminalScreen(term));
            keyboard = new KeyboardDevice(term);
        } catch(IOException e) {
            Util.log(this, e);
        }
    }
    
    public static void exitAll(int code) {
        dis.exit(code);
    }
    
    public void exit(int code) {
        try {
            display.destroy();
            term.flush();
        } catch(IOException e) {
            Util.log(this, e);
        }
        if(dis == this) System.exit(code);
    } 

    @Override
    public Zome start() {
        //TODO
        next();
        return this;
    }
}

/*
 * (C) 2016 K Ring Technologies Ltd.
 * All right reserved.
 * A request to use this code can be sent to <jacko@kring.co.uk>.
 */
package uk.co.kring.system;
import uk.co.kring.basis.*;

/**
 *
 * @author user
 */
public class SystemZome extends Zome {
    static SystemZome dis;
    ViewDevice display;
    ControlDevice keyboard;
    ModelDevice disk;
    
    final public ModelDevice m() {
        return disk;
    }
    
    final public ViewDevice v() {
        return display;
    }
    
    final public ControlDevice c() {
        return keyboard;
    }
    
    public static void main(String[] args) {
        dis = new SystemZome();
        dis.enter(args);
    }
    
    public void enter(String[] args) {
        display = new ConsoleDevice().init(this);
        keyboard = new KeyboardDevice().init(this);
        disk = new FileDevice().init(this);
    }
    
    public static void exitAll(int code) {
        dis.exit(code);
    }
    
    public void exit(int code) {
        disk.destroy();
        keyboard.destroy();
        display.destroy();
        if(dis == this) System.exit(code);
    } 

    @Override
    public Zome start() {
        //TODO
        return this;
    }
}

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

    /**
     * @return
     */
    public final ModelDevice m() {
        return disk;
    }

    /**
     * @return
     */
    public final ViewDevice v() {
        return display;
    }

    /**
     * @return
     */
    public final ControlDevice c() {
        return keyboard;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        dis = new SystemZome();
        dis.enter(args);
    }

    /**
     * @param args
     */
    public void enter(String[] args) {
        display = new ConsoleDevice().init(this);
        keyboard = new KeyboardDevice().init(this);
        disk = new FileDevice().init(this);
    }

    /**
     * @param code
     */
    public static void exitAll(int code) {
        dis.exit(code);
    }

    /**
     * @param code
     */
    public void exit(int code) {
        disk.destroy();
        keyboard.destroy();
        display.destroy();
        if(dis == this) System.exit(code);
    }

    /**
     * @return
     */
    @Override
    public Zome start() {
        //TODO
        return this;
    }
}

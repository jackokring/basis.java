/*
 * (C) 2016 K Ring Technologies Ltd.
 * All right reserved.
 * A request to use this code can be sent to <jacko@kring.co.uk>.
 */
package uk.co.kring.system;
import com.googlecode.lanterna.screen.*;
import java.io.IOException;
import uk.co.kring.basis.*;

/**
 *
 * @author user
 */
public class ConsoleDevice extends ViewDevice {
    Screen screen;
    
    public ConsoleDevice(Screen s) {
        screen = s;
        try {
            screen.startScreen();
        } catch(IOException e) {
            Util.log(this, e);
        }
    }
    
    @Override
    public void destroy() {
        try {
            screen.stopScreen();
        } catch(IOException e) {
            Util.log(this, e);
        }
        screen = null;
    }
}

/*
 * (C) 2016 K Ring Technologies Ltd.
 * All right reserved.
 * A request to use this code can be sent to <jacko@kring.co.uk>.
 */
package uk.co.kring.system;
import uk.co.kring.basis.*;
import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.input.*;

/**
 *
 * @author user
 */
public class KeyboardDevice extends ControlDevice {
    
    @Override
    public KeyboardDevice init(SystemZome z) {
        setZome(z);
        return this;
    }
    
    @Override
    public boolean destroy() {
        //TODO
        return true;
    }
}

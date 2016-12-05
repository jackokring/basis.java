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
public class FileDevice extends ModelDevice {

    public FileDevice init(SystemZome z) {
        setZome(z);
        return this;
    }
    
    @Override
    public boolean destroy() {
        //todo
        return true;
    }
}

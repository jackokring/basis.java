/*
 * (C) 2016 K Ring Technologies Ltd.
 * All right reserved.
 * A request to use this code can be sent to <jacko@kring.co.uk>.
 */
package uk.co.kring.basis;

import java.io.IOException;
import uk.co.kring.system.*;

/**
 *
 * @author user
 */
public abstract class Device extends Format {
    ActionList does;
    FormatList with;
    SystemZome zome;
    
    public abstract Device init(SystemZome z);
    
    final public Zome setZome(SystemZome z) {
        return zome = z;
    }

    @Override
    public List setContent(List l) {
        Util.log(this, new IOException("write fail"));
        return this;
    }

    @Override
    public List content() {
        Util.log(this, new IOException("read fail"));
        return new Empty();
    }

    @Override
    public GeneralString toGString() {
        return new GeneralString().fromObject(this);
    }

    @Override
    public GeneralNumber toGNumber() {
        return new GeneralNumber().fromObject(this);
    }
    
    public abstract boolean destroy();
}

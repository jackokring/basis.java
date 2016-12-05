/*
 * (C) 2016 K Ring Technologies Ltd.
 * All right reserved.
 * A request to use this code can be sent to <jacko@kring.co.uk>.
 */
package uk.co.kring.basis;

/**
 *
 * @author user
 */
public abstract class FormatList extends List {
    FormatList formats;

    @Override
    public List content() {
        if(formats == null) throw new RuntimeException("no formats");
        return formats;
    }

    @Override
    public List setContent(List l) {
        formats = (FormatList)l;
        return this;
    }
        
}

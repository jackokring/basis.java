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
public abstract class ActionList extends List {
    ActionList actions;

    @Override
    public List content() {
        if(actions == null) throw new RuntimeException("no actions");
        return actions;
    }

    @Override
    public List setContent(List l) {
        actions = (ActionList)l;
        return this;
    }
    
}

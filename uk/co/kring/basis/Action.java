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
public abstract class Action extends ActionList {

    @Override
    public abstract List setContent(List l);

    @Override
    public abstract List content();
    
}

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
public abstract class Zome extends List {
    ActionList p;
    FormatList q;
    ActionList r;
    FormatList s;
    ModelDevice model;
    ViewDevice view;
    ControlDevice control;    

    @Override
    public GeneralString toGString() {
        return new GeneralString().fromObject(this);
    }

    @Override
    public GeneralNumber toGNumber() {
        return new GeneralNumber().fromObject(this);
    }

    @Override
    public List content() {
        return new StringFormat().setContent(new GeneralString().fromObject(this));
    }

    @Override
    public List setContent(List l) {
        Util.log(this, new SecurityException("can' alter a zome"));
        return this;
    }
    
}

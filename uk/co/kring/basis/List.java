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
public abstract class List {
    List chain;
    public abstract List content();
    public List next() {
        if(chain == null) throw new RuntimeException("broken chain");
        return chain;
    }
    public List setNext(List l) {
        chain = l;
        return this;
    }
    public abstract List setContent(List l);
    public abstract GeneralString toGString();
    public abstract GeneralNumber toGNumber();
    
    public abstract List dispatchInner(GeneralString a, List l);
    public abstract List asCast(GeneralString a, List l);
}

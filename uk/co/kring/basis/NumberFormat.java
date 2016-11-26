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
public class NumberFormat extends Format {
    GeneralNumber num;

    @Override
    public List setContent(List l) {
        num = l.toGNumber();
        return this;
    }

    @Override
    public List content() {
        return new NumberFormat().setContent(this);
    }

    @Override
    public GeneralString toGString() {
        return num.toGString();
    }

    @Override
    public GeneralNumber toGNumber() {
        return num;
    }

}

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
public class StringFormat extends Format {
    GeneralString text;
    
    @Override
    public List setContent(List l) {
        text = l.toGString();
        return this;
    }
    
    public List setContent(GeneralString s) {
        StringFormat sf = new StringFormat();
        sf.text = s;
        return sf;
    }

    @Override
    public List content() {
        return new StringFormat().setContent(this);
    }

    @Override
    public GeneralString toGString() {
        return text;
    }
    
    @Override
    public GeneralNumber toGNumber() {
        return new GeneralNumber().fromGString(text);
    }

}
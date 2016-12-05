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
public class GeneralNumber {
    double num;

    public GeneralString toGString() {
        return new GeneralString().fromString(Double.toString(num));
    }

    public GeneralNumber fromGString(GeneralString s) {
        try {
            num = Double.valueOf(s.toString());
        } catch(NumberFormatException e) {
            num = Double.NaN;
        }
        return this;
    }
    
    public GeneralNumber fromObject(Object o) {
        num = 0;
        Util.log(o, new UnsupportedOperationException("object as number not yet done"));
        return this;
    }
}

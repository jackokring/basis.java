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
public class GeneralString {
    String str;
    
    public GeneralString fromString(String s) {
        str = s;
        return this;
    }
    
    @Override
    public String toString() {
        return str;
    }
}

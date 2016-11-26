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
public class Util {
    
    public static void log(Object c, Exception e) {
        System.err.print("TRACE: ");
        System.err.println(c.getClass().getCanonicalName());
        e.printStackTrace(System.err);
    }
}

/*
 * (C) 2016 K Ring Technologies Ltd.
 * All right reserved.
 * A request to use this code can be sent to <jacko@kring.co.uk>.
 */
package uk.co.kring.conj;

import java.io.*;

/**
 *
 * @author jacko
 */
public class LargeString {
    
    final int max = 1024 * 1024 * 16;
    char[] chars;
    int len = 0;
    int start = 0;
    InputStreamReader ir = null;
    OutputStreamWriter ow = null;
    LargeString chain = null;
    
    public LargeString(String s, InputStream is, OutputStream os) {
        chars = new char[max];//32 mega bytes
        if(os != null) {
            ow = new OutputStreamWriter(os);
        }
        len = s.length();
        for(int i = 0; i < len; i++) {
            chars[i] = s.charAt(i);
        }
        if(is != null) {
            ir = new InputStreamReader(is);
            fillIt();
        }
    }
    
    public LargeString(LargeString ls) {//shallow clone
        chars = ls.chars;
        len = ls.len;
        start = ls.start;
        ir = ls.ir;
        ow = ls.ow;
        chain = ls.chain;
    }
    
    public int length() {
        return len;
    }
    
    public boolean isFull() {
        return len == max;
    }
    
    public boolean isEmpty() {
        return len == 0;
    }
    
    public LargeString concat(LargeString s) {
        if(chain != null) {
            LargeString chn = this;
            while(chn.chain != null) {
                //can't attach twice
                if(chn == s) throw new ArrayIndexOutOfBoundsException("No Recursion In LargeString");
                chn = chn.chain;
            }
            chn.chain = s;
        } else {
            chain = s;
        }
        return this;
    }
    
    public LargeString substring(int st, int en) {
        LargeString ls = this.substring(st);
        if(en > max) throw new ArrayIndexOutOfBoundsException("Bad Substring Of LargeString");
        if(en > ls.len) ls.fill();
        ls.chain = null;
        ls.ir = null;
        ls.len = (en - st + max)%max;
        return ls;
    } 
    
    public LargeString substring(int st) {
        if(st >= max) throw new ArrayIndexOutOfBoundsException("Bad Substring Of LargeString");
        if(st >= len) fill();
        if(st >= len) throw new ArrayIndexOutOfBoundsException("Bad Substring Of LargeString");
        LargeString ls = new LargeString(this);
        ls.len -= st;
        ls.start += st;
        return ls;
    }
    
    public char charAt(int st) {
        if(st >= len) fill();
        if(st >= len) throw new ArrayIndexOutOfBoundsException("Bad Index In LargeString");
        return chars[(st - start + max)%max];
    }
    
    public LargeString push(char ch) {
        if(len == max) {
            chunk();
            len = 0;
        }
        chars[(start + len)%max] = ch;
        len++;
        return this;
    }
    
    public char pull() {
        char ch = charAt(0);
        len--;
        start++;
        return ch;
    }
    
    public void chunk() {
        try {
            try {
                while(true) {
                    ow.write(pull());
                }
            } catch(ArrayIndexOutOfBoundsException e) {
                
            }
        } catch(IOException e) {
            
        }
    }
    
    public void flush() {
        try {
            try {
                while(true) {
                    ow.write(pull());
                }
            } catch(ArrayIndexOutOfBoundsException e) {
                ow.close();
            }
        } catch(IOException e) {
            
        }
    }
    
    private LargeString fillIt() {
        if(ir == null && chain == null) return this;
        int ch;
        while(len < max) {
            ch = -1;
            try {
                try {
                    if(ir != null) {
                        ch = ir.read();
                        if(ch == -1) {
                            ir.close();
                            throw new IOException();
                        }
                    }
                } catch(IOException e) {
                    if(chain != null) {
                        ch = chain.pull();
                    }
                }
            } catch(ArrayIndexOutOfBoundsException e) {
                if(chain != null) {
                    chain = chain.chain;
                    continue;
                }
            }
            if(ch == -1) return this;
            push((char)ch);
        }
        return this;
    }
    
    public LargeString fill() {
        return fillIt();
    }
}

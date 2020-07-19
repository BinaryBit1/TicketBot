package dev.westernpine.common.uid;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

public class UID {

    /*
     * Default length of a randomly generated uid.
     */
    public static final int length = 6;
    /*
     * All defaultcharacters of a uid.
     */
    private static final String chars = RandomString.upper + RandomString.lower + RandomString.digit;

    /*
     * The actual uid.
     */
    private String uid = "";

    /*
     * Creates a new UID.
     */
    public UID() {
        uid = new RandomString(length, chars).nextString();
    }

    public UID(int length) {
        uid = new RandomString(length, chars).nextString();
    }

    /*
     * Random UID with default configurations.
     */
    public static UID randomUID() {
        return from(new RandomString(6, chars).nextString());
    }

    /*
     * Get a UID object from a string.
     */
    public static UID from(String uid) {
        UID id = new UID(length);
        id.uid = uid.toLowerCase();
        return id;
    }

    /*
     * Returns the String of the UID object.
     */
    @Override
    public String toString() {
        return uid;
    }

    /*
     * Compares UID's
     */
    @Override
    public boolean equals(Object object) {
        UID uid = null;
        if (object instanceof UID)
            uid = (UID) object;
        else
            uid = from(object.toString());
        return this.uid.equalsIgnoreCase(uid.uid);
    }
    
    public static class RandomString {

        /*
         * All upper-case letters.
         */
        public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        /*
         * All lower-case letters.
         */
        public static final String lower = "abcdefghijklmnopqrstuvwxyz";
        /*
         * All numbers.
         */
        public static final String digit = "0123456789";
        /*
         * All hex characters
         */
        public static final String hex = "0123456789ABCDEF";
        /*
         * All uppwer-case, lower-case, and numbers combined.
         */
        public static final String alphanum = upper + lower + digit;
        
        /*
         * Default length.
         */
        private static final int l = 21;
        /*
         * Default random object.
         */
        private static final Random r = new SecureRandom();
        /*
         * Default symbols.
         */
        private static final String s = alphanum;
        
        private final Random random;
        private final char[] symbols;
        private final char[] buf;
        
        /*
         * Creates a new RandomString object.
         */
        public RandomString(int length, Random random, String symbols) {
            if(length <1)
                throw new IllegalArgumentException();
            if(symbols.length() < 2)
                throw new IllegalArgumentException();
            
            this.random = Objects.requireNonNull(random);
            this.symbols = symbols.toCharArray();
            this.buf = new char[length];
        }
        
        public RandomString(int length, String symbols) {
            this(length, r, symbols);
        }
        
        public RandomString(int length, Random random) {
            this(length, r, s);
        }
        
        public RandomString(Random random, String symbols) {
            this(l, random, symbols);
        }
        
        public RandomString(int length) {
            this(length, r, s);
        }
        
        public RandomString(Random random) {
            this(l, new SecureRandom(), s);
        }
        
        public RandomString(String symbols) {
            this(l, r, symbols);
        }
        
        public RandomString() {
            this(l, r, s);
        }
        
        /*
         * Generates a random string based on the specified values of the constructor.
         */
        public String nextString() {
            for(int idx = 0; idx < buf.length; idx++)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return new String(buf);
        }
    }

}
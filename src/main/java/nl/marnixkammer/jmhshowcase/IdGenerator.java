package nl.marnixkammer.jmhshowcase;

import java.security.SecureRandom;

public class IdGenerator {

    long generateId() {
        return Math.abs(new SecureRandom().nextLong());
    }
}

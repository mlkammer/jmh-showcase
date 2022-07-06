package nl.marnixkammer.jmhshowcase;

import java.security.SecureRandom;

class IdGenerator {

    long generateId() {
        return Math.abs(new SecureRandom().nextLong());
    }
}

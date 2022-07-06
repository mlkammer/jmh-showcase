package nl.marnixkammer.jmhshowcase.faster;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;

public class FasterIdGenerator {

    private final UniformRandomProvider randomProvider = RandomSource.SPLIT_MIX_64.create();

    public long generateId() {
        return randomProvider.nextLong(Long.MAX_VALUE);
    }
}

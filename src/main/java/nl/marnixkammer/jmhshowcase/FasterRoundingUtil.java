package nl.marnixkammer.jmhshowcase;

import org.apache.commons.math3.util.FastMath;

class FasterRoundingUtil {

    private static final int FACTOR_7_DIGITS_PRECISION = 10_000_000;

    static long roundTo7Decimals(final double d) {
        final int sign = d < 0 ? -1 : 1;
        return sign * FastMath.round(FastMath.abs(d * FACTOR_7_DIGITS_PRECISION));
    }
}

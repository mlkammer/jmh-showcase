package nl.marnixkammer.jmhshowcase;

import org.openjdk.jmh.profile.JavaFlightRecorderProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import static java.lang.System.currentTimeMillis;

final class AllBenchmarksRunner {

    public static void main(final String... args) throws RunnerException {
        final String className = AllBenchmarksRunner.class.getSimpleName();
        final Options options = new OptionsBuilder()
                .include(".+Benchmark\\..+$")
                .warmupForks(0)
                .forks(0)
                .threads(1)
                .resultFormat(ResultFormatType.JSON)
                .result("target/benchmark-results/" + (className + "_" + currentTimeMillis()) + ".json")
                .addProfiler(JavaFlightRecorderProfiler.class, "dir=target/benchmark-results")
                .build();

        new Runner(options).run();
    }
}

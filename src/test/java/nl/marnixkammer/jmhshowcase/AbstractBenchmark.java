package nl.marnixkammer.jmhshowcase;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.JavaFlightRecorderProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.stream.IntStream;

import static java.lang.System.currentTimeMillis;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@State(Scope.Benchmark)
abstract class AbstractBenchmark {

    @Test
    void executeJmhRunner() throws RunnerException {
        final String className = getClass().getSimpleName();
        final Options options = new OptionsBuilder()
                .include("\\." + className + "\\.")
                .warmupForks(0)
                .forks(0)
                .threads(1)
                .resultFormat(ResultFormatType.JSON)
                .result("target/benchmark-results/" + className + "_" + currentTimeMillis() + ".json")
                .addProfiler(JavaFlightRecorderProfiler.class, "dir=target/benchmark-results")
                .build();

        assertNotNull(new Runner(options).run());
    }

    @Setup(Level.Trial)
    public final void setUpBenchmarkAtTrialLevel() {
        setupBenchmark();
    }

    protected abstract void setupBenchmark();

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 1, time = 1 /*seconds*/)
    @Measurement(iterations = 5, time = 3 /*seconds*/)
    public final void performBenchmark(final Blackhole bh) {
        performBenchmarkIteration(bh);
    }

    protected abstract void performBenchmarkIteration(final Blackhole bh);

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 1, time = 1 /*seconds*/)
    @Measurement(iterations = 1, time = 3 /*seconds*/)
    public void baseline(final Blackhole bh) {
        IntStream.range(0, 1000).forEach(bh::consume);
    }
}

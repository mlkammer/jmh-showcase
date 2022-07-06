package nl.marnixkammer.jmhshowcase;

import nl.marnixkammer.jmhshowcase.dto.InputDTO;
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

@State(Scope.Benchmark)
public class EndpointBenchmarkRunner {

    public static void main(final String... args) throws RunnerException {
        final String className = EndpointBenchmarkRunner.class.getSimpleName();
        final Options options = new OptionsBuilder()
                .include("\\." + className + "\\.")
                .warmupForks(0)
                .forks(0)
                .threads(1)
                .resultFormat(ResultFormatType.JSON)
                .result("target/benchmark-results/" + (className + "_" + currentTimeMillis()) + ".json")
                .addProfiler(JavaFlightRecorderProfiler.class, "dir=target/benchmark-results")
                .build();

        new Runner(options).run();
    }
    private final CalculationEndpoint endpoint = new CalculationEndpoint(new AlgorithmService(new IdGenerator()));
    private InputDTO input;

    @Setup(Level.Trial)
    public final void setUpBenchmark() {
        input = new InputDTO(1_000, 1.23, "MyReference");
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 1, time = 1 /*seconds*/)
    @Measurement(iterations = 5, time = 3 /*seconds*/)
    public final void performBenchmarkIteration(final Blackhole bh) {
        final String jsonResult = endpoint.calculateJsonResult(input);
        bh.consume(jsonResult);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 1, time = 1 /*seconds*/)
    @Measurement(iterations = 1, time = 3 /*seconds*/)
    public void baseline(final Blackhole bh) {
        IntStream.range(0, 1000).forEach(bh::consume);
    }
}

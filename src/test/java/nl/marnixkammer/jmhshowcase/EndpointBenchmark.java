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

import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class EndpointBenchmark {

    private final CalculationEndpoint endpoint = new CalculationEndpoint(new AlgorithmService(new IdGenerator()));
    private InputDTO input;

    @Setup(Level.Trial)
    public final void setUpBenchmark() {
        input = new InputDTO(2, 1.23, "MyReference");
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

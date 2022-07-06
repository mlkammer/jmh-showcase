package nl.marnixkammer.jmhshowcase;

import nl.marnixkammer.jmhshowcase.dto.InputDTO;
import org.openjdk.jmh.infra.Blackhole;

public class EndpointBenchmarkTest extends AbstractBenchmarkTest {

    private CalculationEndpoint endpoint;
    private InputDTO input;

    @Override
    protected void setupBenchmark() {
        endpoint = new CalculationEndpoint(new AlgorithmService(new IdGenerator()));
        input = new InputDTO(2, 1.23, "MyReference");
    }

    @Override
    protected void performBenchmarkIteration(final Blackhole bh) {
        final String jsonResult = endpoint.calculateJsonResult(input);
        bh.consume(jsonResult);
    }
}

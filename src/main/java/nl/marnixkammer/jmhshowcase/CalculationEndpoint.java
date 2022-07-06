package nl.marnixkammer.jmhshowcase;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.marnixkammer.jmhshowcase.dto.InputDTO;
import nl.marnixkammer.jmhshowcase.dto.ResultDTO;

import java.io.IOException;
import java.io.UncheckedIOException;

class CalculationEndpoint {

    private final AlgorithmService algorithm;

    CalculationEndpoint(final AlgorithmService algorithm) {
        this.algorithm = algorithm;
    }

    String calculateJsonResult(final InputDTO input) {
        final ResultDTO result = algorithm.calculate(input);
        return mapToJsonOutput(result);
    }

    private String mapToJsonOutput(final ResultDTO result) {
        try {
            return new ObjectMapper().writeValueAsString(result);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

package nl.marnixkammer.jmhshowcase;

import nl.marnixkammer.jmhshowcase.dto.InputDTO;

import java.util.logging.Logger;

public final class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final CalculationEndpoint endpoint = new CalculationEndpoint(new AlgorithmService(new IdGenerator()));

    public static void main(final String... args) {
        LOGGER.info("Starting...");

        final InputDTO input = new InputDTO(1_000, 1.23, "MyReference");
        final String jsonResult = endpoint.calculateJsonResult(input);
        LOGGER.info("JSON result: " + jsonResult);
    }
}

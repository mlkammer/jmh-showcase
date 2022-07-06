package nl.marnixkammer.jmhshowcase;

import nl.marnixkammer.jmhshowcase.dto.InputDTO;
import nl.marnixkammer.jmhshowcase.dto.PositionDTO;
import nl.marnixkammer.jmhshowcase.dto.ResultDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

class AlgorithmService {

    private static final Logger LOGGER = Logger.getLogger(AlgorithmService.class.getName());

    private final IdGenerator idGenerator;

    AlgorithmService(final IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    ResultDTO calculate(final InputDTO input) {
        final long id = idGenerator.generateId();
        final String description = "depth=" + input.depth() + ", startSeed=" + input.startSeed() + ", reference=" + input.reference();
        LOGGER.info("Input: " + description);
        final List<PositionDTO> positions = calculatePositions(input.depth(), input.startSeed());

        final ResultDTO result = new ResultDTO(id, description, positions);
        LOGGER.info("Result: " + result);
        return result;
    }

    private List<PositionDTO> calculatePositions(final int depth, final double startSeed) {
        return IntStream.range(0, depth)
                .mapToDouble(i -> startSeed * Math.pow(1.000001, i))
                .mapToObj(this::calculatePosition)
                .toList();
    }

    private PositionDTO calculatePosition(final double d) {
        final long x = roundTo7Decimals(d);
        final long y = roundTo7Decimals(d + 1.0);
        return new PositionDTO(x, y);
    }

    private long roundTo7Decimals(final double d) {
        return BigDecimal.valueOf(d)
                .multiply(BigDecimal.valueOf(1e7))
                .setScale(0, RoundingMode.HALF_UP)
                .longValueExact();
    }
}

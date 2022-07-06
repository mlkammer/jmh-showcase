package nl.marnixkammer.jmhshowcase;

import nl.marnixkammer.jmhshowcase.dto.InputDTO;
import nl.marnixkammer.jmhshowcase.dto.PositionDTO;
import nl.marnixkammer.jmhshowcase.dto.ResultDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlgorithmServiceTest {

    @InjectMocks
    private AlgorithmService algorithm;

    @Mock
    private IdGenerator idGenerator;

    @BeforeEach
    void beforeEach() {
        when(idGenerator.generateId()).thenReturn(42L);
    }

    @Test
    void givenInput_whenCalculate_thenExpectedResult() {
        final InputDTO input = new InputDTO(2, 1.0, "ref");

        final ResultDTO result = algorithm.calculate(input);

        final ResultDTO expectedResult = new ResultDTO(
                42L, "depth=2, startSeed=1.0, reference=ref", List.of(
                        new PositionDTO(10000000, 20000000),
                        new PositionDTO(10000010, 20000010)));
        assertEquals(expectedResult, result);
    }
}

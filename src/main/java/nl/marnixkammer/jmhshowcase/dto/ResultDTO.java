package nl.marnixkammer.jmhshowcase.dto;

import java.util.List;

public record ResultDTO(
        long id,
        String description,
        List<PositionDTO> positions
) {}


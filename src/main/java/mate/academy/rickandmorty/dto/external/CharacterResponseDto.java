package mate.academy.rickandmorty.dto.external;

import java.util.List;

public record CharacterResponseDto(
        CharacterResponseInfoDto info,
        List<CharacterClientDto> results
) {
}

package mate.academy.rickandmorty.dto.external;

public record CharacterClientDto(
        Long id,
        String name,
        String status,
        String gender
) {
}

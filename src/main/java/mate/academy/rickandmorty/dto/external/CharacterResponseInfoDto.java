package mate.academy.rickandmorty.dto.external;

public record CharacterResponseInfoDto(
        int count,
        int pages,
        String next,
        String prev
) {
}

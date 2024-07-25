package mate.academy.rickandmorty.mapper;

import mate.academy.rickandmorty.config.MapperConfig;
import mate.academy.rickandmorty.dto.external.CharacterClientDto;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.model.Character;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CharacterClientDto.class)
public interface CharacterMapper {
    CharacterDto toDto(Character character);

    @Mapping(source = "id", target = "externalId")
    Character toModel(CharacterClientDto requestDto);
}

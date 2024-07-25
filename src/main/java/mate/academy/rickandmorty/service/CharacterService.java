package mate.academy.rickandmorty.service;

import java.util.List;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.dto.internal.CharacterSearchParameters;

public interface CharacterService {
    void uploadAllCharacters();

    List<CharacterDto> findAllCharacters(CharacterSearchParameters params);

    CharacterDto findRandomCharacter();
}

package mate.academy.rickandmorty.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.dto.internal.CharacterSearchParameters;
import mate.academy.rickandmorty.exceptions.ProcessingException;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.character.CharacterRepository;
import mate.academy.rickandmorty.repository.character.CharacterSpecificationBuilder;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Service
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;
    private final CharacterClient client;
    private final CharacterMapper characterMapper;
    private final CharacterSpecificationBuilder characterSpecificationBuilder;
    private int charactersNumber;

    public CharacterServiceImpl(
            CharacterRepository characterRepository,
            CharacterClient client,
            CharacterMapper mapper,
            CharacterSpecificationBuilder characterSpecificationBuilder
    ) {
        this.characterRepository = characterRepository;
        this.client = client;
        this.characterMapper = mapper;
        this.characterSpecificationBuilder = characterSpecificationBuilder;
    }

    @Override
    public void uploadAllCharacters() {
        client.uploadAllCharacters(characterRepository);
        setCharactersNumber(client.getCount());
    }

    @Override
    public List<CharacterDto> findAllCharacters(CharacterSearchParameters params) {
        Specification<Character> build = characterSpecificationBuilder.build(params);
        return characterRepository.findAll(build).stream()
                .map(characterMapper::toDto).toList();
    }

    @Override
    public CharacterDto findRandomCharacter() {
        Long randomId = (long) new Random().nextInt(charactersNumber) + 1;
        try {
            Optional<Character> character = characterRepository.findById(randomId);
            return characterMapper.toDto(character.orElseThrow(
                    () -> new ProcessingException("Character not found"))
            );
        } catch (NoSuchElementException e) {
            throw new ProcessingException("Character not found");
        }
    }
}

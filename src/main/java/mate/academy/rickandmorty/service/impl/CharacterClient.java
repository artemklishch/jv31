package mate.academy.rickandmorty.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import lombok.Getter;
import mate.academy.rickandmorty.dto.external.CharacterClientDto;
import mate.academy.rickandmorty.dto.external.CharacterResponseDto;
import mate.academy.rickandmorty.dto.external.CharacterResponseInfoDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.character.CharacterRepository;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CharacterClient {
    private static final String BASE_URL = "https://rickandmortyapi.com/api/character"
            + "?page=1";
    private final HttpClient client = HttpClient.newHttpClient();
    private int count = 0;
    private String next;

    private final ObjectMapper objectMapper;
    private final CharacterMapper characterMapper;

    public CharacterClient(ObjectMapper objectMapper, CharacterMapper characterMapper) {
        this.objectMapper = objectMapper;
        this.characterMapper = characterMapper;
        this.next = BASE_URL;
    }

    public void uploadAllCharacters(CharacterRepository characterRepository) {
        List<Character> characters = getAllCharacters().stream()
                .map(characterMapper::toModel).toList();
        characterRepository.saveAll(characters);
        if (next != null) {
            uploadAllCharacters(characterRepository);
        }
    }

    public List<CharacterClientDto> getAllCharacters() {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(next))
                .build();
        try {
            HttpResponse<String> response = client.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString()
            );
            CharacterResponseDto charactersResponse = objectMapper.readValue(
                    response.body(),
                    CharacterResponseDto.class
            );
            CharacterResponseInfoDto metaInfo = charactersResponse.info();
            this.next = metaInfo.next();
            if (this.count != metaInfo.count()) {
                this.count = metaInfo.count();
            }
            return charactersResponse.results().stream().toList();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

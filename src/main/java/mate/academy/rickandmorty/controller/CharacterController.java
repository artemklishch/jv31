package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.dto.internal.CharacterSearchParameters;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Character management", description = "Endpoints for managing characters")
@Component
@RequiredArgsConstructor
@RestController
@RequestMapping("/characters")
public class CharacterController {
    private final CharacterService characterService;

    @Operation(
            summary = "Upload all characters",
            description = "Fetching all characters from the public API and saving them in DB",
            hidden = true
    )
    @GetMapping("/upload-characters")
    @PostConstruct
    public void uploadCharacters() {
        characterService.uploadAllCharacters();
    }

    @Operation(
            summary = "Get all characters",
            description = "Get all characters. Contains logic to fetch data by query parameters"
    )
    @GetMapping
    public List<CharacterDto> getAllCharacters(CharacterSearchParameters params) {
        return characterService.findAllCharacters(params);
    }

    @Operation(
            summary = "Fetch random character",
            description = "Contains random way to define identifier and fetch respective character"
    )
    @GetMapping("/random")
    public CharacterDto getRandomCharacter() {
        return characterService.findRandomCharacter();
    }
}

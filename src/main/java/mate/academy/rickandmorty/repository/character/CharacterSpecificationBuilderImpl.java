package mate.academy.rickandmorty.repository.character;

import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterSearchParameters;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CharacterSpecificationBuilderImpl implements CharacterSpecificationBuilder<Character> {
    private final SpecificationProviderManager<Character> specificationProviderManager;

    @Override
    public Specification<Character> build(CharacterSearchParameters params) {
        Specification<Character> spec = Specification.where(null);
        if (params.name() != null) {
            spec = spec.and(
                    specificationProviderManager
                            .toProvider("name")
                            .getSpecification(params.name())
            );
        }
        return spec;
    }
}

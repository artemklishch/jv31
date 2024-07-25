package mate.academy.rickandmorty.repository.character;

import mate.academy.rickandmorty.dto.internal.CharacterSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface CharacterSpecificationBuilder<T> {
    Specification<T> build(CharacterSearchParameters string);
}

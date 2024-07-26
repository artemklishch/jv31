package mate.academy.rickandmorty.repository.character.spec;

import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class NameSpecificationProvider implements SpecificationProvider<Character> {
    private final String queryParamName = "name";

    @Override
    public String getKey() {
        return queryParamName;
    }

    @Override
    public Specification<Character> getSpecification(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(queryParamName)),
                        "%" + name.toLowerCase() + "%"
                );
    }
}

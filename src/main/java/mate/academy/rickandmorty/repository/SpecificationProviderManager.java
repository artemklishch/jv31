package mate.academy.rickandmorty.repository;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> toProvider(String key);
}

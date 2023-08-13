package tacos.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.domain.entity.Ingredient;
import tacos.domain.repository.IngredientRepository;


@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private final IngredientRepository ingredientRepository;

    public IngredientByIdConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findById(id).orElse(null);
    }

}
package org.sxs.tacocloud.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * @author sxs
 * @date 2023/7/22
 */
@Data
public class Taco {
	@NotNull
	@Size(min=5,message = "Name must be at least 5 characters long")
	private String name;
	@NotNull
	@Size(min=1,message = "You must choose at least 1 ingredient")
	private List<Ingredient> ingredients;
}

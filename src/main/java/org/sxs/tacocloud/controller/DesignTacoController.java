package org.sxs.tacocloud.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.sxs.tacocloud.domain.Ingredient;
import org.sxs.tacocloud.domain.Ingredient.Type;
import org.sxs.tacocloud.domain.Taco;
import org.sxs.tacocloud.domain.TacoOrder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sxs
 * @date 2023/7/24
 */
@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

	@ModelAttribute
	public void addIngredientToMode(Model model) {
		List<Ingredient> ingredients = Arrays.asList(
				new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
				new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
				new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
				new Ingredient("CARN", "Carnitas", Type.PROTEIN),
				new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
				new Ingredient("LETC", "Lettuce", Type.VEGGIES),
				new Ingredient("CHED", "Cheddar", Type.CHEESE),
				new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
				new Ingredient("SLSA", "Salsa", Type.SAUCE),
				new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
		);
		Type[] types = Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients,type));
		}
	}

	@ModelAttribute(name = "tacoOrder")
	public TacoOrder order() {
		return new TacoOrder();
	}
	@ModelAttribute(name = "taco")
	public Taco taco() {
		Taco taco = new Taco();
		return taco;
	}
	@GetMapping
	public String showDesignForm() {
		return "design";
	}
	@PostMapping
	public String processTaco(@Valid @ModelAttribute("taco") Taco taco1, Errors errors,
	                          @ModelAttribute("tacoOrder") TacoOrder tacoOrder) {
		if (errors.hasErrors()) {
			return "design";
		}
		tacoOrder.addTaco(taco1);
		log.info("Processing taco:{}", taco1.toString());
		log.info("Processing tacoOrder:{}", tacoOrder.getTacos().size());
		return "redirect:/orders/current";
	}
	private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients
				.stream()
				.filter(x -> x.getType().equals(type))
				.toList();
	}
}

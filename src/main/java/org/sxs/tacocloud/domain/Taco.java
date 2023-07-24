package org.sxs.tacocloud.domain;

import lombok.Data;

import java.util.List;

/**
 * @author sxs
 * @date 2023/7/22
 */
@Data
public class Taco {
	private String name;
	private List<Ingredient> ingredients;
}

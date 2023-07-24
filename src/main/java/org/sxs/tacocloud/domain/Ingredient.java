package org.sxs.tacocloud.domain;

import lombok.Data;

/**
 * @author sxs
 * @date 2023/7/22
 */

@Data
public class Ingredient {
	private final String id;
	private final String name;
	private final Type type;

	public enum Type {
		WRAP,PROTEIN,VEGGIES,CHEESE,SAUCE
	}
}

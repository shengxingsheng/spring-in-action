package org.sxs.tacocloud.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author sxs
 * @date 2023/7/22
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Ingredient {
    @Id
    private String id;
    private String name;
    private Type type;


    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }


}

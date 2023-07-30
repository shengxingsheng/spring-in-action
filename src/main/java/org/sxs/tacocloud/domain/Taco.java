package org.sxs.tacocloud.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sxs
 * @date 2023/7/22
 */
@Table
@Data
public class Taco implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private Date createdAt = new Date();
    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;
    /**
     * spring mvc 怎么将表单中的ingredients:x,ingredients:xx,..转换成List<IngredientRef>
     * 答案：是类型转换器,用到两个StringToCollectionConverter和ObjectToObjectConverter
     * 1.StringToCollectionConverter：生成一个ArrayList<IngredientRef>,然后用ObjectToObjectConverter处理元素
     * 2.ObjectToObjectConverter：将单个String x转换为IngredientRef,转换步骤是找到IngredientRef的为参数为String的构造器，然后反射调用，就获得了一个IngredientRef
     */
    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<IngredientRef> ingredients = new ArrayList<>();

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(new IngredientRef(ingredient.getId()));
    }
}

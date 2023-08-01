package org.sxs.tacocloud.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sxs
 * @date 2023/7/22
 */
@Entity
@Data
public class Taco implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private Date createdAt = new Date();
    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;


}

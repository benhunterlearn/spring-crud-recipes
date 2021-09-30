package com.benhunterlearn.springcrudrecipes.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecipeDto {
    private Long id;
    private String title;
    private String description;
    private String instructions;
    private Integer calories;
    @JsonProperty("date-created")
    private LocalDate dateCreated;

    public RecipeDto(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.description = recipe.getDescription();
        this.instructions = recipe.getInstructions();
        this.calories = recipe.getCalories();
        this.dateCreated = recipe.getDateCreated();
    }
}

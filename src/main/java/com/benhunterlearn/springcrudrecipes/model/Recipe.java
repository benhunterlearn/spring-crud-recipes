package com.benhunterlearn.springcrudrecipes.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String instructions;
    private Integer calories;
    private LocalDate dateCreated;

    public Recipe(RecipeDto recipeDto) {
        this.title = recipeDto.getTitle();
        this.description = recipeDto.getDescription();
        this.instructions = recipeDto.getInstructions();
        this.calories = recipeDto.getCalories();
        this.dateCreated = recipeDto.getDateCreated();
    }

    public Recipe patch(RecipeDto recipeDto) {
        if (recipeDto.getTitle() != null) {
            this.title = recipeDto.getTitle();
        }
        if (recipeDto.getDescription() != null) {
            this.description = recipeDto.getDescription();
        }
        if (recipeDto.getInstructions() != null) {
            this.instructions = recipeDto.getInstructions();
        }
        if (recipeDto.getCalories() != null) {
            this.calories = recipeDto.getCalories();
        }
        if (recipeDto.getDateCreated() != null) {
            this.dateCreated = recipeDto.getDateCreated();
        }
        return this;
    }
}

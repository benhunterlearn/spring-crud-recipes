package com.benhunterlearn.springcrudrecipes.repository;

import com.benhunterlearn.springcrudrecipes.model.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findAllByCaloriesBetween(Integer minCalories, Integer maxCalories);
}

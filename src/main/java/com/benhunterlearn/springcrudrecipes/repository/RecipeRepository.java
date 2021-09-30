package com.benhunterlearn.springcrudrecipes.repository;

import com.benhunterlearn.springcrudrecipes.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}

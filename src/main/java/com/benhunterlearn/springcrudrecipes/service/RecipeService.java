package com.benhunterlearn.springcrudrecipes.service;

import com.benhunterlearn.springcrudrecipes.model.Recipe;
import com.benhunterlearn.springcrudrecipes.model.RecipeDto;
import com.benhunterlearn.springcrudrecipes.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public RecipeDto createRecipe(RecipeDto recipeDto) {
        Recipe recipe = this.repository.save(new Recipe(recipeDto));
        RecipeDto outputRecipeDto = new RecipeDto(recipe);
        return outputRecipeDto;
    }

    public List<RecipeDto> getAllRecipes() {
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        this.repository.findAll().forEach(recipeArrayList::add);
        return recipeArrayList.stream().map(recipe -> new RecipeDto(recipe)).collect(Collectors.toList());
    }

    public RecipeDto getRecipeById(Long id) {
        return new RecipeDto(this.repository.findById(id).get());
    }

    public RecipeDto patchRecipeById(Long id, RecipeDto recipeDto) {
        Recipe currentRecipe = this.repository.findById(id).get();
        currentRecipe.patch(recipeDto);
        currentRecipe = this.repository.save(currentRecipe);
        return new RecipeDto(currentRecipe);
    }

    public String deleteRecipeById(Long id) {
        this.repository.deleteById(id);
        return "SUCCESS";
    }
}

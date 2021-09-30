package com.benhunterlearn.springcrudrecipes.controller;

import com.benhunterlearn.springcrudrecipes.model.RecipeDto;
import com.benhunterlearn.springcrudrecipes.repository.RecipeRepository;
import com.benhunterlearn.springcrudrecipes.service.RecipeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @PostMapping("")
    public RecipeDto postCreateRecipe(@RequestBody RecipeDto recipeDto) {
        return this.service.createRecipe(recipeDto);
    }

    @GetMapping("")
    public Iterable<RecipeDto> getAllRecipes() {
        return this.service.getAllRecipes();
    }

    @GetMapping("/{id}")
    public RecipeDto getRecipeById(@PathVariable Long id) {
        return this.service.getRecipeById(id);
    }

    @PatchMapping("/{id}")
    public RecipeDto patchRecipeById(@PathVariable Long id, @RequestBody RecipeDto recipeDto) {
        return this.service.patchRecipeById(id, recipeDto);
    }

    @DeleteMapping("/{id}")
    public String deleteRecipeById(@PathVariable Long id) {
        return this.service.deleteRecipeById(id);
    }

}

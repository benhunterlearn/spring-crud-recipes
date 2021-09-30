package com.benhunterlearn.springcrudrecipes;

import com.benhunterlearn.springcrudrecipes.model.Recipe;
import com.benhunterlearn.springcrudrecipes.model.RecipeDto;
import com.benhunterlearn.springcrudrecipes.repository.RecipeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class RecipeControllerTest {
    @Autowired
    MockMvc mvc;
    RecipeRepository repository;
    ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();
    private Recipe firstRecipe;
    private Recipe secondRecipe;

    @Autowired
    public RecipeControllerTest(RecipeRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    public void setupTests() {
        this.firstRecipe = this.repository.save(new Recipe()
                .setTitle("first title")
                .setDescription("first desc")
                .setInstructions("first instr")
                .setCalories(100)
                .setDateCreated(LocalDate.now()));
        this.secondRecipe = this.repository.save(new Recipe()
                .setTitle("second itle")
                .setDescription("second desc")
                .setInstructions("second instr")
                .setCalories(200)
                .setDateCreated(LocalDate.now()));
    }

    @Test
    public void postCreateRecipeFromValidData() throws Exception {
        RecipeDto recipeDto = new RecipeDto()
                .setTitle("title")
                .setDescription("desc")
                .setInstructions("instr")
                .setCalories(100)
                .setDateCreated(LocalDate.now());
        RequestBuilder request = MockMvcRequestBuilders.post("/recipe")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(recipeDto));
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(recipeDto.getTitle())))
                .andExpect(jsonPath("$.description", is(recipeDto.getDescription())))
                .andExpect(jsonPath("$.instructions", is(recipeDto.getInstructions())))
                .andExpect(jsonPath("$.calories", is(recipeDto.getCalories())))
                .andExpect(jsonPath("$.date-created", is(recipeDto.getDateCreated().toString())));
    }

    @Test
    public void getAllRecipesFromDatabase() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/recipe")
                .accept(MediaType.APPLICATION_JSON);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(this.firstRecipe.getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(this.firstRecipe.getTitle())))
                .andExpect(jsonPath("$[0].description", is(this.firstRecipe.getDescription())))
                .andExpect(jsonPath("$[0].instructions", is(this.firstRecipe.getInstructions())))
                .andExpect(jsonPath("$[0].calories", is(this.firstRecipe.getCalories())))
                .andExpect(jsonPath("$[0].date-created", is(this.firstRecipe.getDateCreated().toString())))
                .andExpect(jsonPath("$[1].id", is(this.secondRecipe.getId().intValue())))
                .andExpect(jsonPath("$[1].title", is(this.secondRecipe.getTitle())))
                .andExpect(jsonPath("$[1].description", is(this.secondRecipe.getDescription())))
                .andExpect(jsonPath("$[1].instructions", is(this.secondRecipe.getInstructions())))
                .andExpect(jsonPath("$[1].calories", is(this.secondRecipe.getCalories())))
                .andExpect(jsonPath("$[1].date-created", is(this.secondRecipe.getDateCreated().toString())));
    }

    @Test
    public void getAllRecipesFromDatabaseFilteredByCalories() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/recipe?max-calories=150")
                .accept(MediaType.APPLICATION_JSON);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(this.firstRecipe.getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(this.firstRecipe.getTitle())))
                .andExpect(jsonPath("$[0].description", is(this.firstRecipe.getDescription())))
                .andExpect(jsonPath("$[0].instructions", is(this.firstRecipe.getInstructions())))
                .andExpect(jsonPath("$[0].calories", is(this.firstRecipe.getCalories())))
                .andExpect(jsonPath("$[0].date-created", is(this.firstRecipe.getDateCreated().toString())))
                .andExpect(jsonPath("$[1]").doesNotExist());
    }

    @Test
    public void getRecipeByValidId() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/recipe/" + this.firstRecipe.getId())
                .accept(MediaType.APPLICATION_JSON);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(this.firstRecipe.getId().intValue())))
                .andExpect(jsonPath("$.title", is(this.firstRecipe.getTitle())))
                .andExpect(jsonPath("$.description", is(this.firstRecipe.getDescription())))
                .andExpect(jsonPath("$.instructions", is(this.firstRecipe.getInstructions())))
                .andExpect(jsonPath("$.calories", is(this.firstRecipe.getCalories())))
                .andExpect(jsonPath("$.date-created", is(this.firstRecipe.getDateCreated().toString())));
    }

    @Test
    public void patchUpdateRecipeWithValidInformation() throws Exception {
        RecipeDto updatedRecipeDto = new RecipeDto()
                .setTitle("new title")
                .setDescription("new desc")
                .setInstructions("new instr")
                .setCalories(-50)
                .setDateCreated(LocalDate.of(2020, 1, 2));
        RequestBuilder request = MockMvcRequestBuilders.patch("/recipe/" + this.firstRecipe.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecipeDto));
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(this.firstRecipe.getId().intValue())))
                .andExpect(jsonPath("$.title", is(updatedRecipeDto.getTitle())))
                .andExpect(jsonPath("$.description", is(updatedRecipeDto.getDescription())))
                .andExpect(jsonPath("$.instructions", is(updatedRecipeDto.getInstructions())))
                .andExpect(jsonPath("$.calories", is(updatedRecipeDto.getCalories())))
                .andExpect(jsonPath("$.date-created", is(updatedRecipeDto.getDateCreated().toString())));
    }

    @Test
    public void deleteRecipeByValidIdSucceeds() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/recipe/" + this.firstRecipe.getId())
                .accept(MediaType.APPLICATION_JSON);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.title").doesNotExist())
                .andExpect(jsonPath("$.description").doesNotExist())
                .andExpect(jsonPath("$.instructions").doesNotExist())
                .andExpect(jsonPath("$.calories").doesNotExist())
                .andExpect(jsonPath("$.date-created").doesNotExist())
                .andExpect(content().string("SUCCESS"));
        assertTrue(this.repository.findById(this.firstRecipe.getId()).isEmpty());
    }
}

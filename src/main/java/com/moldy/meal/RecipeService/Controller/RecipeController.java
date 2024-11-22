package com.moldy.meal.RecipeService.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.moldy.meal.RecipeService.Recipe;
import com.moldy.meal.RecipeService.Repository.RecipeRepository;
import com.moldy.meal.RecipeService.Utils.DTO.RequestRecipeDTO;
import com.moldy.meal.RecipeService.Utils.DTO.ResponseRecipeDTO;
import com.moldy.meal.RecipeService.Utils.Mapping.ConvertRecipeDTOtoRecipe;
import com.moldy.meal.RecipeService.Utils.Mapping.ConvertResponseRecipeDTOToRecipe;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    RecipeRepository recipeRepository;

    @GetMapping("/{id}")
    ResponseEntity<?> getRecipe(@PathVariable("id") Integer id) {
        try {
            Recipe recipe = recipeRepository.getByID(id);
            if (recipe == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found");
            }
            ResponseRecipeDTO response = new ConvertResponseRecipeDTOToRecipe(recipe).getConvertedRecipeDTO();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error finding recipe");
        }
    }
    @GetMapping("/random/{count}")
    ResponseEntity<ObjectNode> getRandomRecipes(@PathVariable("count") Integer count) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonObject = mapper.createObjectNode();

        List<Integer> recipeIDList = recipeRepository.findAllRecipeID();

        Collections.shuffle(recipeIDList); // Randomize the list

        List<Integer> randomIds = recipeIDList.stream().limit(count).toList();

        jsonObject.putPOJO("recipes", randomIds);

        return ResponseEntity.ok(jsonObject);

    }
    @PostMapping
    ResponseEntity<String> postRecipe(@Valid @RequestBody RequestRecipeDTO recipe) {
        try {
            Recipe recipePost = new ConvertRecipeDTOtoRecipe(recipe).getConvertedRecipe();
            recipeRepository.save(recipePost);
            return ResponseEntity.ok("valid");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Recipe could not be saved");
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<String> updateRecipe(@Valid @RequestBody RequestRecipeDTO recipe, @PathVariable("id") Integer id) {
        try {
            Recipe recipePut = new ConvertRecipeDTOtoRecipe(recipe).getConvertedRecipe();
            Recipe updateRecipe = recipeRepository.getByID(id);

            if (updateRecipe == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found");
            }
            updateRecipe.setRecipeName(recipePut.getRecipeName());
            updateRecipe.setInstructions(recipePut.getInstructions());
            updateRecipe.setIngredient(recipePut.getIngredient());

            recipeRepository.save(updateRecipe);

            return ResponseEntity.ok("Updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error updating carrier");
        }
    }
}

package com.moldy.meal.RecipeService.Controller;

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

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    RecipeRepository recipeRepository;

    @GetMapping("/{id}")
    ResponseEntity<ResponseRecipeDTO> getRecipe(@PathVariable("id") Integer id) {
        Recipe recipe = recipeRepository.getByID(id);
        ResponseRecipeDTO response = new ConvertResponseRecipeDTOToRecipe(recipe).getConvertedRecipeDTO();
        return ResponseEntity.ok(response);
    }
    @PostMapping
    ResponseEntity<String> postRecipe(@Valid @RequestBody RequestRecipeDTO recipe) {
        Recipe recipePost = new ConvertRecipeDTOtoRecipe(recipe).getConvertedRecipe();
        recipeRepository.save(recipePost);
        return ResponseEntity.ok("valid");
    }

    @PutMapping("/{id}")
    ResponseEntity<String> updateRecipe(@Valid @RequestBody RequestRecipeDTO recipe, @PathVariable("id") Integer id) {
        Recipe recipePut = new ConvertRecipeDTOtoRecipe(recipe).getConvertedRecipe();
        Recipe updateRecipe = recipeRepository.getByID(id);

        if (updateRecipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found");
        }
        updateRecipe.setRecipeName(recipePut.getRecipeName());
        updateRecipe.setInstructions(recipePut.getInstructions());
        updateRecipe.setIngredient(recipePut.getIngredient());

        recipeRepository.save(updateRecipe);

        return ResponseEntity.ok("updated");
    }
}

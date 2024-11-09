package com.moldy.meal.RecipeService.Controller;

import com.moldy.meal.RecipeService.Recipe;
import com.moldy.meal.RecipeService.Repository.RecipeRepository;
import com.moldy.meal.RecipeService.Utils.Mapping.ConvertRecipeDTOtoRecipe;
import com.moldy.meal.RecipeService.Utils.DTO.RequestRecipeDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    RecipeRepository recipeRepository;

    @GetMapping("/{id}")
    ResponseEntity<Recipe> getRecipe(@PathVariable("id") Integer id){
        Recipe recipe = recipeRepository.getByID(id);
        return ResponseEntity.ok(recipe);
    }
    @PostMapping
    ResponseEntity<String> postRecipe(@Valid @RequestBody RequestRecipeDTO recipe){
        Recipe recipePost = new ConvertRecipeDTOtoRecipe(recipe).getConvertedRecipe();
        recipeRepository.save(recipePost);
        return ResponseEntity.ok("valid");
    }
}

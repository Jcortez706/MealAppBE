package com.moldy.meal.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    RecipeRepository recipeRepository;

    @GetMapping()
    String getRecipe(){
        return recipeRepository.findAllRecipeID().toString();
    }
    @PostMapping
    void postRecipe(@RequestBody Recipe recipe){

    }
}

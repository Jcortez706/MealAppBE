package com.moldy.meal.RecipeService.Utils.Mapping;

import com.moldy.meal.RecipeService.Recipe;
import com.moldy.meal.RecipeService.Utils.DTO.RequestRecipeDTO;

public class ConvertRecipeDTOtoRecipe {
    Recipe recipe = new Recipe();
    public ConvertRecipeDTOtoRecipe(RequestRecipeDTO requestRecipeDTO){
        recipe.setRecipeID(requestRecipeDTO.recipeID);
        recipe.setRecipeName(requestRecipeDTO.recipeName);
        recipe.setIngredient(requestRecipeDTO.ingredient);
        recipe.setInstructions(requestRecipeDTO.instructions);
    }

    public Recipe getConvertedRecipe(){
        return recipe;
    }
}

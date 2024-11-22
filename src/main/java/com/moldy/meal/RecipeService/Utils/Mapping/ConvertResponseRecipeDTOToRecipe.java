package com.moldy.meal.RecipeService.Utils.Mapping;

import com.moldy.meal.RecipeService.Recipe;
import com.moldy.meal.RecipeService.Utils.DTO.ResponseRecipeDTO;

public class ConvertResponseRecipeDTOToRecipe {
    ResponseRecipeDTO responseRecipeDTO = new ResponseRecipeDTO();
    public ConvertResponseRecipeDTOToRecipe(Recipe recipe){
        responseRecipeDTO.recipeID = recipe.getRecipeID();
        responseRecipeDTO.recipeName = recipe.getRecipeName();
        responseRecipeDTO.ingredient = recipe.getIngredient();
        responseRecipeDTO.instructions = recipe.getInstructions();
    }

    public ResponseRecipeDTO getConvertedRecipeDTO(){
        return responseRecipeDTO;
    }
}

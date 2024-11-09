package com.moldy.meal.RecipeService.Utils.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RequestRecipeDTO {
    public int recipeID;

    @NotNull
    @NotEmpty
    public String instructions;

    @NotNull
    @NotEmpty
    public String ingredient;

    @NotNull
    @NotEmpty
    public String recipeName;
}

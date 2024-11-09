package com.moldy.meal.RecipeService.Utils.DTO;

public class RequestRecipeDTOmock {
    public static RequestRecipeDTO createMock() {
        RequestRecipeDTO mockDto = new RequestRecipeDTO();
        mockDto.recipeID = 1;
        mockDto.instructions = "Mix all ingredients thoroughly and bake at 350 degrees for 25 minutes.";
        mockDto.ingredient = "Flour, Sugar, Eggs, Butter";
        mockDto.recipeName = "Classic Cake Recipe";
        return mockDto;
    }
}

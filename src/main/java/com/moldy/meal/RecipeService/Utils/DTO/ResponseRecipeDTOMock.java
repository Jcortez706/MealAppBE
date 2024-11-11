package com.moldy.meal.RecipeService.Utils.DTO;

public class ResponseRecipeDTOMock {
    public static ResponseRecipeDTO createMock() {
        ResponseRecipeDTO mockDto = new ResponseRecipeDTO();
        mockDto.recipeID = 1;
        mockDto.instructions = "Mix all ingredients thoroughly and bake at 350 degrees for 25 minutes.";
        mockDto.ingredient = "Flour, Sugar, Eggs, Butter";
        mockDto.recipeName = "Classic Cake Recipe";
        return mockDto;
    }
}

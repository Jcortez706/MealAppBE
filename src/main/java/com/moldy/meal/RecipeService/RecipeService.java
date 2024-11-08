package com.moldy.meal.RecipeService;

import jakarta.persistence.*;

@Entity
@Table(name = "recipe")
public class RecipeService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recipe_id")
    private int recipeID = 0;

    @Column(name = "recipe", nullable = false)
    private String recipe;

    @Column(name = "ingredient", nullable = false)
    private String ingredient;

    @Column(name = "recipe_name", nullable = false)
    private String recipeName;

    public RecipeService(String recipe, String ingredient, String recipeName){
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.recipeName = recipeName;
    }


    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getRecipeID() {
        return recipeID;
    }
}

package com.moldy.meal.RecipeService;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recipe_id")
    private int recipeID = 0;

    @Column(name = "instructions", nullable = false)
    private String instructions;

    @Column(name = "ingredient", nullable = false)
    private String ingredient;

    @Column(name = "recipe_name", nullable = false)
    private String recipeName;

    //default constructor for JPA
    public Recipe(){}

    public Recipe(String instructions, String ingredient, String recipeName){
        this.instructions = instructions;
        this.ingredient = ingredient;
        this.recipeName = recipeName;
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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setRecipeID(@NotNull int recipeID) {
        this.recipeID = recipeID;
    }
}

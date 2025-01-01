package com.moldy.meal.IngredientsService;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredients")
public class Ingredients {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ingredient_id")
    Integer ingredientId;

    @Column(name = "ingredient_name")
    String ingredientName;

    public Ingredients() {
    }

    public Ingredients(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    private String getIngredientName() {
        return ingredientName;
    }

    private int getIngredientId() {
        return ingredientId;
    }
}

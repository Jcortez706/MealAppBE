package com.moldy.meal.RecipeService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    @Query("SELECT r.recipeID FROM Recipe r")
    List<Integer> findAllRecipeID();
}

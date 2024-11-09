package com.moldy.meal.RecipeService.Repository;

import com.moldy.meal.RecipeService.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    @Query("SELECT r.recipeID FROM Recipe r")
    List<Integer> findAllRecipeID();
    @Query("SELECT r FROM Recipe r WHERE r.recipeID = :id")
    Recipe getByID(@Param("id")Integer id);
}

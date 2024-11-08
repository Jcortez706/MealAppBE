package com.moldy.meal.RecipeService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<RecipeService, Integer> {
    @Query()
    List<Integer> findAllRecipes();
}

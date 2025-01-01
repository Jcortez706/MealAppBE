package com.moldy.meal.IngredientsService.Repository;

import com.moldy.meal.IngredientsService.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IngredientsRepository extends JpaRepository<Ingredients, Integer> {
    @Query("SELECT i FROM Ingredients i WHERE i.ingredientId = :id")
    Ingredients getIngredientById(@Param("id") Integer id);
}

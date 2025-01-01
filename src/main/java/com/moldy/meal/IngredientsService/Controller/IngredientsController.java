package com.moldy.meal.IngredientsService.Controller;

import com.moldy.meal.IngredientsService.Ingredients;
import com.moldy.meal.IngredientsService.Repository.IngredientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredients")
public class IngredientsController {
    @Autowired
    IngredientsRepository ingredientsRepository;

    @GetMapping("/{id}")
    ResponseEntity<?> getIngredientsById(@PathVariable("id") Integer id) {
        try {
            Ingredients ingredients = ingredientsRepository.getIngredientById(id);
            if (ingredients == null) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ingredient not found");
            }
            return ResponseEntity.ok(ingredients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error finding ingredients");
        }
    }

}

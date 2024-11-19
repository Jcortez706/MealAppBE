package com.moldy.meal.RecipeService.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.moldy.meal.RecipeService.Recipe;
import com.moldy.meal.RecipeService.Repository.RecipeRepository;
import com.moldy.meal.RecipeService.Utils.DTO.RequestRecipeDTO;
import com.moldy.meal.RecipeService.Utils.DTO.RequestRecipeDTOmock;
import com.moldy.meal.RecipeService.Utils.DTO.ResponseRecipeDTO;
import com.moldy.meal.RecipeService.Utils.DTO.ResponseRecipeDTOMock;
import com.moldy.meal.RecipeService.Utils.Mapping.ConvertRecipeDTOtoRecipe;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeRepository recipeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postMethodShouldPass() throws Exception {
        RequestRecipeDTO mockRecipe = RequestRecipeDTOmock.createMock();

        Recipe recipe = new ConvertRecipeDTOtoRecipe(mockRecipe).getConvertedRecipe();

        Mockito.when(recipeRepository.save(Mockito.any(Recipe.class))).thenReturn(recipe);

        mockMvc.perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRecipe)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("valid"));
    }

    @Test
    void postMethodShouldFail() throws Exception {
        RequestRecipeDTO mockRecipe = RequestRecipeDTOmock.createMock();
        Recipe recipe = new ConvertRecipeDTOtoRecipe(mockRecipe).getConvertedRecipe();

        Mockito.when(recipeRepository.save(Mockito.any(Recipe.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRecipe)))
                .andExpect(status().isExpectationFailed())
                .andExpect(MockMvcResultMatchers.content().string("Recipe could not be saved"));
    }


    @Test
    void getMethodShouldPass() throws Exception {
        Recipe mockRecipe = new Recipe();
        mockRecipe.setRecipeID(1);
        mockRecipe.setRecipeName("Classic Cake Recipe");
        mockRecipe.setIngredient("Flour, Sugar, Eggs, Butter");
        mockRecipe.setInstructions("Mix all ingredients thoroughly and bake at 350 degrees for 25 minutes.");


        ResponseRecipeDTO mockResponse = ResponseRecipeDTOMock.createMock();

        Mockito.when(recipeRepository.getByID(1)).thenReturn(mockRecipe);

        String expectedResponseJson = objectMapper.writeValueAsString(mockResponse);

        mockMvc.perform(get("/recipe/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRecipe)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponseJson));
    }

    @Test
    void getMethodShouldNotFindRecipeByID() throws Exception {
        RequestRecipeDTO mockRecipe = RequestRecipeDTOmock.createMock();

        Mockito.when(recipeRepository.getByID(1)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/recipe/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRecipe)))

                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Recipe not found"));

    }

    @Test
    void getMethodShouldThrowException() throws Exception {
        Mockito.when(recipeRepository.getByID(1)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/recipe/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(MockMvcResultMatchers.content().string("Error finding recipe"));
    }


    @Test
    void updateRecipeShouldPass() throws Exception {
        RequestRecipeDTO mockRecipe = RequestRecipeDTOmock.createMock();
        Recipe convertedRecipe = new ConvertRecipeDTOtoRecipe(mockRecipe).getConvertedRecipe();

        Recipe existingRecipe = new Recipe();
        existingRecipe.setRecipeID(1);
        existingRecipe.setRecipeName("Original Name");
        existingRecipe.setIngredient("Original Ingredient");
        existingRecipe.setInstructions("Original Instructions");

        Mockito.when(recipeRepository.getByID(1)).thenReturn(existingRecipe);

        mockMvc.perform(put("/recipe/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRecipe)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Updated"));

        Assertions.assertEquals(convertedRecipe.getRecipeName(), existingRecipe.getRecipeName());
        Assertions.assertEquals(convertedRecipe.getIngredient(), existingRecipe.getIngredient());
        Assertions.assertEquals(convertedRecipe.getInstructions(), existingRecipe.getInstructions());

        Mockito.verify(recipeRepository).save(existingRecipe);
    }

    @Test
    void updateMethodShouldFail() throws Exception {
        RequestRecipeDTO mockRecipe = RequestRecipeDTOmock.createMock();
        Recipe convertedRecipe = new ConvertRecipeDTOtoRecipe(mockRecipe).getConvertedRecipe();

        Recipe existingRecipe = new Recipe();
        existingRecipe.setRecipeID(1);
        existingRecipe.setRecipeName("Original Name");
        existingRecipe.setIngredient("Original Ingredient");
        existingRecipe.setInstructions("Original Instructions");

        Mockito.when(recipeRepository.getByID(1)).thenThrow(new RuntimeException());

        mockMvc.perform(put("/recipe/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRecipe)))

                .andExpect(status().isExpectationFailed())
                .andExpect(MockMvcResultMatchers.content().string("Error updating carrier"));
    }

    @Test
    void updateMethodShouldNotFindRecipeByID() throws Exception {
        RequestRecipeDTO mockRecipe = RequestRecipeDTOmock.createMock();

        Mockito.when(recipeRepository.getByID(1)).thenThrow(new RuntimeException());

        mockMvc.perform(put("/recipe/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRecipe)))

                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Recipe not found"));

    }

    @Test
    void getRandomRecipesShouldReturnRecipes() throws Exception {
        // Mocking the recipe IDs returned by the repository
        List<Integer> mockIDList = Arrays.asList(1, 2, 3, 4, 5); // Full list of IDs in DB
        Mockito.when(recipeRepository.findAllRecipeID()).thenReturn(mockIDList);

        // Define the expected JSON structure for a count of 2
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode expectedResponse = mapper.createObjectNode();

        // Simulate the expected result after shuffling and limiting
        List<Integer> randomIds = mockIDList.subList(0, 2); // Simulate first 2 IDs
        expectedResponse.putPOJO("recipes", randomIds);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/recipe/random/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse.toString()));

        // Verify repository interaction
        Mockito.verify(recipeRepository, Mockito.times(1)).findAllRecipeID();
    }


    
}
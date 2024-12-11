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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeController recipeController;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testPostRecipe_Success() throws Exception {
        RequestRecipeDTO mockRecipeDTO = RequestRecipeDTOmock.createMock();

        ResponseEntity<String> response = ResponseEntity.ok().body("valid");
        when(recipeController.postRecipe(mockRecipeDTO)).thenReturn(response);

        mockMvc.perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRecipeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(response.getBody()));
    }


    @Test
    void testPostRecipe_Fail() throws Exception {
        RequestRecipeDTO mockRecipe = RequestRecipeDTOmock.createMock();
        Recipe recipe = new ConvertRecipeDTOtoRecipe(mockRecipe).getConvertedRecipe();

        when(recipeRepository.save(Mockito.any(Recipe.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRecipe)))
                .andExpect(status().isExpectationFailed())
                .andExpect(content().string("Recipe could not be saved"));
    }


    @Test
    void getMethodShouldPass() throws Exception {
        Recipe mockRecipe = new Recipe();
        mockRecipe.setRecipeID(1);
        mockRecipe.setRecipeName("Classic Cake Recipe");
        mockRecipe.setIngredient("Flour, Sugar, Eggs, Butter");
        mockRecipe.setInstructions("Mix all ingredients thoroughly and bake at 350 degrees for 25 minutes.");

        ResponseRecipeDTO mockResponse = ResponseRecipeDTOMock.createMock();

        when(recipeRepository.getByID(1)).thenReturn(mockRecipe);

        String expectedResponseJson = objectMapper.writeValueAsString(mockResponse);

        mockMvc.perform(get("/recipe/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }


    @Test
    void getMethodShouldNotFindRecipeByID() throws Exception {
        RequestRecipeDTO mockRecipe = RequestRecipeDTOmock.createMock();

        when(recipeRepository.getByID(1)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/recipe/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRecipe)))

                .andExpect(status().isNotFound())
                .andExpect(content().string("Recipe not found"));

    }

    @Test
    void getMethodShouldThrowException() throws Exception {
        when(recipeRepository.getByID(1)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/recipe/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isExpectationFailed())
                .andExpect(content().string("Error finding recipe"));
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

        when(recipeRepository.getByID(1)).thenReturn(existingRecipe);

        mockMvc.perform(put("/recipe/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRecipe)))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated"));

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

        when(recipeRepository.getByID(1)).thenThrow(new RuntimeException());

        mockMvc.perform(put("/recipe/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRecipe)))

                .andExpect(status().isExpectationFailed())
                .andExpect(content().string("Error updating carrier"));
    }

    @Test
    void updateMethodShouldNotFindRecipeByID() throws Exception {
        RequestRecipeDTO mockRecipe = RequestRecipeDTOmock.createMock();

        when(recipeRepository.getByID(1)).thenThrow(new RuntimeException());

        mockMvc.perform(put("/recipe/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRecipe)))

                .andExpect(status().isNotFound())
                .andExpect(content().string("Recipe not found"));

    }

    @Test
    void getRandomRecipesShouldReturnRecipes() throws Exception {
        List<Integer> mockIDList = Arrays.asList(1, 2, 3, 4, 5); // Full list of IDs in DB
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode expectedResponse = mapper.createObjectNode();

        List<Integer> randomIds = mockIDList.subList(0, 2); // Simulate first 2 IDs
        expectedResponse.putPOJO("recipes", randomIds);
        ResponseEntity<ObjectNode> response = ResponseEntity.ok(expectedResponse); // Create a ResponseEntity with Ok status and data is the expectedResponse POJO

        when(recipeRepository.findAllRecipeID()).thenReturn(randomIds);

        mockMvc.perform(get("/recipe/random/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse.toString()));
    }

    @Test
    void deleteRecipesByID() throws Exception {
        // Create a mock recipe that will be "found" by the repository
        Recipe mockRecipe = new Recipe();  // Add necessary fields if needed

        // Mock the repository's getById method
        when(recipeRepository.getByID(1)).thenReturn(mockRecipe);

        // For the void delete method, use doNothing()
        doNothing().when(recipeRepository).delete(mockRecipe);

        // Now you can test the controller
        mockMvc.perform(delete("/recipe/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }


    
}
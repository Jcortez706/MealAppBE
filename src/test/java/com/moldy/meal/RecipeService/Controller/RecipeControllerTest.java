package com.moldy.meal.RecipeService.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moldy.meal.RecipeService.Recipe;
import com.moldy.meal.RecipeService.Repository.RecipeRepository;
import com.moldy.meal.RecipeService.Utils.DTO.RequestRecipeDTO;
import com.moldy.meal.RecipeService.Utils.DTO.RequestRecipeDTOmock;
import com.moldy.meal.RecipeService.Utils.DTO.ResponseRecipeDTO;
import com.moldy.meal.RecipeService.Utils.DTO.ResponseRecipeDTOMock;
import com.moldy.meal.RecipeService.Utils.Mapping.ConvertRecipeDTOtoRecipe;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void testPostRecipe() throws Exception {
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
    void testGetRecipe() throws Exception {
        Recipe mockRecipe = new Recipe();
        mockRecipe.setRecipeID(1);
        mockRecipe.setRecipeName("Classic Cake Recipe");
        mockRecipe.setIngredient("Flour, Sugar, Eggs, Butter");
        mockRecipe.setInstructions("Mix all ingredients thoroughly and bake at 350 degrees for 25 minutes.");

        ResponseRecipeDTO mockResponse = ResponseRecipeDTOMock.createMock();

        Mockito.when(recipeRepository.getByID(Mockito.anyInt())).thenReturn(mockRecipe);

        String expectedResponseJson = objectMapper.writeValueAsString(mockResponse);

        mockMvc.perform(get("/recipe/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRecipe)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponseJson));
    }
}
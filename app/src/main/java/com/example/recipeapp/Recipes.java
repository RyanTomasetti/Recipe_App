package com.example.recipeapp;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

public class Recipes {

    private String recipeName;
    private String recipeIngredients;
    private String recipeMethodTitle;
    private String recipeTag;
    private String recipeCalories;
    private String recipe;
    private String thumbnail;

    public Recipes(String name, String ingredients, String recipeMethodTitle, String recipe, String tag, String calories, String thumbnail) {
        recipeName = name;
        recipeIngredients = ingredients;
        recipeTag = tag;
        recipeCalories = calories;
        this.recipeMethodTitle = recipeMethodTitle;
        this.recipe = recipe;
        this.thumbnail = thumbnail;
    }

    public Recipes(String name, String ingredients, String recipeMethodTitle, String recipe, String tag, String calories){
        recipeName = name;
        recipeIngredients = ingredients;
        recipeTag = tag;
        recipeCalories = calories;
        this.recipeMethodTitle = recipeMethodTitle;
        this.recipe = recipe;
    }

    public String getRecipeName(){

        return recipeName;
    }
    public String getRecipeIngredients(){

        return recipeIngredients;
    }
    public String getRecipeMethodTitle(){

        return recipeMethodTitle;
    }
    public String getRecipe(){

        return recipe;
    }
    public String  getThumbnail(){

        return thumbnail;
    }
    public String getRecipeTag(){
        return recipeTag;
    }
    public String getrecipeCalories(){
        return recipeCalories;
    }

    public void setRecipeName(String recipe){
        recipeName = recipe;

    }
    public void setRecipeIngredients(String ingredients){
        recipeIngredients = ingredients;

    }
    public void setRecipeMethodTitle(String methodTitle){
        recipeMethodTitle = methodTitle;

    }
    public void setRecipe(String rec){
        recipe = rec;

    }
    public void setThumbnail(String thumbnailpath){
        thumbnail = thumbnailpath;

    }
    public void setRecipeTag(String tag){
        recipeTag = tag;

    }
    public void setRecipeCalories(String calories){
        recipeCalories = calories;

    }

}

package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    private TextView mRecipeName;
    private TextView mRecipeIngredients;
    private TextView mRecipeTag;
    private TextView mRecipeCalories;
    private TextView mRecipeMethodTitle;
    private TextView mRecipe;
    private ImageView mImage;
    private int Position;

    private Button backButton;
    private Button deleteButton;
    private Button editButton;

    public static ArrayList<Recipes> recipes;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mRecipeName = findViewById(R.id.text_recipe);
        mRecipeIngredients = findViewById(R.id.ingredients);
        mRecipeTag = findViewById(R.id.tag);
        mRecipeCalories = findViewById(R.id.calories);

        mRecipeMethodTitle = findViewById(R.id.method);
        mRecipe = findViewById(R.id.recipe);
        mImage = findViewById(R.id.imageView2);
        backButton = findViewById(R.id.backButton);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);


        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Name");
        String Ingredients = intent.getExtras().getString("Ingredients");
        String Tag = intent.getExtras().getString("Tag");
        String Calories = intent.getExtras().getString("Calories");
        String MethodTitle = intent.getExtras().getString("MethodTitle");
        String Recipe = intent.getExtras().getString("Recipe");
        String Image = intent.getExtras().getString("Image");
        Position = intent.getExtras().getInt("Position");

        mRecipeName.setText(Title);
        mRecipeIngredients.setText(Ingredients);
        mRecipeTag.setText(Tag);
        mRecipeCalories.setText(Calories);
        mRecipeMethodTitle.setText(MethodTitle);
        mRecipe.setText(Recipe);
        if(Image != null){


            File f = new File(Image);
            Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());
            mImage.setImageBitmap(b);



            //Uri imageUri = Uri.parse(Image);
            //mImage.setImageURI(imageUri);
        }

        backButton.setOnClickListener(v -> goBack());
        deleteButton.setOnClickListener(v -> deleteRecipe(Position));
        editButton.setOnClickListener(v -> editRecipe());

        loadRecipes();
        mRecipeIngredients.setMovementMethod(new ScrollingMovementMethod());
        mRecipe.setMovementMethod(new ScrollingMovementMethod());
    }

    private void loadRecipes(){
        SharedPreferences sharedPreferences = getSharedPreferences("Recipes", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Recipes", null);
        Type type = new TypeToken<ArrayList<Recipes>>(){}.getType();
        recipes = gson.fromJson(json, type);

    }

    private void goBack(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void editRecipe(){
        Intent intent = new Intent(this, EditRecipe.class);
        intent.putExtra("Recipe", Position);
        startActivity(intent);
    }

    @SuppressLint("NewApi")
    private void deleteRecipe(int pos){
        ArrayList<Recipes> recipes = MainActivity.recipes;

        File img = new File(String.valueOf(recipes.get(pos).getThumbnail()));
        img.delete();

        recipes.remove(pos);



        SharedPreferences sharedPreferences = getSharedPreferences("Recipes", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipes);
        editor.putString("Recipes", json);
        editor.apply();

        goBack();
    }
}
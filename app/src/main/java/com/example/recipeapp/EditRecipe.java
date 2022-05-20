package com.example.recipeapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class EditRecipe extends AppCompatActivity {

    ImageView imageView;
    Button save;
    Button cancel;

    private int recipePos;
    Recipes recipe;

    EditText rName;
    EditText rIng;
    EditText rSteps;
    EditText rTag;
    EditText rCalories;
    Uri uriImage;
    Bitmap rImage;
    File imgPath;

    private static final int REQUEST_OPEN_RESULT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);


        imageView = findViewById(R.id.imageView);
        save = findViewById(R.id.saveButton);
        cancel = findViewById(R.id.cancelButton);

        Intent intent = getIntent();
        recipePos = intent.getExtras().getInt("Recipe");
        recipe = MainActivity.recipes.get(recipePos);

        //imageView.setOnClickListener(v -> mGetContent.launch("image/*"));
        imageView.setOnClickListener(v -> getImage());

        save.setOnClickListener(v -> saveClose());

        cancel.setOnClickListener(v -> Close());

    }

    /*private void loadRecipes(){
        SharedPreferences sharedPreferences = getSharedPreferences("Recipes", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Recipes", null);
        Type type = new TypeToken<ArrayList<Recipes>>(){}.getType();
        recipes = gson.fromJson(json, type);

        if(recipes == null)
        {
            recipes = new ArrayList<Recipes>();
        }
    }*/


    protected void getImage(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_OPEN_RESULT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == REQUEST_OPEN_RESULT_CODE && resultCode == RESULT_OK) {
            Uri uri;
            if (resultData != null) {
                uri = resultData.getData();
                imageView.setImageURI(uri);
                uriImage = resultData.getData();
                try {
                    rImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {
                        imageView.setImageURI(result);
                        rImage = result;
                    }
                }
            });*/

    private File saveToInternalStorage(Bitmap bitmapImage){

        ArrayList<Recipes> recipes = MainActivity.recipes;

        File img = new File(String.valueOf(recipes.get(recipePos).getThumbnail()));
        img.delete();

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        //path to imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        //Get File count
        rName = findViewById(R.id.recipeName);
        String str = rName.getText().toString();
        //create image Dir
        File mypath = new File(directory, str);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            }catch (IOException e ){
                e.printStackTrace();
            }
        }
        return mypath;
    }

    private void saveClose(){
        rName = findViewById(R.id.recipeName);
        rIng = findViewById(R.id.Ingredients);
        rSteps = findViewById(R.id.Steps);
        rTag = findViewById(R.id.recipeTag);
        rCalories = findViewById(R.id.recipeCalories);
        if(rImage != null){
            imgPath = saveToInternalStorage(rImage);
            recipe.setThumbnail(imgPath.toString());
        }

        recipe.setRecipeName(rName.getText().toString());
        recipe.setRecipeIngredients(rIng.getText().toString());
        recipe.setRecipe(rSteps.getText().toString());
        recipe.setRecipeTag(rTag.getText().toString());
        recipe.setRecipeCalories(rCalories.getText().toString());

        ArrayList<Recipes> recipes = MainActivity.recipes;

        SharedPreferences sharedPreferences = getSharedPreferences("Recipes", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipes);
        editor.putString("Recipes", json);
        editor.apply();

        Close();
    }

    private void Close(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

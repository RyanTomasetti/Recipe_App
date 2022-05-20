package com.example.recipeapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView myRV;
    RecyclerViewAdapter myAdapter;

    public static ArrayList<Recipes> recipes;

    private Button button;

    private EditText search;

    //private int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button = findViewById(R.id.button);
        button.setOnClickListener(v -> newRecipe());

        search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        loadRecipes();



        myRV = findViewById(R.id.recyclerView_id);

        myAdapter = new RecyclerViewAdapter(this, recipes);

        myRV.setLayoutManager(new GridLayoutManager(this, 1));

        myRV.setAdapter(myAdapter);
    }


    private void loadRecipes(){
        SharedPreferences sharedPreferences = getSharedPreferences("Recipes", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Recipes", null);
        Type type = new TypeToken<ArrayList<Recipes>>(){}.getType();
        recipes = gson.fromJson(json, type);

        if(recipes == null)
        {
            recipes = new ArrayList<Recipes>();
        }
    }

    public void newRecipe(){
        Intent intent = new Intent(this, NewRecipe.class);
        startActivity(intent);
    }

    private void filter(String text){
        ArrayList<Recipes> filteredList = new ArrayList<>();
        for(Recipes recipe : recipes){
            if(recipe.getRecipeName().toLowerCase().contains(text.toLowerCase()) || recipe.getRecipeTag().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(recipe);
            }
        }
        myAdapter.filterList(filteredList);
    }

}

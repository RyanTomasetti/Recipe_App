package com.example.recipeapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.checkSelfPermission;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder> {

    private Context mContext;
    private List<Recipes> mData;

    public RecyclerViewAdapter (Context mContext,List<Recipes> mData ){
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_recipe, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {

        holder.recipeTitle.setText(mData.get(position).getRecipeName());
        holder.recipeTag.setText(mData.get(position).getRecipeTag());

        //Need permissions to set image from device
        if(mData.get(position).getThumbnail() != null)
        {
            String path = mData.get(position).getThumbnail();

            File f = new File(String.valueOf(path));
            Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());
            holder.img_recipe_thumbnail.setImageBitmap(b);

            //Log.d("success",mData.get(position).getThumbnail());
            /*
            Uri imageUri = Uri.parse(mData.get(position).getThumbnail());
            holder.img_recipe_thumbnail.setImageURI(imageUri);*/
        }



        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, RecipeActivity.class);


            intent.putExtra("Name", mData.get(position).getRecipeName());
            intent.putExtra("Ingredients", mData.get(position).getRecipeIngredients());
            intent.putExtra("Tag", mData.get(position).getRecipeTag());
            intent.putExtra("Calories", mData.get(position).getrecipeCalories());
            intent.putExtra("MethodTitle",mData.get(position).getRecipeMethodTitle());
            intent.putExtra("Recipe", mData.get(position).getRecipe());
            intent.putExtra("Position", position);

            if(mData.get(position).getThumbnail() != null){
                intent.putExtra("Image", mData.get(position).getThumbnail());
            }

            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void filterList(ArrayList<Recipes> filteredList){
        mData = filteredList;
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView recipeTitle;
        TextView recipeTag;
        CardView cardView;
        ImageView img_recipe_thumbnail;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            recipeTitle = itemView.findViewById(R.id.cardview_recipe);
            recipeTag = itemView.findViewById(R.id.cardview_recipeTag);
            img_recipe_thumbnail = itemView.findViewById(R.id.recipe_img_id);
            cardView = itemView.findViewById(R.id.cardview_id);

        }
    }
}

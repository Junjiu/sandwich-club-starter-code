package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;

        try{

            JSONObject sandwichJSON = new JSONObject(json);

            JSONObject name = sandwichJSON.getJSONObject("name");

            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAsList = new ArrayList<>();
            for(int i = 0; i < alsoKnownAs.length(); ++i){
                alsoKnownAsList.add(alsoKnownAs.getString(i));
            }

            JSONArray ingredients = sandwichJSON.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for(int i = 0; i < ingredients.length(); ++i){
                ingredientsList.add(ingredients.getString(i));
            }

            sandwich = new Sandwich(name.getString("mainName"), alsoKnownAsList,
                    sandwichJSON.getString("placeOfOrigin"),sandwichJSON.getString("description"),
                    sandwichJSON.getString("image"),ingredientsList);
        }catch (Exception e){
            e.printStackTrace();
        }

        return sandwich;
    }
}

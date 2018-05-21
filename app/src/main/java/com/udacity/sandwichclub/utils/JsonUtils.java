package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


public class JsonUtils {

    private enum  jsonKeysDict  {
        name,mainName, alsoKnownAs, placeOfOrigin, description,image, ingredients
    }

    public static Sandwich parseSandwichJson(String json) {
        JSONObject myObj = null;
        Sandwich sandwich = null;
        try {
            myObj = new JSONObject(json);
            System.out.println(myObj.toString(4));

            JSONObject nameObj = myObj.getJSONObject(jsonKeysDict.name.toString());
            String mainName = nameObj.getString(jsonKeysDict.mainName.toString());

            JSONArray alsoKnownAsAry = nameObj.getJSONArray(jsonKeysDict.alsoKnownAs.toString());
            List<String> alsknowAsList = Arrays.asList(createArrayFromJSONArray(alsoKnownAsAry));


            String placeOfOrigin = myObj.getString(jsonKeysDict.placeOfOrigin.toString());

            String description = myObj.getString(jsonKeysDict.description.toString());

            String image = myObj.getString(jsonKeysDict.image.toString());

            JSONArray ingredientsAry = myObj.getJSONArray(jsonKeysDict.ingredients.toString());
            List<String> ingrediantsList = Arrays.asList(createArrayFromJSONArray(ingredientsAry));



            sandwich = new Sandwich(mainName, alsknowAsList, placeOfOrigin, description, image, ingrediantsList);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;


    }

    static String[] createArrayFromJSONArray(JSONArray jsonAry)
    {

        int length = jsonAry.length();
        String[] resultAry = new String[length];

        try {
            for(int i=0;i<length;i++) resultAry[i] = jsonAry.getString(i);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return resultAry;
    }
}

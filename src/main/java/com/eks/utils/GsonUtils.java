package com.eks.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonUtils {
    private static JsonParser JSON_PARSER = new JsonParser();
    public static JsonElement convertStringToJsonElement(String jsonString){
        return JSON_PARSER.parse(jsonString);
    }
    public static JsonElement generateJsonElementWithNeededField(JsonElement jsonElement,String... neededFieldStringArray){
        if (!jsonElement.isJsonObject() && !jsonElement.isJsonArray()){
            throw new RuntimeException("Only support JsonObject and JsonArray.");
        }
        JsonElement resultJsonElement = null;
        if (jsonElement.isJsonObject()){
            JsonObject newJsonObject = new JsonObject();
            JsonObject originalJsonObject = jsonElement.getAsJsonObject();
            for(String neededFieldString : neededFieldStringArray){
                newJsonObject.add(neededFieldString,originalJsonObject.get(neededFieldString));
            }
            resultJsonElement = newJsonObject;
        }
        if(jsonElement.isJsonArray()){
            JsonArray newJsonArray = new JsonArray();
            JsonArray originalJsonArray = jsonElement.getAsJsonArray();
            for(JsonElement originalJsonElement : originalJsonArray){
                JsonElement newJsonElement = generateJsonElementWithNeededField(originalJsonElement, neededFieldStringArray);
                newJsonArray.add(newJsonElement);
            }
            resultJsonElement = newJsonArray;
        }
        return resultJsonElement;
    }
}

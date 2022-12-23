package com.nhlstenden.jabberpoint.files.loading;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nhlstenden.jabberpoint.BitmapItem;
import com.nhlstenden.jabberpoint.Presentation;
import com.nhlstenden.jabberpoint.Slide;
import com.nhlstenden.jabberpoint.TextItem;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONLoader extends FileLoader{

    @Override
    public String getExtension() {
        return "json";
    }

    @Override
    public void loadPresentation(Presentation presentation, File file) {
        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(file));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            presentation.setTitle(getJSONString(jsonObject, "title"));

            JsonArray slides = jsonObject.get("slides").getAsJsonArray();
            int max = slides.size();

            //test

            for (int slideNumber = 0; slideNumber < max; slideNumber++) {
                JsonObject jsonSlide = slides.get(slideNumber).getAsJsonObject();
                Slide slide = new Slide();
                slide.setTitle(getJSONString(jsonSlide, "title"));
                presentation.append(slide);

                JsonArray slideItems = jsonSlide.get("items").getAsJsonArray();
                int maxItems = slideItems.size();
                for (int itemNumber = 0; itemNumber < maxItems; itemNumber++) {
                    JsonObject item = slideItems.get(itemNumber).getAsJsonObject();
                    loadSlideItem(slide, item);
                }
            }
        }
        catch (IOException iox) {
            iox.printStackTrace();
        }
    }

    private String getJSONString(JsonObject object, String key){
            String value = object.get(key).getAsString();
            if(value.isEmpty()){
                value = " ";
            }
            return value;
    }

    private void loadSlideItem(Slide slide, JsonObject item) {
        int level = item.get("level").getAsInt();
        String type = getJSONString(item, "kind");
        if (type.equals("text")) {
            slide.append(new TextItem(level, getJSONString(item, "content")));
        }
        else {
            if (type.equals("image")) {
                slide.append(new BitmapItem(level, getJSONString(item, "content")));
            }
            else {
                System.err.println("Unknown Element type");
            }
        }
    }

    @Override
    public char getShortcut() {
        return 'j';
    }
}

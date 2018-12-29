package com.example.imeshranawaka.styleomega;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

public class loadJSONFromAsset {
    public String readJson(String fileName, AssetManager assets){
        String json = null;
        try {
            InputStream is = assets.open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}

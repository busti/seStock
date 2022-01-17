package de.unihannover.sestock;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

public class ReadJson {

    public <JSonStructure> void read(){
        FileReader fr;
        JSonStructure struct;

        try {
            fr = new FileReader("query.json");
            JsonReader reader = Json.createReader(fr);
            struct = (JSonStructure) reader.read();
            JsonValue value = (JsonValue) struct;
            print(value);
            reader.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }


    }

    public static void print(JsonValue value){
        JsonObject object = null;
        if (value.getValueType() == ValueType.OBJECT) {
            object = (JsonObject) value;
            for (Entry<String, JsonValue> set : object.entrySet()) {
                if (set.getValue() instanceof JsonArray) {
                    System.out.println("Array: ");
                    print((JsonValue) set.getValue());
                } else {
                    System.out.println(set.getKey() + ": " + set.getValue());
                }
            }
        } else if (value.getValueType() == ValueType.ARRAY) {
            JsonArray array = (JsonArray) value;
            for (JsonValue val : array)
                print(val);
        }
    }

}

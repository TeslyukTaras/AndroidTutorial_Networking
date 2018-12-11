package com.teslyuk.android.androidtutorial_volley_retrofit;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.teslyuk.android.androidtutorial_volley_retrofit.data.source.volley_api.WebRequest;
import com.teslyuk.android.androidtutorial_volley_retrofit.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Created by taras on 14.02.16.
 */
public class AndroidTutorialApplication extends Application {

    private static WebRequest mRequest;
    private static AndroidTutorialApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        testJson();
    }

    private void testJson() {
        JSONObject Julia = new JSONObject();
        JSONObject Taras = new JSONObject();
        JSONArray array = new JSONArray();

        try {
            Julia.put("name", "Julia");
            Julia.put("surname", "Gavrikova");

            Taras.put("name", "Taras");
            Taras.put("surname", "Teslyuk");

            Log.d("TEST", "Julia: " + Julia.toString());
            Log.d("TEST", "Taras: " + Taras.toString());

            array.put(Julia);
            array.put(Taras);

            Log.d("TEST", "Array: " + array.toString());

            String arrayString = array.toString();

            JSONArray array1 = new JSONArray(arrayString);
            Log.d("TEST", "length: " + array1.length());


            Gson gson = new Gson();

            for (int i = 0; i < array1.length(); i++) {
                JSONObject object = array1.getJSONObject(i);

                Log.d("TEST", "i: " + i + " has object: " + object.toString());
                Person person = new Person();
                if (object.has("name")) {
                    Log.d("TEST", "object: includes name: " + object.get("name").toString());
                    person.name = object.get("name").toString();
                }

                if (object.has("surname")) {
                    Log.d("TEST", "object: includes surname: " + object.get("surname").toString());
                    person.surname = object.get("surname").toString();
                }

                Log.d("TEST", "New Person object created: " + person.toString());

                Person gsonConvertedPerson = gson.fromJson(object.toString(), Person.class);

                Log.d("TEST", "New gson converted Person created: " + gsonConvertedPerson.toString());
            }

            Person person = new Person();
            person.name = "Vasyl";
            person.surname = "Vasylenko";

            String json = gson.toJson(person);
            Log.d("TEST", "New json : " + json);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class Person {
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("surname")
        @Expose
        private String surname;

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    '}';
        }
    }

    public static WebRequest getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequest == null) {
            mRequest = new WebRequest(instance);
        }
        return mRequest;
    }
}

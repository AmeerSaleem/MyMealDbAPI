package com.example.mymealdbapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView mealName, mealCategory, mealCountry, mealInstructions;
    ImageView mealImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mealName = findViewById(R.id.edit_meal_name);
        mealCategory = findViewById(R.id.edit_meal_category);
        mealCountry = findViewById(R.id.edit_meal_origin);
        mealInstructions = findViewById(R.id.edit_meal_instructions);
        mealImage = findViewById(R.id.image_meal_pic);

        String mealUrl = "https://www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata";

        Toast.makeText(this, "Hello from inside", Toast.LENGTH_SHORT).show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mealUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray mainArray = response.getJSONArray("meals");
                    JSONObject mealObject= mainArray.getJSONObject(0);
                    mealName.setText(mealObject.getString("strMeal"));
                    mealCategory.setText(mealObject.getString("strCategory"));
                    mealCountry.setText(mealObject.getString("strArea"));
                    mealInstructions.setText(mealObject.getString("strInstructions"));
                    Glide.with(getApplicationContext())
                            .load(mealObject.getString("strMealThumb"))
                            .into(mealImage);
//                    Log.e("Error Here", "inside onResponse" + mainArray.getJSONObject(1).getString("strMeal"));
                    if (mainArray != null) {
                        Log.e("Error Here", "inside onResponse" );
                    }
//                    mealName.setText(mainArray.getJSONObject(1).getString("strMeal"));

//                    Toast.makeText(MainActivity.this, "hello" + mainArray.getJSONObject(1).getString("strMeal"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Unable to fetch data", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }
}

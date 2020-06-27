package com.adewale.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.adewale.pokedex.model.Pokemon;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PokemonActivity extends AppCompatActivity {

    public TextView nameTextView;
    public TextView numberTextView;
    private String url;
    public TextView type1TextView;
    public TextView type2TextView;
    private String type, slot;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);


       String name =  getIntent().getStringExtra("name");
       url = getIntent().getStringExtra("url");

       nameTextView  =findViewById(R.id.pokemon_name);
       numberTextView  =findViewById(R.id.pokemon_number);
       type1TextView = findViewById(R.id.pokedex_row_textType1);
       type2TextView = findViewById(R.id.pokedex_row_textType2);
       requestQueue= Volley.newRequestQueue(getApplicationContext());
        loadType();
//       numberTextView.setText(String.format("N%03d",number));
    }


    public void loadType(){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String name = response.getString("name");
                    nameTextView.setText(name.substring(0,1).toUpperCase() + name.substring(1));
                    numberTextView.setText(String.format("N%03d",response.getInt("id")));
                    JSONArray typeEntries = response.getJSONArray("types");
                    for(int i=0; i<typeEntries.length(); i++){
                        JSONObject typeEntry = typeEntries.getJSONObject(i);
                        Integer slot = typeEntry.getInt("slot");
                        String type = typeEntry.getJSONObject("type").getString("name");
                        if(slot==1){
                            type1TextView.setText(type);
                            Log.i("Slot 1", type);
                        }
                        else if(slot==2){
                            Log.i("Slot 1", type);
                            type2TextView.setText(type);
                        }
                    }

                } catch (JSONException e) {
                    Log.e("Error", "Pokemon json error has occured", e);
                }
            }
    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Could not connect to pokedex api", error);
            }
        });
        requestQueue.add(request);
    }
}
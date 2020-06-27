package com.adewale.pokedex.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adewale.pokedex.PokemonActivity;
import com.adewale.pokedex.R;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import static com.adewale.pokedex.adapter.PokedexAdapter.*;

public class PokedexAdapter extends Adapter<PokedexViewHolder> {
    public static class PokedexViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout containerView;
        private TextView textView;
        private TextView titleView;

        public PokedexViewHolder(@NonNull View itemView) {
            super(itemView);
            containerView = itemView.findViewById(R.id.pokedex_row);
            textView = itemView.findViewById(R.id.pokedex_row_text_view);
            titleView = itemView.findViewById(R.id.pokedex_row_text_title);

            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Pokemon current = (Pokemon) containerView.getTag();
                    Intent intent = new Intent(view.getContext(), PokemonActivity.class);
                    intent.putExtra("name", current.getName());
                    intent.putExtra("url", current.getUrl());

                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    public List<Pokemon> pokemons = new ArrayList<>();

    @NonNull
    @Override
    public PokedexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokedex_row, parent,false);
        return new PokedexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokedexViewHolder holder, int position) {
        Pokemon current = pokemons.get(position);
        holder.textView.setText(current.getName());
        holder.titleView.setText(current.getTitle());

        holder.containerView.setTag(current);

    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }
    //Create a new request queue
    private RequestQueue requestQueue;

    public PokedexAdapter(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        loadPokeDex();
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    private void loadPokeDex(){
        String url = "https://pokeapi.co/api/v2/pokemon?limit=100";
        final List<Pokemon> pokemonList = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    for(int i =0; i<results.length(); i++){
                        JSONObject result = results.getJSONObject(i);
                        String name =result.getString("name");

                        Pokemon pokemon = new Pokemon(name.substring(0,1).toUpperCase()+name.substring(1), result.getString("url"));
                        pokemons.add(pokemon);
                        Log.i("Data", String.format("%s, %s", result.getString("name"), result.getString("url")));
                    }
                    notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e("Error", "An error has occured", e);
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

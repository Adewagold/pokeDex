package com.adewale.pokedex.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adewale.pokedex.PokemonActivity;
import com.adewale.pokedex.R;
import com.adewale.pokedex.model.Pokemon;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                    intent.putExtra("number", current.getNumber());

                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    private List<Pokemon> pokemons = Arrays.asList();

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

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    private void loadPokeDex(){
        String url = "https://pokeapi.co/api/v2/pokemon?limit=100";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

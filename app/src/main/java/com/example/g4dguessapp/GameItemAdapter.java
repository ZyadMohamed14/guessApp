package com.example.g4dguessapp;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.g4dguessapp.OnItemClickListener;
import com.example.g4dguessapp.R;

import java.util.ArrayList;
import java.util.List;

public class GameItemAdapter extends RecyclerView.Adapter<GameItemAdapter.GameItemViewHolder> {

    private  ArrayList<GameItem> gameItems ;
    private  OnItemClickListener onItemClickListener;
    private boolean gameStarted = false;

    public GameItemAdapter(ArrayList<GameItem> gameItems, OnItemClickListener onItemClickListener) {
        this.gameItems = gameItems;
        this.onItemClickListener = onItemClickListener;
    }
    public void setGameItems(ArrayList<GameItem> gameItems){
        this.gameItems = gameItems;

    }
    @NonNull
    @Override
    public GameItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new GameItemViewHolder(view);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ArrayList<GameItem>newData){
        gameItems.clear();
        for (int i = 0; i < newData.size(); i++) {
            Log.d("benzsdsdsdsdsds", "updateData: "+newData.get(i).isCardShown());
        }


        gameItems.addAll(newData);
        Log.d("benzsdsdsdsdsds", "gameItems: "+gameItems.size());
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull GameItemViewHolder holder, int position) {
        GameItem gameItem = gameItems.get(position);
        holder.textView.setText(String.valueOf(gameItem.getNumber()));
        if (gameItem.isCardShown()) {
            holder.textView.setVisibility(View.INVISIBLE); // **Hide text view**
            holder.imageView.setVisibility(View.VISIBLE); // **Show image view**
        } else {
            holder.textView.setVisibility(View.VISIBLE); // **Show text view**
            holder.imageView.setVisibility(View.INVISIBLE); // **Hide image view**
        }
        holder.itemView.setOnClickListener(v -> {
            if (gameItem.isGameStared()) {
                holder.imageView.setVisibility(View.INVISIBLE); // **Hide clicked item's image view**
                holder.textView.setVisibility(View.VISIBLE); // **Show clicked item's text view**
                onItemClickListener.onItemClick(gameItem,position);
            }
            else {
                YoYo.with(Techniques.Shake).duration(500).repeat(3).playOn(holder.imageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameItems.size();
    }

    public static class GameItemViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;
        public final TextView textView;

        public GameItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            textView = itemView.findViewById(R.id.item_text);
        }
    }
}
package com.example.g4dguessapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public  class GameItemDiffCallback extends DiffUtil.ItemCallback<GameItem> {

    @Override
    public boolean areItemsTheSame(@NonNull GameItem oldItem, @NonNull GameItem newItem) {
        // Check if items have the same ID
        return oldItem.getId() == newItem.getId(); // Ensure GameItem has an ID
    }

    @Override
    public boolean areContentsTheSame(@NonNull GameItem oldItem, @NonNull GameItem newItem) {
        // Check if contents are the same
        return oldItem.equals(newItem); // Ensure GameItem has an appropriate equals() method
    }

    @Override
    public Object getChangePayload(@NonNull GameItem oldItem, @NonNull GameItem newItem) {
        // Return payload to specify what changed
        // You can return null if no specific change payload is needed
        return super.getChangePayload(oldItem, newItem);
    }
}

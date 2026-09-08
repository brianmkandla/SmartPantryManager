package com.brian.smartpantry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brian.smartpantry.model.PantryItem;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> 
{

    private List<PantryItem> pantryItems;

    public PantryAdapter(List<PantryItem> pantryItems) 
	{
        this.pantryItems = pantryItems;
    }

    // Creates the layout used for each pantry item in the RecyclerView.
    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) 
	{
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    // Places the pantry item's data into the correct views.
    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) 
	{
        PantryItem item = pantryItems.get(position);

        holder.textItemName.setText(item.getName());
        holder.textItemQuantity.setText(
                "Quantity: " + item.getQuantity() + " " + item.getUnit()
        );

        if (item.getExpiryDate() == null || item.getExpiryDate().isEmpty()) 
		{
            holder.textItemExpiry.setText("No expiry date");
        } 
		else 
		{
            holder.textItemExpiry.setText("Expires: " + item.getExpiryDate());
        }
    }

    // Tells RecyclerView how many pantry items are in the list.
    @Override
    public int getItemCount() 
	{
        return pantryItems.size();
    }

    // Holds references to the views used by each pantry item.
    public static class PantryViewHolder extends RecyclerView.ViewHolder 
	{

        TextView textItemName;
        TextView textItemQuantity;
        TextView textItemExpiry;

        public PantryViewHolder(@NonNull View itemView) 
		{
            super(itemView);

            textItemName = itemView.findViewById(R.id.textItemName);
            textItemQuantity = itemView.findViewById(R.id.textItemQuantity);
            textItemExpiry = itemView.findViewById(R.id.textItemExpiry);
        }
    }
}
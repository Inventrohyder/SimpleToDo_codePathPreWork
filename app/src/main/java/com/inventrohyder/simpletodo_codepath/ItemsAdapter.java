package com.inventrohyder.simpletodo_codepath;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private final List<String> mItems;

    public ItemsAdapter(List<String> items) {
        mItems = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use layout inflater to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        // wrap it inside a View Holder and return it
        return new ViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Grab the item at the position
        String item = mItems.get(position);
        // Bind the item into the specified view holder
        holder.bind(item);
    }

    // Tells the recycler view how many items are in the list
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    // Container to provide easy access to views that represent each row of the list
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        // Update the view inside of the view holder with this data
        public void bind(String item) {
            tvItem.setText(item);
        }
    }
}

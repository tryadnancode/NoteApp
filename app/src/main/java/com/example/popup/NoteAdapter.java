package com.example.popup;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private Context context;
    private List<Item> itemsList;
    private OnItemClickListener onItemClickListener;
    private AdapterListener adapterListener;

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    public interface AdapterListener {
        void OnEdit(Item item);
        void OnDelete(int id, int position);
    }

    public NoteAdapter(Context context, AdapterListener listener, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.itemsList = new ArrayList<>();
        this.adapterListener = listener;
        this.onItemClickListener = onItemClickListener;
    }

    public void setItems(List<Item> items) {
        this.itemsList = items;
        notifyDataSetChanged();
    }
//
//    public void addItem(Item item) {
//        itemsList.add(item);
//        notifyItemInserted(itemsList.size() - 1);
//    }

//    public void updateItem(Item item) {
//        int position = getItemPositionById(item.getId());
//        if (position != -1) {
//            itemsList.set(position, item);
//            notifyItemChanged(position);
//        }
//    }

    public void removeItem(int position) {
        itemsList.remove(position);
        notifyItemRemoved(position);
    }

//    public void clearItems() {
//        itemsList.clear();
//        notifyDataSetChanged();
//    }

//    private int getItemPositionById(int id) {
//        for (int i = 0; i < itemsList.size(); i++) {
//            if (itemsList.get(i).getId() == id) {
//                return i;
//            }
//        }
//        return -1;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemsList.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.message.setText(item.getMessage());

        holder.edit.setOnClickListener(v -> adapterListener.OnEdit(item));

        holder.delete.setOnClickListener(v -> adapterListener.OnDelete(item.getId(), position));
        //Share
        holder.noteLayout.setOnLongClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, item.getTitle() + " " + item.getDescription() + " " + item.getMessage());
            context.startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        });
        //Double Click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            private static final long DOUBLE_CLICK_TIME_DELTA = 300; // milliseconds
            long lastClickTime = 0;

            @Override
            public void onClick(View view) {
                long clickTime = System.currentTimeMillis();
                if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                    onItemClickListener.onItemClick(item);
                }
                lastClickTime = clickTime;
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, message;
        ImageView edit, delete;
        LinearLayout noteLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.layout_title);
            description = itemView.findViewById(R.id.layout_description);
            message = itemView.findViewById(R.id.layout_message);
            edit = itemView.findViewById(R.id.edit_layout);
            delete = itemView.findViewById(R.id.delete_layout);
            noteLayout = itemView.findViewById(R.id.noteLayout);
        }
    }
}

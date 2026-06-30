package com.syifa.endemikdb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.syifa.endemikdb.R;
import com.syifa.endemikdb.entities.Favorite;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<Favorite> list;

    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;

    public interface OnItemClickListener {
        void onItemClick(Favorite favorite);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Favorite favorite);
    }

    public FavoriteAdapter(
            List<Favorite> list,
            OnItemClickListener listener,
            OnItemLongClickListener longClickListener
    ) {
        this.list = list;
        this.listener = listener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_endemik,
                        parent,
                        false
                );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        Favorite item = list.get(position);

        holder.tvNama.setText(item.getNama());
        holder.tvKategori.setText(item.getTipe());

        Glide.with(holder.itemView.getContext())
                .load(item.getFoto())
                .into(holder.imgFoto);

        holder.itemView.setOnClickListener(v -> {

            v.animate()
                    .scaleX(0.98f)
                    .scaleY(0.98f)
                    .setDuration(80)
                    .withEndAction(() -> {

                        v.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(120)
                                .start();

                        if (listener != null) {
                            listener.onItemClick(item);
                        }

                    })
                    .start();

        });

        holder.itemView.setOnLongClickListener(v -> {

            if (longClickListener != null) {
                longClickListener.onItemLongClick(item);
            }

            return true;
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgFoto;
        TextView tvNama;
        TextView tvKategori;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFoto = itemView.findViewById(R.id.imgFoto);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvKategori = itemView.findViewById(R.id.tvKategori);
        }
    }
}
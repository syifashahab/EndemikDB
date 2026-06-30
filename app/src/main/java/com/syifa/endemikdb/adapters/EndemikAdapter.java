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
import com.syifa.endemikdb.entities.Endemik;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class EndemikAdapter extends RecyclerView.Adapter<EndemikAdapter.ViewHolder> {

    private List<Endemik> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Endemik endemik);
    }

    public EndemikAdapter(List<Endemik> list) {
        this.list = list;
        this.listener = null;
    }

    public EndemikAdapter(
            List<Endemik> list,
            OnItemClickListener listener
    ) {
        this.list = list;
        this.listener = listener;
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

        Endemik item = list.get(position);

        holder.tvNama.setText(item.getNama());
        holder.tvKategori.setText(item.getTipe());

        Glide.with(holder.itemView.getContext())
                .load(item.getFoto())
                .thumbnail(0.25f)
                .override(400, 400)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .dontAnimate()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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
package ru.mirea.androidcoursework.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.mirea.androidcoursework.R;
import ru.mirea.androidcoursework.entity.CardProduct;

public class CardProductPreviewAdapter extends RecyclerView.Adapter<CardProductPreviewAdapter.CardProductViewHolder> {

    private List<CardProduct> cardProductList;

    public CardProductPreviewAdapter(List<CardProduct> cardProductList) {
        this.cardProductList = cardProductList;
    }

    @NonNull
    @Override
    public CardProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_product_preview, parent, false);
        return new CardProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CardProductViewHolder holder, int position) {
        CardProduct cardProduct = cardProductList.get(position);
        holder.tvTitle.setText(cardProduct.getTitle());
        holder.tvPrice.setText(String.valueOf(cardProduct.getPrice()) + " ₽");
        Picasso.get().load(cardProduct.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return cardProductList.size();
    }

    public static class CardProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice;
        ImageView imageView;

        public CardProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

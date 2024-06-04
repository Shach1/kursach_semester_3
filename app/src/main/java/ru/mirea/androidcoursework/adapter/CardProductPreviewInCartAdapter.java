package ru.mirea.androidcoursework.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.mirea.androidcoursework.R;
import ru.mirea.androidcoursework.entity.CardProductInCart;
import ru.mirea.androidcoursework.interfaceses.IOnCartChangedListener;
import ru.mirea.androidcoursework.main.CartFragment;

public class CardProductPreviewInCartAdapter extends RecyclerView.Adapter<CardProductPreviewInCartAdapter.CardProductInCartViewHolder>
{

    private List<CardProductInCart> cardProductInCartList;
    private IOnCartChangedListener onCartChangedListener;

    public CardProductPreviewInCartAdapter(List<CardProductInCart> cardProductInCartList, IOnCartChangedListener onCartChangedListener)
    {
        this.cardProductInCartList = cardProductInCartList;
        this.onCartChangedListener = onCartChangedListener;
    }

    @NonNull
    @Override
    public CardProductInCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_product_preview_in_cart, parent, false);
        return new CardProductInCartViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull CardProductInCartViewHolder holder, int position) {
        CardProductInCart cardProductInCart = cardProductInCartList.get(position);
        holder.tvTitle.setText(cardProductInCart.getCardProduct().getTitle());
        holder.tvAmount.setText(String.valueOf(cardProductInCart.getAmount()));
        holder.tvTotalPrice.setText(cardProductInCart.getTotalPrice() + " ₽");
        Picasso.get().load(cardProductInCart.getCardProduct().getImageUrl()).into(holder.imageView);

        // Здесь вы можете установить слушатели для кнопок "+" и "-"
        holder.btPlus.setOnClickListener(v -> {
            int newAmount = cardProductInCart.getAmount() + 1;
            double newTotalPrice = cardProductInCart.getCardProduct().getPrice() * newAmount;
            cardProductInCart.setAmount(newAmount);
            cardProductInCart.setTotalPrice(newTotalPrice);
            holder.tvAmount.setText(String.valueOf(newAmount));
            holder.tvTotalPrice.setText(newTotalPrice + " ₽");
            onCartChangedListener.onCartChanged();
        });
        holder.btMinus.setOnClickListener(v -> {
            int newAmount = cardProductInCart.getAmount() - 1;
            if (newAmount < 1) {
                // Если количество товара меньше 1, то удаляем товар из корзины
                cardProductInCartList.remove(cardProductInCart);
                notifyDataSetChanged();
                onCartChangedListener.onCartChanged();
                return;
            }
            double newTotalPrice = cardProductInCart.getCardProduct().getPrice() * newAmount;
            cardProductInCart.setAmount(newAmount);
            cardProductInCart.setTotalPrice(newTotalPrice);
            holder.tvAmount.setText(String.valueOf(newAmount));
            holder.tvTotalPrice.setText(newTotalPrice + " ₽");
            onCartChangedListener.onCartChanged();
        });
    }


    @Override
    public int getItemCount() {
        return cardProductInCartList.size();
    }

    public static class CardProductInCartViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvTotalPrice, tvAmount;
        ImageView imageView;
        Button btPlus, btMinus;

        public CardProductInCartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            imageView = itemView.findViewById(R.id.ivImage);
            btPlus = itemView.findViewById(R.id.btPlus);
            btMinus = itemView.findViewById(R.id.btMinus);
        }
    }
}

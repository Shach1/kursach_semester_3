package ru.mirea.androidcoursework.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import ru.mirea.androidcoursework.databinding.ItemCardProductViewBinding;
import ru.mirea.androidcoursework.entity.CardProduct;
import ru.mirea.androidcoursework.entity.CardProductInCart;
import ru.mirea.androidcoursework.instance.ProductsInCart;

public class ItemCardProductViewFragment extends Fragment
{
    public ItemCardProductViewFragment()
    {
        // Required empty public constructor
    }
    private ItemCardProductViewBinding binding;
    private CardProduct cardProduct;
    private ProductsInCart productsInCart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ItemCardProductViewBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            ItemCardProductViewFragmentArgs args = ItemCardProductViewFragmentArgs.fromBundle(getArguments());
            cardProduct = args.getCardProduct();
        }
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvTitle.setText(cardProduct.getTitle());
        binding.tvPrice.setText(cardProduct.getPrice() + " ₽");
        binding.tvDescription.setText(cardProduct.getDescription());
        Picasso.get().load(cardProduct.getImageUrl()).into(binding.ivImage);

        productsInCart = ProductsInCart.getInstance();
        binding.btAddToCart.setOnClickListener(this::onAddToCart);
    }

    private void onAddToCart(View view)
    {
        if (!productsInCart.getProductsInCart().isEmpty())
        {
            for (CardProductInCart productInCart : productsInCart.getProductsInCart())
            {
                if (productInCart.getCardProduct().getId().equals(cardProduct.getId()))
                {
                    Toast.makeText(getContext(), "Товар уже добавлен в корзину", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            productsInCart.addProductInCart(new CardProductInCart(cardProduct));
            Toast.makeText(getContext(), "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
        }
        else
        {
            productsInCart.addProductInCart(new CardProductInCart(cardProduct));
            Toast.makeText(getContext(), "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
        }
    }
}
